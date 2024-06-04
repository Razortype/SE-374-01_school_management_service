package com.vsproject.VisualProgrammingBackend.service.abstracts;

import com.vsproject.VisualProgrammingBackend.api.dto.ParentUpgradeRequest;
import com.vsproject.VisualProgrammingBackend.core.results.DataResult;
import com.vsproject.VisualProgrammingBackend.core.results.Result;
import com.vsproject.VisualProgrammingBackend.entity.Parent;
import com.vsproject.VisualProgrammingBackend.entity.User;

public interface ParentService {

    DataResult<Parent> getPrentById(int id);
    DataResult<Parent> getParentByEmail(String email);
    Result create(User user, ParentUpgradeRequest request);
    Result save(Parent parent);

}
