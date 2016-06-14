package com.ge.academy.contact_list.repository;

import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by 212564370 on 6/14/2016.
 */
@Component
public class AtomicLongIdProvider implements IdProvider {
    private final AtomicLong counter = new AtomicLong();
    @Override
    public Long getNewId() {
        return counter.incrementAndGet();
    }
}
