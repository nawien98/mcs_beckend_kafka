package org.accolite.service;
import org.accolite.model.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public boolean CreateUser(User user){
        User.persist(user);
        return true;
    }

}