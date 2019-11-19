package kcn.utility;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This class will be under construction for a long time:
 * <p>- Intent is to only have methods that help load files
 * in interesting ways.<p/>
 * <p></p>
 */
public class FileIO
{
    /**
     * Method takes a file path and returns an arraylist of strings,
     * a string for each line; it will chop up long lines
     * <p> If you never want lines chopped, set maxLineLength to -1 </p>
     */
    public static ArrayList<String> load_TextFile_as_Strings(String pathToFile,
                                                             int maxLineLength)
    {
        /* BufferedReader holds the loaded file as to allow efficient file reading */
        BufferedReader bufferedReader = null;
        /* A string for each line, extra for each line chop */
        ArrayList<String> lines = new ArrayList<>();

        /* Opening file and buffering content. Might fail, so you gotta try... */
        try
        {
            bufferedReader = new BufferedReader(new FileReader(pathToFile));
            /* string will hold all incoming lines..*/
            String tempLine;

            /* while querying the buffer for next line does not result in null */
            while((tempLine = bufferedReader.readLine()) != null)
            {
                /* if maxLength supplied is -1, do no splitting, just add */
                if(maxLineLength == -1)
                {
                    lines.add(tempLine);
                } else if(tempLine.length() > maxLineLength)
                {
                    /* else, split long tempLine and add returned shortened string to lines-array */;
                    lines.addAll(Grouper.splitStringToLines(tempLine, maxLineLength));
                }
            }
        } catch(FileNotFoundException e)
        {

            e.printStackTrace();
        } finally // finally-section will be executed regardless
        {
            if(bufferedReader != null)
            {
                try
                {
                    /* closing buffered reader to avoid memory leaks */
                    bufferedReader.close();
                } catch(IOException e)
                {
                    System.out.println("FileIO: Closing file was interrupted and unsuccessful");
                    e.printStackTrace();
                }
            }

            return lines;
        }

    }
}

/*
 * And this tutorial helped me split lines into wished for lengths;
 * http://www.davismol.net/2015/02/03/java-how-to-split-a-string-into-fixed-length-rows-without-breaking-the-words/
 *
 * All hail this mighty tutorial on reading a csv-file and splitting its lines (*this script is a thorough
 * makeover of the tutorial script):
 * https://crunchify.com/how-to-read-convert-csv-comma-separated-values-file-to-arraylist-in-java-using-split-operation/
 * **/

