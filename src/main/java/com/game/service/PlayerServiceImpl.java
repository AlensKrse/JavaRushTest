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


    //Ready
    @Override
    public Player savePlayer(Player player) {
        return playerRepository.save(player);
    }


    //Ready
    @Override
    public Player updatePlayer(Player oldPlayer, Player newPlayer) throws IllegalArgumentException{
        boolean shouldChangeLevel = false;

        final String name = newPlayer.getName();
        if (name != null) {
            if (isNameValid(name)) {
                oldPlayer.setName(name);
            } else {
                throw new IllegalArgumentException();
            }
        }

        final String title = newPlayer.getTitle();
        if (title != null) {
            if (isTitleValid(title)) {
                oldPlayer.setTitle(title);
            } else {
                throw new IllegalArgumentException();
            }
        }

        if (newPlayer.getRace() != null) {
            oldPlayer.setRace(newPlayer.getRace());
        }

        if (newPlayer.getProfession() != null) {
            oldPlayer.setProfession(newPlayer.getProfession());
        }

        final Date birthday = newPlayer.getBirthday();
        if (birthday != null) {
            if (isProdDateValid(birthday)) {
                oldPlayer.setBirthday(birthday);
            } else {
                throw new IllegalArgumentException();
            }
        }
        if (newPlayer.getBanned() != null) {
            oldPlayer.setBanned(newPlayer.getBanned());
        }

        final Integer exp = newPlayer.getExperience();
        if (exp != null) {
            if (isExpValid(exp)) {
                oldPlayer.setExperience(exp);
                shouldChangeLevel = true;
            } else {
                throw new IllegalArgumentException();
            }
        }

        final Integer level = newPlayer.getLevel();
        if (level != null) {
                oldPlayer.setLevel(level);
            shouldChangeLevel = true;
            }else {
                throw new IllegalArgumentException();
            }

        final Integer untilNextLevel = newPlayer.getUntilNextLevel();
        if (untilNextLevel != null) {
            oldPlayer.setUntilNextLevel(untilNextLevel);
        }
        else {
                throw new IllegalArgumentException();
            }

        if (shouldChangeLevel) {
            final Integer level1 = computeLevel(oldPlayer.getExperience());
            oldPlayer.setLevel(level1);
            final Integer untilNextLevel1 = untilNextLevel(oldPlayer.getLevel(), oldPlayer.getExperience());
            oldPlayer.setUntilNextLevel(untilNextLevel1);
        }
        playerRepository.save(oldPlayer);
        return oldPlayer;
    }



    //Ready
    @Override
    public void deletePlayer(Player player) {
    playerRepository.delete(player);
    }


    //Ready
    @Override
    public List<Player> getPlayers(String name, String title, Race race, Profession profession, Long after, Long before,
                                   Boolean banned, Integer minExperience, Integer maxExperience, Integer minLevel, Integer maxLevel) {
        final Date afterDate = after == null ? null : new Date(after);
        final Date beforeDate = before == null ? null : new Date(before);
        final List<Player> list = new ArrayList<>();
        playerRepository.findAll().forEach((player) -> {
            if (name != null && !player.getName().contains(name)) return;
            if (title != null && !player.getTitle().contains(title)) return;
            if (race != null && player.getRace() != race) return;
            if (profession != null && !player.getProfession().equals(profession)) return;
            if (afterDate != null && player.getBirthday().before(afterDate)) return;
            if (beforeDate != null && player.getBirthday().after(beforeDate)) return;
            if (banned != null && player.getBanned().booleanValue() != banned.booleanValue()) return;
            if (minExperience != null && player.getExperience().compareTo(minExperience) < 0) return;
            if (maxExperience != null && player.getExperience().compareTo(maxExperience) > 0) return;
            if (minLevel != null && player.getLevel().compareTo(minLevel) < 0) return;
            if (maxLevel != null && player.getLevel().compareTo(maxLevel) > 0) return;

            list.add(player);
        });
        return list;
    }



    //Ready
    @Override
    public Player getPlayer(Long id) {
        return playerRepository.findById(id).orElse(null);
    }


    //Ready
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


    //Ready
    @Override
    public List<Player> getPage(List<Player> players, Integer pageNumber, Integer pageSize) {
        final Integer page = pageNumber == null ? 0 : pageNumber;
        final Integer size = pageSize == null ? 3 : pageSize;
        final int from = page * size;
        int to = from + size;
        if (to > players.size()) to = players.size();
        return players.subList(from, to);
    }

    //Ready
    @Override
    public boolean isPlayerValid(Player player) {
        return player != null && isNameValid(player.getName()) && isTitleValid(player.getTitle())
                && isProdDateValid(player.getBirthday())
                && player.getBirthday().getTime()>0
                && isExpValid(player.getExperience());
    }



    //Ready
    @Override
    public Integer computeLevel(Integer experience) {
         double result = Math.sqrt(2500 + (200 * experience) - 50)/100;
         return (int) result;
    }

    //Ready
    @Override
    public Integer untilNextLevel(Integer level, Integer experience) {
       double result = 50 * (level+1) * (level+2) - experience;
       return (int) result;
    }


    //Help methods
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

    private Date getDateForYear(int year) {
        final Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        return calendar.getTime();
    }



    private double round(double value) {
        return Math.round(value * 100) / 100D;
    }

}
