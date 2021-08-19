public interface Cipher {

    String encode(String message, String cipher);

    String decode(String encodedMessage, String cipher);

}
