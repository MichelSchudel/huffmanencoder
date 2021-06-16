package nl.craftsmen.huffman;

import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class LZ77EncoderTest {

    private static final String INPUT = "ababcbababaa";
    private final LZ77Encoder lz77Encoder = new LZ77Encoder();

    @Test
    public void testLz77() {
        List<LZ77Triple> result = lz77Encoder.encode(INPUT);
        System.out.println(result);
        assertThat(result).containsExactly(
                buildLZ77triple(0,0,'a'),
                buildLZ77triple(0,0,'b'),
                buildLZ77triple(2,2,'c'),
                buildLZ77triple(4,3,'a'),
                buildLZ77triple(2,2,'a')


        );
        String s= lz77Encoder.decode(result);
        assertThat(s).isEqualTo(INPUT);
    }

    private LZ77Triple buildLZ77triple(int o, int l, char c) {
        return LZ77Triple.builder()
                .offset(o)
                .length(l)
                .character(c).build();
    }

}