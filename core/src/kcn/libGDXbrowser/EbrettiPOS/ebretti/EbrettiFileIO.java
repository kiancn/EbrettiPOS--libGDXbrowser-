package kcn.libGDXbrowser.EbrettiPOS.ebretti;
// by KCN (inheriting from a Crunchy tutorial - link below)

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import kcn.utility.TO;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Purpose of this class is to load csv files lines into the EbrettiPOS application: method is static
 * because I am lazy, and it makes file access very easy.
 * ... Also, because I'm in a hurry, I'm not going for most optimized, but rather completely functional and
 * obvious (in coding style).
 */
public class EbrettiFileIO
{
    /**
     * Supply a txt csv file PATH containing Ebrettipart lines.
     */
    public static ArrayList<EbrettiPart> load_CSV_To_ArrayList(String csvFilePath, String splitString)
    {
        System.out.println("From EbrettiFileIO: Hello from load_CSV_To_A");
        BufferedReader bufferedReader = null;

        ArrayList<EbrettiPart> loadedParts = new ArrayList<>();

        int attemptedFileWrites = 0;

        // trying to load parts, line by line
        try
        {


            String inputLine;
            bufferedReader = new BufferedReader(new FileReader(csvFilePath));


            System.out.println("From EbrettiFileIO: buffered reader is ready : " + bufferedReader.ready());

            // reading in file line by line, until there are no more
            while((inputLine = bufferedReader.readLine()) != null)
            {   // printing to console for debugging
                System.out.println("Raw CSV data: " + inputLine);

                loadedParts.add(convertCSVLineToEbrettiPart(inputLine, splitString));
                attemptedFileWrites += 1;
            }

        } catch(IOException e)
        {
            e.printStackTrace();  // this is standard
        } finally
        {
            try
            {
                if(bufferedReader != null){ bufferedReader.close();}
            } catch(IOException e)
            {
                e.printStackTrace();
            }
        }
        System.out.println(TO.blue("Ebretti IO CSV to ArrayList is returning " + loadedParts.size()
                                   + " after trying to load " + attemptedFileWrites + " items."));
        return loadedParts;
    }

    /* Utility which converts CSV to EbrettiPart using Split Operation */
    private static EbrettiPart convertCSVLineToEbrettiPart(String partCSV, String splitString)
    {
        int csvDataPointCount = 0; // debugging purposes

        if(partCSV != null)
        {   // split segments the supplied string into parts, creating and adding to array a new part for each
            // encounter with any number of space-characters (\\s is space, * indicatas 'any number of reps'.)
            String[] splitData = partCSV.split("\\s*" + splitString + "\\s*"); // splitting the received string

            csvDataPointCount = splitData.length;
            if(splitData.length == 5) // if length is not 5, it is not an Ebretti-Part
            {
                try //
                {
                    EbrettiPart importedPart = new EbrettiPart(splitData[0].trim(),
                                                               splitData[1].trim(),
                                                               splitData[2].trim(),
                                                               Float.valueOf(splitData[3].trim()),
                                                               splitData[4]);
                    return importedPart;

                } catch(NumberFormatException e)
                {
                    System.out.println(TO.red("From EbrettiFileIO: Supplied CSV had bad numbers. Failure entry " +
                                              "created."));
                }

            } else
            {
                System.out.println(TO.red("From EbrettiFileIO: Supplied CSV had wrong length = " + csvDataPointCount));
            }
        }
        // code only reaches here if the split line is different length than 5; failures created for debugging
        return new EbrettiPart("Failure",
                               "40",
                               "Failure to load file on display",
                               0,
                               "");
    }
}

/*
 * All hail this mighty tutorial on reading a csv-file and splitting its lines (*this script is a thorough
 * makeover of the tutorial script):
 * https://crunchify.com/how-to-read-convert-csv-comma-separated-values-file-to-arraylist-in-java-using-split-operation/
 * **/