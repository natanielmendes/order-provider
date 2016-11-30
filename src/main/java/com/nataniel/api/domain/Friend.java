package com.nataniel.api.domain;

//import org.hibernate.annotations.Entity;
import javax.persistence.Entity;

import javax.persistence.*;
import javax.validation.constraints.Max;

/**
 * Created by natan on 15/11/2016.
 */

@Entity
@Table(name="FRIEND")
@NamedQueries({
        @NamedQuery(name = Friend.Queries.FIND_FRIENDS_BY_USERIDFROM_AND_USERIDTO,
                query =
                        "select new com.nataniel.api.domain.Friend(" +
                                " f.id," +
                                " f.userIdFrom," +
                                " f.userIdTo)" +
                                " from Friend f " +
                                " where (f.userIdFrom = :userIdFrom" +
                                " and f.userIdTo = :userIdTo)" +
                                " or (f.userIdFrom = :userIdTo" +
                                " and f.userIdTo = :userIdFrom)" +
                                " order by f.id"
        ),
        @NamedQuery(name = Friend.Queries.FIND_FRIENDS_BY_USERIDFROM,
        query =
                "select new com.nataniel.api.domain.Friend(" +
                        " f.id," +
                        " f.userIdFrom," +
                        " f.userIdTo)" +
                        " from Friend f " +
                        " where f.userIdFrom = :userIdFrom" +
                        " order by f.id")
})
public class Friend {

    public static final class Queries {
        public static final String FIND_FRIENDS_BY_USERIDFROM_AND_USERIDTO = "FIND_FRIENDS_BY_USERIDFROM_AND_USERIDTO";
        public static final String FIND_FRIENDS_BY_USERIDFROM = "FIND_FRIENDS_BY_USERIDFROM";
    }

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "USERID_FROM")
    private Long userIdFrom;

    @Column(name = "USERID_TO")
    private Long userIdTo;

    public Friend() {
    }

    public Friend(Long id, Long userIdFrom, Long userIdTo) {
        this.id = id;
        this.userIdFrom = userIdFrom;
        this.userIdTo = userIdTo;
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
}
