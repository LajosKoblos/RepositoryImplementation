package com.ge.academy.contact_list.repository;
/*
import com.ge.academy.contact_list.entity.Token;
import com.ge.academy.contact_list.entity.User;
import com.ge.academy.contact_list.entity.UserRole;*/
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;


/**
 * Created by 212566307 on 6/14/2016.
 */
public class InMemoryTokenRepositoryTest {

 /*   private InMemoryTokenRepository inMemoryTokenRepository;
    private Map<String, Token> tokenMap;
    private Token token1;
    private Token token2;
    private Token token3;

    @Before
    public void setUp() {
        User user = new User("Andras", "titkospw", UserRole.USER);
        LocalDateTime localDateTime = LocalDateTime.now();
        token1 = new Token("token11", user, user.getRole(), localDateTime);
        token2 = new Token("token22", user, user.getRole(), localDateTime);
        token3 = new Token("token33", user, user.getRole(), localDateTime);
        tokenMap = new ConcurrentHashMap<>();
        tokenMap.put(token1.getTokenId(), token1);
        tokenMap.put(token2.getTokenId(), token2);
        tokenMap.put(token3.getTokenId(), token3);
        inMemoryTokenRepository = new InMemoryTokenRepository();//tokenMap cost keene? TODO?
    }
*/
    /*@Test
    public void findAllShouldReturnAllElementsOfInternalMap() {
        tokenMap = mock(HashMap.class);

        //List<Token> returnedCollection = Arrays.asList(token1, token2, token3);

        when(tokenMap.values()).thenReturn(returnedCollection);

        inMemoryTokenRepository = new InMemoryTokenRepository();
        inMemoryTokenRepository.save(token1);
        inMemoryTokenRepository.save(token2);
        inMemoryTokenRepository.save(token3);

        //When
        //Token result = inMemoryTokenRepository.findOne("token11");

        //Then
        //assertEquals(returnedCollection.toArray(), result.toArray());
    }*/

}
