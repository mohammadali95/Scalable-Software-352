package OtherStuff;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

public class RainbowTables {
    public static ArrayList<String> readPasswordFile() throws IOException {
        FileReader fileReader = new FileReader("passwordlist.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        ArrayList<String> output = new ArrayList<>();
        String current = null;
        while((current = bufferedReader.readLine())!=null){
            output.add(current);
        }
        return output;
    }
    public static Optional<String> search(String plaintext) throws IOException {
        ArrayList<String> rainbowTable = readPasswordFile();
        for(String password: rainbowTable){
            if(plaintext.equals(password)){
                return Optional.of(password);
            }
        }
        return Optional.empty();
    }
    public static double secondsToCrackPassword(String plaintext) throws IOException {
        long start = System.currentTimeMillis();
        search(plaintext);
        long end = System.currentTimeMillis();
        long delta = end - start;
        double elapsedSeconds = delta / 1000.0;
        return elapsedSeconds;
    }
}
