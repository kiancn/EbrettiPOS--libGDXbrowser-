package kcn.libgdxbrowser.grind;


import kcn.libgdxbrowser.Browser;
import kcn.libgdxbrowser.SignallingInputProcessor;
import kcn.libgdxbrowser.grind.contentpages.BlueContent;
import kcn.libgdxbrowser.grind.contentpages.RedContent;

/**
 * This class can be considered the setup-file for the Browser object.<p>
 * This class is as such an example of how such a setup could look.<p>
 * The principle is that all menus and content are added during construction;
 * the browser handles 'everything' from there and just needs to be called from render()
 * <p> I cannot for the life of me figure out if this </p>
 */
public class BrowserBase
{
    /*  a browser has two components; browsers carry a 1) MenuManager, and a 2) ContentManager */
    public final Browser browser;

    public BrowserBase(SignallingInputProcessor inputProcessor)
    {

        browser = new Browser(inputProcessor);

        PreparedMenu preparedMenu = new PreparedMenu(browser);

        /* a segment defining menus */
        browser.menuManager.getMenus().add(preparedMenu.blueContentMenu());
        browser.menuManager.getMenus().add(preparedMenu.redContentMenu());

        /* a segment defining content */
        browser.contentManager.getContents().add(new BlueContent());
        browser.contentManager.getContents().add(new RedContent(inputProcessor));

        /*Then the 'switching on' of the first content to display */
        browser.menuManager.switchToNamedMenu("Blue Content");
        browser.contentManager.switchToContentByName("Blue Content");
    }
}
