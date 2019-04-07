package com.springbook.application.orm.jpa;

public interface UniqueIdGenerator<T> {

    T getNextUniqueId();
}
