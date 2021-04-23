package com.game.service;


import com.game.controller.PlayerOrder;
import com.game.dao.PlayerDAOImpl;
import com.game.entity.Player;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
@Service
@Transactional
public class PlayerServiceImpl implements PlayerService{

    private PlayerDAOImpl playerDAO;

    @Override
    public List<Player> getAllPlayers() {
        return  null;
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
