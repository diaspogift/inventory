package com.dddtraining.inventory.domain.model.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DomainEventPublisher {

    private static final ThreadLocal<DomainEventPublisher> instance = new ThreadLocal<DomainEventPublisher>() {

        protected DomainEventPublisher initialValue() {
            return new DomainEventPublisher();
        }
    };

    private boolean publishing;
    @SuppressWarnings("rawtypes")
	private List<DomainEventSubscriber> subscribers;

    private DomainEventPublisher() {
        super();

        this.setPublishing(false);
        this.ensureSubscribersList();
    }

    public static DomainEventPublisher instance() {
        return instance.get();
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	public <T> void publish(final T aDomainEvent) {

        if (!this.isPublishing() && this.hasSubscribers()) {

            try {

                this.setPublishing(true);

                Class<?> eventType = aDomainEvent.getClass();

				List<DomainEventSubscriber<T>> allSubscribers = this.subscribers();


                for (DomainEventSubscriber aSubscriber : allSubscribers) {

                    Class<?> subscribedToType = aSubscriber.subscribedToEventType();

                    if (eventType == subscribedToType || subscribedToType == DomainEvent.class) {
                        aSubscriber.handleEvent(aDomainEvent);
                    }
                }


            } finally {
                this.setPublishing(false);
            }

        }
    }

    public void publishAll(Collection<DomainEvent> aDomainEvents) {
        for (DomainEvent domainEvent : aDomainEvents) {
            this.publish(domainEvent);
        }
    }

    public void reset() {
        if (!this.isPublishing()) {
            this.setSubscribers(null);
        }
    }
    @SuppressWarnings("unchecked")
	public <T> void subscribe(DomainEventSubscriber<T> aSubscriber) {
        if (!this.isPublishing()) {
            this.ensureSubscribersList();
            this.subscribers().add(aSubscriber);
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
	public <T> void unSubscribe(DomainEventSubscriber<T> aSubscriber) {

		List<DomainEventSubscriber<T>> allSubscribers = this.subscribers();

        Class<?> eventType = aSubscriber.getClass();

        DomainEventSubscriber<T> subscriber = null;


        for (DomainEventSubscriber nextSubscriber : allSubscribers) {

            if (aSubscriber == nextSubscriber) {

                subscriber = nextSubscriber;
            }
        }

        this.subscribers().remove(subscriber);


    }

    @SuppressWarnings("rawtypes")
	private void ensureSubscribersList() {
        if (!this.hasSubscribers()) {
            this.setSubscribers(new ArrayList());
        }
    }

    private boolean hasSubscribers() {
        return this.subscribers() != null;
    }

    @SuppressWarnings("rawtypes")
	private List subscribers() {
        return this.subscribers;
    }


    public boolean isPublishing() {
        return this.publishing;
    }

    public void setPublishing(boolean publishing) {
        this.publishing = publishing;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	public void setSubscribers(List aSubscribers) {
        this.subscribers = aSubscribers;
    }
}
