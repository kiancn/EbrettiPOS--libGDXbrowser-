package kcn.libGDXbrowser.menu;
// by KCN
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Vector2;
import kcn.libGDXbrowser.SheetAnimation;
import kcn.libGDXbrowser.TinyEntity;

public class MenuBar
{
    public float timeSinceLastClick; // float registers delta-time since last click
    public Texture barTexture; // texture that defines the size and dimensions of the menubar
    SheetAnimation clickAnimation; // the animation played on button click
    String labelString; // the text that goes on the menubar
    BitmapFont fontFormattedText; // font will prepare the labelString and hand it over for display
    private float clickAnimationDuration; // float determines click-animation-duration
    private TinyEntity entity; // keeps position, and name

    private final String standardBarTexture = "bar200x40_blueywhity.png";


    public MenuBar(String labelString, String texturePath, int positionX, int positionY)
    {
        // entity's position fixes the relative positions of text and texture.
        entity = new TinyEntity(labelString, positionX, positionY);

        this.labelString = labelString;
        // loading texture
        barTexture = new Texture(Gdx.files.internal(texturePath));

        fontFormattedText = new BitmapFont();
        fontFormattedText.setColor(Color.CHARTREUSE);


        clickAnimation = new SheetAnimation("ebretti-logo-100x36.png", 1, 1, 0.2f);

        clickAnimationDuration = 0.9F;
        timeSinceLastClick = -1;
        labelColor = Color.WHITE;
    }

    public MenuBar(String labelString)
    {
        // entity's position fixes the relative positions of text and texture.
        entity = new TinyEntity(labelString, 0, 0);

        this.labelString = labelString;
        // loading texture
        barTexture = new Texture(Gdx.files.internal(standardBarTexture));

        fontFormattedText = new BitmapFont();
        fontFormattedText.setColor(Color.CHARTREUSE);


        clickAnimation = new SheetAnimation("ebretti-logo-100x36.png", 1, 1, 0.2f);

        clickAnimationDuration = 1.6F;
        timeSinceLastClick = -1;

        labelColor = Color.WHITE;
    }

    public MenuBar(String LABELSTRING, String TEXTUREPATH)
    {
        this(LABELSTRING);

        labelString = LABELSTRING;
        barTexture = new Texture(Gdx.files.internal(TEXTUREPATH));
    }

    public TinyEntity getEntity()
    {
        return entity;
    }
    // position where text will be drawn. coordinate origin is lower-Edge
    public Vector2 getTextDrawingPosition()
    {
        return new Vector2(entity.getPosX() - barTexture.getWidth() / 2 + barTexture.getWidth() / 2 - labelString.length() * 3,
                           entity.getPosY() - barTexture.getHeight() / 2 + 27);
    }

    // position where bar will be drawn. coordinate origin is lower-Edge
    public Vector2 getTextureDrawingPosition()
    {
        return new Vector2(entity.getPosX() - barTexture.getWidth() / 2,
                           entity.getPosY() - barTexture.getHeight() / 2);
    }

    public Texture getBarTexture()
    {
        return barTexture;
    }

    /* DRAWING SECTION */

    /* these next methods are called in the drawMenu() method
     * - first drawBarTexture, then drawBarText, then drawClickAnimation
     * - no need for a new Batch, just pass an already useful one in */

    /** Method draws bar texture.
     * <p></p>Method calls draw on supplied batch; drawing barTexture : Method is called from drawMenu.*/
    public void drawBarTexture(Batch batch)
    {
        batch.draw(barTexture,
                   getTextureDrawingPosition().x,
                   getTextureDrawingPosition().y);
    }

    private Color getLabelColor()
    {
        return labelColor;
    }

    private void setLabelColor(Color labelColor)
    {
        this.labelColor = labelColor;
    }

    private Color labelColor;

    /** Method draws labelString as bar text.
     * <p></p> Method calls draw on BitmapFont object, which uses supplied batch; drawing
     * fontFormattedText: Method is called from drawMenu.*/
    public GlyphLayout drawBarText(Batch batch)
    {
        fontFormattedText.setColor(labelColor);
        return fontFormattedText.draw(batch, labelString,
                                      getTextDrawingPosition().x,
                                      getTextDrawingPosition().y);
    }

    /** Method draws an animation at mouse-cursor position for clickAnimationDuration seconds. */
    public void drawClickAnimation(Batch batch)
    {
        if(timeSinceLastClick > clickAnimationDuration){timeSinceLastClick = -1;}
        if(timeSinceLastClick != -1)
        {
            batch.draw(clickAnimation.getNextFrame(),
                       Gdx.input.getX(),
                       (Gdx.input.getY() - Gdx.graphics.getHeight()) * -1);
            timeSinceLastClick += Gdx.graphics.getDeltaTime();
        }
    }
}
