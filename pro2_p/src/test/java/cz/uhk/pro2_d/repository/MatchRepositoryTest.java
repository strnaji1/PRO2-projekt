package cz.uhk.pro2_d.repository;

import cz.uhk.pro2_d.model.Arena;
import cz.uhk.pro2_d.model.Match;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class MatchRepositoryTest {

    @Autowired
    private MatchRepository matchRepository;
    @Autowired
    private ArenaRepository arenaRepository;

    @Test
    void findAllByArena() {
        Arena a = new Arena();
        a.setName("Arena1");
        a.setCapacity(50);
        arenaRepository.save(a);

        Match m1 = new Match();
        m1.setName("M1");
        m1.setArena(a);
        m1.setParticipants(10);
        matchRepository.save(m1);

        Match m2 = new Match();
        m2.setName("M2");
        m2.setArena(a);
        m2.setParticipants(8);
        matchRepository.save(m2);

        List<Match> byArena = matchRepository.findAllByArena(a);
        assertThat(byArena).hasSize(2)
                .extracting(Match::getName)
                .containsExactlyInAnyOrder("M1","M2");
    }
}
