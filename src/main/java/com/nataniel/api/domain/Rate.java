package com.nataniel.api.domain;

//import org.hibernate.annotations.Entity;
import javax.persistence.Entity;

import javax.persistence.*;

/**
 * Created by natan on 15/11/2016.
 */

@Entity
@Table(name="RATE")
@NamedQueries({
        @NamedQuery(name = Rate.Queries.FIND_RATES_BY_USERID,
                query =
                        "select new com.nataniel.api.domain.Rate(" +
                                " u.id," +
                                " u.userIdFrom," +
                                " u.userIdTo," +
                                " u.rate," +
                                " u.comment)" +
                                " from Rate u " +
                                " where u.userIdTo = :userIdTo"
        )
})
public class Rate {

    public static final class Queries {
        public static final String FIND_RATES_BY_USERID = "FIND_RATES_BY_USERID";
    }

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "USERID_FROM")
    private Long userIdFrom;

    @Column(name = "USERID_TO")
    private Long userIdTo;

    @Column(name = "RATE")
    private Integer rate;

    @Column(name = "COMMENT")
    private String comment;

    public Rate() {
    }

    public Rate(Long id, Long userIdFrom, Long userIdTo, Integer rate, String comment) {
        this.id = id;
        this.userIdFrom = userIdFrom;
        this.userIdTo = userIdTo;
        this.rate = rate;
        this.comment = comment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserIdFrom() {
        return userIdFrom;
    }

    public void setUserIdFrom(Long userIdFrom) {
        this.userIdFrom = userIdFrom;
    }

    public Long getUserIdTo() {
        return userIdTo;
    }

    public void setUserIdTo(Long userIdTo) {
        this.userIdTo = userIdTo;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
