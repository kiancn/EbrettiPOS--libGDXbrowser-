package kcn.jNgine.transcendentals;

/**
 * Purpose of the class is to work as a generic id counter for a logo-scape of objects.
 * Class is accessed through singleton instance.
 */
public class IDCounter
{

    private static IDCounter instance; // the only instance to count ids.
    private int entityCount;

    private IDCounter()
    {
        entityCount = 0;
    }

    /**
     * Singleton access
     */
    public static IDCounter getInstance()
    {
        if(instance == null)
        {
            instance = new IDCounter();
        }
        return instance;
    }

    /**
     * Method returns a different number each time called.
     * Thus, it should only be called once by a single object in need of an id.
     */
    public int getNewId(){ return ++instance.entityCount; }

}
