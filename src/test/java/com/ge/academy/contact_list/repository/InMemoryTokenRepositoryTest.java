package com.ge.academy.contact_list.repository;

import com.ge.academy.contact_list.entity.Token;
import com.ge.academy.contact_list.entity.User;
import com.ge.academy.contact_list.entity.UserRole;
import org.junit.Before;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Created by 212566307 on 6/14/2016.
 */
public class InMemoryTokenRepositoryTest {

    private InMemoryTokenRepository inMemoryTokenRepository;
    private Map<String, Token> tokenMap;
    private Token token1;
    private Token token2;
    private Token token3;

    @Before
    public void setUp(){
        User user = new User("Andras","titkospw", UserRole.USER);
        LocalDateTime localDateTime = LocalDateTime.now();
        token1 = new Token(null, user, user.getRole(), localDateTime);
        tokenMap = new ConcurrentHashMap<>();
        tokenMap.put(token1.getTokenId(), token1);
        tokenMap.put(token2.getTokenId(), token2);
        tokenMap.put(token3.getTokenId(), token3);
        inMemoryTokenRepository = new InMemoryTokenRepository();//tokenMap cost keene? TODO?

    }

}
