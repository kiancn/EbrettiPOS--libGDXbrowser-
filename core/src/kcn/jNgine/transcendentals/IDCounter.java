package kcn.jNgine.transcendentals;

public class IDCounter
{

    private static int entityCount;

    public IDCounter()
    {
        entityCount = 1;
    }

    public static int getNewId()
    {
        return entityCount++;
    }

}
