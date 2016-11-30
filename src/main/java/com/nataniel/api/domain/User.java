package com.nataniel.api.domain;

//import org.hibernate.annotations.Entity;
import javax.persistence.Entity;

import javax.persistence.*;

/**
 * Created by natan on 15/11/2016.
 */

@Entity
@Table(name="USER")
@NamedQueries({
        @NamedQuery(name = User.Queries.FIND_USER_BY_LOGIN_AND_PASSWORD,
                query =
                        "select new com.nataniel.api.domain.User(" +
                                " u.id," +
                                " u.login," +
                                " u.name," +
                                " u.email," +
                                " u.password," +
                                " u.city," +
                                " u.region," +
                                " u.birthDate," +
                                " u.job," +
                                " u.course," +
                                " u.university," +
                                " u.workSince)" +
                                " from User u " +
                                " where u.login = :login and u.password = :password"
        ),
        @NamedQuery(name = User.Queries.FIND_USER_BY_USERID,
                query =
                        "select new com.nataniel.api.domain.User(" +
                                " u.id," +
                                " u.login," +
                                " u.name," +
                                " u.email," +
                                " u.password," +
                                " u.city," +
                                " u.region," +
                                " u.birthDate," +
                                " u.job," +
                                " u.course," +
                                " u.university," +
                                " u.workSince)" +
                                " from User u " +
                                " where u.id IN :userIds"
                ),
        @NamedQuery(name = User.Queries.FIND_ALL_USERS,
        query =
                "select new com.nataniel.api.domain.User(" +
                        " u.id," +
                        " u.login," +
                        " u.name," +
                        " u.email," +
                        " u.city," +
                        " u.region," +
                        " u.birthDate," +
                        " u.job," +
                        " u.course," +
                        " u.university," +
                        " u.workSince)" +
                        " from User u "
        )
})
public class User {

    public static final class Queries {
        public static final String FIND_USER_BY_LOGIN_AND_PASSWORD = "FIND_USER_BY_LOGIN_AND_PASSWORD";
        public static final String FIND_USER_BY_USERID = "FIND_USER_BY_USERID";
        public static final String FIND_ALL_USERS = "FIND_ALL_USERS";
    }

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "LOGIN")
    private String login;

    @Column(name = "NAME")
    private String name;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "CITY")
    private String city;

    @Column(name = "REGION")
    private String region;

    @Column(name = "BIRTHDATE")
    private String birthDate;

    @Column(name = "JOB")
    private String job;

    @Column(name = "COURSE")
    private String course;

    @Column(name = "UNIVERSITY")
    private String university;

    @Column(name = "WORK_SINCE")
    private String workSince;

    public User() {
    }

    public User(Long id, String login, String name, String email, String password, String city, String region, String birthDate, String job, String course, String university, String workSince) {
        this.id = id;
        this.login = login;
        this.name = name;
        this.email = email;
        this.password = password;
        this.city = city;
        this.region = region;
        this.birthDate = birthDate;
        this.job = job;
        this.course = course;
        this.university = university;
        this.workSince = workSince;
    }

    public User(Long id, String login, String name, String email, String city, String region, String birthDate, String job, String course, String university, String workSince) {
        this.id = id;
        this.login = login;
        this.name = name;
        this.email = email;
        this.city = city;
        this.region = region;
        this.birthDate = birthDate;
        this.job = job;
        this.course = course;
        this.university = university;
        this.workSince = workSince;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getWorkSince() {
        return workSince;
    }

    public void setWorkSince(String workSince) {
        this.workSince = workSince;
    }
}
