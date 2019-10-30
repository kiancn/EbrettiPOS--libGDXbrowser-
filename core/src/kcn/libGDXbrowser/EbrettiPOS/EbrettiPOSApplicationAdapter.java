package kcn.libGDXbrowser.EbrettiPOS;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import kcn.libGDXbrowser.*;
import kcn.libGDXbrowser.EbrettiPOS.ebretti.EbrettiPOS;

/**
 * This is the class that hooks up the Ebretti POS / browser to the libGDX DesktopLauncher
 */
public class EbrettiPOSApplicationAdapter
        extends ApplicationAdapter
{
    SpriteBatch batch; // the batch that draws it all
    /* THE listener for input during execution (multiplexor will be implemented) */
    SignallingInputProcessor inputProcessor;

    EbrettiPOS posEx;

//    ExitButton exitButton;

    // 3 silly attributes only important to the WASD animated movable entity
    // (its a blue flame named Dorothy; as far as she knows, she always burns. )
    MoverSimple2D mover; // completely unimportant, a funny left-over; control a blue flame with WASD
    TinyEntity entity; // completely unimportant, a funny left-over; the entity controlled with WASD, Dorothy
    SheetAnimation characterAnimation;

    @Override
    public void create()
    {
        // This is sillyness leftover from the earliest iteration. I cannot get myself to remove it.
        characterAnimation = new SheetAnimation("spritesheets/flameball-32x32.png",
                                                4,
                                                1,
                                                0.2f);

        entity = new TinyEntity("Dorothy", 1079, 680);
        mover = new MoverSimple2D(3, entity);

        inputProcessor = new SignallingInputProcessor(mover.callbackMethodKeyDown, mover.callbackMethodKeyUp);

        batch = new SpriteBatch();

        Gdx.input.setInputProcessor(inputProcessor);

        posEx = new EbrettiPOS(inputProcessor);


//        exitButton = new ExitButton(Gdx.graphics.getWidth()-80,Gdx.graphics.getHeight()-80);
//        inputProcessor.getTouchUpCallbackMethods().add(exitButton.methodOnClickEvent);
    }

    @Override
    public void render()
    {
        mover.adjustEntityPosition();

        Gdx.gl.glClearColor(4f / 255f, 45f / 255f, 75f / 255f, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        batch.begin();

        posEx.browser.menuManager.drawCurrentMenu(batch);

        posEx.browser.contentManager.drawContent(batch);

        batch.draw(characterAnimation.getNextFrame(), entity.getPosX(), entity.getPosY());

        batch.end();
    }

    @Override
    public void dispose()
    {
        batch.dispose();
    }
}