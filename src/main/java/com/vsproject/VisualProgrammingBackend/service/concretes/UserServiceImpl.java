package com.vsproject.VisualProgrammingBackend.service.concretes;

import com.vsproject.VisualProgrammingBackend.api.dto.ParentUpgradeRequest;
import com.vsproject.VisualProgrammingBackend.api.dto.StudentUpgradeRequest;
import com.vsproject.VisualProgrammingBackend.api.dto.TeacherUpgradeRequest;
import com.vsproject.VisualProgrammingBackend.api.dto.UserUpgradeRequest;
import com.vsproject.VisualProgrammingBackend.core.enums.AccountType;
import com.vsproject.VisualProgrammingBackend.core.enums.Profession;
import com.vsproject.VisualProgrammingBackend.core.enums.Role;
import com.vsproject.VisualProgrammingBackend.core.results.*;
import com.vsproject.VisualProgrammingBackend.core.utils.AuthUserUtil;
import com.vsproject.VisualProgrammingBackend.entity.User;
import com.vsproject.VisualProgrammingBackend.repository.UserRepository;
import com.vsproject.VisualProgrammingBackend.service.abstracts.ParentService;
import com.vsproject.VisualProgrammingBackend.service.abstracts.StudentService;
import com.vsproject.VisualProgrammingBackend.service.abstracts.TeacherService;
import com.vsproject.VisualProgrammingBackend.service.abstracts.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final ParentService parentService;
    private final StudentService studentService;
    private final TeacherService teacherService;

    private final AuthUserUtil authUserUtil;

    @Override
    public DataResult<User> getUserById(int id) {

        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return new ErrorDataResult<>("User not found: " + id);
        }
        return new SuccessDataResult<>(user, "User found");

    }

    @Override
    public DataResult<User> getUserByEmail(String email) {

        User user = userRepository.getUserByEmail(email).orElse(null);
        if (user == null) {
            return new ErrorDataResult<>("User not found: " + email);
        }
        return new SuccessDataResult<>(user, "User found");

    }

    @Override
    public Result upgradeAuthUserAccount(AccountType accountType, Map<String, Object> request) {
        User user = authUserUtil.getAuthenticatedUser();
        if (user == null) {
            return new ErrorResult("Authenticated User not found");
        }
        return upgradeUserAccount(user, accountType, request);
    }

    @Override
    public Result upgradeUserAccount(User user, AccountType accountType, Map<String, Object> request) {

        if (accountType == null) {
            return new ErrorResult("AccountType need to specified");
        }

        if (user.getRole() != Role.USER) {
            return new ErrorResult("Upgrade only allowed to USER role only: " + user.getRole().name());
        }

        DataResult upgradeDtoResult = convertObjectToDto(user, accountType, request);
        if (!upgradeDtoResult.isSuccess()) {
            return new ErrorResult(upgradeDtoResult.getMessage());
        }
        Object upgradeDto = upgradeDtoResult.getData();

        Result createResult = new ErrorResult("Transfer object not initialised (upgradeDTO instance error)");
        if (upgradeDto instanceof ParentUpgradeRequest) {
            createResult = parentService.create(user, (ParentUpgradeRequest) upgradeDto);
        } else if (upgradeDto instanceof StudentUpgradeRequest) {
            createResult = studentService.create(user, (StudentUpgradeRequest) upgradeDto);
        } else if (upgradeDto instanceof TeacherUpgradeRequest) {
            createResult = teacherService.create(user, (TeacherUpgradeRequest) upgradeDto);
        }

        return createResult;

    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }

    private DataResult<UserUpgradeRequest> convertObjectToDto(User user, AccountType accountType, Map<String, Object> request) {

        String firstname = user.getFirstname();
        String lastname = user.getLastname();
        String phoneNumber = user.getPhoneNumber();

        // parent params
        String billingAddress = "";

        // student params
        String schoolNumber = "";

        // teacher params
        Profession profession = Profession.NOT_DEFINED;

        try {

            switch (accountType) {
                case PARENT -> {
                    billingAddress = (String) request.get("billing_address");
                }
                case STUDENT -> {
                    schoolNumber = (String) request.get("school_number");
                }
                case TEACHER -> {
                    profession = (Profession) request.get("profession");
                }
            }
        } catch (IllegalArgumentException e) {
            return new ErrorDataResult<>("Error creating DTO: " + e.getMessage());
        } catch (Exception e) {
            return new ErrorDataResult<>("UEO: " + e.getMessage());
        }

        UserUpgradeRequest editRequest = switch (accountType) {
            case PARENT -> ParentUpgradeRequest.builder()
                    .firstname(firstname)
                    .lastname(lastname)
                    .phoneNumber(phoneNumber)
                    .billingAddress(billingAddress)
                    .build();
            case STUDENT -> StudentUpgradeRequest.builder()
                    .firstname(firstname)
                    .lastname(lastname)
                    .phoneNumber(phoneNumber)
                    .schoolNumber(schoolNumber)
                    .build();
            case TEACHER -> TeacherUpgradeRequest.builder()
                    .firstname(firstname)
                    .lastname(lastname)
                    .phoneNumber(phoneNumber)
                    .profession(profession)
                    .build();
        };

        return new SuccessDataResult<>(editRequest, "EditRequest Created");

    }

}
