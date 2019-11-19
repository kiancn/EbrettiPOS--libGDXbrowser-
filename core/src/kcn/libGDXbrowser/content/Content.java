package kcn.libgdxbrowser.content;

import com.badlogic.gdx.graphics.g2d.Batch;

/**
 * The intent is to extend this class with actual content
 */
public abstract class Content
{
    /*
     The name of Content object is all important, since the ContentManager locate any Content through this
     name (inspect .
    */
    public String name;

    /*
     * Overriding method should have responsibility for adjusting any data that needs to be
     * when extending Content class 'gains focus'.
     * The overriding method will be pulled at least every time a ContentManager.
     * is switching to new content (in SwitchToContentByName)
     * */
    public void gainFocus(){System.out.println("Content " + name + " gained focus. ");}

    /* (Almost same as above)
     * Overriding method should have responsibility for adjusting any data that needs to be
     * when extending Content class 'loses focus'.
     * The overriding method will be pulled at least every time a ContentManager
     * is switching to new content (in SwitchToContentByName)*/
    public void loseFocus(){System.out.println("Content " + name + " losing focus. ");}

    /*
     * This method is overridden
     * */
    public void drawContent(Batch batch)
    {
        System.out.println("You should override this method.");
    }
}
