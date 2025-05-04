package cz.uhk.pro2_d.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArenaEqualsHashCodeTest {

    @Test
    void equals_sameId_shouldBeEqual() {
        Arena a1 = new Arena();
        a1.setId(42L);
        Arena a2 = new Arena();
        a2.setId(42L);

        assertEquals(a1, a2, "Two arenas with same id should be equal");
        assertEquals(a1.hashCode(), a2.hashCode(), "Hash codes should match for equal arenas");
    }

    @Test
    void equals_differentId_shouldNotBeEqual() {
        Arena a1 = new Arena();
        a1.setId(1L);
        Arena a2 = new Arena();
        a2.setId(2L);

        assertNotEquals(a1, a2, "Arenas with different ids should not be equal");
    }

    @Test
    void equals_nullAndOtherType_shouldNotBeEqual() {
        Arena a = new Arena();
        a.setId(5L);

        assertNotEquals(a, null, "Arena should not be equal to null");
        assertNotEquals(a, "some string", "Arena should not be equal to an object of different type");
    }

    @Test
    void hashCode_consistency() {
        Arena a = new Arena();
        a.setId(99L);

        int hc1 = a.hashCode();
        int hc2 = a.hashCode();
        assertEquals(hc1, hc2, "hashCode should be consistent across multiple invocations");
    }
}
