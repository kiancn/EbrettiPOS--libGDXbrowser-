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

        buyButton = new BuyButton(900,200);
    }

    @Override
    public void gainFocus(){
        timeSinceFocusGain = 0;
        /*super.gainFocus();*/

    }

    @Override
    public void loseFocus()    {

        inputProcessor.getTouchUpCallbackMethods().remove(buyButton.methodOnClickEvent);
        buttonAddedToInputProcessor = false;
       /* super.loseFocus();*/
    }

    @Override
    public void drawContent(Batch batch)
    {
        if(timeSinceFocusGain < 0.4) // just a rather arbitrary limit
        {
            timeSinceFocusGain += Gdx.graphics.getDeltaTime();
        }

        if(!buttonAddedToInputProcessor && timeSinceFocusGain > 0.3f){
            inputProcessor.getTouchUpCallbackMethods().add(buyButton.methodOnClickEvent);
            buttonAddedToInputProcessor = true;
            System.out.println("From ShoppingCartContent: Adding input processor for buy button.");
        }

//        batch.draw(buyButton.,(float)buyButton.position.x,(float)buyButton.position.y);
        buyButton.drawButton(batch);

        fontRenderer.setColor(Color.GOLDENROD);
        fontRenderer.draw(batch,
                          "SHOPPING CART",
                          510,
                          Gdx.graphics.getHeight() - 50);
        fontRenderer.setColor(Color.RED);
        fontRenderer.draw(batch,
                          "SHOPPING CART",
                          515,
                          Gdx.graphics.getHeight() - 35);
        fontRenderer.setColor(Color.FIREBRICK);
        fontRenderer.draw(batch,
                          "SHOPPING CART",
                          520,
                          Gdx.graphics.getHeight() - 20);



        fontRenderer.setColor(Color.LIGHT_GRAY);
        fontRenderer.draw(batch,
                          "Items in cart: " + shoppingCart.getPartsInCart().size(),
                          350,
                          Gdx.graphics.getHeight() - 30);

        fontRenderer.setColor(Color.GOLD);
        fontRenderer.draw(batch,
                          "Running total: " + shoppingCart.getRunningTotal(),
                          350,
                          Gdx.graphics.getHeight() -50);


        if(shoppingCart.getPartsInCart().size() > 0)
        {

            int yAdj = Gdx.graphics.getHeight() - 100;

            fontRenderer.setColor(Color.LIGHT_GRAY);
            fontRenderer.draw(batch,"Your order: ",80,yAdj+20);

            for(int i = 0; i < shoppingCart.getPartsInCart().size(); i++)
            {
                fontRenderer.setColor(Color.WHITE);
                fontRenderer.draw(batch, "#" + (i+1),
                                  25,
                                  yAdj - i * 35);

                fontRenderer.setColor(Color.WHITE);
                fontRenderer.draw(batch, shoppingCart.getPartsInCart().get(i).ID,
                                  100,
                                   yAdj - i * 35);

                fontRenderer.draw(batch, shoppingCart.getPartsInCart().get(i).category,
                                  100 + 150,
                                  yAdj - i * 35);

                fontRenderer.draw(batch, shoppingCart.getPartsInCart().get(i).description,
                                  100 + 250,
                                  yAdj - i * 35);
                fontRenderer.setColor(Color.LIME);
                fontRenderer.draw(batch, "Cost: "+shoppingCart.getPartsInCart().get(i).price,
                                  100 + 550,
                                  yAdj - i * 35);
            }
        }
    }
}
