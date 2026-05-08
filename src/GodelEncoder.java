import java.util.*;
        import java.util.regex.*;

public class GodelEncoder {

    // Encode state qi -> i zeros (q1 -> "0", q2 -> "00", ...)
    private static String encodeState(int i) {
        return "0".repeat(i);
    }

    // Encode tape symbol:
    // 0 -> X1 -> 1 zero
    // 1 -> X2 -> 2 zeros
    // blank -> X3 -> 3 zeros
    // 2 -> X4 -> 4 zeros  (extra symbols from the transitions, mapped in order of appearance)
    // 3 -> X5 -> 5 zeros
    // 4 -> X6 -> 6 zeros
    // 5 -> X7 -> 7 zeros
    private static final Map<String, Integer> SYMBOL_MAP = new LinkedHashMap<>();
    static {
        SYMBOL_MAP.put("0", 1); // X1
        SYMBOL_MAP.put("1", 2); // X2
        // blank would be X3 = 3, not used here
        SYMBOL_MAP.put("2", 4); // X4
        SYMBOL_MAP.put("3", 5); // X5
        SYMBOL_MAP.put("4", 6); // X6
        SYMBOL_MAP.put("5", 7); // X7
    }

    private static String encodeSymbol(String sym) {
        int idx = Integer.parseInt(sym)+1;
        return "0".repeat(idx);
    }

    // Encode direction: L -> D1 -> 1 zero, R -> D2 -> 2 zeros
    private static String encodeDirection(String dir) {
        return switch (dir) {
            case "L" -> "0";
            case "R" -> "00";
            default -> throw new IllegalArgumentException("Unknown direction: " + dir);
        };
    }

    // Parse state string like "q17" -> 17
    private static int parseState(String s) {
        return Integer.parseInt(s.substring(1));
    }

    /**
     * Encodes a TM transition string into its Gödel encoding.
     *
     * Input format (one transition per line):
     *   (qi,Xj)=(qk,Xl,D)
     * Example:
     *   (q1,1)=(q3,0,R)
     *
     * Output: binary string of 0s and 1s, transitions separated by "11"
     */
    public static String encode(String input) {
        // Regex: (q<i>,<sym>)=(q<k>,<sym>,<dir>)
        Pattern pattern = Pattern.compile(
                "\\(q(\\d+),(\\d+)\\)=\\(q(\\d+),(\\d+),([LR])\\)"
        );

        List<String> encodedTransitions = new ArrayList<>();

        for (String line : input.split(";")) {
            line = line.trim();
            if (line.isEmpty()) continue;

            Matcher m = pattern.matcher(line);
            if (!m.matches()) {
                System.err.println("Skipping unrecognized line: " + line);
                continue;
            }

            int i = Integer.parseInt(m.group(1)); // source state index
            String symJ = m.group(2);             // read symbol
            int k = Integer.parseInt(m.group(3)); // target state index
            String symL = m.group(4);             // write symbol
            String dir = m.group(5);              // direction

            String encoded =
                    encodeState(i)     + "1" +
                            encodeSymbol(symJ) + "1" +
                            encodeState(k)     + "1" +
                            encodeSymbol(symL) + "1" +
                            encodeDirection(dir);

            encodedTransitions.add(encoded);
        }

        return String.join("11", encodedTransitions);
    }

    public static void main(String[] args) {
        String transitions = """
            (q1,1)=(q1,1,R)
            (q1,2)=(q3,0,L)
            (q3,1)=(q3,1,L)
            (q3,2)=(q4,2,R)
            (q4,1)=(q5,3,R)
            (q4,0)=(q8,0,R)
            (q5,1)=(q5,1,R)
            (q5,0)=(q6,0,R)
            (q6,1)=(q6,1,R)
            (q6,2)=(q7,1,L)
            (q7,1)=(q7,1,L)
            (q7,0)=(q7,0,L)
            (q7,3)=(q4,3,R)
            (q8,1)=(q8,1,R)
            (q8,2)=(q9,4,L)
            (q9,1)=(q9,1,L)
            (q9,0)=(q9,0,L)
            (q9,3)=(q9,3,L)
            (q9,2)=(q10,2,R)
            (q10,0)=(q17,2,R)
            (q10,3)=(q11,2,R)
            (q11,3)=(q11,3,R)
            (q11,0)=(q12,0,R)
            (q12,1)=(q13,5,R)
            (q12,4)=(q16,4,L)
            (q13,1)=(q13,1,R)
            (q13,4)=(q14,4,R)
            (q14,1)=(q14,1,R)
            (q14,2)=(q15,1,L)
            (q15,1)=(q15,1,L)
            (q15,4)=(q15,4,L)
            (q15,1)=(q12,1,R)
            (q16,5)=(q16,1,L)
            (q16,0)=(q9,0,L)
            (q17,1)=(q17,2,R)
            (q17,4)=(q2,2,R)
            """;

        String result = encode(transitions);
        System.out.println(result);
    }
}