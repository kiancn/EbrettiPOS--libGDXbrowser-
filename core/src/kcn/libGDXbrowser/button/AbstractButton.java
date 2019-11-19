package kcn.libgdxbrowser.button;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Vector2;
import kcn.methodreferencing.MethodPack;
import kcn.methodreferencing.CallbackMethod;
import kcn.utility.WindowMath;

/**
 * The decision to have this abstract button class was made to enable easy base functionality on
 * possible extending buttons.
 * Class is made for close compatibility with IButton interface:
 * Extending classes could/should override methods from IButton;
 * call super.drawButton in extending class and super.actIfHit_CallbackMethod
 * to utilize base class funtionality.
 */
public abstract class AbstractButton
        implements IButton
{
    /* these are methods supplied that will be run when button is clicked */
    public final MethodPack callbackMethods;
    // method
    protected CallbackMethod methodOnClickEvent;
    /* private Texture buttonTexture; // texture of button, standard supplied */
    private Vector2 position; // position of button, designating lower left corner of button
    // clicked
    private Vector2 buttonExtent; // x value is extent on x-axis from position;

    public AbstractButton(int positionX, int positionY, int buttonExtentX, int buttonExtentY)
    {
        position = new Vector2(positionX, positionY);
        buttonExtent = new Vector2(buttonExtentX, buttonExtentY);
        callbackMethods = new MethodPack();

        System.out.println("Button exists at " + position + " & pretends to extend: " + buttonExtent);
    }

    private Vector2 getButtonExtent(){ return buttonExtent; }
    public MethodPack getCallbackMethods(){ return callbackMethods; }
    public Vector2 getPosition(){ return position; }
    public CallbackMethod getMethodOnClickEvent(){ return methodOnClickEvent; }

    /** Method contains all the instructions needed to render button graphic  */
    @Override
    public abstract void drawButton(Batch batch);
    /** Method contains all the instructions needed to render button label text  */
    @Override
    public abstract GlyphLayout drawLabel(Batch batch, float x, float y);

    @Override
    public void setPosition(int x, int y)
    {
        position.x = x;
        position.y = y;
    }

    /**
     * Method checks if button is hit and executes supplied callback methods.
     * Method should never by called directly, but be supplied to an input processor
     * and get pulled on input events.
     * <p>If an overriding class does not want method-pack of method references fired
     * at exactly when button is hit, it should override this method completely check and </p>
     */
    @Override
    public void actIfHit_CallbackMethod(Vector2 hitPoint)
    {
        if(WindowMath.isPointWithinArea(hitPoint, buttonExtent, position))
        {
            System.out.println("Button position: " + position + " | Extent: " + buttonExtent+ " | Hit " +
                               "detected at: " +hitPoint);

            callbackMethods.run();
        }
    }
}
