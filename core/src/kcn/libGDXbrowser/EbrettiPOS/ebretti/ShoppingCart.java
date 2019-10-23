package kcn.libGDXbrowser.EbrettiPOS.ebretti;

import kcn.jNgine.transcendentals.IDCounter;

import java.util.ArrayList;
/* Class instance acts a shopping cart. */
public class ShoppingCart
{

    public final int cartID;
    private ArrayList<EbrettiPart> partsInCart;
    private float runningTotal;

    public ShoppingCart()
    {
        cartID = IDCounter.getNewId();
        partsInCart = new ArrayList<>();
        runningTotal = 0;
    }

    public ArrayList<EbrettiPart> getPartsInCart()
    {
        return partsInCart;
    }

    public float getRunningTotal()
    {
        return runningTotal;
    }

    public void addToCart(EbrettiPart part)
    {

        partsInCart.add(part);
        runningTotal += part.price;
        System.out.println("Part " + part.ID + "added to cart.");
    }

    public void removeFromCart(EbrettiPart part)
    {
        if(partsInCart.contains(part))
        {
            partsInCart.remove(part);
            runningTotal -= part.price;
            System.out.println("Part " + part.ID + " removed to cart.");
        } else
        {
            System.out.println("There was no part to remove from shopping cart list match part " + part.ID);
        }
    }
}
