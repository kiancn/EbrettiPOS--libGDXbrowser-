package kcn.libgdxbrowser.grind;

import kcn.libgdxbrowser.Browser;
import kcn.libgdxbrowser.menu.Menu;
import kcn.libgdxbrowser.menu.MenuBar;


/**
 * This class contains methods that return a Menu object;
 * each method supplies a specific menu (typically
 * specifying each menu-bar in a separate method).
 * <p></p> <i>This class is specific to the example; every implementation
 * needs a 'prepared set of menu', but as it stand I have not been able to
 * find a proper shape of either a super class, super abstract class or
 * interface: since the implementation of menus would vary greatly
 * according to the specific need of the implementation; but left
 * without a template prepared menu - what then? Annotations, don't
 * know how.?
 * I now know! </i>
 */
public class PreparedMenu
{
    // browser object; each menubar knows its menuManager through the browser.
    private Browser browser;

    public PreparedMenu(Browser BROWSER)
    {
        browser = BROWSER;
    }


    /* Individual menu methods: there are a thousand ways to start optimizing the
     * following, but I kept it clear on purpose. */

    /**
     * Returns a Menu object with two MenuBars; blue and red
     */
    public Menu blueContentMenu()
    {
        // A) creating menu object to return.
        /* A newing menu needs to know it's name and the menu manager it belongs to. */
        Menu returnMenu = new Menu("Blue Content", browser.menuManager);
        /* doing the actual work if adding menu bars (the options) to each menu
         * is done by getStandardMenu; */

        // adding each menu bar is then appropriate (newing it at the same time)
        returnMenu.bars.add(new MenuBar("Blue Content"));
        returnMenu.bars.add(new MenuBar("Red Content"));

        /* Method positions menus according to a rigth adjust scheme, displaying
         * menubars in a downward cascade */
        returnMenu.arrangeMenuBars(100, 0,
                                   560, 50);

        return returnMenu;
    }

    /**
     * Returns a Menu object with two MenuBars; red and blue
     */
    public Menu redContentMenu()
    {

        Menu returnMenu = new Menu("Red Content", browser.menuManager);

        returnMenu.bars.add(new MenuBar("Blue Content"));
        returnMenu.bars.add(new MenuBar("Red Content"));

        returnMenu.arrangeMenuBars(380, 0,
                                   0, 50);
        return returnMenu;
    }
}