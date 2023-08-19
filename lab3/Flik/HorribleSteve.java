import org.junit.Test;
import static org.junit.Assert.*;

public class HorribleSteve {
    @Test
    public void filkTest() {
        int i = 1, j = 1;
        assertTrue("wrong at " + i, Flik.isSameNumber(i, j));
        i = 5;
        j = 5;
        assertTrue("wrong at " + i, Flik.isSameNumber(i, j));
        i = 128;
        j = 128;
        assertTrue("wrong at " + i, Flik.isSameNumber(i, j));
    }

    public static void main(String[] args) {
        int i = 0;
        for (int j = 0; i < 500; ++i, ++j) {
            if (!Flik.isSameNumber(i, j)) {
                break; // break exits the for loop!
            }
        }
        System.out.println("i is " + i);
    }
}
