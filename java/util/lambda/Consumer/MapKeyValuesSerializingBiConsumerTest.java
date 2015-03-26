/**
 * @test
 * @summary Sanity test for Consumer
 * @library ../
 * @(#)MapKeyValuesSerializingBiConsumerTest.java
 * @author Tristan Yan
 * @run testng MapKeyValuesSerializingBiConsumerTest
 */

import java.io.ObjectOutput;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.BiConsumer;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

public class MapKeyValuesSerializingBiConsumerTest {
    @Test
    public void testSerialization() {
        Map<String, String> map = new TreeMap<>();
        map.put("A", "alpha");
        map.put("B", "beta");
        map.put("C", "gamma");
        List<? extends Serializable> expectedWrites = Arrays.asList("A", "alpha", "B", "beta", "C", "gamma");
        MockObjectOutput moo = new MockObjectOutput(expectedWrites);
        BiConsumer<String, String> bc =
                (s1, s2) -> { moo.writeObject(s1); moo.writeObject(s2); };
        map.forEach(bc);
    }

    public static final class MockObjectOutput implements ObjectOutput {

        private final Iterator<? extends Serializable> iterator;

        public MockObjectOutput(Iterable<? extends Serializable> expectedWrites) {
            this.iterator = expectedWrites.iterator();
        }

        @Override
        public void writeObject(Object obj) {
            assertEquals(this.iterator.next(), obj);
        }

        @Override
        public void writeInt(int v) {
            assertEquals(this.iterator.next(), v);
        }

        @Override
        public void write(int i) {
            throw new RuntimeException("write not implemented");
        }

        @Override
        public void write(byte[] bs) {
            throw new RuntimeException("write not implemented");
        }

        @Override
        public void write(byte[] bs, int off, int len) {
            throw new RuntimeException("write not implemented");
        }

        @Override
        public void writeBoolean(boolean v) {
            throw new RuntimeException("writeBoolean not implemented");
        }

        @Override
        public void writeByte(int v) {
            throw new RuntimeException("writeByte not implemented");
        }

        @Override
        public void writeShort(int v) {
            throw new RuntimeException("writeShort not implemented");
        }

        @Override
        public void writeChar(int v) {
            throw new RuntimeException("writeChar not implemented");
        }

        @Override
        public void writeLong(long v) {
            throw new RuntimeException("writeLong not implemented");
        }

        @Override
        public void writeFloat(float v) {
            throw new RuntimeException("writeFloat not implemented");
        }

        @Override
        public void writeDouble(double v) {
            throw new RuntimeException("writeDouble not implemented");
        }

        @Override
        public void writeBytes(String s) {
            throw new RuntimeException("writeBytes not implemented");
        }

        @Override
        public void writeChars(String s) {
            throw new RuntimeException("writeChars not implemented");
        }

        @Override
        public void writeUTF(String s) {
            throw new RuntimeException("writeUTF not implemented");
        }

        @Override
        public void flush() {
            throw new RuntimeException("flush not implemented");
        }

        @Override
        public void close() {
            throw new RuntimeException("close not implemented");
        }
    }
}