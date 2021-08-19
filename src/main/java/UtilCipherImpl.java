import java.util.Optional;

public class UtilCipherImpl implements UtilCipher {


    public Optional<String> filterAllNonChars(String message) {

        return message.chars().sequential().filter(Character::isLetter)
                .mapToObj(Character::toString)
                .map(String::toLowerCase)
                .reduce((s, s2) -> s + s2);
    }

    /**
     * @param cipher  should not be empty or null
     * @param messageLength should not be zero
     *
     */
    public String correctCipherLength(String cipher, int messageLength) {

        String result = cipher;
        int wholeRepeat = messageLength / cipher.length();
        int chars = messageLength % cipher.length();

        for (int i = 1; i < wholeRepeat; i++) {
            result = result + cipher;
        }
        for (int i = 0; i < chars; i++) {
            result = result + cipher.split("")[i];
        }

        return result;
    }
}
