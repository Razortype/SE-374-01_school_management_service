package com.vsproject.VisualProgrammingBackend.service.concretes;

import com.vsproject.VisualProgrammingBackend.api.dto.PurchaseRequest;
import com.vsproject.VisualProgrammingBackend.api.dto.TransactionResponse;
import com.vsproject.VisualProgrammingBackend.core.results.*;
import com.vsproject.VisualProgrammingBackend.core.utils.TransactionUtil;
import com.vsproject.VisualProgrammingBackend.entity.Product;
import com.vsproject.VisualProgrammingBackend.entity.Student;
import com.vsproject.VisualProgrammingBackend.entity.StudentCard;
import com.vsproject.VisualProgrammingBackend.entity.Transaction;
import com.vsproject.VisualProgrammingBackend.repository.TransactionRepository;
import com.vsproject.VisualProgrammingBackend.service.abstracts.CardService;
import com.vsproject.VisualProgrammingBackend.service.abstracts.ProductService;
import com.vsproject.VisualProgrammingBackend.service.abstracts.StudentService;
import com.vsproject.VisualProgrammingBackend.service.abstracts.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final StudentService studentService;
    private final ProductService productService;
    private final CardService cardService;

    private final TransactionUtil transactionUtil;

    @Override
    public List<Transaction> getAllTransaction(LocalDateTime startDate, LocalDateTime endDate) {
        return transactionRepository.findAllByDateBetween(startDate, endDate);
    }

    @Override
    public List<Transaction> getAllTransaction(int page, int size, LocalDateTime startDate, LocalDateTime endDate) {

        boolean isPageable = page >= 0 && size >= 0;
        if (startDate == null) { startDate = LocalDateTime.of(1970, 1, 1, 0, 0); }
        if (endDate == null) { endDate = LocalDateTime.of(9999, 12, 31, 23, 59, 59); }

        if (isPageable) {
            Pageable pageable = PageRequest.of(page, size);
            Page<Transaction> transactionsPage = transactionRepository.findAllByDateBetween(startDate, endDate, pageable);
            return transactionsPage.getContent();
        } else {
            return getAllTransaction(startDate, endDate);
        }

    }

    @Override
    public DataResult<List<TransactionResponse>> getAllTransactionResponse(int page, int size, LocalDateTime startDate, LocalDateTime endDate) {

        List<String> appliedFeatures = new ArrayList<>();
        if (page >= 0 && size >= 0) { appliedFeatures.add("[Pageable]"); }
        if (startDate == null) { appliedFeatures.add("[MinStart]"); }
        if (endDate == null) { appliedFeatures.add("[MaxEnd]"); }

        List<Transaction> transactions = getAllTransaction(page, size, startDate, endDate);
        List<TransactionResponse> responses = transactionUtil.mapToTransactionResponses(transactions);
        return new SuccessDataResult<>(responses, "All Transaction fetched. " + String.join(" ", appliedFeatures));

    }

    @Override
    public Result save(Transaction transaction) {
        try {
            transactionRepository.save(transaction);
        } catch (Exception e) {
            return new ErrorResult("UEO: " + e.getMessage());
        }
        return new SuccessResult("Transaction saved");
    }

    @Override
    public Result purchase(PurchaseRequest request) {

        DataResult<Product> productResult = productService.getProductById(request.getProductId());
        if (!productResult.isSuccess()) {
            return new ErrorResult(productResult.getMessage());
        }
        DataResult<Student> studentResult = studentService.getStudentById(request.getStudentId());
        if (!studentResult.isSuccess()) {
            return new ErrorResult(studentResult.getMessage());
        }
        Product product = productResult.getData();
        Student student = studentResult.getData();
        StudentCard card = student.getCard();

        if (student.getBlockedProducts().contains(product)) {
            return new ErrorResult("Purchase failed. product blocked");
        }
        if (product.getQuantity() < request.getBoughtQuantity()) {
            return new ErrorResult(String.format("Requested amount not exist in storage: %d/%d", request.getBoughtQuantity(), product.getQuantity()));
        }
        double purchaseAmount = request.getBoughtQuantity() * product.getPrice();
        if (purchaseAmount > student.getCard().getCardBalance()) {
            return new ErrorResult(String.format("Student [%s] don't have sufficient balance. %d/%d", student.getSchoolNumber(), student.getCard().getCardBalance(), purchaseAmount));
        }

        product.setQuantity(product.getQuantity() - request.getBoughtQuantity());
        card.setCardBalance(card.getCardBalance() - purchaseAmount);

        Transaction transaction = Transaction.builder()
                .quantityBought(request.getBoughtQuantity())
                .totalPrice(purchaseAmount)
                .student(student)
                .product(product)
                .build();

        Result productSaveResult = productService.save(product);
        Result cardSaveResult = cardService.save(card);
        Result transactionSaveResult = save(transaction);

        for (Result result: List.of(productSaveResult, cardSaveResult, transactionSaveResult)) {
            if (!result.isSuccess()) {
                return result;
            }
        }

        return new SuccessResult("Purchase completed");
    }

    @Override
    public Result depositMoney(int studentId, double amount) {

        DataResult<Student> studentResult = studentService.getStudentById(studentId);
        if (!studentResult.isSuccess()) {
            return new ErrorResult(studentResult.getMessage());
        }
        Student student = studentResult.getData();
        StudentCard card = student.getCard();

        card.setCardBalance(card.getCardBalance() + amount);
        Result cardSaveResult = cardService.save(card);
        if (!cardSaveResult.isSuccess()) {
            return cardSaveResult;
        }

        return new SuccessResult("Deposit money complete");
    }

}
