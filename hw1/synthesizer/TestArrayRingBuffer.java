package synthesizer;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Iterator;

/**
 * Tests the ArrayRingBuffer class.
 * 
 * @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void someTest() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<>(4);
        assertTrue(arb.isEmpty());
        assertFalse(arb.isFull());
        arb.enqueue(1);
        arb.enqueue(2);
        arb.enqueue(3);
        arb.enqueue(4);
        assertFalse(arb.isEmpty());
        assertTrue(arb.isFull());
        for (Integer i : arb) {
            for (Integer j : arb) {
                System.out.println("i: " + i + " j: " + j);
            }
        }
        for (int i = 1; i <= 4; i++) {
            assertTrue(arb.peek() == i);
            assertTrue(arb.dequeue() == i);
        }

    }

    /** Calls tests for ArrayRingBuffer. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestArrayRingBuffer.class);
    }
}
