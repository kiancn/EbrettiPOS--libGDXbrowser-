package kcn.methodreferencing;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;


/**
 * A MeRef lets you roll up a reference to an object and a method;
 * and pass that method around and execute it from anywhere on command.
 * <p></p>
 * - Supplied generic types:<p><i>
 * <p>* generic type V is used input-parameters             (Values)
 * <p>* generic type O is generally used as return type     (Output)</i>
 * <p>
 * <p> (The just-mentioned rule is broken in run(O,V), which returns an Object) </p><p></p>
 * <p> Internal exception handling registers the different possible errors, and stops
 * the MeRef instance from functioning if even a single exception happens; the exception is captured in
 * the exceptionsCaught[] as ints for manual (user) processing. The MePack O,V is able
 * to detect defect MeRefs and remove them automatically. </p>
 * <p>
 * * Object type (in parameters and return types) is here to allow manual type
 *  casting and greater flexibility.
 **/
public class MeRef<V, O>
        implements IMethodReference
{
    /* ~<>~ Fields ~<>~ */

    private final String[] ErrorDescriptionStrings = {"IllegalAccessExceptionCaught",
            "InvocationTargetExceptionCaught",
            "NoSuchMethodExceptionCaught",
            "ExecutingObjectMissingCaught",
            "MethodObjectMissingCaught"};
    private Object objectReference; /* reference to the Object that executes the method (must own method) */
    private Method method; /* reference to instance of Method class, contents supplied at construction time */
    private Class[] argClasses; /* the option to supply an array of argClasses  */
    private boolean persistentNullChecks;/* if true referenced object is null-checked every execution */
    private boolean shortCircuitRun; /* bool used in all 'run' methods for avoiding fatal missing references*/
    private int[] exceptionsCaught;
    private int IllegalAccessExceptionCaught; /* see getExceptionsCaught() for usages of these ints */
    private int InvocationTargetExceptionCaught;
    private int ExecutingObjectMissingCaught;
    private int MethodObjectMissingCaught;
    private int NoSuchMethodExceptionCaught;

    /* ~<>~ Constructors ~<>~ */

    /* A proper description of the practical cases for each constructor is needed and upcoming  */

    public MeRef(Object executingObject, Method methodThatWillBeExecuted)
    {
        objectReference = executingObject;
        method = methodThatWillBeExecuted;
        persistentNullChecks = true; /* safety by default */
        initializeExceptionsArray();
    }

    public MeRef(Object executingObject, String methodName)
    {
        objectReference = executingObject;
        try
        {
            method = executingObject.getClass().getMethod(methodName);
        } catch(NoSuchMethodException e)
        {
            NoSuchMethodExceptionCaught++;
            shortCircuitRun = true;
        }
        persistentNullChecks = true; /* safety by default */
        initializeExceptionsArray();
    }

    public MeRef(Object executingObject, String methodName, Class[] argClasses)
    {
        this.argClasses = argClasses;
        objectReference = executingObject;
        try
        {
            method = executingObject.getClass().getMethod(methodName, argClasses);
        } catch(NoSuchMethodException e)
        {
            NoSuchMethodExceptionCaught++;
            shortCircuitRun = true;
        }
        persistentNullChecks = true; /* safety by default */
        initializeExceptionsArray();
    }

    /**
     * this constructor exists for the adventurous who might want to disable the auto-handling of null
     * references the other option is to pull getExecutingObject() and getExceptionsCaught()
     * and do handle possible exceptions manually
     */
    public MeRef(Object executingObject, String methodName, boolean handleHealthChecksAutomatically)
    {
        objectReference = executingObject;
        try
        {
            method = executingObject.getClass().getMethod(methodName);
        } catch(NoSuchMethodException e)
        {
            NoSuchMethodExceptionCaught++;
            shortCircuitRun = true;
        }
        persistentNullChecks = handleHealthChecksAutomatically;
        initializeExceptionsArray();
    }

    public MeRef(Object executingObject, Method methodThatWillBeExecuted, boolean handleHealthChecksAutomatically)
    {
        objectReference = executingObject;
        method = methodThatWillBeExecuted;
        persistentNullChecks = handleHealthChecksAutomatically;
        initializeExceptionsArray();
    }
    /* ~<>~ Methods ~<>~ */


    /**
     * Method executes supplied method with no parameters and a type O return
     */
    @SuppressWarnings("unchecked") /* compiler is unsure of return type (because invoke does predict type)*/
    public O run()
    {
        O result = null;

        if(persistentNullChecks){ isNullFound();}

        if(!shortCircuitRun)
        {
            try
            {
                /* invoke is executed on Method object, return type is cast to O */
                result = (O)method.invoke(objectReference);
            } catch(IllegalAccessException e)
            {
                IllegalAccessExceptionCaught++;
            } catch(InvocationTargetException e)
            {
                InvocationTargetExceptionCaught++;
            }
        }
        /* null returns are passed */
        return result;
    }

    /**
     * Method executes supplied method with a single type V parameter,
     * and returns a type O object.
     **/
    @SuppressWarnings("unchecked") /* same (see O run() )*/
    public O run(V vValue)
    {
        if(persistentNullChecks){ isNullFound(); }

        if(!shortCircuitRun)
        {
            try
            {
                return (O)method.invoke(objectReference, vValue);
            } catch(IllegalAccessException e)
            {
                IllegalAccessExceptionCaught++;
            } catch(InvocationTargetException e)
            {
                InvocationTargetExceptionCaught++;
            }
        }

        return null;
    }

    /**
     * Method executes supplied method with two type V parameters,
     * and returns a type O object.
     **/
    @SuppressWarnings("unchecked") /* same (see run() )*/
    public O run_VV(V vValueA, V vValueB)
    {

        if(persistentNullChecks){ isNullFound(); }
        if(!shortCircuitRun)
        {
            try
            {
                return (O)method.invoke(objectReference, vValueA, vValueB);
            } catch(IllegalAccessException e)
            {
                IllegalAccessExceptionCaught++;
            } catch(InvocationTargetException e)
            {
                InvocationTargetExceptionCaught++;
            }
        }

        return null;
    }

    /**
     * Method takes an array of objects of type V and returns an object of type O.
     * <p></p><b>LIMITS:</b><p>
     * <i> * cannot be used to return an array of primary types! </i><p>
     *
     * @param valuesArray an array of type V objects.
     * @return object of type O
     **/
    @SuppressWarnings("unchecked")
    public O run(V[] valuesArray)
    {
        if(persistentNullChecks){ isNullFound(); }

        if(!shortCircuitRun)
        {
            try
            {
                return (O)method.invoke(objectReference, new Object[]{valuesArray});
            } catch(IllegalAccessException e)
            {
                IllegalAccessExceptionCaught++;
            } catch(InvocationTargetException e)
            {
                InvocationTargetExceptionCaught++;
            }
        }

        return null;
    }

    /**
     * Method takes a Object-object and a V-object as arguments, and returns an
     * O-type object.
     * <p></p><p> - method takes first a parameter object of type Object
     * <p> - then method takes a parameter object type V
     * <p> - method returns a type O object
     * <p>
     * <b> - NB. the contained method must take care to cast the return Object to
     * something useful.* </b>
     * <p>
     * - Method is named different because the compiler cannot distinguish between
     * run(V,V) and run(Object,V), - and so run_ObjV was born.
     * Sorry for the confusion. </p>
     *
     * @param inputObject Object type object; any object (remember to type cast in
     *                    executing method)
     * @param value       an object of type V
     * @return an O-type object
     */
    @SuppressWarnings("unchecked") /* same (see run() */
    public O run_ObjV(Object inputObject, V value)
    {
        /* if autoHandle.. is true, check if object is null*/
        if(persistentNullChecks){ isNullFound(); }
        /* if object is not null, shortCir.. will be false*/
        if(!shortCircuitRun)
        {
            /* A. type cast return type to O; for the compiler
             *  B. call invoke on referenced method with inputObject and value as
             * parameters
             * C. return type O-object from this method */
            try
            {
                return (O)method.invoke(objectReference, inputObject, value);
            } catch(IllegalAccessException e)
            {
                IllegalAccessExceptionCaught++; /* this should never happen because  */
            } catch(InvocationTargetException e)
            {
                InvocationTargetExceptionCaught++;
            }
        }
        return null;
    }

    /**
     * Method takes an Object object and an array of objects of type V and returns
     * an object of type O.
     * <p></p>
     * <b>LIMITS:</b><p>
     * <i> * cannot be used to return an array of fundamental types! </i><p>
     *
     * @param inputObject an object of type Object.
     * @param valuesArray an array of type V objects.
     * @return object of type O
     **/
    @SuppressWarnings("unchecked") /* same (see run() )*/
    public O run(Object inputObject, V[] valuesArray)
    {
        if(persistentNullChecks){ isNullFound();}
        if(!shortCircuitRun)
        {
            try
            {
                return (O)method.invoke(objectReference,
                                        inputObject,
                                        new Object[]{valuesArray});
            } catch(IllegalAccessException e)
            {
                IllegalAccessExceptionCaught++;
            } catch(InvocationTargetException e)
            {
                InvocationTargetExceptionCaught++;
            }
        }
        /* null returns are passed */
        return null;
    }

    /* Method takes two params of diff types and returns an object type object */
    public Object run(O inputTypeO, V inputTypeV)
    {
        /* if auto-handling null object, do that*/
        if(persistentNullChecks){ isNullFound(); }
        if(!shortCircuitRun)
        {
            try
            {
                return getMethodObject().invoke(objectReference, inputTypeO, inputTypeV);
            } catch(IllegalAccessException e)
            {
                IllegalAccessExceptionCaught++;
            } catch(InvocationTargetException e)
            {
                InvocationTargetExceptionCaught++;
            }
        }
        return null;
    }

    /**
     * Returns true if persistent null checks are enabled, meaning that executing object and method object
     * will be checked for presence before every use/access (in run..(..)-methods).
     */
    public boolean isPersistentCheckingEnabled(){ return persistentNullChecks; }

    /**
     * Method lets you turn on or off persistent null checks
     */
    public void setPersistentNullChecks(boolean checkForNull){ persistentNullChecks = checkForNull; }

    /**
     * Method returns true if either null was found or exceptions were registered.
     */
    public boolean isReferenceBroke()
    {
        if(didExceptionsHappen()){return true;}
        return isNullFound();
    }

    /**
     * Method returns true if exceptions were registered during execution
     * - this is the 'soft check', in that it does not directly check for null.
     */
    public boolean didExceptionsHappen()
    {
        /* handling the array counting up exceptions thrown */
        for(int exceptionCount : exceptionsCaught)
        {
            if(exceptionCount > 0) return true;
        }
        return false;
    }

    /**
     * Method returns true if executing Object object or Method object is null.
     **/
    public boolean isNullFound()
    {
        /* if either checking-method returns true, return true*/
        if(isExecutingObjectNull() || isMethodObjectNull()){ return true; }
        /* else, null was not found*/
        return false;
    }

    /* Possibly I don't want to do this at all: Method object is
    encapsulated and its state is untouched after construction,
     it shouldn't be able to disappear. If this method goes,
    these three associated methods will reduce to just isNullFound()...
     */

    /**
     * Method returns true is referenced 'method-executing' object was found null
     */
    public boolean isExecutingObjectNull()
    {
        if(Objects.isNull(objectReference))
        {
            ExecutingObjectMissingCaught++;
            shortCircuitRun = true;
            return true;
        } else{ return false; }
    }

    /**
     * Method returns true is contained Method type object is
     */
    public boolean isMethodObjectNull()
    {
        if(Objects.isNull(method)) /* the only risk to check is the method executing object */
        {
            MethodObjectMissingCaught++;
            shortCircuitRun = true;
            return true;
        } else{ return false; }
    }

    /**
     * Method returns the contained Method object: if you want to do more reflection
     * magic, here is direct access ...
     */
    @Override
    public Method getMethodObject(){ return method; }

    @Override
    public Object getExecutingObject()
    {
        if(!isNullFound()){ return objectReference; } else{ return null; }
    }

    public int[] getExceptionsCaught()
    {
        initializeExceptionsArray(); /* re-initializing exceptionsCaught */
        return exceptionsCaught;
    }

    private void initializeExceptionsArray()
    {
        exceptionsCaught = new int[]{IllegalAccessExceptionCaught,
                InvocationTargetExceptionCaught,
                NoSuchMethodExceptionCaught,
                ExecutingObjectMissingCaught,
                MethodObjectMissingCaught
        };
    }

    /**
     * Returns array of strings representing possible error-types in order
     * of the int-array counting caught exceptions exceptionsArray.
     * The array represents the possible exceptions and null objects,
     * but NOT and NEVER a log of actual events;
     * method is provided mainly for debugging purposes for.
     */
    public String[] getExceptionDescriptionStrings()
    {
        return ErrorDescriptionStrings;
    }
}
/*
 * This saved my life as to how to supply the array of V to invoke in run(V[]) :
 * https://yourmitra.wordpress.com/2008/09/26/using-java-reflection-to-invoke-a-method-with-array-parameters/
 *
 * On the perils of Generics and suppressing warnings
 * http://www.angelikalanger.com/GenericsFAQ/FAQSections/ProgrammingIdioms.html#FAQ300
 * https://www.codejava.net/java-core/the-java-language/suppresswarnings-annotation-examples
 * */