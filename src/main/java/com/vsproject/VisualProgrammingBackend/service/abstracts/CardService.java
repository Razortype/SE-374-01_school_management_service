package com.vsproject.VisualProgrammingBackend.service.abstracts;

import com.vsproject.VisualProgrammingBackend.core.results.Result;
import com.vsproject.VisualProgrammingBackend.entity.Student;
import com.vsproject.VisualProgrammingBackend.entity.StudentCard;

public interface CardService {

    Result save(StudentCard card);
    StudentCard generateStudentCard();

}
