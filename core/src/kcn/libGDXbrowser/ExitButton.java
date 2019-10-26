package kcn.libGDXbrowser;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Vector2;
import kcn.methodreferencing.MethodReference;
import kcn.utility.WindowMath;


/* I need to change the way input is handled. Not working atm: Concurrency exceptions occur when
* button is 'removed from focus'. */
public class ExitButton
        implements IButton
{
    public MethodReference methodOnClickEvent;
    private Texture buttonImage; // texture that will be displayed when button is drawn

    //    private String buttonLabel; // text that will be written in relation to texture
//    public Vector2 labelRelativePosition; // position adjustment/difference label will drawn from texture
    private String pathToTextureFile;  // path to file
    private Vector2 position; // position in program window where button will be drawn

    public ExitButton(String pathToTextureFile, Vector2 buttonPosition/*, String BUTTONLABEL*/)
    {
        this.pathToTextureFile = pathToTextureFile;
        position = buttonPosition;

        buttonImage = new Texture(pathToTextureFile);

//        buttonLabel = BUTTONLABEL;


        // initializing method that will be returned when a button is clicked. This is so messy. Because, try..
        try
        {
            methodOnClickEvent = new MethodReference((Object)this,
                                                     this.getClass().getMethod("actIfHit_CallbackMethod",
                                                                               Vector2.class));
            //  System.out.println("From EbrettiSelectionButton: MethodReference report that it works: " + !methodOnClickEvent.isReferenceBroke());
        } catch(NoSuchMethodException e)
        {
            System.out.println("From exit-button: Failure to create");
        }
    }

    public ExitButton(int x, int y)
    {
        this("buttons/exitbutton.png", new Vector2(x, y));
    }

    public Vector2 getPosition(){ return position; }

    @Override
    public void drawButton(Batch batch)
    {
        batch.draw(buttonImage, position.x, position.y);
    }

    @Override
    public GlyphLayout drawLabel(Batch batch, float x, float y)
    {
        return null;
    }

    @Override
    public void setPosition(int x, int y)
    {
        position.x = x;
        position.y = y;
    }

    //// method, pulled on click, will quit program
    @Override
    public void actIfHit_CallbackMethod(Vector2 hitPoint)
    {
        if(WindowMath.isPointWithinArea(hitPoint,
                                        new Vector2(buttonImage.getWidth(), buttonImage.getHeight()),
                                        position
                                       ))
        {
            Gdx.app.exit();
        }
    }
}
