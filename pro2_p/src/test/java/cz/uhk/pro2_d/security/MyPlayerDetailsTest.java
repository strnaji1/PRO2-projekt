package cz.uhk.pro2_d.security;

import cz.uhk.pro2_d.model.Player;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class MyPlayerDetailsTest {

    @Test
    void getUsername_returnsPlayerUsername() {
        Player player = new Player();
        player.setUsername("testuser");
        MyPlayerDetails details = new MyPlayerDetails(player);

        assertEquals("testuser", details.getUsername());
    }

    @Test
    void getPassword_returnsPlayerPassword() {
        Player player = new Player();
        player.setPassword("secret");
        MyPlayerDetails details = new MyPlayerDetails(player);

        assertEquals("secret", details.getPassword());
    }

    @Test
    void getAuthorities_containsRoleWithPrefix() {
        Player player = new Player();
        player.setRole("ADMIN");
        MyPlayerDetails details = new MyPlayerDetails(player);

        Collection<? extends GrantedAuthority> auths = details.getAuthorities();
        assertEquals(1, auths.size(), "Should have exactly one authority");
        GrantedAuthority ga = auths.iterator().next();
        assertEquals("ROLE_ADMIN", ga.getAuthority());
    }

    @Test
    void accountStatusFlags_allReturnTrue() {
        Player player = new Player();
        MyPlayerDetails details = new MyPlayerDetails(player);

        assertTrue(details.isAccountNonExpired(), "Account should be non-expired");
        assertTrue(details.isAccountNonLocked(), "Account should be non-locked");
        assertTrue(details.isCredentialsNonExpired(), "Credentials should be non-expired");
        assertTrue(details.isEnabled(), "Account should be enabled");
    }
}
