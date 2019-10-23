package kcn.libGDXbrowser;
// by KCN
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;

import com.badlogic.gdx.graphics.g2d.TextureRegion;


public class SheetAnimation
{
    // All values initialized at construction time

    // constants respresenting the COLUMNS (vertical) and ROWS (horizontal) of the animationSheet
    private final int FRAME_COLS, FRAME_ROWS;
    Animation<TextureRegion> animation; // object that will be queried for textures
    Texture animationSheet; // the source for the TextureRegions
    float frameDuration; // whole seconds a single frame remains visible
    private float stateTime; // state-time helps measure intervals between frame-shifts

    public SheetAnimation(String pathToSheet, int FRAME_COLS, int FRAME_ROWS, float frameDuration)
    {
        this.FRAME_COLS = FRAME_COLS;
        this.FRAME_ROWS = FRAME_ROWS;
        this.frameDuration = frameDuration;

        stateTime = 0;

        createAnimation(pathToSheet);
    }

    public Animation<TextureRegion> getAnimation()
    {
        return animation;
    }

    private void createAnimation(String path)
    {
        // initializing animationSheet, reading image from file
        animationSheet = new Texture(Gdx.files.internal(path));

        // temporary 2d TextureRegion array; needed to order frames into a 1d array
        TextureRegion[][] temp = TextureRegion.split(animationSheet,
                                                     animationSheet.getWidth() / FRAME_COLS,
                                                     animationSheet.getHeight() / FRAME_ROWS);

        TextureRegion[] animationFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        // migrating the temp frames to animationFrames
        int frameIndex = 0;

        int ballon[] = new int[3];


        for(int i = 0; i < FRAME_ROWS; i++)
        {
            for(int j = 0; j < FRAME_COLS; j++)
            {
                animationFrames[frameIndex++] = temp[i][j];
            }
        }

        animation = new Animation<TextureRegion>(frameDuration, animationFrames);
    }

    // the idea is to call this method in the Render() method (where-ever you keep that)
    public TextureRegion getNextFrame()
    {

        stateTime += Gdx.graphics.getDeltaTime();
        return animation.getKeyFrame(stateTime, true);

//        TextureRegion nextFrame = animation.getKeyFrame(stateTime,true);
//        return nextFrame;
    }
}
/**
 * https://www.gamefromscratch.com/post/2013/10/15/LibGDX-Tutorial-4-Handling-the-mouse-and-keyboard.aspx
 **/

