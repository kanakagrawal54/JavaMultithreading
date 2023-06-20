import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {
    public static void main(String[] args) throws InterruptedException, FileNotFoundException {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        List<Task> taskList = new ArrayList<>();
        File myObj = new File("src/inputfile1.txt");
        Scanner myReader = new Scanner(myObj);
        int numOfLines = 3;
        String inputText = null;
        DictionarySingleton.getInstance();
        while(true) {
           inputText = null;
           int counter = 0;
           while (myReader.hasNextLine() && counter < numOfLines) {
               if (inputText == null)
                   inputText = myReader.nextLine();
               else
                   inputText = inputText.concat(myReader.nextLine());
               inputText = inputText.concat(" ");
               counter++;
           }
           if (inputText == null)
               break;
           else
           {
               Task task = new Task(inputText);
           taskList.add(task);
           }

        }
        List<Future<Result>> resultList = null;

        try {
            resultList = executor.invokeAll(taskList);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executor.shutdown();

        System.out.println("\n========Printing the results======");
      //  System.out.println(resultList.size());
        StringBuilder finalText = new StringBuilder();
        int totalIncorrectWord =0;
        for (int i = 0; i < resultList.size(); i++) {
            Future<Result> future = resultList.get(i);
            try {
                Result res = future.get();

                totalIncorrectWord +=res.getIncorrectword();
                finalText.append(res.getModifiedText1());
                //finalText.append(" ");
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }

        }
        System.out.println("Total incorrect words are " + totalIncorrectWord);
        System.out.println("------The complete text read by threads ----\n " + finalText);
    }
}
