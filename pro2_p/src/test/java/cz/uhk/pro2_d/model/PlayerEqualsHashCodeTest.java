package cz.uhk.pro2_d.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerEqualsHashCodeTest {

    @Test
    void equals_sameId_shouldBeEqual() {
        Player p1 = new Player();
        p1.setId(7L);
        Player p2 = new Player();
        p2.setId(7L);

        assertEquals(p1, p2, "Two players with the same id should be equal");
        assertEquals(p1.hashCode(), p2.hashCode(), "Hash codes should match for equal players");
    }

    @Test
    void equals_differentId_shouldNotBeEqual() {
        Player p1 = new Player();
        p1.setId(1L);
        Player p2 = new Player();
        p2.setId(2L);

        assertNotEquals(p1, p2, "Players with different ids should not be equal");
    }

    @Test
    void equals_nullAndOtherType_shouldNotBeEqual() {
        Player p = new Player();
        p.setId(10L);

        assertNotEquals(p, null, "Player should not be equal to null");
        assertNotEquals(p, "not a player", "Player should not be equal to an object of a different type");
    }

    @Test
    void hashCode_consistency() {
        Player p = new Player();
        p.setId(42L);

        int hc1 = p.hashCode();
        int hc2 = p.hashCode();
        assertEquals(hc1, hc2, "hashCode should be consistent across multiple invocations");
    }
}
