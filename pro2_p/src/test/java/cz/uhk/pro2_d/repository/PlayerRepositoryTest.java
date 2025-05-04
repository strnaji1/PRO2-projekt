package cz.uhk.pro2_d.repository;

import cz.uhk.pro2_d.model.Player;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
class PlayerRepositoryTest {

    @Autowired
    private PlayerRepository playerRepository;

    @Test
    void saveAndFindByUsername() {
        Player p = new Player();
        p.setName("Test");
        p.setUsername("tester");
        p.setPassword("pw");
        playerRepository.save(p);

        Player found = playerRepository.findByUsername("tester");
        assertThat(found).isNotNull();
        assertThat(found.getName()).isEqualTo("Test");
    }

    @Test
    void uniqueUsernameConstraint() {
        Player p1 = new Player();
        p1.setName("A");
        p1.setUsername("dup");
        p1.setPassword("x");
        playerRepository.save(p1);

        Player p2 = new Player();
        p2.setName("B");
        p2.setUsername("dup");
        p2.setPassword("y");

        assertThrows(DataIntegrityViolationException.class, () -> {
            playerRepository.saveAndFlush(p2);
        });
    }
}
