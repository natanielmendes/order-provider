package com.nataniel.api.domain;

import javax.persistence.Entity;
import javax.persistence.*;

/**
 * Created by natan on 15/11/2016.
 */

@Entity
@Table(name="PRODUCT_ORDER")
@NamedQueries({
//        @NamedQuery(name = ProductOrder.Queries.FIND_USER_BY_LOGIN_AND_PASSWORD,
//                query =
//                        "select new com.nataniel.api.domain.ProductOrder(" +
//                                " u.id," +
//                                " u.login," +
//                                " u.name," +
//                                " u.email," +
//                                " u.password," +
//                                " u.city," +
//                                " u.region," +
//                                " u.birthDate," +
//                                " u.job," +
//                                " u.course," +
//                                " u.university," +
//                                " u.workSince)" +
//                                " from ProductOrder u " +
//                                " where u.login = :login and u.password = :password"
//        ),
//        @NamedQuery(name = ProductOrder.Queries.FIND_USER_BY_USERID,
//                query =
//                        "select new com.nataniel.api.domain.ProductOrder(" +
//                                " u.id," +
//                                " u.login," +
//                                " u.name," +
//                                " u.email," +
//                                " u.password," +
//                                " u.city," +
//                                " u.region," +
//                                " u.birthDate," +
//                                " u.job," +
//                                " u.course," +
//                                " u.university," +
//                                " u.workSince)" +
//                                " from ProductOrder u " +
//                                " where u.id IN :userIds"
//                ),
        @NamedQuery(name = ProductOrder.Queries.FIND_ALL_ORDERS,
        query =
                "select new com.nataniel.api.domain.ProductOrder(" +
                        " p.id," +
                        " p.controlNumber," +
                        " p.registrationDate," +
                        " p.name," +
                        " p.price," +
                        " p.productQuantity," +
                        " p.customerCode)" +
                        " from ProductOrder p "
        ),
        @NamedQuery(name = ProductOrder.Queries.FIND_PRODUCTS_BY_CONTROLNUMBER,
        query =
                "select new com.nataniel.api.domain.ProductOrder(" +
                        " p.id," +
                        " p.controlNumber," +
                        " p.registrationDate," +
                        " p.name," +
                        " p.price," +
                        " p.productQuantity," +
                        " p.customerCode)" +
                        " from ProductOrder p " +
                        " where p.controlNumber in :controlNumber"
        ),
        @NamedQuery(name = ProductOrder.Queries.FIND_PRODUCTS_BY_DATE,
                query =
                        "select new com.nataniel.api.domain.ProductOrder(" +
                                " p.id," +
                                " p.controlNumber," +
                                " p.registrationDate," +
                                " p.name," +
                                " p.price," +
                                " p.productQuantity," +
                                " p.customerCode)" +
                                " from ProductOrder p " +
                                " where p.registrationDate in :registrationDate"
        )
})
public class ProductOrder {

    public static final class Queries {
        public static final String FIND_ALL_ORDERS = "FIND_ALL_ORDERS";
        public static final String FIND_PRODUCTS_BY_CONTROLNUMBER = "FIND_PRODUCTS_BY_CONTROLNUMBER";
        public static final String FIND_PRODUCTS_BY_DATE = "FIND_PRODUCTS_BY_DATE";
    }

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "CONTROL_NUMBER")
    private String controlNumber;

    @Column(name = "REG_DT")
    private String registrationDate;

    @Column(name = "NOME")
    private String name;

    @Column(name = "PRICE")
    private Double price;

    @Column(name = "PROD_QUANTITY")
    private Integer productQuantity;

    @Column(name = "COD_CUSTOMER")
    private String customerCode;

    public ProductOrder() {
    }

    public ProductOrder(Long id, String controlNumber, String registrationDate, String name, Double price, Integer productQuantity, String customerCode) {
        this.id = id;
        this.controlNumber = controlNumber;
        this.registrationDate = registrationDate;
        this.name = name;
        this.price = price;
        this.productQuantity = productQuantity;
        this.customerCode = customerCode;
    }

    public ProductOrder(String controlNumber, String registrationDate, String name, Double price, Integer productQuantity, String customerCode) {
        this.controlNumber = controlNumber;
        this.registrationDate = registrationDate;
        this.name = name;
        this.price = price;
        this.productQuantity = productQuantity;
        this.customerCode = customerCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getControlNumber() {
        return controlNumber;
    }

    public void setControlNumber(String controlNumber) {
        this.controlNumber = controlNumber;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(Integer productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }
}
