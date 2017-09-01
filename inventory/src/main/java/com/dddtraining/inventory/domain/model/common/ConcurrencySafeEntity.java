package com.dddtraining.inventory.domain.model.common;

import com.dddtraining.inventory.domain.model.Entity;

public class ConcurrencySafeEntity extends Entity {

    private int concurrencyVersion;

    protected ConcurrencySafeEntity() {
        super();
    }

    public int concurrencyVersion() {
        return this.concurrencyVersion;
    }

    public void setConcurrencyVersion(int aVersion) {
        this.failWhenConcurrencyViolation(aVersion);
        this.concurrencyVersion = aVersion;
    }

    public void failWhenConcurrencyViolation(int aVersion) {
        if (aVersion != this.concurrencyVersion()) {
            throw new IllegalStateException(
                    "Concurrency Violation: Stale data detected. Entity was already modified.");
        }
    }

}
