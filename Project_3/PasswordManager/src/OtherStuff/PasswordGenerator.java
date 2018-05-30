package OtherStuff;
import java.util.ArrayList;
import java.util.Random;
import java.util.stream.IntStream;

public class PasswordGenerator {

    private int maxLength;
    private boolean hasSpecialChar;
    private boolean hasUpperCase;
    private boolean hasLowerCase;
    private boolean hasNumber;

    private final static int[] upperAlphabet = IntStream.rangeClosed(65,90).toArray();
    private final static int[] lowerAlphabet = IntStream.rangeClosed(97,122).toArray();
    private final static int[] specialChars = IntStream.rangeClosed(33,64).toArray();
    private final static int[] numbers = IntStream.rangeClosed(48,57).toArray();

    public static char getRandomChar(int[] charSet) {
        Random rand = new Random();
        int selection = charSet[rand.nextInt(charSet.length - 1)];
        return (char) selection;
    }


    // all parameters changed by text box in GUI
    public PasswordGenerator() {
        this.maxLength = 20;
        this.hasSpecialChar = true;
        this.hasUpperCase = true;
        this.hasLowerCase = true;
        this.hasNumber = true;
    }

    public String generate() {

        Random rand = new Random();
        char[] password = new char[maxLength];
        ArrayList<Integer> valid = new ArrayList<>();
        for (int i = 0; i < maxLength; i++) {
            valid.add(i);
        }
        if (hasSpecialChar) {
            int index = valid.get(rand.nextInt(valid.size()));
            password[index] = getRandomChar(specialChars);
            valid.remove((Integer)index);
        } if (hasUpperCase) {
            int index = valid.get(rand.nextInt(valid.size()));
            password[index] = getRandomChar(upperAlphabet);
            valid.remove((Integer)index);
        } if (hasLowerCase) {
            int index = valid.get(rand.nextInt(valid.size()));
            password[index] = getRandomChar(lowerAlphabet);
            valid.remove((Integer)index);
        } if (hasNumber) {
            int index = valid.get(rand.nextInt(valid.size()));
            password[index] = getRandomChar(numbers);
            valid.remove((Integer)index);
        }
        for (int i = 0; i < maxLength; i++) {
            if ((password[i]) == '\0') {
                int charSetChoice = rand.nextInt(3);
                int[][] charSets = new int[][]{specialChars,upperAlphabet,lowerAlphabet,numbers};
                password[i] = getRandomChar(charSets[charSetChoice]);
            }
        }
        return String.valueOf(password);
    }

    public void setMaxLength(int maxLength) {
        if (maxLength < 4) {
            this.maxLength = 4;
        } else {
            this.maxLength = maxLength;
        }
    }

    public void setHasSpecialChar(boolean hasSpecialChar) {
        this.hasSpecialChar = hasSpecialChar;
    }

    public void setHasUpperCase(boolean hasUpperCase) {
        this.hasUpperCase = hasUpperCase;
    }

    public void setHasLowerCase(boolean hasLowerCase) {
        this.hasLowerCase = hasLowerCase;
    }

    public void setHasNumber(boolean hasNumber) {
        this.hasNumber = hasNumber;
    }

}
