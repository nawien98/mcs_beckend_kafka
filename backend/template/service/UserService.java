package org.accolite.service;
import org.accolite.model.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public boolean CreateUser(User user){
        logger.info("[UserService] create user request: {}", user);
        User.persist(user);
        return true;
    }

    public User FindUserById(long id){
        logger.info("[UserService] find user request: {}", id);
        return User.findById(id);
    }

    public boolean UpdateUser(long id, User user){
        logger.info("[UserService] update user request: {} {}", id, user.name);
        User entity = User.findById(id);
        if (entity == null){
            return false;
        }else {
            entity.name=user.name;
            return true;
        }
    }

    public boolean DeleteUserById(long id){
        logger.info("[UserService] delete user request: {}", id);
        return User.deleteById(id);
    }

}