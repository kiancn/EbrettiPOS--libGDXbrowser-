package kcn.libGDXbrowser.button.templates;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Vector2;
import kcn.libGDXbrowser.SheetAnimation;
import kcn.libGDXbrowser.button.AbstractButton;
import kcn.libGDXbrowser.button.IButton;
import kcn.methodreferencing.MethodReference;

/**
 * Instances of ActionButton receives click events and is able of execute any supplied method references.
 * There is a constructor which takes only a position and a label; it will provide a 'standard skinned'
 * button at the position provided; it is recommended to provide own skin.
 */
public class ActionButton
        extends AbstractButton
        implements IButton
{
    /* path to standard graphic for this button */
    private final String pathToStandardTexture = "find a candidate";
    /* path to standard animation graphic for this button */
    private final String pathToStandardAnimation = "find a candidate";
    /* this string will be displayed as a label centered on the button graphic */
    public String buttonLabel;
    public Vector2 labelPosition;
    // button graphic
    Texture buttonTexture;
    SheetAnimation buttonClickAnimation;

    /**
     *
     */
    private ActionButton(int positionX, int positionY, String labelText, String pathToTexture)
    {

        this(positionX, positionY, new Texture(pathToTexture));

        buttonLabel = labelText;
        labelPosition = setLabelPositionCentered();
    }

    public ActionButton(int positionX, int positionY, Texture BUTTONTEXTURE)
    {
        super(positionX, positionY, BUTTONTEXTURE.getWidth(), BUTTONTEXTURE.getWidth());

        buttonTexture = BUTTONTEXTURE;

        methodOnClickEvent = new MethodReference((Object)this,
                                                 "actIfHit_CallbackMethod",
                                                 Vector2.class);

    }

    public Vector2 setLabelPositionCentered()
    {
        return new Vector2(getPosition().x + buttonTexture.getWidth() / 2 - buttonLabel.length() * 13,
                           getPosition().y + buttonTexture.getHeight() / 2 - 7);
    }

    @Override
    public void drawButton(Batch batch)
    {
        batch.draw(buttonTexture, getPosition().x, getPosition().y);
    }

    @Override
    public GlyphLayout drawLabel(Batch batch, float x, float y)
    {
        return null;
    }


}
