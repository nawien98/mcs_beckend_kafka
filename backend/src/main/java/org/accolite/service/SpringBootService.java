package org.accolite.service;

import org.accolite.model.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SpringBootService {
    private static final Logger logger = LoggerFactory.getLogger(QuarkusService.class);
    public boolean generateSpringService(Task task){
        logger.info("[generateSpringService] request: {}", task);
        return false;
    }
}
