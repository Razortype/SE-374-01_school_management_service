package com.vsproject.VisualProgrammingBackend.service.abstracts;

import com.vsproject.VisualProgrammingBackend.core.enums.privateEnums.AccountType;
import com.vsproject.VisualProgrammingBackend.core.results.DataResult;
import com.vsproject.VisualProgrammingBackend.core.results.Result;
import com.vsproject.VisualProgrammingBackend.entity.User;

import java.util.Map;

public interface UserService {

    DataResult<User> getUserById(int id);
    DataResult<User> getUserByEmail(String email);
    Result upgradeAuthUserAccount(AccountType accountType, Map<String, Object> request);
    Result upgradeAdminUserAccount(int userId, AccountType accountType, Map<String, Object> request);
    Result upgradeUserAccount(User user, AccountType accountType, Map<String, Object> request);
    void delete(User user);

}
