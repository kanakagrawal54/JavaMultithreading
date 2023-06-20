import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLOutput;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ConcurrentHashMap;


class DictionarySingleton{

    private static DictionarySingleton single_instance = null;

    // public ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
    public Set<String> threadSafeSet = new CopyOnWriteArraySet<>();
    private DictionarySingleton () throws FileNotFoundException{
       // try {
            File myObj = new File("src/dictionaryfile.txt");
            Scanner myReader = new Scanner(myObj);

            while (myReader.hasNextLine()) {
                String word= myReader.nextLine();
//                String[] words = data.split(" ");
//                for (String word : words) {
                    word = word.toLowerCase();
                //System.out.println(word);
                    if (!threadSafeSet.contains(word))
                        threadSafeSet.add(word);

            }
            myReader.close();

//        } catch(FileNotFoundException e) {
//            System.out.println("An error occured");
//            single_instance = null;
//        }


    }

    public static synchronized DictionarySingleton getInstance() throws FileNotFoundException
    {
        if(single_instance == null)
            single_instance = new DictionarySingleton();

        return single_instance;
    }

}
