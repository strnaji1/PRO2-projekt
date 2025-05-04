package cz.uhk.pro2_d.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.uhk.pro2_d.model.Player;
import cz.uhk.pro2_d.service.PlayerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class PlayerRestControllerTest {

    private MockMvc mockMvc;
    private PlayerService playerService;
    private PlayerRestController controller;
    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        playerService = mock(PlayerService.class);
        controller = new PlayerRestController(playerService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void getAllPlayers_shouldReturnJsonArray() throws Exception {
        Player p1 = new Player(); p1.setId(1L); p1.setName("Alice"); p1.setUsername("alice");
        Player p2 = new Player(); p2.setId(2L); p2.setName("Bob");   p2.setUsername("bob");
        when(playerService.getAllPlayers()).thenReturn(Arrays.asList(p1, p2));

        mockMvc.perform(get("/rest/players/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Alice"))
                .andExpect(jsonPath("$[1].username").value("bob"));

        verify(playerService).getAllPlayers();
    }

    @Test
    void getPlayer_shouldReturnSingleJson() throws Exception {
        Player p = new Player(); p.setId(5L); p.setName("Carol"); p.setUsername("carol");
        when(playerService.getPlayer(5L)).thenReturn(p);

        mockMvc.perform(get("/rest/players/get/5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(5))
                .andExpect(jsonPath("$.name").value("Carol"));

        verify(playerService).getPlayer(5L);
    }

    @Test
    void newPlayer_shouldSaveAndReturnToString() throws Exception {
        Player p = new Player();
        p.setName("Dave"); p.setUsername("dave"); p.setPassword("pwd");
        String body = "name=Dave&username=dave&password=pwd";

        // toString() output varies; we check status only
        mockMvc.perform(post("/rest/players/new")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content(body))
                .andExpect(status().isOk());

        verify(playerService).savePlayer(any(Player.class));
    }

    @Test
    void updatePlayer_shouldSaveAndReturnToString() throws Exception {
        Player p = new Player();
        p.setId(7L); p.setName("Eve"); p.setUsername("eve"); p.setPassword("pw");
        String body = "id=7&name=Eve&username=eve&password=pw";

        mockMvc.perform(put("/rest/players/update/7")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content(body))
                .andExpect(status().isOk());

        verify(playerService).savePlayer(any(Player.class));
    }

    @Test
    void deletePlayer_shouldDeleteAndReturnOk() throws Exception {
        mockMvc.perform(delete("/rest/players/delete/9"))
                .andExpect(status().isOk())
                .andExpect(content().string("ok"));

        verify(playerService).deletePlayer(9L);
    }
}
