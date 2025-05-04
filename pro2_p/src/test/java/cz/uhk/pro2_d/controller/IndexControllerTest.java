package cz.uhk.pro2_d.controller;

import cz.uhk.pro2_d.service.ArenaService;
import cz.uhk.pro2_d.service.MatchService;
import cz.uhk.pro2_d.service.RefereeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class IndexControllerTest {

    private MatchService matchService;
    private RefereeService refereeService;
    private ArenaService arenaService;
    private IndexController indexController;

    @BeforeEach
    void setUp() {
        matchService = mock(MatchService.class);
        refereeService = mock(RefereeService.class);
        arenaService = mock(ArenaService.class);
        indexController = new IndexController(matchService, refereeService, arenaService);
    }

    @Test
    void testIndex_PopulatesCountsAndReturnsIndexView() {
        Model model = mock(Model.class);

        when(matchService.getAllMatches()).thenReturn(Collections.nCopies(3, null));
        when(refereeService.getAllReferees()).thenReturn(Collections.nCopies(2, null));
        when(arenaService.getAllArenas()).thenReturn(Collections.nCopies(5, null));

        String view = indexController.index(model);

        verify(model).addAttribute("matchCount", 3);
        verify(model).addAttribute("refereeCount", 2);
        verify(model).addAttribute("arenaCount", 5);
        assertEquals("index", view);
    }

    @Test
    void testTestEndpoint_ReturnsTestString() {
        String body = indexController.test();
        assertEquals("test", body);
    }

    @Test
    void testAdmin_ReturnsAdminView() {
        assertEquals("admin", indexController.admin());
    }

    @Test
    void testLogin_ReturnsLoginView() {
        assertEquals("login", indexController.login());
    }

    @Test
    void testForbidden_Returns403View() {
        assertEquals("403", indexController.forbidden());
    }
}
