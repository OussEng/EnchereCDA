package fr.eni.enchere.security;

import fr.eni.enchere.user.bo.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticatedUser {

    public User get() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof UserPrincipal)) {
            throw new RuntimeException("No authenticated user found.");
        }

        return ((UserPrincipal) authentication.getPrincipal()).getUser();
    }
}
