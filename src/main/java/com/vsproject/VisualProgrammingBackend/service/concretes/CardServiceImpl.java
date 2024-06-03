package com.vsproject.VisualProgrammingBackend.service.concretes;

import com.vsproject.VisualProgrammingBackend.core.results.ErrorResult;
import com.vsproject.VisualProgrammingBackend.core.results.Result;
import com.vsproject.VisualProgrammingBackend.core.results.SuccessResult;
import com.vsproject.VisualProgrammingBackend.entity.StudentCard;
import com.vsproject.VisualProgrammingBackend.repository.StudentCardRepository;
import com.vsproject.VisualProgrammingBackend.service.abstracts.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final StudentCardRepository cardRepository;

    @Override
    public Result save(StudentCard card) {
        try {
            cardRepository.save(card);
        } catch (Exception e) {
            return new ErrorResult("UEO: " + e.getMessage());
        }
        return new SuccessResult("StudentCard saved");
    }


}
