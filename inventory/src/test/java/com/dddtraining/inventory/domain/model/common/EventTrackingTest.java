package com.dddtraining.inventory.domain.model.common;

import org.junit.After;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class EventTrackingTest {

    private static final Logger logger = LoggerFactory
            .getLogger(EventTrackingTest.class);

    private List<Class<? extends DomainEvent>> handledEvents;
    private Map<String, String> handledNotifications;


    protected EventTrackingTest() {


        super();


    }


    protected void expectedEvent(Class<? extends DomainEvent> aDomainEventType) {
        this.expectedEvent(aDomainEventType, 1);
    }

    protected void expectedEvent(Class<? extends DomainEvent> aDomainEventType, int aTotal) {
        int count = 0;

        for (Class<? extends DomainEvent> type : this.handledEvents) {
            if (type == aDomainEventType) {
                ++count;
            }
        }

        if (count != aTotal) {
            throw new IllegalStateException("Expected " + aTotal + " " + aDomainEventType.getSimpleName() + " events, but handled "
                    + this.handledEvents.size() + " events: " + this.handledEvents);
        }
    }


    protected void expectedEvents(int anEventCount) {
        if (this.handledEvents.size() != anEventCount) {
            throw new IllegalStateException("Expected " + anEventCount + " events, but handled " + this.handledEvents.size()
                    + " events: " + this.handledEvents);

        }
    }

    protected void expectedNotification(Class<? extends DomainEvent> aNotificationType) {
        this.expectedNotification(aNotificationType, 1);
    }

    protected void expectedNotification(Class<? extends DomainEvent> aNotificationType, int aTotal) {
        try {
            Thread.sleep(200L);
        } catch (InterruptedException e) {
            // ignore
        }

        int count = 0;

        String notificationTypeName = aNotificationType.getName();

        for (String type : this.handledNotifications.values()) {
            if (type.equals(notificationTypeName)) {
                // System.out.println("MATCHED: " + type);
                // System.out.println("WITH: " + notificationTypeName);
                ++count;
            }
        }

        if (count != aTotal) {
            throw new IllegalStateException("Expected " + aTotal + " " + aNotificationType.getSimpleName()
                    + " notifications, but handled " + this.handledNotifications.size() + " notifications: "
                    + this.handledNotifications.values());
        }
    }

    @Before
    public void setUp() throws Exception {

        DomainEventPublisher.instance().reset();

        DomainEventPublisher.instance().subscribe(new DomainEventSubscriber<DomainEvent>() {
            @Override
            public void handleEvent(DomainEvent aDomainEvent) {

                logger.debug("\n"+aDomainEvent.toString());
              System.out.println("\n"+aDomainEvent.toString());

                handledEvents.add(aDomainEvent.getClass());
            }

            @Override
            public Class<DomainEvent> subscribedToEventType() {
                return DomainEvent.class;
            }
        });

        this.handledEvents = new ArrayList<Class<? extends DomainEvent>>();
        this.handledNotifications = new HashMap<String, String>();
    }


    @After
    public void tearDown() throws Exception {
    }


    public List<Class<? extends DomainEvent>> handledEvents() {
        return this.handledEvents;
    }

    public Map<String, String> handledNotifications() {
        return this.handledNotifications;
    }


}
