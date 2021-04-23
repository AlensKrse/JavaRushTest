package com.game.dao;

import com.game.controller.PlayerOrder;
import com.game.entity.Player;

import java.util.List;

public interface PlayerDAO {
    /*
1. получать список всех зарегистрированных игроков;
2. создавать нового игрока;
3. редактировать характеристики существующего игрока;
4. удалять игрока;
5. получать игрока по id;
6. получать отфильтрованный список игроков в соответствии с переданными фильтрами;
7. получать количество игроков, которые соответствуют фильтрам.
 */
    List<Player> getAllPlayers();

    void savePlayer(Player player);

    Player updatePlayer(Player oldPlayer, Player newPlayer);

    void deletePlayer(Player player);

    Player getPlayer(int id);

    List<Player> sortPlayers(List<Player> players, PlayerOrder order);

    int countOfSortedPlayers(List<Player> SortedPlayers);
}
