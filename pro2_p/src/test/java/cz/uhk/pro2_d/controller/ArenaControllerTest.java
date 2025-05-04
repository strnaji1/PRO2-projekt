package cz.uhk.pro2_d.controller;

import cz.uhk.pro2_d.model.Arena;
import cz.uhk.pro2_d.service.ArenaService;
import cz.uhk.pro2_d.service.MatchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ArenaControllerTest {

    private ArenaService arenaService;
    private MatchService matchService;
    private ArenaController arenaController;
    private Model model;

    @BeforeEach
    void setUp() {
        arenaService  = mock(ArenaService.class);
        matchService  = mock(MatchService.class);
        arenaController = new ArenaController(arenaService, matchService);
        model = mock(Model.class);
    }

    @Test
    void testListArenas() {
        Arena arena = new Arena();
        arena.setId(1L);
        arena.setName("Test Arena");
        arena.setCapacity(100);
        List<Arena> arenas = Collections.singletonList(arena);

        when(arenaService.getAllArenas()).thenReturn(arenas);
        when(matchService.countMatchesByArena(arena)).thenReturn(5);

        String view = arenaController.listArenas(model);

        Map<Long, Integer> expectedCount = new HashMap<>();
        expectedCount.put(1L, 5);

        verify(model).addAttribute("arenas", arenas);
        verify(model).addAttribute("arenaMatchCount", expectedCount);
        assertEquals("arenas_list", view);
    }

    @Test
    void testAddArenaForm() {
        String view = arenaController.addArenaForm(model);
        verify(model).addAttribute(eq("arena"), any(Arena.class));
        assertEquals("arenas_add", view);
    }

    @Test
    void testSaveArena_Success() {
        BindingResult result = mock(BindingResult.class);
        Arena arena = new Arena();
        when(result.hasErrors()).thenReturn(false);

        String view = arenaController.saveArena(arena, result, model);

        verify(arenaService).saveArena(arena);
        assertEquals("redirect:/arenas", view);
    }

    @Test
    void testSaveArena_Error() {
        BindingResult result = mock(BindingResult.class);
        Arena arena = new Arena();
        when(result.hasErrors()).thenReturn(true);

        String view = arenaController.saveArena(arena, result, model);

        assertEquals("arenas_add", view);
    }

    @Test
    void testConfirmDeleteArena() {
        Arena arena = new Arena();
        when(arenaService.getArenaById(1L)).thenReturn(arena);

        String view = arenaController.confirmDeleteArena(1L, model);

        verify(model).addAttribute("arena", arena);
        assertEquals("arenas_delete", view);
    }

    @Test
    void testDeleteArena() {
        String view = arenaController.deleteArena(1L);
        verify(arenaService).deleteArena(1L);
        assertEquals("redirect:/arenas", view);
    }

    @Test
    void testEditArenaForm() {
        Arena arena = new Arena();
        when(arenaService.getArenaById(1L)).thenReturn(arena);

        String view = arenaController.editArenaForm(1L, model);

        verify(model).addAttribute("arena", arena);
        assertEquals("arenas_add", view);
    }

    @Test
    void testArenaDetail() {
        Arena arena = new Arena();
        when(arenaService.getArenaById(1L)).thenReturn(arena);

        String view = arenaController.arenaDetail(1L, model);

        verify(model).addAttribute("arena", arena);
        assertEquals("arenas_detail", view);
    }
}
