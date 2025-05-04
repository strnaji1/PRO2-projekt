package cz.uhk.pro2_d.service;

import cz.uhk.pro2_d.model.Player;
import cz.uhk.pro2_d.repository.PlayerRepository;
import cz.uhk.pro2_d.security.MyPlayerDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlayerServiceImplTest {

    private PlayerRepository playerRepository;
    private PasswordEncoder passwordEncoder;
    private PlayerServiceImpl playerService;

    @BeforeEach
    void setUp() {
        playerRepository = mock(PlayerRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);
        playerService = new PlayerServiceImpl(playerRepository, passwordEncoder);
    }

    @Test
    void savePlayer_ShouldEncodePassword_WhenPasswordIsPlainText() {
        Player player = new Player();
        player.setPassword("test123");

        when(passwordEncoder.encode("test123")).thenReturn("hashed123");

        playerService.savePlayer(player);

        ArgumentCaptor<Player> captor = ArgumentCaptor.forClass(Player.class);
        verify(playerRepository).save(captor.capture());

        assertEquals("hashed123", captor.getValue().getPassword());
    }

    @Test
    void savePlayer_ShouldNotEncodePassword_WhenPasswordIsAlreadyEncoded() {
        Player player = new Player();
        player.setPassword("$2a$alreadyEncoded");

        playerService.savePlayer(player);

        verify(passwordEncoder, never()).encode(anyString());
        verify(playerRepository).save(player);
    }

    @Test
    void getPlayer_ShouldReturnPlayer_WhenExists() {
        Player mockPlayer = new Player();
        mockPlayer.setId(1);
        when(playerRepository.findById(1L)).thenReturn(Optional.of(mockPlayer));

        Player result = playerService.getPlayer(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void getPlayer_ShouldReturnNull_WhenNotFound() {
        when(playerRepository.findById(1L)).thenReturn(Optional.empty());

        Player result = playerService.getPlayer(1L);

        assertNull(result);
    }

    @Test
    void deletePlayer_ShouldCallRepository() {
        playerService.deletePlayer(1L);
        verify(playerRepository).deleteById(1L);
    }

    @Test
    void findByUsername_ShouldReturnPlayer() {
        Player player = new Player();
        when(playerRepository.findByUsername("john")).thenReturn(player);

        Player result = playerService.findByUsername("john");

        assertEquals(player, result);
    }

    @Test
    void loadUserByUsername_ShouldReturnUserDetails_WhenPlayerExists() {
        Player player = new Player();
        player.setUsername("john");

        when(playerRepository.findByUsername("john")).thenReturn(player);

        UserDetails details = playerService.loadUserByUsername("john");

        assertNotNull(details);
        assertTrue(details instanceof MyPlayerDetails);
    }

    @Test
    void loadUserByUsername_ShouldThrow_WhenPlayerNotFound() {
        when(playerRepository.findByUsername("john")).thenReturn(null);

        assertThrows(UsernameNotFoundException.class, () -> {
            playerService.loadUserByUsername("john");
        });
    }
}