package pl.szymonmilczarek.phorestapp.controller.dto;

import lombok.Getter;
import lombok.Setter;
import pl.szymonmilczarek.phorestapp.dao.entity.Player;
import pl.szymonmilczarek.phorestapp.model.GameContext;

import java.util.List;

@Getter
@Setter
public class GameResultDTO {

    private PlayerDTO playerDTO;
    private List<String> result;
    private Boolean win;

    public GameResultDTO(GameContext context) {
        this.result = context.getResult();
        this.win = context.getWin();
        this.playerDTO = new PlayerDTO(context.getPlayer());
    }
}
