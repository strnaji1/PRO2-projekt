package cz.uhk.pro2_d.repository;

import cz.uhk.pro2_d.model.Referee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class RefereeRepositoryTest {

    @Autowired
    private RefereeRepository refereeRepository;

    @Test
    void saveAndFindById() {
        Referee r = new Referee();
        r.setName("Ref");
        Referee saved = refereeRepository.save(r);

        Optional<Referee> loaded = refereeRepository.findById(saved.getId());
        assertThat(loaded).isPresent();
        assertThat(loaded.get().getName()).isEqualTo("Ref");
    }
}
