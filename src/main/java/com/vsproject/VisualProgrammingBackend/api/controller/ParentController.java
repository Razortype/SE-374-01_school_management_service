package com.vsproject.VisualProgrammingBackend.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/parent")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
public class ParentController {



}
