import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;


class DictionarySingleton{

    private static DictionarySingleton single_instance = null;

    public ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();

    private DictionarySingleton(){
        try {
            File myObj = new File("src/dictionaryfile.txt");
            Scanner myReader = new Scanner(myObj);

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] words = data.split(" ");
                for (String word : words)
                {   word = word.toLowerCase();
                    if(!map.containsKey(word))
                        map.put(word,1);
                }
            }
            myReader.close();
        } catch(FileNotFoundException e) {
            System.out.println("An error occured");
            single_instance = null;
        }


    }

    public static synchronized DictionarySingleton getInstance()
    {
        if(single_instance == null)
            single_instance = new DictionarySingleton();

        return single_instance;
    }

}
