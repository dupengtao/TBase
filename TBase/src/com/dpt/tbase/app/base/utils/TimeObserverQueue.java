package com.dpt.tbase.app.base.utils;

import java.util.Collection;
import java.util.LinkedList;

public class TimeObserverQueue<E extends TimeObserver> extends LinkedList<TimeObserver> {

    /**
     * 
     */
    private static final long serialVersionUID = 1675023904426152435L;
    private static TimeObserverQueue<TimeObserver> mQueue;
    
    public static TimeObserverQueue<? extends TimeObserver> getTimeObserverQueue(){
        if(mQueue==null){
            mQueue = new TimeObserverQueue<TimeObserver>();
        }
        return mQueue;
    }
    public static TimeObserverQueue<? extends TimeObserver> getTimeObserverQueue(Collection<? extends TimeObserver> collection){
        if(mQueue==null){
            mQueue = new TimeObserverQueue<TimeObserver>(collection);
        }
        return mQueue;
    }
    
    private TimeObserverQueue() {
        super();
    }

    private TimeObserverQueue(Collection<? extends TimeObserver> collection) {
        super(collection);
    }
    
    

    @Override
    public boolean add(TimeObserver object) {
        boolean add = super.add(object);
        try {
            if (add) {
                TimerManager.initTimerManager().addObserver(object);
            }
            return add;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void clear() {
        TimerManager.initTimerManager().deleteObservers();
        TimerManager.initTimer().cancel();
        TimerManager.stop();
        super.clear();
    }

    public boolean remove(TimeObserver object) {
        boolean remove = super.remove(object);
        if(remove){
            TimerManager.initTimerManager().deleteObserver(object);
            if(this.size()==0){
                clear();
            }
        }
        return remove;
    }
    
    

}
