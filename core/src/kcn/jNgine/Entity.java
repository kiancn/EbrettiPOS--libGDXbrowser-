package kcn.jNgine;


/** The thought is that entity might serve a sort of boiler-plate print
 * :
 * remember to leave untouced and update */
public class Entity extends Thing implements ISituational
{

    public Entity(String name)
    {
        super(name); /*  */
    }

    @Override
    public boolean initialize()
    {
        return super.initialize();
    }

    @Override
    public boolean prepare()
    {
        int I = getID();
        return super.prepare();
    }

    @Override
    public void update()
    {
        super.update();
    }
}
