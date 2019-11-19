package kcn.libgdxbrowser.grind.contentpages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import kcn.libgdxbrowser.content.Content;
import kcn.libgdxbrowser.content.IDrawContent;
/** BlueContent is a kind of 'Hello World' of Content classes */
public class BlueContent extends Content implements IDrawContent
{
    BitmapFont fontRenderer; // font-renderer takes care of processing string input

    public BlueContent()
    {
        /* this name must match the name assigned to a Menu, if the content is to
        be brought to focus by a click on a menu bar. */
        name = "Blue Content";

        /* A BitmapFont object is wonderful and allows us to order 'draw calls'
        * to the SpriteBatch being called in render() in the specific ...ApplicationAdaptor
        * */
        fontRenderer = new BitmapFont();
    }

    @Override
    public void drawContent(Batch batch){

        Gdx.gl.glClearColor(13f / 255f, 39f / 255f, 158f / 255f, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        /*Statement makes font-renderer interpret string, calculate
         * graphic output, and place a draw call with the Batch object;
         * ultimately the batch itself is called to draw(...) */
        fontRenderer.setColor(Color.BLACK);
        fontRenderer.draw(batch, "Blue Content", 30, 90);
    }

}
