package com.vsproject.VisualProgrammingBackend.core.utils;

import com.vsproject.VisualProgrammingBackend.entity.User;
import com.vsproject.VisualProgrammingBackend.repository.UserRepository;
import com.vsproject.VisualProgrammingBackend.service.abstracts.TokenService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@Transactional
public class AuthUserUtil {

    private final UserRepository userRepository;
    private final TokenService tokenService;

    @Autowired
    public AuthUserUtil(UserRepository userRepository, TokenService tokenService) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
    }

    public User getAuthenticatedUser() {

        Authentication auth = this.getAuthentication();
        if (auth == null) { return null; }

        return userRepository.getUserByEmail(auth.getName()).orElse(null);

    }

    public boolean IsRequestAuthenticated() {
        Authentication auth = this.getAuthentication();
        return auth.isAuthenticated();
    }

    private Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public void demolishAuthUser() {
        User user = getAuthenticatedUser();
        if (user == null) {
            return;
        }

        demolishUser(user);

    }

    public void demolishUser(User user) {

        SecurityContextHolder.clearContext();
        tokenService.deleteAllTokenByUser(user);
        userRepository.delete(user);

    }

}