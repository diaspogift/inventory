package com.dddtraining.inventory.domain.model.common;

import java.time.ZonedDateTime;

public interface DomainEvent {

	public int eventVersion();
	public ZonedDateTime occurredOn();
}
