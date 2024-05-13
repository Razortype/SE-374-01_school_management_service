package com.vsproject.VisualProgrammingBackend.core.utils;

import com.vsproject.VisualProgrammingBackend.core.results.DataResult;
import com.vsproject.VisualProgrammingBackend.core.results.ErrorDataResult;
import com.vsproject.VisualProgrammingBackend.core.results.SuccessDataResult;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EnumUtil {

    public DataResult<List<String>> getPublicEnumValues(String enumName) {
        return getEnumValuesByPath(enumName, "com.vsproject.VisualProgrammingBackend.core.enums.publicEnums.");
    }

    public DataResult<List<String>> getPrivateEnumValues(String enumName) {
        return getEnumValuesByPath(enumName, "com.vsproject.VisualProgrammingBackend.core.enums.privateEnums.");
    }

    public DataResult<List<String>> getEnumValuesByPath(String enumName, String path) {
        List<String> enumValues = new ArrayList<>();
        try {
            String className = convertToClassName(enumName);
            Class<?> enumClass = Class.forName(path + className);

            if (enumClass.isEnum()) {
                Object[] constants = enumClass.getEnumConstants();
                for (Object constant : constants) {
                    enumValues.add(constant.toString());
                }
            }
        } catch (ClassNotFoundException e) {
            return new ErrorDataResult<>("Enum not found: " + enumName);
        } catch (Exception e) {
            return new ErrorDataResult<>("Unexpected Error Occurred: " + e.getMessage());
        }

        return new SuccessDataResult<>(enumValues, "Enum found");
    }

    private String convertToClassName(String enumName) {
        StringBuilder classNameBuilder = new StringBuilder();
        String[] parts = enumName.split("-");
        for (String part : parts) {
            classNameBuilder.append(Character.toUpperCase(part.charAt(0))).append(part.substring(1));
        }
        return classNameBuilder.toString();
    }

}
