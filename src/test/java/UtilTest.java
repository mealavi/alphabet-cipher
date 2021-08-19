import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class UtilTest {

    private UtilCipherImpl util;

    @BeforeEach
    void setUp() {
        util = new UtilCipherImpl();
    }

    @ParameterizedTest
    @MethodSource("correctCipherLengthProvider")
    public void correctCipherLength_should_returnCorrectCipherText(String cipher, int messageLength, String expected) {
        String result = util.correctCipherLength(cipher, messageLength);
        assertEquals(result, expected);
    }


    public static Stream<Arguments> correctCipherLengthProvider() {
        String sampleCipher = "justasampelcipher";
        String sampleMessage = "meetmeontuesdayeveningatsevenmeetmeontuesdayeveningatsevenmeetmeontuesdayeveningatseven";
        String expectedSampleCipher = "justasampelcipherjustasampelcipherjustasampelcipherjustasampelcipherjustasampelcipherju";
        return Stream.of(
                Arguments.of("abc", 8, "abcabcab"),
                Arguments.of("abcdefg", 7, "abcdefg"),
                Arguments.of("567", 8, "56756756"),
                Arguments.of("vigilance", 29, "vigilancevigilancevigilancevi"),
                Arguments.of(sampleCipher, sampleMessage.length(), expectedSampleCipher)

        );
    }


    @ParameterizedTest
    @MethodSource("filterAllNonCharsProvider")
    void filterAllNonChars_should_returnCorrectText(String cipher,String expectedString) {

        assertEquals(util.filterAllNonChars(cipher).get(), expectedString);
    }


    public static Stream<Arguments> filterAllNonCharsProvider() {

        return Stream.of(
                Arguments.of("      lots of white space", "lotsofwhitespace"),
                Arguments.of("some REALLY, @interesting cipher here !", "somereallyinterestingcipherhere"),
                Arguments.of("13a25631313b", "ab"),
                Arguments.of("\"!§$%/(    )==??'_:*test", "test")


        );
    }
}