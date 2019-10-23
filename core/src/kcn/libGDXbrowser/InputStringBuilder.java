package kcn.libGDXbrowser;
// by KCN
import kcn.methodreferencing.MethodReference;
import kcn.utility.TO;

import java.util.ArrayList;
/**Class instance registers characters typed (through callbackMethod given as parameter to appropriate
 * input processor )*/
public class InputStringBuilder
{
    public MethodReference callbackMethod; // this whole thing works by subscribing the callbackMethod to
    // a SignallingInputProcessor
 //   public MethodReference customCallbackMethod; // put your very own method-ref here (MethodPack

    public ArrayList<String> getListOfReceivedStrings()   {        return listOfReceivedStrings;    }

    // instead?)
    private ArrayList<String> listOfReceivedStrings;
   public final ReceivingObject receiver;

    public String returnString;

    public InputStringBuilder()
    {
        listOfReceivedStrings = new ArrayList<>();

        receiver = new ReceivingObject();

        try
        {
            callbackMethod = new MethodReference(receiver, receiver.getClass().getMethod(
                    "receiveCharacter_callbackMethod", char.class));

            System.out.println("From SimpleStringBuilder: callbackMethod is broke = " + callbackMethod.isReferenceBroke());
        } catch(NoSuchMethodException e)
        {
            System.out.println("From SimpleStringBuilder: callbackMethod is null = " + callbackMethod == null);
            System.out.println(e.toString());
        }
    }

    public class ReceivingObject
    {
        public String receivedString = "";

        public String receiveCharacter_callbackMethod(char incomingChar)
        {
            if(incomingChar == 13)
            { // 13 is the Enter-key
                listOfReceivedStrings.add(receivedString);
                System.out.println("Recieved lines to far;");
                for(String line : listOfReceivedStrings)
                {
                    System.out.println(TO.blue(line));
                }
                // because receivedString needs resetting; creating returnString to hold value typed.
                returnString = receivedString.toString();
                // resetting receivedString
                receivedString = "";
                // debugging, remember to comment out
                System.out.println("Content of returnString: " + returnString);
                // return the copy of the receivedString
                return returnString;
            }

            // if received char is not enter/13, simply add character to string being built
            receivedString += incomingChar;
            System.out.println("Incoming char" + incomingChar + " as byte: " + (byte)incomingChar + " as " +
                               "int:" + (int)incomingChar);

            // return an empty string; the idea is that whoever receives the return value
            // will distinguish between a an empty string "" and a string with 1 character or more.
            return "";
        }
    }
}
