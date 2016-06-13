package com.ge.academy.contact_list.repository;

import com.ge.academy.contact_list.entity.Token;
import com.ge.academy.contact_list.entity.UserRole;
import com.ge.academy.contact_list.exception.EntityNotFoundException;


public class InMemoryTokenRepository implements TokenRepository {
    @Override
    public Token save(Token token) throws EntityNotFoundException {
        return null;
    }

    @Override
    public void delete(String s) throws EntityNotFoundException {

    }

    @Override
    public Token findOne(String s) throws EntityNotFoundException {
        return null;
    }

    @Override
    public boolean isUserInRole(UserRole userRole) {
        return false;
    }
}
