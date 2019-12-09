import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class Main {


    public static void main(String[] args) {
        if (args.length != 3) {
            throw new IllegalArgumentException("Wrong number of arguments");
        }

        if (!args[0].equals("-e") && !args[0].equals("-d")) {
            throw new IllegalArgumentException("Expected -e or -d argument");
        }

        byte[] inputText;
        try {
            inputText = Files.readAllBytes(Paths.get(args[2]));
        } catch (IOException e) {
            throw new IllegalStateException("Cant read file");
        }
        RC4 rc4 = new RC4(args[1].getBytes());

        var result = args[0].equals("-e") ? rc4.encrypt(inputText) : rc4.decrypt(inputText);

        try (FileOutputStream stream = new FileOutputStream("./result")) {
            stream.write(result);
        } catch (IOException e) {
            throw new IllegalStateException("Cant read file");
        }
    }

}
