package kcn.libGDXbrowser.EbrettiPOS.ebretti;

import com.badlogic.gdx.Gdx;
import kcn.libGDXbrowser.Browser;
import kcn.libGDXbrowser.menu.Menu;
import kcn.libGDXbrowser.menu.MenuBar;


/** This class contains methods that return menus. */
public class EbrettiMenus
{
    Browser browser;

    public EbrettiMenus(Browser BROWSER)
    {
        browser = BROWSER;
    }

    /** Method adds standard bars to menu supplied and return it */
    private Menu addStandardMenuBars(Menu returnMenu)
    {
        returnMenu.bars.add(new MenuBar("Main Menu"));
        returnMenu.bars.add(new MenuBar("Search for Parts"));
        returnMenu.bars.add(new MenuBar("Shopping Cart"));

        /* This gives all the menus the same placement - upper-right-corner-ish */
        returnMenu.arrangeMenuBars_Right();

        returnMenu.bars.add(new MenuBar("Go Back", "bar200x40_blueywhity.png",
                                        Gdx.graphics.getWidth()-300  ,300));
//        returnMenu.bars.add(new MenuBar("Go Back", "buttons/Button10.png",
//                                        Gdx.graphics.getWidth()-300  ,300));
        return returnMenu;
    }

    /** method returns an appropriate Menu-object: the main menu*/
    public Menu mainMenu()
    {
        /* A newing menu needs to know it's name and the menu manager it belongs to. */
        Menu returnMenu = new Menu("Main Menu", browser.menuManager);
        /* doing the actual work if adding menu bars (the options) to each menu
         * is done by getStandardMenu; */
        return addStandardMenuBars(returnMenu);
    }

    /** method returns an appropriate Menu-object: the Search For Parts menu */
    public Menu searchForPartsMenu()
    {
        Menu returnMenu = new Menu("Search for Parts", browser.menuManager);
        return addStandardMenuBars(returnMenu);
    }

    /** method returns an appropriate Menu-object */
    public Menu shoppingCartMenu()
    {
        Menu returnMenu = new Menu("Shopping Cart", browser.menuManager);
        return addStandardMenuBars(returnMenu);
    }
}
