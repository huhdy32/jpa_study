package com.example.jpastudy.entity;

import static org.junit.jupiter.api.Assertions.*;

import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
class OrderCompanyRepositoryTest {

    @Autowired
    private EntityManager entityManager;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderCompanyRepository orderCompanyRepository;


    @Test
    void test() {
        final List<Member> members = new ArrayList<>();
        final List<Product> products = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            members.add(new Member(String.valueOf(i)));
        }
        for (int i = 0; i < 10; i++) {
            products.add(new Product("PRODUCT" + String.valueOf(i)));
        }
        final List<Orders> orders = members.stream()
                .flatMap(member -> products.stream()
                        .map(product -> new Orders(member, product)))
                .collect(Collectors.toList());

        final List<OrderCompany> orderCompanies = orders.stream()
                .flatMap(orders1 ->
                        Stream.of(new OrderCompany(orders1, "test"), new OrderCompany(orders1, "test"), new OrderCompany(orders1, "test"), new OrderCompany(orders1, "test"))
                ).toList();
        memberRepository.saveAll(members);
        productRepository.saveAll(products);
        orderRepository.saveAll(orders);
        orderCompanyRepository.saveAll(orderCompanies);

        entityManager.flush();
        entityManager.clear();

        orderCompanyRepository.findAll()
                .forEach(orderCompany -> {
                    System.out.println(orderCompany.getOrder().getName());
                    count ++;
                });
        System.out.println("총 갯수 "  + count);
    }
    int count = 0;

    @Test
    void testWithFetch() {
        final List<Member> members = new ArrayList<>();
        final List<Product> products = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            members.add(new Member(String.valueOf(i)));
        }
        for (int i = 0; i < 10; i++) {
            products.add(new Product("PRODUCT" + String.valueOf(i)));
        }
        final List<Orders> orders = members.stream()
                .flatMap(member -> products.stream()
                        .map(product -> new Orders(member, product)))
                .collect(Collectors.toList());

        final List<OrderCompany> orderCompanies = orders.stream()
                .flatMap(orders1 ->
                        Stream.of(new OrderCompany(orders1, "test"), new OrderCompany(orders1, "test"),
                                new OrderCompany(orders1, "test"), new OrderCompany(orders1, "test"))
                ).toList();
        memberRepository.saveAll(members);
        productRepository.saveAll(products);
        orderRepository.saveAll(orders);
        orderCompanyRepository.saveAll(orderCompanies);

        entityManager.flush();
        entityManager.clear();

        orderCompanyRepository.queryFetch()
                .forEach(orderCompany -> {
                    System.out.println(orderCompany.getOrder().getName());
                    count++;
                });
        System.out.println("총 갯수 " + count);
    }
}