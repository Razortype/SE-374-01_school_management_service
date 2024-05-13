package com.vsproject.VisualProgrammingBackend.api.controller;

import com.vsproject.VisualProgrammingBackend.core.enums.privateEnums.AccountType;
import com.vsproject.VisualProgrammingBackend.core.results.DataResult;
import com.vsproject.VisualProgrammingBackend.core.results.Result;
import com.vsproject.VisualProgrammingBackend.entity.User;
import com.vsproject.VisualProgrammingBackend.service.abstracts.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<DataResult<User>> getUserById(@PathVariable int id) {

        DataResult<User> result = userService.getUserById(id);
        result.determineHttpStatus();

        return ResponseEntity.status(result.getHttpStatus())
                .body(result);

    }

    @PostMapping("/upgrade")
    public ResponseEntity<Result> upgradeUser(
            @RequestParam(name = "account_type") AccountType accountType,
            @RequestBody Map<String, Object> upgradeRequest
            ) {

        Result upgradeResult = userService.upgradeAuthUserAccount(accountType, upgradeRequest);
        upgradeResult.determineHttpStatus();

        return ResponseEntity.status(upgradeResult.getHttpStatus())
                .body(upgradeResult);

    }

}
