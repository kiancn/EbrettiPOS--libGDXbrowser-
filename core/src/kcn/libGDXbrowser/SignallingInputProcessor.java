package kcn.libGDXbrowser;
// by KCN

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import kcn.methodreferencing.*;
import kcn.utility.TO;


/**
 * Methods in this InputProcessor (-implementing-) class are executed when input events occur.
 * <p>The idea is to supply MethodReferences from objects that want to be
 * informed of input events.
 * <p></p> * (Be aware that, as such, there is no distinction between
 * left and and right mouse clicks; libGDX was originally designed for android). </p>
 *
 * the need to return a boolean is inherited, and return values are not used or meaningful at the moment
 */
public class SignallingInputProcessor
        implements InputProcessor
{
    public final MethodPack touchUpCallbackMethods; // pack that will execute when button is released
    MethodReference pressedKeyCallbackMethod; // these two methodreferences are going to replaced
    MethodReference upKeyCallbackMethod;        // with method-packs.
    private MethodPack charTypedCallbackMethods;

    // methodpacks
    MethodPack keyDownCallbackMethods;
    MethodPack keyUpCallbackMethods;

    public SignallingInputProcessor(MethodReference pressedKeyCallbackMethod, MethodReference upKeyCallbackMethod)
    {
        this.pressedKeyCallbackMethod = pressedKeyCallbackMethod;
        this.upKeyCallbackMethod = upKeyCallbackMethod;

        keyDownCallbackMethods = new MethodPack();
        keyUpCallbackMethods = new MethodPack();


        charTypedCallbackMethods = new MethodPack();
        touchUpCallbackMethods = new MethodPack();
    }

    public MethodPack getCharTypedCallbackMethods()
    {
        return charTypedCallbackMethods;
    }

    public MethodPack getTouchUpCallbackMethods()
    {
        return touchUpCallbackMethods;
    }

    /**
     * Called when a key was pressed
     *
     * @param keycode one of the constants in {@link Input.Keys}
     * @return whether the input was processed
     */
    @Override
    public boolean keyDown(int keycode)
    {
        if(pressedKeyCallbackMethod != null)
        {
            pressedKeyCallbackMethod.run_paramT(keycode);
        }
        return false;
    }

    /**
     * Called when a key was released
     *
     * @param keycode one of the constants in {@link Input.Keys}
     * @return whether the input was processed
     */
    @Override
    public boolean keyUp(int keycode)
    {
        if(pressedKeyCallbackMethod != null)
        {
            upKeyCallbackMethod.run_paramT(keycode);
        }
        return false;
    }

    /**
     * Called when a key was typed
     *
     * @param character The character
     * @return whether the input was processed
     */
    @Override
    public boolean keyTyped(char character)
    {
        charTypedCallbackMethods.run(character);
        return false;
    }

    /**
     * Called when a finger was lifted or a mouse button was released. The button parameter will be on iOS.
     *
     * @param screenX
     * @param screenY
     * @param pointer the pointer for the event.
     * @param button  the button
     * @return whether the input was processed
     */
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button)
    {
        System.out.println(TO.yellow("You released that cursor, yeahh. At X:" + screenX + "\tY: " + (Gdx.graphics.getHeight() - screenY)));

        touchUpCallbackMethods.run(new Vector2(screenX, screenY));
        return false;
    }

    // touch-down (both touch and ) is not implemented; but the method needs to be overriden anyway
    /**
     * Called when the screen was touched or a mouse button was pressed. The button parameter will be  on iOS.
     *
     * @param screenX The x coordinate, origin is in the upper left corner
     * @param screenY The y coordinate, origin is in the upper left corner
     * @param pointer the pointer for the event.
     * @param button  the button
     * @return whether the input was processed
     */
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button){ return false; }

    // not implemented; but the method needs to be overriden anyway
    /**
     * Called when a finger or the mouse was dragged.
     *
     * @param screenX
     * @param screenY
     * @param pointer the pointer for the event.
     * @return whether the input was processed
     */
    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer)
    {
        return false;
    }
    // not implemented; but the method needs to be overriden anyway
    /**
     * Called when the mouse was moved without any buttons being pressed. Will not be called on iOS.
     *
     * @param screenX
     * @param screenY
     * @return whether the input was processed
     */
    @Override
    public boolean mouseMoved(int screenX, int screenY)
    {
        return false;
    }

    // not implemented; but the method needs to be overriden anyway
    /**
     * Called when the mouse wheel was scrolled. Will not be called on iOS.
     *
     * @param amount the scroll amount, -1 or 1 depending on the direction the wheel was scrolled.
     * @return whether the input was processed.
     */
    @Override
    public boolean scrolled(int amount)
    {
        return false;
    }
}
