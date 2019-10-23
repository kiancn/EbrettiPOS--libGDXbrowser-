package kcn.jNgine;

import kcn.methodreferencing.MethodReference;
import kcn.jNgine.Thing;
import kcn.jNgine.ISituational;

import java.util.ArrayList;
import java.util.List;

public abstract class Situation
{
    /* Nothing to see yet*/


    private ArrayList<ISituational> thingsInSituation;
    private ArrayList<MethodReference> updateMethods;

    public boolean addThingToSituation(ISituational thingToAdd) throws NoSuchMethodException
    {
        getThingsInSituation().add(thingToAdd);

        if (thingToAdd.isPrimaryThing()) {
            getUpdateMethods().add(
                    new MethodReference(thingToAdd, "update"));
        }
        return true;
    }

    public ISituational getThing(int thingID)
    {
        for (ISituational thing : getThingsInSituation()) {
            if (thing.getID() == thingID) {
                return thing;
            }
        }
        return null;
    }

    public ISituational getThing(String name)
    {
        for (ISituational thing : getThingsInSituation()) {
            if (thing.getName() == name) {
                return thing;
            }
        }
        return null;
    }

//    public <T> T getThingByClassName(String className) throws ClassNotFoundException
//    {
//        Class<T> classOfThing;
//        if (Class.forName(className) != null) {
//            classOfThing = (Class<T>) Class.forName(className);
//        } else {
//            classOfThing = null;
//
//        }
//        for (Thing thing : getThingsInSituation()) {
//            if (thing.getClass().equals(classOfThing)) {
//                return (T) thing;
//            }
//        }
//        return null;
//    }

    public ISituational[] getThings(String name)
    {
        List<ISituational> situationals = new ArrayList<ISituational>();

        for (ISituational thing : getThingsInSituation()) {
            if (thing.getName() == name) {
                situationals.add(thing);
            }
        }
        return (ISituational[]) situationals.toArray();
    }

    public ArrayList<ISituational> getAllParents()
    {
        ArrayList<ISituational> parentThings = new ArrayList<ISituational>();
        for (ISituational thing : getThingsInSituation()) {
            if (thing.isPrimaryThing()) {
                parentThings.add(thing);
            }
        }
        return parentThings;
    }

    public ArrayList<ISituational> getThingsInSituation()
    {
        return thingsInSituation;
    }

    public void setThingsInSituation(ArrayList<ISituational> thingsInSituation)
    {
        this.thingsInSituation = thingsInSituation;
    }

    public ArrayList<MethodReference> getUpdateMethods()
    {
        return updateMethods;
    }

    public void setUpdateMethods(ArrayList<MethodReference> updateMethods)
    {
        this.updateMethods = updateMethods;
    }
}
