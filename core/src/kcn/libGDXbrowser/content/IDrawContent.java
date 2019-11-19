package kcn.libgdxbrowser.content;

import com.badlogic.gdx.graphics.g2d.Batch;

public interface IDrawContent
{
    public void gainFocus();
    public void loseFocus();

    public void drawContent(Batch batch);
}
