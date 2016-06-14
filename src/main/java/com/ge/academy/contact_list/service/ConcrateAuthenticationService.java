package com.ge.academy.contact_list.service;

import com.ge.academy.contact_list.entity.Token;
import com.ge.academy.contact_list.entity.User;
import com.ge.academy.contact_list.exception.AuthenticationFailedException;
import com.ge.academy.contact_list.exception.EntityNotFoundException;
import com.ge.academy.contact_list.exception.TokenExpiredException;
import com.ge.academy.contact_list.repository.InMemoryTokenRepository;
import com.ge.academy.contact_list.repository.InMemoryUserRepository;
import com.ge.academy.contact_list.repository.TokenRepository;
import com.ge.academy.contact_list.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

/**
 * Created by 212566307 on 6/14/2016.
 */
public class ConcrateAuthenticationService implements AuthenticationService {

    @Autowired
    private InMemoryTokenRepository inMemoryTokenRepository;
    @Autowired
    private InMemoryUserRepository inMemoryUserRepository;

    @Override
    public Token findTokenById(String tokenId) throws TokenExpiredException {
        Token t = inMemoryTokenRepository.findOne(tokenId);
        if (t.getExpiresOn().isAfter(LocalDateTime.now())) {
            throw new TokenExpiredException();
        } else {
            return t;
        }
    }

    @Override
    public Token authenticate(String username, String password) throws AuthenticationFailedException {
        try {
            User user = inMemoryUserRepository.findOne(username);
            if(!user.getPassword().equals(password)){
                throw new AuthenticationFailedException();
            }else{
                Token t = new Token(null,user,user.getRole(),LocalDateTime.now().plusHours(2));
                inMemoryTokenRepository.save(t);
                return t;
            }
        } catch (EntityNotFoundException e) {
            throw new AuthenticationFailedException();
        }
    }

    @Override
    public void logOut(String username) throws EntityNotFoundException {
        try{
            inMemoryTokenRepository.delete(username);
        }catch(EntityNotFoundException e){
            throw new EntityNotFoundException(UserRepository.class,username);
        }
    }
}
