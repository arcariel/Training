import java.util.Random;

public class ParenthesesValidator {

    public static void main(String[] args) {
        String generatedString = parenthesesStringCreation(10);
        System.out.println("The string: " + generatedString + " has " + countValidParentheses(generatedString, 0) + " valid Parentheses.");
    }

    private static String parenthesesStringCreation(int stringLength) {
        int unicodeLeftLimit = 40;
        int unicodeRightLimit = 41;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(stringLength);
        for (int i = 0; i < stringLength; i++) {
            int randomUnicodeCharValue = unicodeLeftLimit + (int)
                    (random.nextFloat() * (unicodeRightLimit - unicodeLeftLimit + 1));
            buffer.append((char) randomUnicodeCharValue);
        }
        return buffer.toString();
    }

    private static int countValidParentheses(String originalString, int counter) {
        if (originalString.contains("()")) {
            counter ++;
            String subString = originalString.replaceFirst("\\(\\)", "");
            return countValidParentheses(subString, counter);
        }
        return counter;
    }
}
