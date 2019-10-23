package kcn.libGDXbrowser.EbrettiPOS.EbrettiContentPages;
// by KCN

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import kcn.libGDXbrowser.content.Content;

public class MainMenuContent
        extends Content
{

    Texture mainMenuSplash; // the text which will be shown at top of screen
    BitmapFont font;

    Texture ebrettiLogo;

    int redEbrettiMoveMod;


    public MainMenuContent()
    {

        name = "Main Menu";
        mainMenuSplash = new Texture("rinsedbike250x154.png");

        font = new BitmapFont();

//        ebrettiLogo = new Texture("animals/birthesdyrehandel.png"); // dyrehandel hack
        ebrettiLogo = new Texture("ebretti-logo-278x101.png"); // dyrehandel hack

        redEbrettiMoveMod = -670;


    }


    @Override
    public void drawContent(Batch batch)
    {
        redEbrettiMoveMod += 2;

        if(redEbrettiMoveMod > 520){redEbrettiMoveMod = -620;}

        batch.draw(ebrettiLogo, (int)(ebrettiLogo.getWidth() * 0.5), 230); // dyrehandel hack

        font.setColor(Color.GOLD);
        font.draw(batch, "Ebretti", 5, 600 + redEbrettiMoveMod);
        font.draw(batch, "Ebretti", 5, 570 + redEbrettiMoveMod);
        font.draw(batch, "Ebretti", 5, 530 + redEbrettiMoveMod);
        font.draw(batch, "Ebretti", 5, 480 + redEbrettiMoveMod);
        font.draw(batch, "Ebretti", 5, 420 + redEbrettiMoveMod);
        font.draw(batch, "Ebretti", 5, 350 + redEbrettiMoveMod);
        font.draw(batch, "Ebretti", 5, 280 + redEbrettiMoveMod);
        font.draw(batch, "Ebretti", 5, 190 + redEbrettiMoveMod);
        font.draw(batch, "Ebretti", 5, 90 + redEbrettiMoveMod);

        batch.draw(mainMenuSplash, 180, 330);

        font.setColor(Color.GOLD);
        font.draw(batch, "Onderdelenprijslijst", 270, 510);
        font.setColor(Color.WHITE);
        font.draw(batch, "Onderdelenprijslijst", 280, 500);

        font.setColor(Color.WHITE);
        font.draw(batch, "Ebretti", 450, 530);
        font.setColor(Color.GOLD);
        font.draw(batch, "Ebretti", 450, 490);
        font.setColor(Color.GOLDENROD);
        font.draw(batch, "Ebretti", 450, 430);
        font.setColor(Color.WHITE);
        font.draw(batch, "Ebretti", 450, 300);
        font.setColor(Color.LIGHT_GRAY);
        font.draw(batch, "Ebretti", 450, 100);

//        batch.draw(ebrettiLogo, 0,0); // dyrehandel hack
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
