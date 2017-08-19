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
    private List<DomainEventSubscriber> subscribers;

    public static DomainEventPublisher instance(){ return instance.get();}


    public <T> void publish(final T aDomainEvent){

        if(!this.isPublishing() && this.hasSubscribers()){
            
            try{

                this.setPublishing(true);

                Class<?> eventType = aDomainEvent.getClass();

                List<DomainEventSubscriber<T>> allSubscribers = this.subscribers();


                for(DomainEventSubscriber aSubscriber : allSubscribers){

                    Class<?> subscribedToType = aSubscriber.subscribedToEventType();

                    if(eventType == subscribedToType || subscribedToType == DomainEvent.class){
                        aSubscriber.handleEvent(aDomainEvent);
                    }
                }


            }
            finally {
                this.setPublishing(false);
            }

        }
    }

    public void publishAll(Collection<DomainEvent> aDomainEvents) {
        for (DomainEvent domainEvent : aDomainEvents) {
            this.publish(domainEvent);
        }
    }

    public void reset(){
        if (!this.isPublishing()) {
            this.setSubscribers(null);
        }
    }

    public <T> void subscribe(DomainEventSubscriber<T> aSubscriber){
        if(!this.isPublishing()){
            this.ensureSubscribersList();
            this.subscribers().add(aSubscriber);
        }
    }

    private DomainEventPublisher() {
        super();

        this.setPublishing(false);
        this.ensureSubscribersList();
    }

    private void ensureSubscribersList() {
        if(!this.hasSubscribers()){
            this.setSubscribers(new ArrayList());
        }
    }

    private boolean hasSubscribers() {
        return this.subscribers() != null;
    }

    private List subscribers() {
        return this.subscribers;
    }


    public boolean isPublishing() {
        return this.publishing;
    }

    public void setPublishing(boolean publishing) {
        this.publishing = publishing;
    }

    public void setSubscribers(List aSubscribers) {
        this.subscribers = aSubscribers;
    }
}
