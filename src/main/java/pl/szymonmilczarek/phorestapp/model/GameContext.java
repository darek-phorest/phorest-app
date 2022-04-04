package pl.szymonmilczarek.phorestapp.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import pl.szymonmilczarek.phorestapp.dao.entity.Machine;
import pl.szymonmilczarek.phorestapp.dao.entity.Player;
import pl.szymonmilczarek.phorestapp.utils.ResultGenerator;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class GameContext {

    public static final Long DEFAULT_PLAY_VALUE = 1L;

    private ResultGenerator generator = new ResultGenerator();

    private Player player;
    private Machine machine;
    private Long playerAvailableMoney = 0L;
    private Long possibleWin = 0L;
    private List<String> result = new ArrayList<>();
    private Boolean win;


    public GameContext(Player player, Machine machine) {
        this.player = player;
        this.machine = machine;
    }

    public GameContext playOneGame() {
        this.playerAvailableMoney = player.getPlayerMoney() - DEFAULT_PLAY_VALUE;
        this.possibleWin = machine.getMachineMoney() + DEFAULT_PLAY_VALUE;
        this.result = generator.getResult();
        this.win = this.getResult().stream().distinct().count() == 1;
        return this;
    }

    public void clearContext() {
        this.playerAvailableMoney = 0L;
        this.possibleWin = 0L;
        this.win = false;
        this.result.clear();
    }



}
