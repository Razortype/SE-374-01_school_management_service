package com.vsproject.VisualProgrammingBackend.api.dto;

import com.vsproject.VisualProgrammingBackend.core.enums.privateEnums.Profession;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TeacherUpgradeRequest extends UserUpgradeRequest {

    private Profession profession;

}
