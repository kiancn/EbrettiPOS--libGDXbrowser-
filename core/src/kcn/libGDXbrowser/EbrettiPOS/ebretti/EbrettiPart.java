package kcn.libGDXbrowser.EbrettiPOS.ebretti;
// by KCN
import com.badlogic.gdx.graphics.Texture;

public class EbrettiPart
{
    public final String category;
    public final String ID;
    public final String description;
    public final float price;

    public final String pathToImage;

    public final String pathToImagePlaceholder = "image-missing-lo.png";

    public final Texture partImage;

    public EbrettiPart(String category, String ID, String description, float price, String pathToImage)
    {
        this.category = category;
        this.ID = ID;
        this.description = description;
        this.price = price;
        this.pathToImage = pathToImage;

        if(!pathToImage.equalsIgnoreCase("") && !pathToImage.equalsIgnoreCase("missing")){
            partImage = new Texture(pathToImage);
        } else {
            partImage = new Texture(pathToImagePlaceholder);
        }
    }
}
