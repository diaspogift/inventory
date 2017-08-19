package com.dddtraining.inventory.domain.model.common;

public interface DomainEventSubscriber<T> {

    public void handleEvent(final T aDomainEvent);
    public Class<T> subscribedToEventType();

}
