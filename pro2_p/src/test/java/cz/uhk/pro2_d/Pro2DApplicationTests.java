package cz.uhk.pro2_d;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureTestDatabase(replace = Replace.ANY)
class Pro2DApplicationTests {

    @Test
    void contextLoads() {
        // jen ověří, že se Spring kontext načte bez problémů
    }

}
