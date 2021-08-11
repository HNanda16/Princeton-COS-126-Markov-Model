/* *****************************************************************************
 *  Name:    Hasit Nanda
 *  NetID:
 *  Precept:
 *
 *  Partner Name:
 *  Partner NetID:
 *  Partner Precept:
 *
 *  Description:  Defines the Markov Model data type, which allows the user to
 * simultaneously store a symbol table containing the frequency of each k-gram
 * and a symbol table containing the frequency of character occurences after
 * each k-gram
 *
 **************************************************************************** */

public class MarkovModel {

    int order;
    int textLength;
    ST<String, Integer> table1;
    ST<String, int[]> table2;

    // creates a Markov model of order k based on the specified text

    public MarkovModel(String text, int k) {
        table1 = new ST<String, Integer>();
        table2 = new ST<String, int[]>();
        order = k;
        textLength = text.length();
        String addition = String.valueOf(text.charAt(0));
        for (int i = 1; i < order; i++) {
            addition += text.charAt(i);
        }
        String circular = text + addition;


        for (int i = 0; i < textLength; i++) {
            String kgram = circular.substring(i, (i + order));
            if (table1.contains(kgram)) {
                Integer current = table1.get(kgram);
                table1.put(kgram, current + 1);
            }
            else {
                table1.put(kgram, 1);
            }

            char newChar = circular.charAt(i + order);

            if (table2.contains(kgram)) {
                int[] values = table2.get(kgram);
                values[newChar] += 1;
                table2.put(kgram, values);
            }
            else {
                int[] values = new int[128];
                values[newChar] = 1;
                table2.put(kgram, values);
            }
        }
    }


    // returns the order of the model (also known as k)
    public int order() {
        return order;
    }

    // returns a String representation of the model (more info below)
    public String toString() {
        String values;
        int counter = 0;
        String[] result = new String[textLength];
        for (Object key : table2.keys()) {
            String keyString = (String) key;
            values = keyString + ":";
            int[] value = table2.get(keyString);
            int length = value.length;
            for (int i = 0; i < length; i++) {
                if (value[i] > 0) {
                    values += " " + Character.toString((char) i) + " " + Integer
                            .toString(value[i]);
                }
            }
            result[counter] = values;
            counter += 1;
        }
        String finalValues = result[0] + "\n";
        for (int i = 1; i < counter; i++) {
            finalValues += result[i] + "\n";
        }
        return finalValues;
        /* String[] updated = new String[counter];
        for (int i = 0; i < counter; i++) {
            updated[i] = temp[i];
        }
        boolean sorted = false;
        while (!sorted) {
            for (int i = 0; i < updated.length; i++) {
                sorted = true;
                if (updated[i].compareTo(updated[i + 1]) > 0) {
                    sorted = false;
                    String transition = updated[i];
                    updated[i] = updated[i + 1];
                    updated[i + 1] = transition;
                }
            }
        }

        for (int i = 0; i < updated.length; i++) {
            finalValues += updated[i] + "\n";
        }
        */
    }

    // returns the # of times 'kgram' appeared in the input text
    public int freq(String kgram) {
        if (kgram.length() != order) {
            throw new IllegalArgumentException(
                    "Input to method must match defined length of k-gram");
        }
        return table1.get(kgram);
    }

    // returns the # of times 'c' followed 'kgram' in the input text
    public int freq(String kgram, char c) {
        if (kgram.length() != order) {
            throw new IllegalArgumentException(
                    "Input to method must match defined length of k-gram");
        }
        int[] newArray = table2.get(kgram);
        return newArray[c];
    }

    // returns a random character, chosen with weight proportional to the
    // number of times each character followed 'kgram' in the input text
    public char random(String kgram) {
        if (kgram.length() != order) {
            throw new IllegalArgumentException(
                    "Input to method must match defined length of k-gram");
        }
        if (!table1.contains(kgram)) {
            throw new IllegalArgumentException("k-gram does not appear in input text");
        }
        int[] newArray = table2.get(kgram);
        int position = StdRandom.discrete(newArray);
        char character = Character.toString((char) position).charAt(0);
        return character;
    }


    // tests all instance methods to make sure they're working as expected
    public static void main(String[] args) {
        String text2 = "gagggagaggcgagaaa";
        MarkovModel model2 = new MarkovModel(text2, 2);
        StdOut.println(model2);

    }
}

