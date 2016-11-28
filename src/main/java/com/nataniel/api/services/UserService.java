package com.nataniel.api.services;

import org.apache.camel.Exchange;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import com.nataniel.api.domain.User;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

/**
 * Created by natan on 16/11/2016.
 */
@Service("userService")
public class UserService {

    @PersistenceContext
    transient EntityManager entityManager;

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

//        EntityManagerFactory factory = Persistence.createEntityManagerFactory("service-provider");
//        entityManager.persist(user);

//        exchange.getOut().setHeader("Access-Control-Allow-Origin", "*");
        userAccountJSON.put("id", user.getId());
        return userAccountJSON.toString();
    }
}
