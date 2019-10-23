package kcn.jNgine;

/** Implementing this interfaces signifies that an implementing class is able to
*   - initialize itself into a scene
*   - prepare itself for first activity
*   - update itself
*  */
public interface ISituational
{
    /* consider this a separate constructor phase: method should be called only upon situation setting up. */
    boolean initialize();
    /* this method is run before the first update, and should be used for initialization that requires
    * other parts/situation in the situation to have initialized themselves. */
    boolean prepare();
    /* this method is run every  time increment  while part  exists  in situation */
    void update();
    /* method should return true if implementing ISituational is at top of ISit heirarchy */
    boolean isPrimaryThing();

    String getName();
    int getID();

}
