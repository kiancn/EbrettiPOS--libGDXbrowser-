package kcn.libGDXbrowser.EbrettiPOS.ebretti;
// by KCN
import kcn.libGDXbrowser.Browser;
import kcn.libGDXbrowser.EbrettiPOS.EbrettiContentPages.SearchForPartsContent;
import kcn.libGDXbrowser.EbrettiPOS.EbrettiContentPages.ShoppingCartContent;
import kcn.libGDXbrowser.EbrettiPOS.EbrettiContentPages.MainMenuContent;
import kcn.libGDXbrowser.SignallingInputProcessor;


import java.util.ArrayList;

/** This class can be considered the setup-file for the Browser object.<p>
 * This class is as such an example of how such a setup could look.<p>
 * The principle is that all menus and content are added during construction;
 * the browser handles 'everything' from there and just needs to be called from render() */
public class EbrettiPOS
{
    public final Browser browser;

    public ShoppingCart shoppingCart;

    ArrayList<EbrettiPart> ebrettiParts; // this list will carry the from-file-loaded spare bike parts

    // this path is annoyingly absolute; another solution with relative path is needed
    private final String pathToPartsCSVFile = "core\\assets\\partsCSV.txt";


    public EbrettiPOS(SignallingInputProcessor inputProcessor)
    {
        /* newing up the browser that runs it all */
        browser = new Browser(inputProcessor);
        /*  newing up the cart for parts */
        shoppingCart = new ShoppingCart();
        /* preparedMenus contains just methods for generating the menus prepared here */
        EbrettiMenus preparedMenus = new EbrettiMenus(browser);

        ebrettiParts = new ArrayList<>();

        /* using static utility method to load the spare part list from file. splitString is your separator
        *  of choice; my current list is ;-separated */
        ebrettiParts = EbrettiFileIO.load_CSV_To_ArrayList(pathToPartsCSVFile, ";");
        System.out.println("From EbrettiPOSExample constructor: Length of parts list is " + ebrettiParts.size());

        /* generating menus and adding them to menu-manager */
        browser.menuManager.getMenus().add(preparedMenus.mainMenu());
        browser.menuManager.getMenus().add(preparedMenus.searchForPartsMenu());
        browser.menuManager.getMenus().add(preparedMenus.shoppingCartMenu());

        /* adding content pages */
        browser.contentManager.getContents().add(new MainMenuContent());
        browser.contentManager.getContents().add(new SearchForPartsContent(inputProcessor, shoppingCart,ebrettiParts));
        browser.contentManager.getContents().add(new ShoppingCartContent(shoppingCart,inputProcessor));

        /*
         'switching on' main menu; content cannot be added before there is an active menu; a currentMenu
         is needed before first frame.
        */
        browser.menuManager.switchToNamedMenu("Main Menu");

        /* then content manager is switched on ... */
        browser.contentManager.switchToContentByName("Main Menu");
    }

}
