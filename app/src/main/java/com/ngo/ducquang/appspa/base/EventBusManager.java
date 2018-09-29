package com.ngo.ducquang.appspa.base;

import org.greenrobot.eventbus.EventBus;

public class EventBusManager
{
    private final EventBus eventBus = EventBus.getDefault();
    
    private static EventBusManager instance = null;

    public static EventBusManager instance()
    {
        synchronized (EventBusManager.class)
        {
            if (instance == null)
            {
                instance = new EventBusManager();
            }

            return instance;
        }
    }

    private EventBusManager()
    {
    }

    public void post(Object object)
    {
        if(object != null && eventBus != null )
        {
            eventBus.post(object);
        }
        else
        {
            LogManager.tagDefault().error("post null");
        }
    }

    public void register(Object object)
    {
        if(object != null && eventBus != null && (!eventBus.isRegistered(object)))
        {
            eventBus.register(object);
        }
        else
        {
            LogManager.tagDefault().error("register null");
        }
    }

    public void unregister(Object object)
    {
        if(object != null && eventBus != null)
        {
            eventBus.unregister(object);
        }
        else
        {
            LogManager.tagDefault().error("unregister null");
        }
    }

    public void postSticky(Object object)
    {
        if(object != null && eventBus != null)
        {
            eventBus.postSticky(object);
        }
        else
        {
            LogManager.tagDefault().error("unregister null");
        }
    }
}
