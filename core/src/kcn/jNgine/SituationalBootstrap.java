package kcn.jNgine;


import kcn.methodreferencing.MethodPack;
import kcn.methodreferencing.MethodReference;


import java.util.*;

public class SituationalBootstrap
{
    public MethodPack initializerMethods;
    public MethodPack prepareMethods;
    public MethodPack updateMethods;

    List<ISituational> objectsInSituation;

    public SituationalBootstrap()
    {
        this.objectsInSituation = new ArrayList<ISituational>();
        this.initializerMethods = new MethodPack();
        this.prepareMethods = new MethodPack();
        this.updateMethods = new MethodPack();
    }

    public void createMethodsPacks(ArrayList<ISituational> iSituationals) /*throws NoSuchMethodException*/
    {
        /* Finding initializer methods; adding to initializerMethods*/
        for (ISituational sitObj : iSituationals) {
            try {
                initializerMethods.add(new MethodReference(sitObj, "initialize"));
            } catch (Exception e) {
                System.out.println(sitObj.toString() + " " + e.toString()); /* there will be a logger system */
            }
        }

        /* Finding initializer methods; adding to initializerMethods*/
        for (ISituational sitObj : iSituationals) {
            try {
                prepareMethods.add(new MethodReference(sitObj, "prepare"));
            } catch (Exception e) {
                System.out.println(sitObj.toString() + " " + e.toString());
            }
        }

        /* Finding initializer methods; adding to initializerMethods*/
        for (ISituational sitObj : iSituationals) {
            try {
                updateMethods.add(new MethodReference(sitObj, "update"));
            } catch (Exception e) {
                System.out.println(sitObj.toString() + " " + e.toString());
            }
        }
    }
}
