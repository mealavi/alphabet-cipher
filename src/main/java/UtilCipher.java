import java.util.Optional;

public interface UtilCipher {
    Optional<String> filterAllNonChars(String message);
    String correctCipherLength(String cipher, int messageLength);

}
