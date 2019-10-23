package kcn.libGDXbrowser.menu;

import com.badlogic.gdx.graphics.g2d.Batch;
import kcn.libGDXbrowser.SignallingInputProcessor;
import kcn.libGDXbrowser.content.ContentManager;

import java.util.ArrayList;

/**
 * Intent:
 * - class instance will take care of registering individual menus
 * - keep track of which menu is in focus
 * - be able to call the 'in-focus' Menu to draw itself
 * - be able to switch to 'named' Menu on command (put named menu 'in focus')
 * <p>
 * - using contains to allow variety between label and menu name
 */
public class MenuManager
{
    public SignallingInputProcessor inputProcessor;
    private ArrayList<Menu> menus; // list of menus
    private Menu currentMenu; // currently 'in-focus'-menu
    private ArrayList<Menu> previousMenus; // previously 'in-focus'-menu
    private String goBackString; // string used to register user click on a 'back button'

    // experimentall, the menumanager gets to know about the ContentManager
    ContentManager contentManager;

    private MenuManager(ArrayList<Menu> MENUS, String goBackString, SignallingInputProcessor processor)
    {
        menus = new ArrayList<>();
        menus.addAll(MENUS);
        currentMenu = menus.get(0);
        this.previousMenus = new ArrayList<>();
        this.goBackString = goBackString;
        inputProcessor = processor;
    }

    public MenuManager(SignallingInputProcessor processor, ContentManager CONTENTMANAGER)
    {
        inputProcessor = processor;
        contentManager = CONTENTMANAGER;

        menus = new ArrayList<>();
        currentMenu = null;
        this.previousMenus = new ArrayList<>();

        this.goBackString = "Go Back";
    }

    public void initializeMenuManager()
    {
        if(menus.size() > 0)
        {
            currentMenu = menus.get(0);
        } else
        {
            System.out.println("From MenuManager: no menus found");
        }
    }

    public boolean setCurrentMenu(String menuName)
    {
        if(menus.size() > 0)
        {
            for(Menu menu : menus)
            {
                if(menu.name.contains(menuName))
                {
                    currentMenu = menu;
                    return true;
                }
            }
        }
        // code only reaches here if no menu was found by supplied string-content
        return false;
    }

    public ArrayList<Menu> getMenus()
    {
        return menus;
    }

    // method will check if supplied name matches the name-attribute of a menu in menus
    public boolean findMenuByName(String menuName)
    {
        for(Menu menu : menus)
        {
            if(menu.name.contains(menuName)){return true;}
        }
        // code only reaches this point if no match was found.
        return false;
    }

    /**
     * method returns true if switch to next menu was successful
     */
    public boolean switchToNamedMenu(String menuName)
    {
        // System.out.println("Hello from switchToNameMenu");

        // goBackString is received as a signal to go back to previously active menu
        if(menuName.contains(goBackString))
        {
            if(previousMenus.size() > 0)
            {
                inputProcessor.getTouchUpCallbackMethods().remove(currentMenu.touchUpCallbackMethod);
                goToPreviousMenu();
                inputProcessor.getTouchUpCallbackMethods().add(currentMenu.touchUpCallbackMethod);
                return true;
            }

        }

        // if menu by supplied name is found
        if(findMenuByName(menuName))
        {
            System.out.println("From switchToNamedMenu: a match for " + menuName + " was found");
            // locate that menu
            for(Menu menu : menus)
            {
                if(menu.name.contains(menuName))
                {
                    if(inputProcessor.getTouchUpCallbackMethods().length() > 0)
                    {
                        inputProcessor.getTouchUpCallbackMethods().remove(currentMenu.touchUpCallbackMethod);
                    }
                    // adding menu to list of previously in-focus menus
                    previousMenus.add(menu);
                    // ... and switch current menu on focus
                    currentMenu = menu;
                    inputProcessor.getTouchUpCallbackMethods().add(currentMenu.touchUpCallbackMethod);

                    // contentManager gets informed of menu changes
                    contentManager.switchToContentByName(currentMenu.name);

                    return true;
                }
            }
        }
        // code only reaches this point if no menu was found: do nothing, return false;
        System.out.println("From MenuManager: No menu change was made.");
        return false;
    }

    private void goToPreviousMenu()
    {
        // only if focus was previously on another, then, ..
        if(previousMenus.size() > 0)
        {
            // setting last active menu as currently active
            currentMenu = previousMenus.get(previousMenus.size() - 1);

            // contentManager gets a chance to react (does nothing yet)
            contentManager.switchToContentByName(currentMenu.name);

            // removing just 'put-in-focus' menu from previousMenus list
            previousMenus.remove(previousMenus.size() - 1);
        } else
        {
            System.out.println("From MenuManager. Debugger says, there is no previous menu.");
        }
    }

    public void drawCurrentMenu(Batch batch)
    {
        if(currentMenu != null)
        {
            currentMenu.drawMenu(batch);
        } else
        {
            System.out.println("From MenuManager. Bugger says currentMenu was null");
        }
    }
}
