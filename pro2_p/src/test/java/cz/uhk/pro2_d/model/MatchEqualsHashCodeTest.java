package cz.uhk.pro2_d.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MatchEqualsHashCodeTest {


    @Test
    void equals_differentId_shouldNotBeEqual() {
        Match m1 = new Match();
        m1.setId(1L);
        Match m2 = new Match();
        m2.setId(2L);

        assertNotEquals(m1, m2, "Matches with different ids should not be equal");
    }

    @Test
    void equals_nullAndOtherType_shouldNotBeEqual() {
        Match m = new Match();
        m.setId(5L);

        assertNotEquals(m, null, "Match should not be equal to null");
        assertNotEquals(m, "some string", "Match should not be equal to an object of a different type");
    }

    @Test
    void hashCode_consistency() {
        Match m = new Match();
        m.setId(55L);

        int hc1 = m.hashCode();
        int hc2 = m.hashCode();
        assertEquals(hc1, hc2, "hashCode should be consistent across multiple invocations");
    }
}
