import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

class FileManager {

    FileManager() {
    }

    byte[] readFileToByteArray(String path) {
        try {
            BufferedReader in = new BufferedReader(new FileReader(path));
            String s;
            StringBuilder g = new StringBuilder();
            while ((s = in.readLine()) != null) {
                g.append(s);
            }
            return g.toString().getBytes(StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.err.println("Error in FileManager " + e.toString());
        }
        return null;
    }
}
