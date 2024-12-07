package com.example.jpastudy.entity;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderCompanyRepository extends JpaRepository<OrderCompany, Long> {

    @Query("SELECT oc FROM OrderCompany oc JOIN FETCH oc.order")
    List<OrderCompany> queryFetch();
}
