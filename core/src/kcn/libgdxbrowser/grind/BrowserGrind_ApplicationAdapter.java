package kcn.libgdxbrowser.grind;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import kcn.libgdxbrowser.SignallingInputProcessor;

/**
 * This class bootstraps the browser into the main render() cycle
 */
public class BrowserGrind_ApplicationAdapter extends ApplicationAdapter
{
    SpriteBatch batch; //
    /* anything that needs notification on user events subscribe here */
    SignallingInputProcessor inputProcessor;

    /* The BrowserBase handles creating all the interesting objects that go into
    * the experience; menus and content */
    BrowserBase browserBase;

    @Override
    public void create()
    {
        batch = new SpriteBatch();

        inputProcessor = new SignallingInputProcessor();

        Gdx.input.setInputProcessor(inputProcessor);

        browserBase = new BrowserBase(inputProcessor);

    }

    @Override
    public void render()
    {
        Gdx.gl.glClearColor(0f, 0, .1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        browserBase.browser.contentManager.drawContent(batch);

        browserBase.browser.menuManager.drawCurrentMenu(batch);

        batch.end();
    }

    @Override
    public void dispose()
    {
        batch.dispose();

    }
}
