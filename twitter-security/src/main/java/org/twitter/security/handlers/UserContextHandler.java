package org.twitter.security.handlers;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.twitter.security.model.UserContext;

@Component
public class UserContextHandler {

    public UserContext getCurrentUserContext() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ((UserContext)principal);
    }
}
