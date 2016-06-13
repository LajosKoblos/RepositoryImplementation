package com.ge.academy.contact_list.repository;

import com.ge.academy.contact_list.entity.Token;
import com.ge.academy.contact_list.entity.UserRole;
import com.ge.academy.contact_list.exception.EntityNotFoundException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;


public class InMemoryTokenRepository implements TokenRepository {

    private Map<Integer, Token> tokenStore = new ConcurrentHashMap<>();
    private AtomicInteger currentId = new AtomicInteger(0);

    @Override
    public Token save(Token token) throws EntityNotFoundException {
        if(token.getTokenId() == null){

        }
    }

    private Token updateExistingToken(Token token) throws EntityNotFoundException{
        if(tokenStore.)
    }

    private Token createNewRecord(Token token){
        tokenStore.put(currentId.getAndIncrement(),token);
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
