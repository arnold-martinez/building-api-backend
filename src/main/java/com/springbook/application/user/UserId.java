package com.springbook.application.user;

import com.springbook.application.orm.jpa.AbstractEntityId;

import java.util.UUID;

public class UserId extends AbstractEntityId<UUID> {

    protected UserId() { }

    public UserId(UUID id) {
        super(id);
    }
}
