public class Day1 {

    private static int numZeros = 0;
    private static int dial = 50;
    
    public static void main(String[] args) {
        String resource = (args != null && args.length > 0) ? args[0] : "input.txt";
        try {
            String input = ResourceReader.readResourceAsString(resource);
            decodeInput(input);
            print("Zeros: " + numZeros);
        } catch (Exception e) {
            System.err.println("Failed to read resource '" + resource + "': " + e.getMessage());
        }
    }

    private static void decodeInput(String input) {
        String[] movement = input.split("\n");

        for (String move : movement) {
            if (move.startsWith("L")) {
                moveLeft(getValue(move));
            } else {
                moveRight(getValue(move));
            }
        }
    }

    private static void moveLeft(int amount) {
        for (int c = 1; c <= amount; c++) {
            dial -= 1;
            if (dial == 0) numZeros++;
            if (dial < 0) dial = 99;
        }
    }

    private static void moveRight(int amount) {
        for (int c = 1; c <= amount; c++) {
            dial += 1;
            if (dial > 99) {
                dial = 0;
                numZeros++;
            }
        }
    }

    private static int getValue(String input) {
        return Integer.parseInt(input.substring(1));
    }

    private static void print(Object input) {
        System.out.println(input);
    }
}
