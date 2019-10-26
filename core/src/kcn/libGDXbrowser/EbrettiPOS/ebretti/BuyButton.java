package kcn.libGDXbrowser.EbrettiPOS.ebretti;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Vector2;
import kcn.libGDXbrowser.IButton;
import kcn.libGDXbrowser.SheetAnimation;
import kcn.methodreferencing.MethodReference;
import kcn.utility.WindowMath;

public class BuyButton
        implements IButton
{
    public MethodReference methodOnClickEvent;
    public float durationSinceLastClick;
    public float animationPlayDuration;
    public Vector2 position;
    SheetAnimation buttonClickAnimation; // animation shown on button click
    Texture buttonTexture;

    public BuyButton(int xPosition, int yPosition)
    {
        position = new Vector2(xPosition, yPosition);

//        buttonClickAnimation = new SheetAnimation("obesewomandoingcartwheels.png",
        buttonClickAnimation = new SheetAnimation("skeletonDance-anim-1200x1200.png",
                                                  10,
                                                  5,
                                                  1.3f);

        buttonTexture = new Texture("buybutton.png");

        animationPlayDuration = 4f;

        try
        {
            methodOnClickEvent = new MethodReference((Object)this,
                                                     this.getClass().getMethod("actIfHit_CallbackMethod",
                                                                               Vector2.class));
            System.out.println("BuyButton MethodReference is working: " + !methodOnClickEvent.isReferenceBroke());

        } catch(NoSuchMethodException e)
        {
            System.out.println("From buy button: Failure to create");
        }

        durationSinceLastClick = 1000; //a number bigger than
    }

    @Override
    public void drawButton(Batch batch)
    {
        batch.draw(buttonTexture, position.x, position.y);

        // 7 overweight women doing summersaults across bottom of screen
        if(durationSinceLastClick <= animationPlayDuration)
        {
            int numberOfAnimations = 11;
            int perAnimationXAdjustment = 180;

            durationSinceLastClick += Gdx.graphics.getDeltaTime();

            for(int i = 0; i<numberOfAnimations; i++)
            {
                batch.draw(buttonClickAnimation.getNextFrame(),
                           i*perAnimationXAdjustment,
                           0);
            }
        }
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

    @Override
    public void actIfHit_CallbackMethod(Vector2 hitPoint)
    {
        if(WindowMath.isPointWithinArea(hitPoint,
                                        new Vector2(buttonTexture.getWidth(), buttonTexture.getHeight()),
                                        position
                                       ))
        {
            durationSinceLastClick = 0;
            System.out.println("You hit the buy button!");
        }
    }
}
