package com.vsproject.VisualProgrammingBackend.repository;

import com.vsproject.VisualProgrammingBackend.entity.Parent;
import com.vsproject.VisualProgrammingBackend.entity.StudentBlockedProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BlockedProductRepository extends JpaRepository<StudentBlockedProduct, UUID> {

    Page<StudentBlockedProduct> findAllByParent(Parent parent, Pageable pageable);

}
