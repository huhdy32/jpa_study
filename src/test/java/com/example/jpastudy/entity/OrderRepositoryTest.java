package com.example.jpastudy.entity;

import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class OrderRepositoryTest {

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
//
//        productRepository.queryProducts().stream()
//                .forEach(product -> product.getOrder().size());

//        entityManager.clear();

        orderRepository.queryAll().stream()
                .forEach(orders1 -> {
                    orders1.getMember().getName();
                    orders1.getProduct().getName();
                    orders1.getOrderCompanies().size();
                });
//        orderRepository.findAll().stream()
//                .forEach(orders1 -> {
////                    orders1.getMember().getName();
//                    orders1.getProduct().getName();
//                });

        // ManyToOne 관계의 경우, fetch Join을 통해 쿼리 하나로 만들어보리기
    }
}