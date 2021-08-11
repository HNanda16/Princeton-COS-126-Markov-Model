/* *****************************************************************************
 *  Name:    Hasit Nanda
 *  NetID:
 *  Precept:
 *
 *  Partner Name:
 *  Partner NetID:
 *  Partner Precept:
 *
 *  Description: Generates pseudo-random text based on the frequency of characters
 * in the original text based on the previous k-gram
 *
 **************************************************************************** */

public class TextGenerator {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        String text = StdIn.readAll();

        MarkovModel generator = new MarkovModel(text, k);
        String kgram = text.substring(0, k);
        StdOut.print(kgram);

        for (int i = 0; i < (t - k); i++) {
            char character = generator.random(kgram);
            StdOut.print(character);
            kgram = kgram.substring(1) + character;
        }
    }
}
