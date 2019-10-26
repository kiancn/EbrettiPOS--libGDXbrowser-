package kcn.libGDXbrowser.EbrettiPOS.EbrettiContentPages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import kcn.libGDXbrowser.EbrettiPOS.ebretti.BuyButton;
import kcn.libGDXbrowser.SignallingInputProcessor;
import kcn.libGDXbrowser.content.Content;
import kcn.libGDXbrowser.content.IDrawContent;
import kcn.libGDXbrowser.EbrettiPOS.ebretti.ShoppingCart;

public class ShoppingCartContent
        extends Content
        implements IDrawContent
{
    BitmapFont fontRenderer;
    ShoppingCart shoppingCart;

    BuyButton buyButton;

    SignallingInputProcessor inputProcessor;

    float timeSinceFocusGain; // float tracks time since focus gain
    boolean buttonAddedToInputProcessor; // tracks what you think

    public ShoppingCartContent(ShoppingCart SHOPPINGCART, SignallingInputProcessor INPUTPROCESSOR)
    {
        name = "Shopping Cart";

        fontRenderer = new BitmapFont();

        shoppingCart = SHOPPINGCART;

        inputProcessor = INPUTPROCESSOR;

        buyButton = new BuyButton(900, 200);
    }

    @Override
    public void gainFocus()
    {
        timeSinceFocusGain = 0;
        /*super.gainFocus();*/

    }

    @Override
    public void loseFocus()
    {

        inputProcessor.getTouchUpCallbackMethods().remove(buyButton.methodOnClickEvent);
        buttonAddedToInputProcessor = false;
        /* super.loseFocus();*/
    }

    @Override
    public void drawContent(Batch batch)
    {
        if(timeSinceFocusGain < 0.4f) // just a rather arbitrary limit
        {
            timeSinceFocusGain += Gdx.graphics.getDeltaTime();
        }

        if(!buttonAddedToInputProcessor && timeSinceFocusGain > 0.3f)
        {
            inputProcessor.getTouchUpCallbackMethods().add(buyButton.methodOnClickEvent);
            buttonAddedToInputProcessor = true;
            System.out.println("From ShoppingCartContent: Adding input processor for buy button.");
        }

//        batch.draw(buyButton.,(float)buyButton.position.x,(float)buyButton.position.y);
        buyButton.drawButton(batch);

        fontRenderer.setColor(Color.GOLDENROD);
        fontRenderer.draw(batch,
                          "SHOPPING CART",
                          110,
                          Gdx.graphics.getHeight() - 50);
        fontRenderer.setColor(Color.RED);
        fontRenderer.draw(batch,
                          "SHOPPING CART",
                          115,
                          Gdx.graphics.getHeight() - 35);
        fontRenderer.setColor(Color.FIREBRICK);
        fontRenderer.draw(batch,
                          "SHOPPING CART",
                          120,
                          Gdx.graphics.getHeight() - 20);


        fontRenderer.setColor(Color.LIGHT_GRAY);
        fontRenderer.draw(batch,
                          "Items in cart: " + shoppingCart.getPartsInCart().size(),
                          350,
                          Gdx.graphics.getHeight() - 30);

        fontRenderer.setColor(Color.GOLD);
        fontRenderer.draw(batch,
                          "Running total: " + String.format("%.2f", shoppingCart.getRunningTotal()),
                          350,
                          Gdx.graphics.getHeight() - 50);


        if(shoppingCart.getPartsInCart().size() > 0)
        {

            int yAdj = Gdx.graphics.getHeight() - 100;
            int xAdj = 280;

            fontRenderer.setColor(Color.LIGHT_GRAY);
            fontRenderer.draw(batch, "Your order: ", 80, yAdj + 20);

            for(int i = 0; i < shoppingCart.getPartsInCart().size(); i++)
            {
                batch.draw(shoppingCart.getPartsInCart().get(i).partImage,
                           xAdj-200,
                           yAdj-15 - i*35);

                fontRenderer.setColor(Color.WHITE);
                fontRenderer.draw(batch, "#" + (i + 1),
                                  xAdj - 75,
                                  yAdj - i * 35);

                fontRenderer.setColor(Color.WHITE);
                fontRenderer.draw(batch, shoppingCart.getPartsInCart().get(i).ID,
                                  xAdj,
                                  yAdj - i * 35);

                fontRenderer.draw(batch, shoppingCart.getPartsInCart().get(i).category,
                                  xAdj + 150,
                                  yAdj - i * 35);

                fontRenderer.draw(batch, shoppingCart.getPartsInCart().get(i).description,
                                  xAdj + 250,
                                  yAdj - i * 35);
                fontRenderer.setColor(Color.LIME);
                fontRenderer.draw(batch,
                                  "Cost: " + String.format("%.2f", shoppingCart.getPartsInCart().get(i).price),
                                  xAdj + 550,
                                  yAdj - i * 35);
            }
        }
    }
}
