package cz.uhk.pro2_d.controller;

import cz.uhk.pro2_d.model.Player;
import cz.uhk.pro2_d.service.PlayerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PlayerControllerTest {

    private PlayerService playerService;
    private PlayerController playerController;

    @BeforeEach
    void setUp() {
        playerService = mock(PlayerService.class);
        playerController = new PlayerController(playerService);
    }

    @Test
    void testList() {
        Model model = mock(Model.class);
        when(playerService.getAllPlayers()).thenReturn(Collections.emptyList());

        String viewName = playerController.list(model);

        verify(model).addAttribute(eq("players"), any());
        assertEquals("players_list", viewName);
    }

    @Test
    void testDetail() {
        Model model = mock(Model.class);
        Player player = new Player();
        when(playerService.getPlayer(1L)).thenReturn(player);

        String viewName = playerController.detail(model, 1L);

        verify(model).addAttribute("player", player);
        assertEquals("players_detail", viewName);
    }

    @Test
    void testAdd() {
        Model model = mock(Model.class);

        String viewName = playerController.add(model);

        verify(model).addAttribute(eq("player"), any(Player.class));
        assertEquals("players_add", viewName);
    }

    @Test
    void testEdit() {
        Model model = mock(Model.class);
        Player player = new Player();
        when(playerService.getPlayer(1L)).thenReturn(player);

        String viewName = playerController.edit(model, 1L);

        verify(model).addAttribute("player", player);
        assertEquals("players_add", viewName);
    }

    @Test
    void testAddSave() {
        Player player = new Player();

        String viewName = playerController.addSave(player);

        verify(playerService).savePlayer(player);
        assertEquals("redirect:/players/", viewName);
    }

    @Test
    void testDelete() {
        Model model = mock(Model.class);
        Player player = new Player();
        when(playerService.getPlayer(1L)).thenReturn(player);

        String viewName = playerController.delete(model, 1L);

        verify(model).addAttribute("player", player);
        assertEquals("players_delete", viewName);
    }

    @Test
    void testDeleteConfirm() {
        String viewName = playerController.deleteConfirm(1L);

        verify(playerService).deletePlayer(1L);
        assertEquals("redirect:/players/", viewName);
    }
}
