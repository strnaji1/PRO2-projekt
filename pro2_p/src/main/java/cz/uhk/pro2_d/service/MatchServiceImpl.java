package cz.uhk.pro2_d.service;

import cz.uhk.pro2_d.model.Match;
import cz.uhk.pro2_d.model.Arena;
import cz.uhk.pro2_d.repository.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatchServiceImpl implements MatchService {

    private final MatchRepository matchRepository;

    @Autowired
    public MatchServiceImpl(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    @Override
    public List<Match> getAllMatches() {
        return matchRepository.findAll();
    }

    @Override
    public void saveMatch(Match match) {
        matchRepository.save(match);
    }

    @Override
    public Match getMatch(long id) {
        return matchRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteMatch(long id) {
        matchRepository.deleteById(id);
    }

    @Override
    public int countMatchesByArena(Arena arena) {
        return matchRepository.findAllByArena(arena).size();
    }
}
