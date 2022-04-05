package pl.szymonmilczarek.phorestapp.service;

import org.springframework.stereotype.Service;
import pl.szymonmilczarek.phorestapp.controller.dto.GameResultDTO;
import pl.szymonmilczarek.phorestapp.controller.dto.PlayerDTO;
import pl.szymonmilczarek.phorestapp.dao.entity.Machine;
import pl.szymonmilczarek.phorestapp.dao.entity.Player;
import pl.szymonmilczarek.phorestapp.dao.repository.MachineRepository;
import pl.szymonmilczarek.phorestapp.dao.repository.PlayerRepository;
import pl.szymonmilczarek.phorestapp.exceptions.EntityDoesNotExistException;
import pl.szymonmilczarek.phorestapp.exceptions.NotEnoughMoneyException;
import pl.szymonmilczarek.phorestapp.model.GameContext;

import javax.transaction.Transactional;

@Service
public class GameService {

    private GameContext gameContext;

    private final MachineRepository machineRepository;
    private final PlayerRepository playerRepository;

    public GameService(
            MachineRepository machineRepository,
            PlayerRepository playerRepository) {
        this.machineRepository = machineRepository;
        this.playerRepository = playerRepository;
    }

    public PlayerDTO startGame(Long playerId, Long money) {
        var player = getPlayer(playerId);
        player.setPlayerMoney(player.getPlayerMoney() + money);
        var updatedPlayer = playerRepository.save(player);
        return new PlayerDTO(updatedPlayer);
    }

    public GameResultDTO playGame(Long playerId, Long machineId) {
        var player = getPlayer(playerId);
        var machine = getMachine(machineId);
        var ctx = playGame(player, machine);
        updateStateAfterGame();
        return new GameResultDTO(ctx);
    }

    public Long withdrawMoney(Long playerId) {
        var player = getPlayer(playerId);
        var money = player.getPlayerMoney();
        player.setPlayerMoney(0L);
        playerRepository.save(player);
        return money;
    }

    public Player getPlayer(Long id) {
        return playerRepository.findById(id)
                .orElseThrow(() -> new EntityDoesNotExistException(String.valueOf(id)));
    }

    private Machine getMachine(Long id) {
        return machineRepository.findById(id)
                .orElseThrow(() -> new EntityDoesNotExistException(String.valueOf(id)));
    }

    private GameContext playGame(Player player, Machine machine) {
        var availableMoney = player.getPlayerMoney();
        if (availableMoney > 0) {
            gameContext = new GameContext(player, machine).playOneGame();
            return gameContext;
        } else {
            throw new NotEnoughMoneyException(String.valueOf(player.getId()));
        }
    }

    @Transactional
    public void updateStateAfterGame() {
        var player = gameContext.getPlayer();
        var machine = gameContext.getMachine();
        player.setPlayerMoney(
                gameContext.getWin()
                        ? gameContext.getPossibleWin() + gameContext.getPlayerAvailableMoney()
                        : gameContext.getPlayerAvailableMoney());
        machine.setMachineMoney(
                gameContext.getWin()
                        ? 0L
                        : gameContext.getPossibleWin());
        playerRepository.save(player);
        machineRepository.save(machine);
    }
}
