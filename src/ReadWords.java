
    import java.util.Scanner;
import java.io.*;

    public class ReadWords {
        public static void main(String[] args) throws IOException {

            File file = new File("src/inputfile1.txt");
            Scanner input = new Scanner(file);

            int count = 0;
            while (input.hasNext()) {
                String word  = input.next();
                System.out.println(word);
                count = count + 1;
            }
            System.out.println("Word count: " + count);
        }
    }

