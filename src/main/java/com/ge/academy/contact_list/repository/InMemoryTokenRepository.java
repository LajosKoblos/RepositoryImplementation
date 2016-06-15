package com.ge.academy.contact_list.repository;

import com.ge.academy.contact_list.entity.Token;
import com.ge.academy.contact_list.entity.UserRole;
import com.ge.academy.contact_list.exception.EntityNotFoundException;
import org.springframework.stereotype.Repository;

import java.rmi.server.UID;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class InMemoryTokenRepository implements TokenRepository {

    private Map<String, Token> tokenStore = new ConcurrentHashMap<>();
    private AtomicInteger currentId = new AtomicInteger(0);

    public InMemoryTokenRepository() {
        tokenStore = new ConcurrentHashMap<>();
    }

    public InMemoryTokenRepository(Map<String, Token> m){
        this.tokenStore = m;
    }

    @Override
    public Token save(Token token) throws EntityNotFoundException {
        if(token.getTokenId() == null){
            return createNewRecord(token);
        }else{
            return updateExistingToken(token);
        }
    }

    private Token createNewRecord(Token token){
        String tokenId = UUID.randomUUID().toString();
        Token tokenWithId = new Token(token, tokenId);
        tokenStore.put(tokenId,tokenWithId);
        return tokenWithId;
    }

    private Token updateExistingToken(Token token) throws EntityNotFoundException{
        if(tokenStore.get(token.getTokenId()) == null){
            throw new EntityNotFoundException(Token.class,token.getTokenId());
        }else{
            tokenStore.put(token.getTokenId(),token);
            return token;
        }
    }

    @Override
    public void delete(String tokenId) throws EntityNotFoundException {
        if(tokenStore.get(tokenId) == null){
            throw new EntityNotFoundException(Token.class,tokenId);
        }else{
            tokenStore.remove(tokenId);
        }
    }

    @Override
    public Token findOne(String tokenId) throws EntityNotFoundException {
        Token thisToken = tokenStore.get(tokenId);

        if(thisToken == null){
            throw new EntityNotFoundException(Token.class,tokenId);
        }else{
            return thisToken;
        }
    }

}
