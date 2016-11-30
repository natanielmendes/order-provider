package com.nataniel.api.services;

import com.google.gson.Gson;
import com.nataniel.api.BadRequestException;
import org.apache.camel.Exchange;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import com.nataniel.api.domain.User;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * Created by natan on 16/11/2016.
 */
@Service("userService")
public class UserService {
    @Transactional
    public String createUser(Exchange exchange) {
        JSONObject userAccountJSON = (JSONObject) exchange.getIn().getHeader("jsonRequest");

        // manipulando entidade
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("service-provider");

        // criando entidade de gerenciamento
        EntityManager manager = factory.createEntityManager();

        // abrindo transação
        manager.getTransaction().begin();

        User user = new User();
        user.setLogin(userAccountJSON.getString("login"));
        user.setEmail(userAccountJSON.getString("email"));

        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(userAccountJSON.getString("password").getBytes(),0,userAccountJSON.getString("password").length());
            BigInteger i = new BigInteger(1, m.digest());

            //Formatando o resuldado em uma cadeia de 32 caracteres, completando com 0 caso falte
            userAccountJSON.put("password", String.format("%1$032X", i));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        user.setPassword(userAccountJSON.getString("password"));
        user.setName(userAccountJSON.getString("name"));
        user.setCity(userAccountJSON.getString("city"));
        user.setRegion(userAccountJSON.getString("region"));
        user.setBirthDate(userAccountJSON.getString("birthdate"));
        user.setJob("Não Definido");
        user.setCourse("Não Definido");
        user.setUniversity("Não Definido");
        user.setWorkSince("00/00/0000");
        manager.persist(user);

        // salvando as alterações no banco
        manager.getTransaction().commit();

        // fechando entidade de gerenciamento
        manager.close();

        // fechando conexao
        factory.close();

        userAccountJSON.put("id", user.getId());
        return userAccountJSON.toString();
    }

    @Transactional
    public String login(Exchange exchange) {
        JSONObject loginJSON = (JSONObject) exchange.getIn().getHeader("jsonRequest");

        try{
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(loginJSON.getString("password").getBytes(),0,loginJSON.getString("password").length());
            BigInteger i = new BigInteger(1, m.digest());

            //Formatando o resuldado em uma cadeia de 32 caracteres, completando com 0 caso falte
            loginJSON.put("password", String.format("%1$032X", i));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        // manipulando entidade
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("service-provider");

        // criando entidade de gerenciamento
        EntityManager manager = factory.createEntityManager();

        // abrindo transação
        manager.getTransaction().begin();

        Query query = manager.createNamedQuery(User.Queries.FIND_USER_BY_LOGIN_AND_PASSWORD);
        query.setParameter("login", loginJSON.get("login"));
        query.setParameter("password", loginJSON.get("password"));

        List usersFound = query.getResultList();

        if (usersFound.isEmpty()) {
            JSONObject jo = new JSONObject();
            jo.put("error", "user_not_found_in_database");
            return jo.toString();
        }

        // fechando entidade de gerenciamento
        manager.close();

        // fechando conexao
        factory.close();

        Gson gson = new Gson();
        String json = gson.toJson(usersFound);

        return json;
    }

    @Transactional
    public String getAllUsers() {
        // manipulando entidade
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("service-provider");

        // criando entidade de gerenciamento
        EntityManager manager = factory.createEntityManager();

        // abrindo transação
        manager.getTransaction().begin();

        Query query = manager.createNamedQuery(User.Queries.FIND_ALL_USERS);

        List usersFound = query.getResultList();

        if (usersFound.isEmpty()) {
            JSONObject jo = new JSONObject();
            jo.put("error", "no_users_in_database");
            return jo.toString();
        }

        // fechando entidade de gerenciamento
        manager.close();

        // fechando conexao
        factory.close();

        Gson gson = new Gson();
        String json = gson.toJson(usersFound);

        return json;
    }

    @Transactional
    public String getUserById(Long userId) {
        // manipulando entidade
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("service-provider");

        // criando entidade de gerenciamento
        EntityManager manager = factory.createEntityManager();

        // abrindo transação
        manager.getTransaction().begin();

        Query query = manager.createNamedQuery(User.Queries.FIND_USER_BY_USERID);
        query.setParameter("userIds", userId);

        List usersFound = query.getResultList();

        if (usersFound.isEmpty()) {
            JSONObject jo = new JSONObject();
            jo.put("error", "no_users_in_database");
            return jo.toString();
        }

        // fechando entidade de gerenciamento
        manager.close();

        // fechando conexao
        factory.close();

        Gson gson = new Gson();
        String json = gson.toJson(usersFound.get(0));

        return json;
    }

    public String updateUserById(Exchange exchange) {
        JSONObject userAccountJSON = (JSONObject) exchange.getIn().getHeader("jsonRequest");

        // manipulando entidade
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("service-provider");

        // criando entidade de gerenciamento
        EntityManager manager = factory.createEntityManager();

        // abrindo transação
        manager.getTransaction().begin();

        User user;
        if (userAccountJSON.has("id")) {
            user = manager.find(User.class, userAccountJSON.getLong("id"));
        } else {
            JSONObject jo = new JSONObject();
            jo.put("error", "param_id_required");
            return jo.toString();
        }
        if (userAccountJSON.has("login")) {
            user.setLogin(userAccountJSON.getString("login"));
        }
        if (userAccountJSON.has("email")) {
            user.setEmail(userAccountJSON.getString("email"));
        }
        if (userAccountJSON.has("password")) {
            try {
                MessageDigest m = MessageDigest.getInstance("MD5");
                m.update(userAccountJSON.getString("password").getBytes(),0,userAccountJSON.getString("password").length());
                BigInteger i = new BigInteger(1, m.digest());

                //Formatando o resuldado em uma cadeia de 32 caracteres, completando com 0 caso falte
                userAccountJSON.put("password", String.format("%1$032X", i));
                user.setPassword(userAccountJSON.getString("password"));
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        if (userAccountJSON.has("name")) {
            user.setName(userAccountJSON.getString("name"));
        }
        if (userAccountJSON.has("city")) {
            user.setCity(userAccountJSON.getString("city"));
        }
        if (userAccountJSON.has("region")) {
            user.setRegion(userAccountJSON.getString("region"));
        }
        if (userAccountJSON.has("birthdate")) {
            user.setBirthDate(userAccountJSON.getString("birthdate"));
        }
        if (userAccountJSON.has("job")) {
            user.setJob(userAccountJSON.getString("job"));
        }
        if (userAccountJSON.has("course")) {
            user.setCourse(userAccountJSON.getString("course"));
        }
        if (userAccountJSON.has("university")) {
            user.setUniversity(userAccountJSON.getString("university"));
        }
        if (userAccountJSON.has("workSince")) {
            user.setWorkSince(userAccountJSON.getString("workSince"));
        }
        manager.persist(user);

        // salvando as alterações no banco
        manager.getTransaction().commit();

        Query query = manager.createNamedQuery(User.Queries.FIND_USER_BY_USERID);
        query.setParameter("userIds", userAccountJSON.getLong("id"));

        List usersFound = query.getResultList();

        if (usersFound.isEmpty()) {
            JSONObject jo = new JSONObject();
            jo.put("error", "no_users_in_database");
            return jo.toString();
        }

        // fechando entidade de gerenciamento
        manager.close();

        // fechando conexao
        factory.close();

        Gson gson = new Gson();
        String json = gson.toJson(usersFound.get(0));

        return json;
    }
}
