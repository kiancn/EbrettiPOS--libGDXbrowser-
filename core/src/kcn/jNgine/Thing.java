package kcn.jNgine;

/**
 * A thing is a complex of intrinsic attributes and changing parts
 * A thing can be a part of another thing. Why shouldn't it?
 * If a thing that
 */


import kcn.jNgine.transcendentals.IDCounter;
import kcn.utility.TO;

import java.util.List;

public abstract class Thing
        implements ISituational
{

    /**
     * INTRINSIBUTES - INTRINSIC ATTRIBUTES
     **/
    /* set at construction time; a primary object is used by parts */
//    public final boolean isPrimaryThing;

    /* ID is a unique identifier assigned at conception */
    public final int ID;
    public boolean isPrimaryThing;
    /* array holds the components of this thing; the components of a thing are it's parts */
    private List<ISituational> parts;
    /* a readable name of thing */
    private String name;

    protected Thing(String name, ISituational parent)
    {
        ID = IDCounter.getInstance().getNewId();
        this.name = name;
        isPrimaryThing = false;
    }

    /**
     * Constructor is mainly intended to implement Entity's/abstract base objects
     **/
    protected Thing(String name)
    {
        ID = IDCounter.getInstance().getNewId();
        this.name = name;
        isPrimaryThing = true;
    }

    private List<ISituational> getParts()
    {
        return parts;
    }

    private ISituational getPart(int index)
    {
        if(index >= parts.size()){ return null; } else return parts.get(index);
    }

    public String getName(){ return name; }

    @Override
    public int getID(){ return ID; }

    public boolean isPrimaryThing(){ return isPrimaryThing; }

    @Override
    public boolean initialize()
    {
        System.out.println(TO.green("[" + ID + "]" + "Initialize ran on " + name));
        return false;
    }

    @Override
    public boolean prepare()
    {
        System.out.println(TO.yellow("[" + ID + "]" + "Prepare ran on " + name));
        return false;
    }

    @Override
    public void update()
    {
        System.out.println(TO.blue("[" + ID + "]" + "Update ran on " + name + " (from super class)"));
    }
}
