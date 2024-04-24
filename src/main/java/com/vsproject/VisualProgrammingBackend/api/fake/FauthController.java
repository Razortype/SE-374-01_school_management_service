package com.vsproject.VisualProgrammingBackend.api.fake;

import com.vsproject.VisualProgrammingBackend.core.results.DataResult;
import com.vsproject.VisualProgrammingBackend.core.results.ErrorDataResult;
import com.vsproject.VisualProgrammingBackend.core.results.SuccessDataResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("api/v1/fauth")
public class FauthController {

    @PostMapping("/login")
    public ResponseEntity<DataResult> fakeAuthLogin(@RequestBody FLoginDto request) {

        DataResult result;
        if (request.getEmail().isBlank()
                || !request.getEmail().equals("admin")
                || request.getPassword().isBlank()
                || !request.getPassword().equals("1234")
        ) {
            result = new ErrorDataResult("Login Error Occurred");
        } else {
            result = new SuccessDataResult("login successful", "Login successful");
        }

        if (!result.isSuccess()) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);

    }

}