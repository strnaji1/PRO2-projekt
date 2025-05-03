package cz.uhk.pro2_d.service;

import cz.uhk.pro2_d.model.Arena;
import cz.uhk.pro2_d.model.Match;
import cz.uhk.pro2_d.model.Player;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MatchService {
    List<Match> getAllMatches();
    void saveMatch(Match match);
    Match getMatch(long id);
    void deleteMatch(long id);
    int countMatchesByArena(Arena arena);

    void addPlayerToMatch(Match match, Player player);
    void removePlayerFromMatch(Match match, Player player);
    boolean isPlayerInMatch(Match match, Player player);
}
