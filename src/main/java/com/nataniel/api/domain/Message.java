package com.nataniel.api.domain;

//import org.hibernate.annotations.Entity;
import javax.persistence.Entity;

import javax.persistence.*;
import javax.validation.constraints.Max;

/**
 * Created by natan on 15/11/2016.
 */

@Entity
@Table(name="MESSAGE")
@NamedQueries({
        @NamedQuery(name = Message.Queries.FIND_MESSAGES_BY_USERIDFROM_AND_USERIDTO,
                query =
                        "select new com.nataniel.api.domain.Message(" +
                                " m.id," +
                                " m.userIdFrom," +
                                " m.userIdTo," +
                                " m.message)" +
                                " from Message m " +
                                " where (m.userIdFrom = :userIdFrom" +
                                " and m.userIdTo = :userIdTo)" +
                                " or (m.userIdFrom = :userIdTo" +
                                " and m.userIdTo = :userIdFrom)" +
                                " order by m.id"
        )
})
public class Message {

    public static final class Queries {
        public static final String FIND_MESSAGES_BY_USERIDFROM_AND_USERIDTO = "FIND_MESSAGES_BY_USERIDFROM_AND_USERIDTO";
    }

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "USERID_FROM")
    private Long userIdFrom;

    @Column(name = "USERID_TO")
    private Long userIdTo;

    @Column(name = "MESSAGE")
    private String message;

    public Message() {
    }

    public Message(Long id, Long userIdFrom, Long userIdTo, String message) {
        this.id = id;
        this.userIdFrom = userIdFrom;
        this.userIdTo = userIdTo;
        this.message = message;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
