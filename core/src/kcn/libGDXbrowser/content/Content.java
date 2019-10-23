package kcn.libGDXbrowser.content;

import com.badlogic.gdx.graphics.g2d.Batch;

/**The intent is to extend this class with actual content*/
public abstract class Content
{
    // The name of Content object is all important;
    public String name;

    /*
    * Overriding method should have responsibility for adjusting any data that needs to be
    * when extending Content class 'gains focus'.
    * The overriding method will be pulled at least every time a ContentManager.
    * is switching to new content (in SwitchToContentByName)
    * */
    public void gainFocus(){System.out.println("This method needs to be overwritten.");}

    /* (Almost same as above)
     * Overriding method should have responsibility for adjusting any data that needs to be
     * when extending Content class 'loses focus'.
     * The overriding method will be pulled at least every time a ContentManager
     * is switching to new content (in SwitchToContentByName)*/
    public void loseFocus(){System.out.println("This method needs to be overwritten.");}

    /*
    *
    * */
    public void drawContent(Batch batch){
        System.out.println("You should override this method.");
    }
}
