package kcn.libGDXbrowser.menu;
// by KCN

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

import kcn.methodreferencing.MethodReference;
import kcn.utility.WindowMath;
import kcn.utility.TO;

import java.util.ArrayList;

public class Menu
{

    public final String name;

    public final ArrayList<MenuBar> bars;
    //    private final int ID;
    public MethodReference touchUpCallbackMethod; // mouse-up = touch-up; named for consistency

    MenuManager manager; // the manager is the active context that this menu exists via



    public Menu(String NAME, MenuManager MANAGER)
    {
        name = NAME;
        manager = MANAGER;

//        ID = IDCounter.getNewId();
        bars = new ArrayList<>();

        /* making the methodreferences seems cumbersome because fetching the method-objects might throw
        that NoSuchMethodException */
        try
        {
            touchUpCallbackMethod = new MethodReference((Object)this,
                                                        this.getClass().getMethod("isMenuHit",
                                                                                  Vector2.class));
            System.out.println("isMenuHit method reference is broke: " + touchUpCallbackMethod.isReferenceBroke());
        } catch(NoSuchMethodException e)
        {
            System.out.println("isMenuHitMethod was not a hit: Method object could not be created\n" + TO.red(e.getLocalizedMessage()));
        }
    }

    public void drawMenu(Batch batch)
    {
        for(MenuBar bar : bars)
        {
            bar.drawBarTexture(batch);
            bar.drawBarText(batch);
            bar.drawClickAnimation(batch);
        }
    }

    /**
     * Method checks if each menu bar was hit, if it resets time since hit
     * and 'switches to the menu' hit.
     */
    public boolean isMenuHit(Vector2 hitPoint)
    {
        for(MenuBar bar : bars)
        {
            if(WindowMath.isPointWithinArea(
                    hitPoint,
                    new Vector2(bar.getBarTexture().getWidth(),
                                bar.getBarTexture().getHeight()),
                    bar.getTextureDrawingPosition()))
            {
                bar.timeSinceLastClick = 0;
                System.out.println(TO.underline("We have a hit on " + bar.getEntity().getName()));

                // if a menu-bar was hit, contact manager to switch focus to menu hit
                manager.switchToNamedMenu(bar.getEntity().getName());

                return true;
            }
        }
        // if menu was not hit, return false;
        return false;
    }

    /* menu bars will be positioned in an equal y-spread from center; and fixed center x */
    public void arrangeMenuBars_Center()
    {
        for(int i = 0; i < bars.size(); i++)
        {
            bars.get(i).getEntity().setPosX(Gdx.graphics.getWidth() / 2F);
            bars.get(i).getEntity().setPosY(
                    Gdx.graphics.getHeight() / 2F +
                    (bars.size() * bars.get(i).barTexture.getHeight() * 1.5F) -
                    (i * bars.get(i).barTexture.getHeight() * 1.5f));
        }
    }

    public void arrangeMenuBars_Right()
    {
        for(int i = 0; i < bars.size(); i++)
        {
            bars.get(i).getEntity().setPosX(Gdx.graphics.getWidth() - 300);
            bars.get(i).getEntity().setPosY(Gdx.graphics.getHeight() / 2F + (bars.size() * 60) - (i * 60));
        }
    }

    /**
     * Method arranges menu with a
     */
    public void arrangeMenuBars_Left()
    {
        arrangeMenuBars(Gdx.graphics.getWidth() / 4, 0,
                        Gdx.graphics.getHeight() / 2F - bars.size() * 60,
                        60);
    }

    /**
     * Method arranges positions like this:
     * <p> - The body of the menu will have global xAdjustment position on x-axis.
     * <p> - Absolute y-position of each menu bar will be adjusted by the global y adjustment variable.
     * <p> - The relative y-axis position of each bar will be adjusted by the per item y-adjustment
     * variable. The items in question are all the menu-bars in the bars-list.
     * <p>NB: bars heights are adjusted so that the first bar is placed topmost,
     * and numerically going up, bars go down.</p>
     */
    public void arrangeMenuBars(float xAdjustment_Global, float xAdjustment_Item,
                                float yAdjustment_Global, float yAdjustment_Item)
    {
        for(int i = 0; i < bars.size(); i++)
        {
            bars.get(i).getEntity().setPosX(xAdjustment_Global + i * xAdjustment_Item);
            bars.get(i).getEntity().setPosY(yAdjustment_Global + (bars.size() * yAdjustment_Item) - (i * yAdjustment_Item));
        }
    }
}