package com.nataniel.api.domain;

//import org.hibernate.annotations.Entity;

import javax.persistence.*;

/**
 * Created by natan on 15/11/2016.
 */

@Entity
@Table(name="ORDER_DETAILS")
@NamedQueries({
        @NamedQuery(name = OrderDetails.Queries.FIND_DETAILS_BY_CONTROLNUMBER,
                query =
                        "select new com.nataniel.api.domain.OrderDetails(" +
                                " o.id," +
                                " o.controlNumber," +
                                " o.total)" +
                                " from OrderDetails o " +
                                " where o.controlNumber in :controlNumber"
                ),
        @NamedQuery(name = OrderDetails.Queries.UPDATE_TOTAL_BY_CONTROLNUMBER,
                query =
                        "update com.nataniel.api.domain.OrderDetails o" +
                                " set o.total = :total " +
                                " where o.controlNumber = :controlNumber"
                ),
        @NamedQuery(name = OrderDetails.Queries.FIND_ALL_ORDER_DETAILS,
                query =
                        "select new com.nataniel.api.domain.OrderDetails(" +
                                " o.id," +
                                " o.controlNumber," +
                                " o.total)" +
                                " from OrderDetails o "
        )
})
public class OrderDetails {
    public static final class Queries {
        public static final String FIND_DETAILS_BY_CONTROLNUMBER = "FIND_DETAILS_BY_CONTROLNUMBER";
        public static final String UPDATE_TOTAL_BY_CONTROLNUMBER = "UPDATE_TOTAL_BY_CONTROLNUMBER";
        public static final String FIND_ALL_ORDER_DETAILS = "FIND_ALL_ORDER_DETAILS";
    }

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "CONTROL_NUMBER")
    private String controlNumber;

    @Column(name = "TOTAL")
    private Double total;

    public OrderDetails() {
    }

    public OrderDetails(Long id, String controlNumber, Double total) {
        this.id = id;
        this.controlNumber = controlNumber;
        this.total = total;
    }

    public OrderDetails(String controlNumber, Double total) {
        this.controlNumber = controlNumber;
        this.total = total;
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

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}
