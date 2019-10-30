package kcn.libGDXbrowser.EbrettiPOS.EbrettiContentPages;
// by KCN

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import kcn.libGDXbrowser.content.Content;
import kcn.libGDXbrowser.content.IDrawContent;

public class MainMenuContent
        extends Content implements IDrawContent
{

    Texture mainMenuSplash; // the text which will be shown at top of screen
    BitmapFont font;

    Texture ebrettiLogo;

    String companyName = "Ebretti";

    int movingTextModification;


    public MainMenuContent()
    {
        name = "Main Menu";

        mainMenuSplash = new Texture("rinsedbike250x154.png");

        font = new BitmapFont();

//        ebrettiLogo = new Texture("animals/birthesdyrehandel.png"); // dyrehandel hack
        ebrettiLogo = new Texture("ebretti-logo-278x101.png"); // dyrehandel hack

        movingTextModification = -670;
    }


    @Override
    public void drawContent(Batch batch)
    {
        drawWelcomeScreen(batch);
//        batch.draw(ebrettiLogo, 0,0); // dyrehandel hack

    }

    private void drawWelcomeScreen(Batch batch)
    {
        movingTextModification += 2;
//        batch.draw(ebrettiLogo, (int)(ebrettiLogo.getWidth() * 0.5), 230); // dyrehandel hack


        if(movingTextModification > 1000){movingTextModification = -670;}

        font.setColor(Color.GOLD);
        font.draw(batch, companyName, 15, 600 + movingTextModification);
        font.draw(batch, companyName, 15, 570 + movingTextModification);
        font.draw(batch, companyName, 15, 530 + movingTextModification);
        font.draw(batch, companyName, 15, 480 + movingTextModification);
        font.draw(batch, companyName, 15, 420 + movingTextModification);
        font.draw(batch, companyName, 15, 350 + movingTextModification);
        font.draw(batch, companyName, 15, 280 + movingTextModification);
        font.draw(batch, companyName, 15, 190 + movingTextModification);
        font.draw(batch, companyName, 15, 90 + movingTextModification);

        batch.draw(mainMenuSplash, 180, 330);

        font.setColor(Color.GOLD);
        font.draw(batch, "Onderdelenprijslijst", 270, 510);
        font.setColor(Color.WHITE);
        font.draw(batch, "Onderdelenprijslijst", 280, 500);

        font.setColor(Color.WHITE);
        font.draw(batch, companyName, 450, 530);
        font.setColor(Color.GOLD);
        font.draw(batch, companyName, 450, 490);
        font.setColor(Color.GOLDENROD);
        font.draw(batch, companyName, 450, 430);
        font.setColor(Color.WHITE);
        font.draw(batch, companyName, 450, 300);
        font.setColor(Color.LIGHT_GRAY);
        font.draw(batch, companyName, 450, 100);
    }

    @Override
    public void loseFocus()
    {
        System.out.println(name + " content lost focus.");
    }
    @Override
    public void gainFocus()
    {
        System.out.println(name + " content gained focus.");
    }

}
