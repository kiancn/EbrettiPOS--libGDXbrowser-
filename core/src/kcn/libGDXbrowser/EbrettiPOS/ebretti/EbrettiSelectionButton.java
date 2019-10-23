package kcn.libGDXbrowser.EbrettiPOS.ebretti;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Vector2;

import kcn.libGDXbrowser.IButton;
import kcn.libGDXbrowser.SheetAnimation;
import kcn.methodreferencing.*;
import kcn.utility.WindowMath;

// One day it was realized that politics can also be theatre. I'm afraid this happened long ago.
// Kodning er tusind små tricks og en hel masse tung tænkning.

public class EbrettiSelectionButton
        implements IButton
{
    // Customer-basket
    private final String pathToStandardAdditiveButton = "buttons/button-blue-20x20.png";
    private final String pathToStandardRemovalButton = "buttons/button-orange-20x20.png";
    private final String pathToStandardButtonAnimation = "buttons/button-blue-anim20x160.png";
    private final String pathToStandardRemovalButtonAnimation = "buttons/button-orange-anim20x160.png";


    public MethodReference methodOnClickEvent; // this referenced method will act on input received.

    public EbrettiPart connectedPart; // this is the part connected
    public Vector2 position; // position indicate coordinate of lower left corner of texture
    public String buttonLabelText; // it's what you think it is, ohh yeah
    Texture buttonTexture; // button image
    // position; it is assumed that the proportions of the animation will fit proportions of the button.
    SheetAnimation clickedButtonAnimation; // this animation will play when button is clicked at button
    BitmapFont fontRenderer; // this baby will render any label you want
    ShoppingCart connectedShoppingCart;

    private boolean buttonWasRecentlyClicked; // bool turns true on hit click, switched false after duration
    private float timeSinceLastClick; // tracks time since last click in seconds;
    private float animationDuration; // the amount of time animation will run;

    private boolean clickAddsToCart; // bool decides if button adds or subtracts parts from shoppingCart.


    public EbrettiSelectionButton(EbrettiPart connectedPart, ShoppingCart shoppingCart,
                                  boolean buttonClickAddsToCart)
    {
        connectedShoppingCart = shoppingCart;

        clickAddsToCart = buttonClickAddsToCart;

        // initializing method that will be returned when a button is clicked. This is so messy. Because, try..
        try
        {
            methodOnClickEvent = new MethodReference((Object)this,
                                                     this.getClass().getMethod("actIfHit_CallbackMethod",
                                                                               Vector2.class));
        //  System.out.println("From EbrettiSelectionButton: MethodReference report that it works: " + !methodOnClickEvent.isReferenceBroke());
        } catch(NoSuchMethodException e)
        {
            System.out.println("From button connected to part " + connectedPart.ID + ": Failure to create");
        }

        this.connectedPart = connectedPart; // informing the button of the bike-part connected to it

        // just a random starting coordinate; actual position will be updated to fit on-screen content
        this.position = new Vector2(10, 10);


        buttonLabelText = clickAddsToCart ? "Add to basket!" : "Remove";
        fontRenderer = new BitmapFont();

        //deciding texture based on type of button
        buttonTexture = clickAddsToCart ?
                new Texture(pathToStandardAdditiveButton) :
                new Texture(pathToStandardRemovalButton);

        //deciding animation based on type of button
        clickedButtonAnimation = clickAddsToCart ?
                new SheetAnimation(pathToStandardButtonAnimation, 1, 8, 0.3F) :
                new SheetAnimation(pathToStandardRemovalButtonAnimation,
                                   1,
                                   8,
                                   0.3F);

        // setting time animation to fit number of frames */times frame Duration */times 2 = 4,8 seconds;
        animationDuration = 2.4F;
        // setting time since last click to 0 here will make button animate as it appears on screen
        timeSinceLastClick = 0F;
    }

    public MethodReference getMethodOnClickEvent()
    {
        return methodOnClickEvent;
    }

    @Override
    public void drawButton(Batch batch)
    {
        if(!buttonWasRecentlyClicked)
        {
            batch.draw(buttonTexture, position.x, position.y);
        } else
        {
            // sending current frame for rendering.
            batch.draw(clickedButtonAnimation.getNextFrame(), position.x, position.y);
            // updating time since last click
            timeSinceLastClick += Gdx.graphics.getDeltaTime();

            // checking if it is time to not consider last click recent; if so, switch bool
            if(timeSinceLastClick > animationDuration){buttonWasRecentlyClicked = false; }
        }
    }

    public GlyphLayout drawLabel(Batch batch, float x, float y)
    {
        return fontRenderer.draw(batch, buttonLabelText, x, y);
    }

    @Override
    public void setPosition(int x, int y)
    {
        position.x = x;
        position.y = y;
    }

    public void actIfHit_CallbackMethod(Vector2 hitPoint)
    {
        // checking if incoming hit point is in the clickable area of this button
        if(WindowMath.isPointWithinArea(hitPoint,
                                        new Vector2(buttonTexture.getWidth(), buttonTexture.getHeight()),
                                        position
                                       ))
        {

            buttonWasRecentlyClicked = true;
            timeSinceLastClick = 0f;

            if(clickAddsToCart)
            {
                System.out.println("You clicked to add " + connectedPart.ID + " to cart " + connectedShoppingCart.cartID);
                connectedShoppingCart.addToCart(connectedPart);
            } else
            {
                System.out.println("You clicked to remove " + connectedPart.ID + " from cart " + connectedShoppingCart.cartID);
                connectedShoppingCart.removeFromCart(connectedPart);
            }
        }

    }
}
