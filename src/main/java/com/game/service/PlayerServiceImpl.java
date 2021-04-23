package com.game.service;


import com.game.controller.PlayerOrder;
import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
@Service
@Transactional
public class PlayerServiceImpl implements PlayerService{



    @Override
    public Player savePlayer(Player player) {
return player;
    }

    @Override
    public Player updatePlayer(Player oldPlayer, Player newPlayer) {
        return null;
    }

    @Override
    public void deletePlayer(Player player) {

    }

    @Override
    public List<Player> getPlayers(String name, String title, Race race, Profession profession, Long after, Long before,
                                   Boolean banned, Integer minExperience, Integer maxExperience, Integer minLevel, Integer maxLevel) {
        return null;
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
    public List<Player> getPage(List<Player> players, Integer pageNumber, Integer pageSize) {
        return null;
    }


    @Override
    public boolean isPlayerValid(Player player) {
        return false;
    }

    @Override
    public double computeRating(double speed, boolean isUsed, Date prod) {
        return 0;
    }
}
