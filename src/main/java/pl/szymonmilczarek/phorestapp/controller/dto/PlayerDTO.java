package pl.szymonmilczarek.phorestapp.controller.dto;

import lombok.Getter;
import lombok.Setter;
import pl.szymonmilczarek.phorestapp.dao.entity.Player;

@Getter
@Setter
public class PlayerDTO {

    private Long id;
    private Long playerMoney;

    public PlayerDTO() {
    }

    public PlayerDTO(Player player) {
        this.id = player.getId();
        this.playerMoney = player.getPlayerMoney();
    }


}
