package kcn.libGDXbrowser.EbrettiPOS.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import kcn.libGDXbrowser.EbrettiPOS.EbrettiPOSApplicationAdapter;


public class DesktopLauncher
{
    public static void main(String[] arg)
    {

        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();


        config.resizable = false;
//        config.title = "Ebretti Onderdelenprijslijst [KEA Opgave af KCN]";
        config.title = "Birthes Dyrehandel [KEA Opgave af KCN]";
        config.width = 1900;
        config.height = 1000;

        new LwjglApplication(new EbrettiPOSApplicationAdapter(), config);

    }
}
