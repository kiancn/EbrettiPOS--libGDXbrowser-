package kcn.libgdxbrowser.grind.contentpages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import kcn.libgdxbrowser.SignallingInputProcessor;
import kcn.libgdxbrowser.button.templates.ActionButton;
import kcn.libgdxbrowser.content.Content;
import kcn.libgdxbrowser.content.IDrawContent;
import kcn.methodreferencing.CallbackMethod;

public class RedContent extends Content implements IDrawContent
{

    BitmapFont fontRenderer;
    ActionButton plus_ActionButton;
    ActionButton minus_ActionButton;
    boolean inFocus; // keeps track of whether content is in focus; starts at no.
    SignallingInputProcessor inputProcessor; // keeps tabs on presses and lifts.
    private CallbackMethod plus_ButtonHit_CallbackMethod;
    private CallbackMethod minus_ButtonHit_CallbackMethod;

    int numberCount_ClickProxy;

    public RedContent(SignallingInputProcessor INPUTPROCESSOR)
    {
        inFocus = false;

        /* this name must match the name assigned to a Menu, if the content is to
        be brought to focus by a click on a menu bar. */
        name = "Red Content";

        fontRenderer = new BitmapFont();

        plus_ActionButton = new ActionButton(150, 150, "Plus", "ui/green_tick.png");
        minus_ActionButton = new ActionButton(220, 150, "Minus", "ui/green_cross.png");

        plus_ButtonHit_CallbackMethod = new CallbackMethod((Object)this, "plusButtonHit_CallbackMethod");
        minus_ButtonHit_CallbackMethod = new CallbackMethod((Object)this, "minusButtonHit_CallbackMethod");

        System.out.println("From RedContent - buttonHit_CallbackMethod reports that it is " + ((!plus_ButtonHit_CallbackMethod.isReferenceBroke()) ? "functional." : "broke."));

        plus_ActionButton.callbackMethods.add(plus_ButtonHit_CallbackMethod);
        minus_ActionButton.callbackMethods.add(minus_ButtonHit_CallbackMethod);

        inputProcessor = INPUTPROCESSOR;
    }

    @Override
    public void drawContent(Batch batch)
    {
        Gdx.gl.glClearColor(255f / 255f, 67f / 255f, 66f / 255f, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        plus_ActionButton.drawButton(batch);
        fontRenderer.draw(batch, plus_ActionButton.buttonLabel,
                          plus_ActionButton.getPosition().x - 20,
                          plus_ActionButton.getPosition().y);

        minus_ActionButton.drawButton(batch);
        fontRenderer.draw(batch, minus_ActionButton.buttonLabel,
                          minus_ActionButton.getPosition().x - 20,
                          minus_ActionButton.getPosition().y);

        fontRenderer.draw(batch, "Red Content Text", 20, 100);
        fontRenderer.draw(batch, "Clicks:" + numberCount_ClickProxy, 200, 350);

    }

    /***/
    @Override
    public void gainFocus()
    {
        inputProcessor.getTouchUpCallbackMethods().add(minus_ActionButton.getMethodOnClickEvent());

        inputProcessor.getTouchUpCallbackMethods().add(plus_ActionButton.getMethodOnClickEvent());
        inFocus = true;
        super.gainFocus();
    }

    @Override
    public void loseFocus()
    {
        inputProcessor.getTouchUpCallbackMethods().remove(minus_ActionButton.getMethodOnClickEvent());

        inputProcessor.getTouchUpCallbackMethods().remove(plus_ActionButton.getMethodOnClickEvent());
        inFocus = false;
        super.loseFocus();
    }

    public void plusButtonHit_CallbackMethod()
    {
        numberCount_ClickProxy++;
//        System.out.println("You hit me, ahh, you hit me. ");
    }

public void minusButtonHit_CallbackMethod(){
        numberCount_ClickProxy--;
    System.out.println("You hit me, ahh, you hit me. ");
}

}