package kcn.libGDXbrowser.EbrettiPOS.EbrettiContentPages;
// by KCN

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import kcn.libGDXbrowser.SignallingInputProcessor;
import kcn.libGDXbrowser.InputStringBuilder;
import kcn.libGDXbrowser.EbrettiPOS.ebretti.EbrettiPart;
import kcn.libGDXbrowser.EbrettiPOS.ebretti.EbrettiSelectionButton;
import kcn.libGDXbrowser.content.Content;
import kcn.libGDXbrowser.EbrettiPOS.ebretti.ShoppingCart;
import kcn.libGDXbrowser.content.IDrawContent;

import java.util.ArrayList;
// this class contains a lot of functionality that needs to generalized for further use.

public class SearchForPartsContent
        extends Content
        implements IDrawContent
{
    SignallingInputProcessor inputProcessor; // buttons are registered with an input processor

    Texture texture; // the text which will be shown at top of screen (not render()'ed right now)
    BitmapFont fontRenderer; //

    InputStringBuilder simpleStringBuilder; // object enables assessing keyboard input in a reasonable way

    ShoppingCart shoppingCart; // this object hold the list of selected parts (and parts)
    // these are the currently searched parts; which parts are actually being drawn is decided through the
    // high and low indexes
    ArrayList<EbrettiPart> currentEbrettiParts;
    private ArrayList<EbrettiPart> ebrettiParts;
    // lists contains a button for each part in ebrettiParts-list: buttons adding and ditto removing parts
    private ArrayList<EbrettiSelectionButton> buttons_Adds;
    private ArrayList<EbrettiSelectionButton> buttons_Removes;
    private float timeAfterFocusGain_InactivePeriod; // period after focus gain before input-processing
    // methods are added to input-processor (to avoid concurrency exceptions)
    private float timeSinceFocusGain; // tracks period between focus gain and adding buttons to inputProcess
    private boolean hasButtonsBeenAddedToInputProcessor;// tracks if is safe to click content; switched by
    private int currentPartListView_LowIndex;
    private int currentPartListView_HighIndex;
    private int numberOfPartListView_Intervals;
    private ArrayList<EbrettiSelectionButton> currentButtons_Adds; // additive buttons, one for each part
    private ArrayList<EbrettiSelectionButton> currentButtons_Removes;  // removal buttons, one for each part
    private String mostRecentUserQuery; // string holds the most recent user search (remember to set to "")

    public SearchForPartsContent(SignallingInputProcessor INPUTPROCESSOR,
                                 ShoppingCart SHOPPINGCART,
                                 ArrayList<EbrettiPart> ebrettiPartsList)
    {
        inputProcessor = INPUTPROCESSOR;
        shoppingCart = SHOPPINGCART;

        name = "Search for Parts";
        texture = new Texture("badlogic.jpg");

        ebrettiParts = ebrettiPartsList;
        buttons_Adds = new ArrayList<>();
        buttons_Removes = new ArrayList<>();

        simpleStringBuilder = new InputStringBuilder();

        for(EbrettiPart part : ebrettiParts)
        {
            buttons_Adds.add(new EbrettiSelectionButton(part, shoppingCart, true));
        }

        for(EbrettiPart part : ebrettiParts)
        {
            buttons_Removes.add(new EbrettiSelectionButton(part, shoppingCart, false));
        }

        fontRenderer = new BitmapFont();

        timeSinceFocusGain = 0F;

        hasButtonsBeenAddedToInputProcessor = false;
        timeAfterFocusGain_InactivePeriod = 0.2F;

        currentEbrettiParts = new ArrayList<>(ebrettiParts);
        currentButtons_Adds = new ArrayList<>(buttons_Adds);
        currentButtons_Removes = new ArrayList<>(buttons_Removes);

        mostRecentUserQuery = "";

        adjustDetailsForCurrentPartList(currentEbrettiParts);

    }

    /**
     * Method is pulled every frame by contentManager
     */
    @Override
    public void drawContent(Batch batch)
    {
        checkIfButtonShouldBeAddedToInputManager();


        checkForNewUserQuery(simpleStringBuilder.returnString);
        // is it safe to add the buttons? (premature adding throws concurrent manipulation exceptions


        // draws every part of the supplied list
        drawPartList(batch, currentEbrettiParts);

        drawSearchText(batch);

        // draws the content of the shopping cart
        drawCart(batch);
    }

    private void drawSearchText(Batch batch)
    {

        int yAdjustment = Gdx.graphics.getHeight() - 17;
        int xAdjustment = 150;


        /* drawing text to accompany searches (notice the else that catches a null returnString)*/
        if(simpleStringBuilder.returnString != null)
        {
            fontRenderer.setColor(Color.GOLD);
            fontRenderer.draw(batch, "Current search: " + simpleStringBuilder.returnString,
                              xAdjustment,
                              yAdjustment);
            if(numberOfPartListView_Intervals > 0)
            {
                fontRenderer.setColor(Color.LIGHT_GRAY);
                fontRenderer.draw(batch,
                                  "There were at least " + ((numberOfPartListView_Intervals - 1) * 10) + " " +
                                  "results",
                                  xAdjustment, yAdjustment - 20);
                fontRenderer.draw(batch,
                                  "Search a between 1 and " + numberOfPartListView_Intervals +
                                  " to view result pages 1 to " + numberOfPartListView_Intervals,
                                  xAdjustment, yAdjustment - 40);
                fontRenderer.draw(batch,
                                  "You are " + (int)((double)currentPartListView_LowIndex / currentEbrettiParts.size() * 100) +
                                  "% into current list",
                                  xAdjustment + 450, yAdjustment - 40);
            }

            fontRenderer.setColor(Color.WHITE);
            fontRenderer.draw(batch, "Searched terms:", 30, yAdjustment);
            fontRenderer.setColor(Color.LIGHT_GRAY);

            /* writing out the search previous search strings */
            for(String searchString : simpleStringBuilder.getListOfReceivedStrings())
            {
                yAdjustment -= 17;
                fontRenderer.draw(batch,
                                  searchString,
                                  50,
                                  yAdjustment);
            }
        } else
        { /* this is only drawn if no search has been made yet. */
            fontRenderer.draw(batch, "You are viewing all items as one list.", 150, yAdjustment);
            fontRenderer.draw(batch, "Type a number between 1 and " + numberOfPartListView_Intervals +
                                     " to view another part of the full list.",
                              xAdjustment, yAdjustment - 40);
            fontRenderer.draw(batch,
                              "Or type in your own search below. NB Backspace is not implemented.",
                              xAdjustment, yAdjustment - 80);
        }

        fontRenderer.setColor(Color.GOLD);
        fontRenderer.draw(batch, "Enter you search: " + simpleStringBuilder.receiver.receivedString,
                          50, 50);

        fontRenderer.draw(batch,
                          "Enter  *.*  to view full list again.",
                          420, 20);

    }

    /**
     * Method draws the requested list to window/screen/canvas (and buttons)
     */
    private void drawPartList(Batch batch, ArrayList<EbrettiPart> ebrettiParts)
    {
        int yAdjPerItem = 0;
        int numOfItems = 1 + Math.abs(currentPartListView_HighIndex - currentPartListView_LowIndex);


        if(ebrettiParts.size() > 0)
        {
            int yAdjGlobal = 220;

            // adding parts to the display/window/screen
            for(int i = currentPartListView_LowIndex; i <= currentPartListView_HighIndex; i++)
            {
                // drawing image representation or placeholder, if there is no png/jpg present
                batch.draw(ebrettiParts.get(i).partImage, 180 - ebrettiParts.get(i).partImage.getWidth(),
                           yAdjGlobal + numOfItems * 50 - (yAdjPerItem * 50) - ebrettiParts.get(i).partImage.getHeight());

                // drawing part category text
                fontRenderer.setColor(Color.CORAL);
                fontRenderer.draw(batch, ebrettiParts.get(i).category, 200,
                                  yAdjGlobal + numOfItems * 50 - (yAdjPerItem * 50));

                // drawing part ID number
                fontRenderer.setColor(Color.LIGHT_GRAY);
                fontRenderer.draw(batch, "ID: " + ebrettiParts.get(i).ID, 200,
                                  yAdjGlobal + numOfItems * 50 - (yAdjPerItem * 50) - 20);

                // drawing description text
                fontRenderer.setColor(Color.WHITE);
                fontRenderer.draw(batch, ebrettiParts.get(i).description, 300,
                                  yAdjGlobal + numOfItems * 50 - (yAdjPerItem * 50));

                // drawing price text
                fontRenderer.setColor(Color.GOLD);
                fontRenderer.draw(batch, "Price: " + String.format("%,.2f",
                                                                      ebrettiParts.get(i).price),
                                  350,
                                  yAdjGlobal + numOfItems * 50 - (yAdjPerItem * 50) - 20);


                // drawing button to add to shopping cart
                currentButtons_Adds.get(i).setPosition(535,
                                                       yAdjGlobal + numOfItems * 50 - (yAdjPerItem * 50) - 20);
                currentButtons_Adds.get(i).drawButton(batch);
                // drawing label for this button
                currentButtons_Adds.get(i).drawLabel(batch, currentButtons_Adds.get(i).position.x,
                                                     currentButtons_Adds.get(i).position.y);

                // drawing removal button
                currentButtons_Removes.get(i).setPosition((int)(currentButtons_Adds.get(i).position.x + 105),
                                                          (int)currentButtons_Adds.get(i).position.y);
                currentButtons_Removes.get(i).drawButton(batch);
                // drawing label for this button
                currentButtons_Removes.get(i).drawLabel(batch, currentButtons_Removes.get(i).position.x,
                                                        currentButtons_Removes.get(i).position.y);

                yAdjPerItem++;
            }
        }
        /* text showing current partial list size. drawn at bottom */
        fontRenderer.setColor(Color.WHITE);
        fontRenderer.draw(batch, "Viewing list is " + ebrettiParts.size() + " long.", 200, 20);
    }

    /**
     * Method draws shopping cart content at a particular/arbitrary setup (lower right corner)
     */
    private void drawCart(Batch batch)
    {

        Vector2 originAdjustment = new Vector2(875, 720); // upper left corner ; because we go down every

        /* a little flavor text for the shopping cart */
        fontRenderer.draw(batch, "Shopping cart overview:", originAdjustment.x, originAdjustment.y + 50);

        // iterating, drawing all parts in cart
        if(shoppingCart.getPartsInCart().size() > 0)
        {

            for(int i = 0; i < shoppingCart.getPartsInCart().size(); i++)
            {
                fontRenderer.draw(batch,
                                  "#" + i,
                                  originAdjustment.x - 30,
                                  originAdjustment.y - i * 42);

                fontRenderer.draw(batch,
                                  shoppingCart.getPartsInCart().get(i).category,
                                  originAdjustment.x,
                                  originAdjustment.y - i * 42);


                fontRenderer.draw(batch,
                                  shoppingCart.getPartsInCart().get(i).description,
                                  originAdjustment.x,
                                  originAdjustment.y - i * 42 - 20);

                fontRenderer.draw(batch,
                                  "" + String.format("%.2f", shoppingCart.getPartsInCart().get(i).price),
                                  originAdjustment.x + 200,
                                  originAdjustment.y - i * 42);


            }
            fontRenderer.draw(batch,
                              "Running Total: " + (shoppingCart.getRunningTotal()),
                              Gdx.graphics.getWidth() - 200,
                              50);
        }
        if(shoppingCart.getPartsInCart().size() <= 0)
        {
            fontRenderer.draw(batch,
                              "It puts the parts in the basket.",
                              originAdjustment.x,
                              originAdjustment.y);
        }
    }

    @Override
    public void loseFocus()
    {
        System.out.println(name + " content lost focus.");
        inputProcessor.getCharTypedCallbackMethods().remove(simpleStringBuilder.callbackMethod);
        // removing buttons from inputProcessor when not in focus
        removeButtonsFromInputProcessor();

        hasButtonsBeenAddedToInputProcessor = false;
    }

    @Override
    public void gainFocus()
    {

        timeSinceFocusGain = 0F; // as content comes into focus

        inputProcessor.getCharTypedCallbackMethods().add(simpleStringBuilder.callbackMethod);

        System.out.println(name + " content gained focus.");
    }

    private void checkIfButtonShouldBeAddedToInputManager()
    {
        // check is necessary because immediate adding of buttons caused concurrent writing to a methodpack
        // in input-processor, which is a no-good exception to ignore.
        if(!hasButtonsBeenAddedToInputProcessor) // proceed if button have not already been added.
        {
            // check if it is time to add buttons
            if(timeSinceFocusGain > timeAfterFocusGain_InactivePeriod)
            {
                System.out.println("Adding buttons to input processor.");

                addButtonsToInputProcessor();
                hasButtonsBeenAddedToInputProcessor = true;

            } else // and if less time then the inactive period has passed, update time-period.
            {
                timeSinceFocusGain += Gdx.graphics.getDeltaTime();
            }
        }
    }

    private void removeButtonsFromInputProcessor()
    {
        if(currentEbrettiParts.size() > 0)
        {
            for(int i = currentPartListView_LowIndex; i <= currentPartListView_HighIndex; i++)
            {
                boolean didRemove =
                        inputProcessor.touchUpCallbackMethods.remove(currentButtons_Adds.get(i).methodOnClickEvent);
                System.out.println("Additive Button was removed : " + didRemove);
            }

            for(int i = currentPartListView_LowIndex; i <= currentPartListView_HighIndex; i++)
            {
                boolean didRemove =
                        inputProcessor.touchUpCallbackMethods.remove(currentButtons_Removes.get(i).methodOnClickEvent);
                System.out.println("Removal Button was removed : " + didRemove);
            }
        }
    }

    private void addButtonsToInputProcessor()
    {
        if(currentEbrettiParts.size() > 0)
        {
            for(int i = currentPartListView_LowIndex; i <= currentPartListView_HighIndex; i++)
            {
                boolean didAdd =
                        inputProcessor.touchUpCallbackMethods.add(currentButtons_Adds.get(i).methodOnClickEvent);
                System.out.println("Additive Button was added : " + didAdd);
            }

            for(int i = currentPartListView_LowIndex; i <= currentPartListView_HighIndex; i++)
            {
                boolean didAdd =
                        inputProcessor.touchUpCallbackMethods.add(currentButtons_Removes.get(i).methodOnClickEvent);
                System.out.println("Removal Button was added : " + didAdd);
            }
        }
    }


    /**
     * This method will check if it is time to generate a new partial list for 'viewing' in content;
     * - it can either prompt a new partial list from signallingStringBuilder.returnString if size > 3
     * <p>
     * - it can signal a change in viewing interval ( currentPartListView_LowIndex and currentPartListView_HighIndex)
     * <p>
     * - if returnString is not changed, do nothing
     */
    private void checkForNewUserQuery(String queryString)
    {
        if(queryString != null)
        {
            if(queryString.toLowerCase().equalsIgnoreCase(mostRecentUserQuery.toLowerCase()))
            {
                return; // do nothing and return if there was no change
            }

            System.out.println("From checkForNewUserQuery: Query string received " + queryString);

            // if string is short enough to be an interval number
            if(queryString.length() <= 2)
            {
                int possibleIntervalRequestInt = -1;

                try // seeing if we're dealing with a number
                {
                    possibleIntervalRequestInt = Integer.parseInt(queryString);

                } catch(NumberFormatException e)
                {
                    System.out.println("You entered something short that was not an index number.");
                }
                // when changing context, first all buttons must unsubscribe, new buttons must then subscr.
                if(possibleIntervalRequestInt != -1)
                {
                    loseFocus();
                    setViewedInterval(possibleIntervalRequestInt);
                    gainFocus();
                }
            }

            if(queryString.length() >= 3)
            {
                handleSearch(queryString);
            }

            mostRecentUserQuery = queryString;
            return;
        }
    }

    /**
     * Method signals a change in viewing index, low and high
     * - it takes an int that signals the interval number wished;
     * the method simply changes the two ints; the change is updated on drawing
     * - if number is out of range, do nothing
     */
    private void setViewedInterval(int intervalIndex)
    {
        if(intervalIndex >= 1 && intervalIndex <= numberOfPartListView_Intervals)
        {
            currentPartListView_LowIndex = intervalIndex * 10 - 10;
            System.out.println("From setViewedInterval: low index set : " + currentPartListView_LowIndex);
            // if the high index needs adjustment, it is caught here
            if(intervalIndex * 10 >= currentEbrettiParts.size())
            {
                currentPartListView_HighIndex = currentEbrettiParts.size() - 1;
                System.out.println("From setViewedInterval: high index set : " + currentPartListView_HighIndex);
            } else
            {
                currentPartListView_HighIndex = intervalIndex * 10;
                System.out.println("From setViewedInterval: high index set : " + currentPartListView_HighIndex);
            }
        } else
        {
            System.out.println("There aren't that many intervals");
        }
    }

    /* Method 'looks up queried term in full parts list and creates partial list based on the search (and
    two lists for buttons); method is pulled only from checkForNewUserQuery */
    private void handleSearch(String searchTerm)
    {
        // got to lose focus to gain focus on something new
        loseFocus();

        lookUpSearchAndCreateNewPartialLists(searchTerm);

        // update indexes of the new current viewing selection
        adjustDetailsForCurrentPartList(currentEbrettiParts);

        // when all details are settled, gain focus again
        gainFocus();
    }
    /***/
    private void lookUpSearchAndCreateNewPartialLists(String searchTerm)
    {
        currentEbrettiParts = new ArrayList<>();
        currentButtons_Adds = new ArrayList<>();
        currentButtons_Removes = new ArrayList<>();

        // if *.* is entered, all items are listed
        if(searchTerm.equalsIgnoreCase("*.*"))
        {
            for(int i = 0; i < ebrettiParts.size(); i++)
            {
                currentEbrettiParts.add(ebrettiParts.get(i));
                currentButtons_Adds.add(buttons_Adds.get(i));
                currentButtons_Removes.add(buttons_Removes.get(i));
            }
        } else
        {
            for(int i = 0; i < ebrettiParts.size(); i++)
            {   // if part of a category or description string matches search, add parts to lists
                if(ebrettiParts.get(i).category.toLowerCase().contains(searchTerm.toLowerCase()) ||
                   ebrettiParts.get(i).description.toLowerCase().contains(searchTerm.toLowerCase()))
                {
                    currentEbrettiParts.add(ebrettiParts.get(i));
                    currentButtons_Adds.add(buttons_Adds.get(i));
                    currentButtons_Removes.add(buttons_Removes.get(i));
                }
            }
        }
    }

    /**
     * Method sets the low and high index of the current partial ebrettiPartslist to display!!
     * It will be fired from the method that generates the partial list.
     */
    private void adjustDetailsForCurrentPartList(ArrayList<EbrettiPart> partialList)
    {
        // if list is empty
        if(partialList.size() == 0)
        {
            numberOfPartListView_Intervals = 1;
            System.out.println("There are no parts on list, so number of intervals set to 1");
            currentPartListView_LowIndex = 0;
            currentPartListView_HighIndex = 0;
            return;
        }

        // if there are members on list, decide # of segments to chop current partial list into
        numberOfPartListView_Intervals = (int)Math.ceil((double)partialList.size() / 10); // rounding div up
        System.out.println("From adjustDetailsForCurrentPartList: Number of intervals decided to " + numberOfPartListView_Intervals
                           + " from the list containing in total " + partialList.size());

        setViewedInterval(1);
    }
}