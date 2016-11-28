package com.nataniel.api.services;

import com.google.gson.Gson;
import com.nataniel.api.BadRequestException;
import org.apache.camel.Exchange;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import com.nataniel.api.domain.User;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.List;

/**
 * Created by natan on 16/11/2016.
 */
@Service("userService")
public class UserService {
    public String listUsers(String nome) {
//        JSONObject jo = new JSONObject();
//        jo.append("olá", exchange.toString());
        return nome;
    }

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
        user.setPassword(userAccountJSON.getString("password"));
        user.setName(userAccountJSON.getString("name"));
        user.setCity(userAccountJSON.getString("city"));
        user.setRegion(userAccountJSON.getString("region"));
        user.setBirthDate(userAccountJSON.getString("birthdate"));

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
            throw new BadRequestException("user_not_found_in_database");
        }

        // fechando entidade de gerenciamento
        manager.close();

        // fechando conexao
        factory.close();

        Gson gson = new Gson();
        String json = gson.toJson(usersFound);

        return json;
    }
}
