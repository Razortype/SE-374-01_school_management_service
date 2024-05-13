package com.vsproject.VisualProgrammingBackend.api.controller;

import com.vsproject.VisualProgrammingBackend.core.results.DataResult;
import com.vsproject.VisualProgrammingBackend.core.utils.EnumUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/enum")
@RequiredArgsConstructor
public class EnumController {

    private final EnumUtil enumUtil;

    @GetMapping("public/{enum-name}")
    public ResponseEntity<DataResult<List<String>>> getPublicEnumValues(@PathVariable(name = "enum-name") String enumName) {
        DataResult<List<String>> result = enumUtil.getPublicEnumValues(enumName);

        result.determineHttpStatus();
        return ResponseEntity.status(result.getHttpStatus())
                .body(result);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("private/{enum-name}")
    public ResponseEntity<DataResult<List<String>>> getPrivateEnumValues(@PathVariable(name = "enum-name") String enumName) {
        DataResult<List<String>> result = enumUtil.getPrivateEnumValues(enumName);

        result.determineHttpStatus();
        return ResponseEntity.status(result.getHttpStatus())
                .body(result);
    }

}
