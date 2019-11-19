package kcn.libgdxbrowser;
// by KCN

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import kcn.methodreferencing.CallbackMethod;

/**
 * Class instance will keep track of keyboard input of W, A, S, and D keys;
 * and, on registering key presses, will move a TinyEntity in a pattern of
 * <p>W - moves entity distance +'movementIncrement' on y-axis.</p>
 * <p>A - moves entity distance -'movementIncrement' on x-axis.</p>
 * <p>S - moves entity distance -'movementIncrement' on y-axis.</p>
 * <p>D - moves entity distance +'movementIncrement' on x-axis.</p>
 *
 * <p>This is not a serious solution, but a proof of concept, that works fine.</p>
 * */
public class MoverSimple2D
{
    // position increments entity will move when adjusting position
    public float movementIncrement;
    // methodreferences will be inserted into an input processor,
    // and be executed on event of pressed keys
    public CallbackMethod callbackMethodKeyDown;
    public CallbackMethod callbackMethodKeyUp;
    // the supplied entity to which movement is being applied
    TinyEntity entity;
    // boolean switches that help determine if movement is appropriate
    boolean WisPressed;
    boolean AisPressed;
    boolean SisPressed;
    boolean DisPressed;

    public MoverSimple2D(float movementIncrement, TinyEntity entity)
    {
        this.movementIncrement = movementIncrement;
        this.entity = entity;


        try
        {
            callbackMethodKeyDown = new CallbackMethod((Object)this,
                                                       this.getClass().getMethod("actOnKeyDownInput", int.class));
           // System.out.println("From MoverSimple2D: callbackMethodKeyDown is working " +
            // !callbackMethodKeyDown.isReferenceBroke());

            callbackMethodKeyUp = new CallbackMethod((Object)this,
                                                     this.getClass().getMethod("actOnKeyUpInput", int.class));
           // System.out.println("From MoverSimple2D: callbackMethodKeyUp is working " +
            // !callbackMethodKeyUp.isReferenceBroke());
        } catch(NoSuchMethodException e)
        {
            e.printStackTrace();
        }
    }


    /** Method flips fitting booleans when a key is pressed.*/
    public void actOnKeyDownInput(int keyCode)
    {
        switch(keyCode)
        {
            case Keys.W:
                WisPressed = true;
                break;
            case Keys.A:
                AisPressed = true;
                break;
            case Keys.S:
                SisPressed = true;
                break;
            case Keys.D:
                DisPressed = true;
                break;
            default:
                break;
        }
    }

    /** Method flips fitting booleans when a key is released.*/
    public void actOnKeyUpInput(int keyCode)
    {
        switch(keyCode)
        {
            case Keys.W:
                WisPressed = false;
                break;
            case Keys.A:
                AisPressed = false;
                break;
            case Keys.S:
                SisPressed = false;
                break;
            case Keys.D:
                DisPressed = false;
                break;
            default:
                break;
        }
    }
    /**
     * Method direcly poll WASD and moves entity according to WASD 2D-scheme.
     * The polling way to get keys pressed.
     *  method is intended to be used if an input processor is for some reason
    * not approprite */
    public void manualPolling()
    {
        boolean isWPressed = Gdx.input.isKeyPressed(Keys.W);
        boolean isAPressed = Gdx.input.isKeyPressed(Keys.A);
        boolean isSPressed = Gdx.input.isKeyPressed(Keys.S);
        boolean isDPressed = Gdx.input.isKeyPressed(Keys.D);

        if(isWPressed){entity.setPosY(entity.getPosY() + movementIncrement);}
        if(isSPressed){entity.setPosY(entity.getPosY() - movementIncrement);}
        if(isAPressed){entity.setPosX(entity.getPosX() - movementIncrement);}
        if(isDPressed){entity.setPosX(entity.getPosX() + movementIncrement);}
    }

    /**
     * Method moves entity if appropriate bool is true.
     * */
    public void adjustEntityPosition()
    {
        if(WisPressed)
        {
            // System.out.println("You pressed W");
            entity.setPosY(entity.getPosY() + movementIncrement);
        }
        if(SisPressed)
        {
            // System.out.println("You pressed S");
            entity.setPosY(entity.getPosY() - movementIncrement);
        }
        if(AisPressed)
        {
            // System.out.println("You pressed A");
            entity.setPosX(entity.getPosX() - movementIncrement);
        }
        if(DisPressed)
        {
            // System.out.println("You pressed D");
            entity.setPosX(entity.getPosX() + movementIncrement);
        }
    }
}
