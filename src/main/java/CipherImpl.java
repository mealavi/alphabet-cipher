import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class CipherImpl implements Cipher {

    private final UtilCipher utilCipher;

    final static String[] alphabet = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
    public static final java.util.List<String> alphabetList = Arrays.asList(CipherImpl.alphabet);
    String stringAlphabet = Arrays.stream(alphabet).reduce((s1, s2) -> s1 + s2).get();
    HashMap<String, String> cipherTable = new HashMap<>();
    ArrayList<String> cipherTableStr = new ArrayList<>();


    public CipherImpl(UtilCipher utilCipher) {
        this.utilCipher = utilCipher;
        IntStream.range(0, alphabet.length).sequential().
                forEachOrdered((int i) -> {
                    cipherTable.put(alphabet[i], stringAlphabet.substring(i) + stringAlphabet.substring(0, i));
                });
        cipherTable.forEach((s, s2) -> cipherTableStr.add(s2));

    }


    @Override
    public String encode(String message, String cipher) {

        Objects.requireNonNull(message, "MESSAGE should not be null");
        Objects.requireNonNull(cipher, "CIPHER should not be null");

        message = utilCipher.filterAllNonChars(message).orElseThrow(IllegalArgumentException::new);
        cipher = utilCipher.filterAllNonChars(cipher).orElseThrow(IllegalArgumentException::new);

        cipher = utilCipher.correctCipherLength(cipher, message.length());


        String[] cipherArray = cipher.split("");
        String[] messageSplit = message.split("");
        ArrayList<String> a = new ArrayList<>();
        IntStream.range(0, cipherArray.length).sequential().
                forEachOrdered((int i) -> {
                    a.add(String.valueOf(cipherTable.get(cipherArray[i]).charAt(stringAlphabet.indexOf(messageSplit[i]))));
                });
        return a.stream().reduce((s, s2) -> s + s2).get();
    }

    @Override
    public String decode(String encodedMessage, String cipher) {

        String newCipher = utilCipher.correctCipherLength(cipher, encodedMessage.length());
        ArrayList<Character> messageArray=new ArrayList<>();
        IntStream.range(0, newCipher.length()).sequential()
                .mapToObj(letter -> {
                    String s= String.valueOf(newCipher.charAt(letter));
                    return encodedMessage.charAt(letter)+"," +alphabetList.indexOf(s);
                })
                .forEach(s -> {
                    String l=s.split(",")[0];
                    String i=s.split(",")[1];
                    cipherTableStr.forEach(j -> {
                        if (String.valueOf(j.charAt(Integer.valueOf(i))).equalsIgnoreCase(l)) {
                            messageArray.add(j.charAt(0));
                        }
                    });
                });

        return messageArray.stream().map(String::valueOf).collect(Collectors.joining());
    }


}
