package com.ge.academy.contact_list.repository;

import com.ge.academy.contact_list.entity.Token;
import com.ge.academy.contact_list.entity.User;
import com.ge.academy.contact_list.entity.UserRole;
import com.ge.academy.contact_list.exception.EntityNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.*;



/**
 * Created by 212566307 on 6/14/2016.
 */
public class InMemoryTokenRepositoryTest {

    private InMemoryTokenRepository inMemoryTokenRepository;
    private Map<String, Token> tokenMap;
    private Token token1;
    private Token token2;
    private Token token3;
    private Token tokenWithNoId;

    @Before
    public void setUp() {
        User user = new User("Andras", "titkospw", UserRole.USER);
        LocalDateTime localDateTime = LocalDateTime.now();
        token1 = new Token("token11", user, user.getRole(), localDateTime);
        token2 = new Token("token22", user, user.getRole(), localDateTime);
        token3 = new Token("token33", user, user.getRole(), localDateTime);
        tokenWithNoId = new Token(null, user, user.getRole(), localDateTime);
        tokenMap = new ConcurrentHashMap<>();
        tokenMap.put(token1.getTokenId(), token1);
        tokenMap.put(token2.getTokenId(), token2);
        tokenMap.put(token3.getTokenId(), token3);
        inMemoryTokenRepository = new InMemoryTokenRepository(tokenMap);//tokenMap cost keene? TODO?
    }

    @Test
    public void findAllShouldReturnAllElementsOfInternalMap() {
        tokenMap = mock(HashMap.class);
        User user = new User("Andras", "titkospw", UserRole.USER);
        Token newToken = new Token("newtoken", user, user.getRole(), LocalDateTime.now());

        when(tokenMap.get("newtoken")).thenReturn(newToken);

        inMemoryTokenRepository = new InMemoryTokenRepository(tokenMap);

        //When
        Token result = inMemoryTokenRepository.findOne("newtoken");

        //Then
        verify(tokenMap).get("newtoken");
        assertEquals(newToken, result);
    }

    @Test(expected = EntityNotFoundException.class)
    public void addTokenWithNoIdShouldReturnException() {
        tokenMap = mock(HashMap.class);

        inMemoryTokenRepository = new InMemoryTokenRepository(tokenMap);

        when(tokenMap.get(token1.getTokenId())).thenReturn(null);

        inMemoryTokenRepository.save(token1);

    }

    @Test
    public void addTokenWithNoIdShouldReturnExceptionWithParams() {
        tokenMap = mock(HashMap.class);

        inMemoryTokenRepository = new InMemoryTokenRepository(tokenMap);

        when(tokenMap.get(token1.getTokenId())).thenReturn(null);

        try {
            inMemoryTokenRepository.save(token1);
        } catch (EntityNotFoundException e) {
            assertEquals(Token.class, e.getEntityType());
            assertEquals(token1.getTokenId(), e.getEntityId());
        }

    }

    @Test
    public void saveWithNoIdToken() {
        tokenMap = mock(HashMap.class);
       /// UUID uuid = mock(UUID.class); ///eztmeghogyanha statikus

        inMemoryTokenRepository = new InMemoryTokenRepository(tokenMap);

        inMemoryTokenRepository.save(tokenWithNoId);

        //Then
        verify(tokenMap).put(any(), tokenWithNoId);
    }

    @Test
    public void findOneElementInRepositoryThrowError(){
        tokenMap = mock(HashMap.class);

        inMemoryTokenRepository = new InMemoryTokenRepository(tokenMap);

        when(tokenMap.get("tokenid")).thenReturn(null);

        try{
            inMemoryTokenRepository.findOne("tokenid");
        }catch(EntityNotFoundException e){
            assertEquals("tokenid",e.getEntityId());
            assertEquals(Token.class,e.getEntityType());
        }
    }

    @Test
    public void findOneElement(){
        tokenMap = mock(HashMap.class);

        inMemoryTokenRepository = new InMemoryTokenRepository(tokenMap);

        when(tokenMap.get("tokenid")).thenReturn(token1);

        Token t = inMemoryTokenRepository.findOne("tokenid");

        assertEquals(t,token1);
    }

    @Test
    public void deleteExistingElement(){
        tokenMap = mock(HashMap.class);

        inMemoryTokenRepository = new InMemoryTokenRepository(tokenMap);

        when(tokenMap.get("tokenid")).thenReturn(token1);

        inMemoryTokenRepository.delete("tokenid");

        verify(tokenMap).remove("tokenid");
    }



    @Test
    public void deleteNonExistingElement(){
        tokenMap = mock(HashMap.class);

        inMemoryTokenRepository = new InMemoryTokenRepository(tokenMap);

        when(tokenMap.get("tokenid")).thenReturn(null);

        try {
            inMemoryTokenRepository.delete("tokenid");
        }catch(EntityNotFoundException e){
            assertEquals(Token.class,e.getEntityType());
            assertEquals("tokenid",e.getEntityId());
        }

    }
}
