import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;


class CipherImplTest {


    private UtilCipher utilCipher;

    private CipherImpl cipherService;

    @BeforeEach
    void setUp() {
        utilCipher = new UtilCipherImpl();
        cipherService = new CipherImpl(utilCipher);
    }

    @ParameterizedTest
    @MethodSource("provider")
    void encode_should_processCorrect(String message, String cipher, String expected) {
        String encodedMessage = cipherService.encode(message, cipher);
        assertEquals(expected, encodedMessage);
    }



    @ParameterizedTest
    @MethodSource("encodeExceptionProvider")
    void encode_given_invalidInputs_should_throws_exception(String message, String cipher, Exception exception) {
        assertThrows(exception.getClass(), () -> cipherService.encode(message, cipher));

    }

    public static Stream<Arguments> encodeExceptionProvider() {
        return Stream.of(
                Arguments.of("", "vigilance", new IllegalArgumentException()),
                Arguments.of("meetmebythetree", "", new IllegalArgumentException()),
                Arguments.of(null, "", new NullPointerException()),
                Arguments.of("meetmebythetree", null, new NullPointerException())
        );
    }

    @ParameterizedTest
    @MethodSource("provider")
    public void decode_should_processCorrect(String message, String cipher, String encodedMessage) {
        assertEquals(cipherService.decode(encodedMessage, cipher), message);
    }

    public static Stream<Arguments> provider() {
        return Stream.of(
                Arguments.of("meetmeontuesdayeveningatseven", "vigilance", "hmkbxebpxpmyllyrxiiqtoltfgzzv"),
                Arguments.of("meetmebythetree", "scones", "egsgqwtahuiljgs")
        );
    }
}