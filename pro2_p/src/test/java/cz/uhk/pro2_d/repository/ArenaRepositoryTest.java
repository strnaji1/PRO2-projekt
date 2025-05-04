package cz.uhk.pro2_d.repository;

import cz.uhk.pro2_d.model.Arena;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ArenaRepositoryTest {

    @Autowired
    private ArenaRepository arenaRepository;

    @Test
    void saveAndFindById() {
        Arena a = new Arena();
        a.setName("TestArena");
        a.setCapacity(20);
        Arena saved = arenaRepository.save(a);

        Optional<Arena> loaded = arenaRepository.findById(saved.getId());
        assertThat(loaded).isPresent();
        assertThat(loaded.get().getName()).isEqualTo("TestArena");
    }
}
