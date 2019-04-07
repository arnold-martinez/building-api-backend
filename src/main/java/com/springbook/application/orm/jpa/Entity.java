package com.springbook.application.orm.jpa;

public interface Entity<T extends EntityId> {

    T getId();
}
