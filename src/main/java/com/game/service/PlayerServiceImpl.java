package com.game.service;


import com.game.controller.PlayerOrder;
import com.game.controller.PlayerOrderId;
import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.repository.PlayerRepository;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


@Service
@Transactional
public class PlayerServiceImpl implements PlayerService{

    private PlayerRepository playerRepository;
    private PlayerOrderId PlayerOrderId;

    public PlayerServiceImpl() {}

    public PlayerServiceImpl(PlayerRepository playerRepository) {
        super();
        this.playerRepository = playerRepository;
    }

    @Override
    public Player savePlayer(Player player) {
        return playerRepository.save(player);
    }

    @Override
    public Player updatePlayer(Player oldPlayer, Player newPlayer) {
        return null;
    }

    @Override
    public void deletePlayer(Player player) {
    playerRepository.delete(player);
    }

    @Override
    public List<Player> getPlayers(String name, String title, Race race, Profession profession, Long after, Long before,
                                   Boolean banned, Integer minExperience, Integer maxExperience, Integer minLevel, Integer maxLevel) {
        final Date afterDate = after == null ? null : new Date(after);
        final Date beforeDate = before == null ? null : new Date(before);
        final List<Player> list = new ArrayList<>();
        playerRepository.findAll().forEach((ship) -> {
            if (name != null && !ship.getName().contains(name)) return;
            if (title != null && !ship.getTitle().contains(title)) return;
            if (race != null && ship.getRace() != race) return;
            if (profession != null && !ship.getProfession().equals(profession)) return;
            if (afterDate != null && ship.getBirthday().before(afterDate)) return;
            if (beforeDate != null && ship.getBirthday().after(beforeDate)) return;
            if (banned != null && ship.getBanned().booleanValue() != banned.booleanValue()) return;
            if (minExperience != null && ship.getExperience().compareTo(minExperience) < 0) return;
            if (maxExperience != null && ship.getExperience().compareTo(maxExperience) > 0) return;
            if (minLevel != null && ship.getLevel().compareTo(minLevel) < 0) return;
            if (maxLevel != null && ship.getLevel().compareTo(maxLevel) > 0) return;

            list.add(ship);
        });
        return list;
    }

    @Override
    public Player getPlayer(Long id) {
        return playerRepository.findById(id).orElse(null);
    }

    @Override
    public List<Player> sortPlayers(List<Player> players, PlayerOrder order) {
        if (order != null) {
            players.sort((player1, player2) -> {
                switch (order) {
                    case ID: return player1.getId().compareTo(player2.getId());
                    case NAME: return player1.getName().compareTo(player2.getName());
                    case EXPERIENCE: return player1.getExperience().compareTo(player2.getExperience());
                    case BIRTHDAY: return player1.getBirthday().compareTo(player2.getBirthday());
                    case LEVEL: return player1.getLevel().compareTo(player2.getLevel());
                    default: return 0;
                }
            });
        }
        else {
            players.sort((player1, player2) -> {
                switch (PlayerOrderId) {
                    case ID: return player1.getId().compareTo(player2.getId());
                    default: return 0;
                }
            });
        }
        return players;
    }

    @Override
    public List<Player> getPage(List<Player> players, Integer pageNumber, Integer pageSize) {
        final Integer page = pageNumber == null ? 0 : pageNumber;
        final Integer size = pageSize == null ? 3 : pageSize;
        final int from = page * size;
        int to = from + size;
        if (to > players.size()) to = players.size();
        return players.subList(from, to);
    }


    @Override
    public boolean isPlayerValid(Player player) {
        return player != null && isNameValid(player.getName()) && isTitleValid(player.getTitle())
                && isProdDateValid(player.getBirthday())
                && player.getBirthday().getTime()>0
                && isExpValid(player.getExperience());
    }
    private boolean isNameValid(String value) {
        final int maxStringLength = 12;
        return value != null && !value.isEmpty() && value.length() <= maxStringLength;
    }
    private boolean isTitleValid(String value) {
        final int maxStringLength = 30;
        return value != null && !value.isEmpty() && value.length() <= maxStringLength;
    }
    private boolean isProdDateValid(Date birthday) {
        final Date startBirth = getDateForYear(2000);
        final Date endBirth = getDateForYear(3000);
        return birthday != null && birthday.after(startBirth) && birthday.before(endBirth);
    }

    private boolean isExpValid(Integer expSize) {
        final int minCrewSize = 0;
        final int maxCrewSize = 10_000_000;
        return expSize != null && expSize.compareTo(minCrewSize) >= 0 && expSize.compareTo(maxCrewSize) <= 0;
    }

    @Override
    public double computeLevel(Integer experience) {
        return 0;
    }

    @Override
    public double untilNextLevel(Integer level, Integer experience) {
        return 0;
    }

    private Date getDateForYear(int year) {
        final Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        return calendar.getTime();
    }

    private int getYearFromDate(Date date) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

}
