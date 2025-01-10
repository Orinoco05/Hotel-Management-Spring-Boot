package com.example.demo.repository;

import com.example.demo.model.CustomerEntity;
import org.apache.catalina.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

    @Query(value = "select u from CustomerEntity u where u.status='ACTIVE' and (u.name like :keyword " +
            "or u.phone like :keyword" +
            " or u.email like :keyword" +
            " or u.citizen_identification_card like :keyword) ")
    Page<CustomerEntity> searchByKeyword(String keyword, Pageable pageable);

    CustomerEntity findByName(String name);

    CustomerEntity findByEmail(String email);
}
