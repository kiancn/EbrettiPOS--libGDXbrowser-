package kcn.utility;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class WindowMath
{
    /**
     * method returns true if point is within area
     * <p></p> - all this only work for squares and only for axis-aligned boxes/squares.
     *
     * @param areaOrigin is interpreted from 'bottom right' of texture
     */
    public static boolean isPointWithinArea(Vector2 point, Vector2 areaDimension, Vector2 areaOrigin)
    {
        boolean xHit = false; // flips if point x is found within requested area
        boolean yHit = false;

        /* deciding if checked point is in supplied area*/
        if(point.x >= areaOrigin.x
           && point.x <= areaOrigin.x + areaDimension.x)
        {
            xHit = true;
        }
        // the funny math is needed because delivered point coordinate y starts a top of window
        if((point.y - Gdx.graphics.getHeight()) * -1 >= areaOrigin.y
           && ((point.y - Gdx.graphics.getHeight()) * -1) <= areaOrigin.y + areaDimension.y)
        {
            yHit = true;
        }

        if(xHit && yHit)
        {
             // System.out.println(TO.green("Hit detected at X: " + point.x + " \tY: " + ((point.y - 500) *
            // -1)));
            return true;
        } else
        {
            //   System.out.println(TO.purple("Miss detected at X: " + point.x + " \tY: " + ((point.y - 500) *
            //   -1)));
            return false;
        }
    }
}
