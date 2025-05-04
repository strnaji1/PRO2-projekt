package cz.uhk.pro2_d.service;

import cz.uhk.pro2_d.model.Arena;
import cz.uhk.pro2_d.model.Match;
import cz.uhk.pro2_d.model.Player;
import cz.uhk.pro2_d.repository.MatchRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MatchServiceImplTest {

    private MatchRepository matchRepository;
    private MatchServiceImpl matchService;

    @BeforeEach
    void setUp() {
        matchRepository = mock(MatchRepository.class);
        matchService = new MatchServiceImpl(matchRepository);
    }

    @Test
    void getAllMatches_ShouldReturnMatchList() {
        List<Match> matches = List.of(new Match(), new Match());
        when(matchRepository.findAll()).thenReturn(matches);

        List<Match> result = matchService.getAllMatches();

        assertEquals(2, result.size());
    }

    @Test
    void saveMatch_ShouldCallRepositorySave() {
        Match match = new Match();

        matchService.saveMatch(match);

        verify(matchRepository).save(match);
    }

    @Test
    void getMatch_ShouldReturnMatch_WhenFound() {
        Match match = new Match();
        when(matchRepository.findById(1L)).thenReturn(Optional.of(match));

        Match result = matchService.getMatch(1L);

        assertNotNull(result);
        assertEquals(match, result);
    }

    @Test
    void getMatch_ShouldReturnNull_WhenNotFound() {
        when(matchRepository.findById(1L)).thenReturn(Optional.empty());

        Match result = matchService.getMatch(1L);

        assertNull(result);
    }

    @Test
    void deleteMatch_ShouldCallDeleteById() {
        matchService.deleteMatch(1L);

        verify(matchRepository).deleteById(1L);
    }

    @Test
    void countMatchesByArena_ShouldReturnCorrectCount() {
        Arena arena = new Arena();
        when(matchRepository.findAllByArena(arena)).thenReturn(List.of(new Match(), new Match(), new Match()));

        int count = matchService.countMatchesByArena(arena);

        assertEquals(3, count);
    }

    @Test
    void addPlayerToMatch_ShouldAddPlayer_WhenCapacityAllows() {
        Arena arena = new Arena();
        arena.setCapacity(2);

        Match match = new Match();
        match.setArena(arena);
        match.setPlayers(new HashSet<>(Set.of()));  // 0 players

        Player player = new Player();

        matchService.addPlayerToMatch(match, player);

        assertTrue(match.getPlayers().contains(player));
        verify(matchRepository).save(match);
    }

    @Test
    void addPlayerToMatch_ShouldNotAdd_WhenFull() {
        Arena arena = new Arena();
        arena.setCapacity(1);

        Player existing = new Player();
        Match match = new Match();
        match.setArena(arena);
        match.setPlayers(new HashSet<>(Set.of(existing))); // already full

        Player newPlayer = new Player();

        matchService.addPlayerToMatch(match, newPlayer);

        assertFalse(match.getPlayers().contains(newPlayer));
        verify(matchRepository, never()).save(match);
    }

    @Test
    void removePlayerFromMatch_ShouldRemovePlayer() {
        Player player = new Player();
        Match match = new Match();
        match.setPlayers(new HashSet<>(Set.of(player)));

        matchService.removePlayerFromMatch(match, player);

        assertFalse(match.getPlayers().contains(player));
        verify(matchRepository).save(match);
    }

    @Test
    void isPlayerInMatch_ShouldReturnTrue_IfInMatch() {
        Player player = new Player();
        Match match = new Match();
        match.setPlayers(Set.of(player));

        assertTrue(matchService.isPlayerInMatch(match, player));
    }

    @Test
    void isPlayerInMatch_ShouldReturnFalse_IfNotInMatch() {
        Player player = new Player();
        Match match = new Match();
        match.setPlayers(new HashSet<>());

        assertFalse(matchService.isPlayerInMatch(match, player));
    }
}
