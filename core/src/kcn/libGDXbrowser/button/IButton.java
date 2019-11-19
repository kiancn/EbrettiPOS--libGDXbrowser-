package kcn.libgdxbrowser.button;
// by KCN
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Vector2;

/**Interface promises implementing class will be a drawable button */
public interface IButton
{
    public void drawButton(Batch batch);

    public GlyphLayout drawLabel(Batch batch, float x, float y);

    public void setPosition(int x, int y);

    public void actIfHit_CallbackMethod(Vector2 hitPoint);
}
