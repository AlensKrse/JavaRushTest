package com.game.service;

import com.game.controller.PlayerOrder;
import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;

import java.util.Date;
import java.util.List;

public interface PlayerService {
    /*
    1. получать список всех зарегистрированных игроков;
2. создавать нового игрока;
3. редактировать характеристики существующего игрока;
4. удалять игрока;
5. получать игрока по id;
6. получать отфильтрованный список игроков в соответствии с переданными фильтрами;
7. получать количество игроков, которые соответствуют фильтрам.
     */

    Player savePlayer(Player player);

    Player getPlayer(int id);

    Player updatePlayer(Player oldPlayer, Player newPlayer) throws IllegalArgumentException;

    void deletePlayer(Player player);

    List<Player> getPlayers(String name,
                            String title,
                            Race race,
                            Profession profession,
                            Long after,
                            Long before,
                            Boolean banned,
                            Integer minExperience,
                            Integer maxExperience,
                            Integer minLevel,
                            Integer maxLevel);

    List<Player> sortPlayers(List<Player> players, PlayerOrder order);

    List<Player> getPage(List<Player> players, Integer pageNumber, Integer pageSize);

    boolean isPlayerValid(Player player);

    double computeRating(double speed, boolean isUsed, Date prod);


}
