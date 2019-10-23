package kcn.jNgine.transcendentals;

//import kcn.datastructures.MethodPack;
//import kcn.datastructures.MethodReference;

//import kcn.methodreferencing.MethodReference;

import kcn.methodreferencing.MethodPack;
import kcn.methodreferencing.MethodReference;

import java.lang.reflect.InvocationTargetException;

/**
 * Tickers main purpose is to
 * - Ticker should have only one implementation per scene
 **/
public class Ticker
{
    /**
     * this field contains the (pretty) accurate time in nanoseconds since january 1st 1970 (at construction time)
     **/
    private final long timeAtInception;

    /**
     * The main purpose of ticker is to give notice to a bunch of methods at specific time intervals
     **/
    private long tickIntervalInNanoSeconds;

    private long timeSinceLastTick;

    private boolean continueTicking;

    /**
     * Clients are those that recieve notice when Alarm detects requested interval;
     *
     * @usage:
     **/
    private MethodPack clients;


    /**
     * At construction
     * <p>
     * - MethodReferences are added later
     */
    public Ticker()
    {
        timeAtInception = System.nanoTime();
        timeSinceLastTick = timeAtInception;
        clients = new MethodPack();

        continueTicking = false;
    }

    /* Method returns time At Inception, inception connoting construction */
    public long getTimeAtInception()
    {
        return timeAtInception;
    }

    /* Method returns time (long) since construction in nanoseconds */
    public long getTimeSinceInception() { return System.nanoTime() - timeAtInception; }

    /**
     * Method initializes cycle of alarms, sent to method group.
     * - Method should ONLY be called once per MethodPack
     * - When I forget: 0.01s is 10.000.000 nanoseconds
     **/
    public void startTicking(long tickIntervalInNanoSecondsInput) throws InvocationTargetException, IllegalAccessException
    {
        tickIntervalInNanoSeconds = tickIntervalInNanoSecondsInput;

        /* to achieve consistence, first tick is set to contruction time, though no tick is sent before first interval */
        timeSinceLastTick = getTimeSinceInception();

        continueTicking = true;

        tickTock();
    }
    /** This method stops the Ticker ticking; because tock()
     *  checks if continueChecking is true - and if not
     *  returns false, which breaks the loop of ticktock(), thus
     *  ending time and the progress of things **/
    public void stopTicking() {continueTicking = false;}

    public void continueTicking() throws InvocationTargetException, IllegalAccessException
    {
        if(tickIntervalInNanoSeconds != 0){
            startTicking(tickIntervalInNanoSeconds);
        }
    }

    /**  **/
    private void tickTock() throws InvocationTargetException, IllegalAccessException
    {
        while (tock(clients)) {
            if (tick()) {
                signalClients(clients);
            }
        }
    }

    /* tock asks each MethodReference if it is effectively null and removes it from 'clients' if it is */
    private boolean tock(MethodPack methodsToCheck)
    {
        if (!continueTicking) { return false;}

        methodsToCheck.handleBadReferences();
        return true;
    }

    /*Tick checks if the time elapsed since last run of method is requested signal interval  */
    private boolean tick()
    {
        /* checking if it is time to tick*/
        return System.nanoTime() - timeSinceLastTick > tickIntervalInNanoSeconds;
    }
    /* */
    private void signalClients(MethodPack methodsThatExecuteOnTick) throws InvocationTargetException, IllegalAccessException
    {
        methodsThatExecuteOnTick.run();
    }

    public void addClient(MethodReference clientMethodToRunOnTick)
    {
        clients.add(clientMethodToRunOnTick);
    }

//    public void removeClient(Object objectToRemove){
//        for (int i =0; i < clients.length();
//    }

    public void overwriteClientList(MethodPack clientList)
    {
        clients = clientList;
    }
}
