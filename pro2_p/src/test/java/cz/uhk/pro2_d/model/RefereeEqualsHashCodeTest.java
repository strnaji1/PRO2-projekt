package cz.uhk.pro2_d.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RefereeEqualsHashCodeTest {

    @Test
    void equals_sameId_shouldBeEqual() {
        Referee r1 = new Referee();
        r1.setId(15L);
        Referee r2 = new Referee();
        r2.setId(15L);

        assertEquals(r1, r2, "Two referees with the same id should be equal");
        assertEquals(r1.hashCode(), r2.hashCode(), "Hash codes should match for equal referees");
    }

    @Test
    void equals_differentId_shouldNotBeEqual() {
        Referee r1 = new Referee();
        r1.setId(1L);
        Referee r2 = new Referee();
        r2.setId(2L);

        assertNotEquals(r1, r2, "Referees with different ids should not be equal");
    }

    @Test
    void equals_nullAndOtherType_shouldNotBeEqual() {
        Referee r = new Referee();
        r.setId(5L);

        assertNotEquals(r, null, "Referee should not be equal to null");
        assertNotEquals(r, "some string", "Referee should not be equal to an object of a different type");
    }

    @Test
    void hashCode_consistency() {
        Referee r = new Referee();
        r.setId(99L);

        int hc1 = r.hashCode();
        int hc2 = r.hashCode();
        assertEquals(hc1, hc2, "hashCode should be consistent across multiple invocations");
    }
}
