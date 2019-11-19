package kcn.libgdxbrowser.content;
// by KCN

import com.badlogic.gdx.graphics.g2d.Batch;
import kcn.libgdxbrowser.SignallingInputProcessor;

import java.util.ArrayList;
/*I realize too late that everything named manager should be named controller - maybe this will be
corrected soon*/

/**
 * The content manager is able to switch between contents on demand.
 * To achieve this, supply a name identical to the name of a member
 * of contents (to switchToContentByName(String name)).
 * <p>The idea is to new up a contentManager
 * <p>+ add content
 * <p>+ set a content-'page' to currently active before first frame
 * <p>-> and execute switchToContentByName to bring
 *  other content into focus.</p>
 */
public class ContentManager
{
    SignallingInputProcessor inputProcessor; // processor handling input
    private ArrayList<Content> contents; // the 'pages of content'
    private Content currentContent; // current content on display

    public ContentManager(SignallingInputProcessor INPUTPROCESSOR)
    {
        inputProcessor = INPUTPROCESSOR;
        contents = new ArrayList<>();
    }


    public ArrayList<Content> getContents()
    {
        return contents;
    }

    public Content getCurrentContent()
    {
        return currentContent;
    }

    /**
     * Switches current content to supplied name.
     * Method is executed in MenuManager.
     */
    public boolean switchToContentByName(String name)
    {
        if(contents == null)
        {
            System.out.println("From ContentManager: contents is null");
            return false;
        }
        if(contents.size() < 1)
        {
            System.out.println("From ContentManager: contents list is empty");
            return false;
        }

        for(Content content : contents)
        {
            if(content.name.contains(name))
            {
                if(currentContent!=null)
                {
                    currentContent.loseFocus();
                }
                currentContent = content;
                currentContent.gainFocus();
                return true;
            }
        }

        // code only reaches here if no content by supplied name was found
        System.out.println("From ContentManager: content by supplied name not found. ");
        return false;
    }

    /*  */
    public void drawContent(Batch batch)
    {
        currentContent.drawContent(batch);
    }

}
