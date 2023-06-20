import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

class Task implements Callable<Result> {
    private String inputText;
   private int incorrectWords=0;
    private int correctWords =0;

    private StringBuilder modifiedText;

    public Task(String inputText) {
        this.inputText =  inputText;
    }

    @Override
    public Result call() throws Exception
    {

        DictionarySingleton inst  = DictionarySingleton.getInstance();
                String[] myInput = inputText.split(" ");
                modifiedText = new StringBuilder();

                for (String word : myInput) {
                    word = word.toLowerCase();
                    if (!inst.threadSafeSet.contains(word))
                    {   modifiedText.append(word);
                        modifiedText.append(" ");
                        incorrectWords++;
                    }
                    else
                    {  modifiedText.append(word);
                        modifiedText.append(" ");
                        correctWords++;
                    }
                }


        return new Result(this.incorrectWords,this.correctWords, this.modifiedText );
    }
}
