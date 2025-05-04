package cz.uhk.pro2_d.controller;

import cz.uhk.pro2_d.model.Match;
import cz.uhk.pro2_d.model.Player;
import cz.uhk.pro2_d.service.ArenaService;
import cz.uhk.pro2_d.service.MatchService;
import cz.uhk.pro2_d.service.PlayerService;
import cz.uhk.pro2_d.service.RefereeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import org.springframework.security.core.Authentication;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class MatchControllerTest {

    private MatchService matchService;
    private RefereeService refereeService;
    private ArenaService arenaService;
    private PlayerService playerService;
    private MatchController matchController;

    @BeforeEach
    public void setUp() {
        matchService = mock(MatchService.class);
        refereeService = mock(RefereeService.class);
        arenaService = mock(ArenaService.class);
        playerService = mock(PlayerService.class);
        matchController = new MatchController(matchService, refereeService, arenaService, playerService);
    }

    @Test
    public void testList_ReturnsMatchListTemplate() {
        Model model = mock(Model.class);
        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn("testuser");
        when(playerService.findByUsername("testuser")).thenReturn(new Player());

        String viewName = matchController.list(model, auth);

        assertEquals("matches_list", viewName);
        verify(model).addAttribute(eq("matches"), any());
        verify(model).addAttribute(eq("currentPlayer"), any());
    }

    @Test
    public void testDetail_ReturnsMatchDetailTemplate() {
        Model model = mock(Model.class);
        Authentication auth = mock(Authentication.class);
        Match match = new Match();
        Player player = new Player();
        match.setPlayers(Collections.singleton(player));
        match.setArena(new cz.uhk.pro2_d.model.Arena() {{ setCapacity(10); }});

        when(matchService.getMatch(1L)).thenReturn(match);
        when(auth.getName()).thenReturn("testuser");
        when(playerService.findByUsername("testuser")).thenReturn(player);

        String viewName = matchController.detail(model, 1L, auth);

        assertEquals("matches_detail", viewName);
        verify(model).addAttribute(eq("match"), eq(match));
        verify(model).addAttribute(eq("currentPlayer"), eq(player));
    }
}
