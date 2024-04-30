package com.vsproject.VisualProgrammingBackend.service.abstracts;

import com.vsproject.VisualProgrammingBackend.entity.User;

public interface TokenService {

    void deleteAllTokenByUser(User user);

}
