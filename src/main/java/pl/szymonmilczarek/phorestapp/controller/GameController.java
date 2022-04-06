package pl.szymonmilczarek.phorestapp.controller;

import org.springframework.web.bind.annotation.*;
import pl.szymonmilczarek.phorestapp.controller.dto.GameResultDTO;
import pl.szymonmilczarek.phorestapp.controller.dto.PlayerDTO;
import pl.szymonmilczarek.phorestapp.service.GameService;

@RestController
@RequestMapping("/api")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping("/start")
    public PlayerDTO startGame(
            @RequestParam(name = "playerId") Long playerId,
            @RequestParam(name = "money") Long money
    ) {
        return gameService.startGame(playerId, money); // ❓ how does the client create a Player in the system ?
    }

    @PostMapping("/play")
    public GameResultDTO playGame(
            @RequestParam(name = "playerId") Long playerId,
            @RequestParam(name = "machineId") Long machineId
    ) {
        return gameService.playGame(playerId, machineId);
    }

    @PostMapping("/withdraw-money")
    public Long withdrawMoney(
            @RequestParam(name = "playerId") Long playerId
    ) {
        return gameService.withdrawMoney(playerId);
    }
}
