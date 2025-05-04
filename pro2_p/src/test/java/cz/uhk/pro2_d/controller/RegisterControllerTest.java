package cz.uhk.pro2_d.controller;

import cz.uhk.pro2_d.model.Player;
import cz.uhk.pro2_d.service.PlayerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class RegisterControllerTest {

    private PlayerService playerService;
    private PasswordEncoder passwordEncoder;
    private RegisterController registerController;

    @BeforeEach
    void setUp() {
        playerService = mock(PlayerService.class);
        passwordEncoder = mock(PasswordEncoder.class);
        registerController = new RegisterController(playerService, passwordEncoder);
    }

    @Test
    void showRegisterFormAddsEmptyPlayer() {
        Model model = mock(Model.class);

        String view = registerController.showRegisterForm(model);

        verify(model).addAttribute(eq("player"), any(Player.class));
        assertEquals("register", view);
    }

    @Test
    void registerPlayer_WhenUsernameExists_ReturnsForm() {
        Player input = new Player();
        input.setUsername("taken");
        BindingResult result = mock(BindingResult.class);

        when(playerService.findByUsername("taken")).thenReturn(new Player());
        when(result.hasErrors()).thenReturn(true);

        String view = registerController.registerPlayer(input, result);

        verify(result).rejectValue("username", "error.player", "Uživatelské jméno již existuje.");
        assertEquals("register", view);
        verify(playerService, never()).savePlayer(any());
    }

    @Test
    void registerPlayer_Success_RedirectsToLogin() {
        Player input = new Player();
        input.setUsername("newuser");
        input.setPassword("rawpw");
        BindingResult result = mock(BindingResult.class);

        when(playerService.findByUsername("newuser")).thenReturn(null);
        when(result.hasErrors()).thenReturn(false);
        when(passwordEncoder.encode("rawpw")).thenReturn("encodedpw");

        String view = registerController.registerPlayer(input, result);

        assertEquals("redirect:/login?registered", view);
        // password was encoded and set
        assertEquals("encodedpw", input.getPassword());
        // role was set
        assertEquals("USER", input.getRole());
        verify(playerService).savePlayer(input);
    }
}
