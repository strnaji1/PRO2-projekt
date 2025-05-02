package cz.uhk.pro2_d.service;

import cz.uhk.pro2_d.model.Match;
import cz.uhk.pro2_d.model.Arena;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MatchService {
    List<Match> getAllMatches();
    void saveMatch(Match match);
    Match getMatch(long id);
    void deleteMatch(long id);

    int countMatchesByArena(Arena arena);
}
