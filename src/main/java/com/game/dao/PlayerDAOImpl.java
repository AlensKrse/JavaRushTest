package com.game.dao;

import com.game.controller.PlayerOrder;
import com.game.entity.Player;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
@Repository
public class PlayerDAOImpl implements PlayerDAO{

    private SessionFactory sessionFactory;
    @Override
    public List<Player> getAllPlayers() {
        return null;
    }

    @Override
    public void savePlayer(Player player) {

    }

    @Override
    public Player updatePlayer(Player oldPlayer, Player newPlayer) {
        return null;
    }

    @Override
    public void deletePlayer(Player player) {

    }

    @Override
    public Player getPlayer(int id) {
        return null;
    }

    @Override
    public List<Player> sortPlayers(List<Player> players, PlayerOrder order) {
        return null;
    }

    @Override
    public int countOfSortedPlayers(List<Player> SortedPlayers) {
        return 0;
    }
}
