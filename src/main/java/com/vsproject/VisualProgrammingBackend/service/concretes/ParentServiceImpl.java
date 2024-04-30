package com.vsproject.VisualProgrammingBackend.service.concretes;

import com.vsproject.VisualProgrammingBackend.api.dto.ParentUpgradeRequest;
import com.vsproject.VisualProgrammingBackend.core.enums.Role;
import com.vsproject.VisualProgrammingBackend.core.results.*;
import com.vsproject.VisualProgrammingBackend.core.utils.AuthUserUtil;
import com.vsproject.VisualProgrammingBackend.entity.Parent;
import com.vsproject.VisualProgrammingBackend.entity.User;
import com.vsproject.VisualProgrammingBackend.repository.ParentRepository;
import com.vsproject.VisualProgrammingBackend.service.abstracts.ParentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ParentServiceImpl implements ParentService {

    private final ParentRepository parentRepository;
    private final AuthUserUtil authUserUtil;

    @Override
    public DataResult<Parent> getParentByEmail(String email) {
        Parent parent = parentRepository.findParentByEmail(email).orElse(null);
        if (parent == null) {
            return new ErrorDataResult<>("Parent not found: " + email);
        }
        return new SuccessDataResult<>("Parent found");
    }

    @Override
    public Result create(User user, ParentUpgradeRequest request) {

        authUserUtil.demolishUser(user);

        Parent parent = Parent.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .phoneNumber(user.getPhoneNumber())
                .role(Role.PARENT)
                .birthYear(user.getBirthYear())
                .createdAt(user.getCreatedAt())

                .billingAddress(request.getBillingAddress())
                .upgradedAt(LocalDateTime.now())
                .build();

        return save(parent);
    }

    @Override
    public Result save(Parent parent) {
        try {
            parentRepository.save(parent);
        } catch (Exception e) {
            return new ErrorResult("UEO: " + e.getMessage());
        }
        return new SuccessResult("Parent saved");
    }
}
