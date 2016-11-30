package com.nataniel.api.services;

import com.google.gson.Gson;
import com.nataniel.api.domain.Rate;
import com.nataniel.api.domain.User;
import org.apache.camel.Exchange;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by natan on 16/11/2016.
 */
@Service("rateService")
public class RateService {
    @Transactional
    public String rateUser(Exchange exchange) {
        JSONObject rateJSON = (JSONObject) exchange.getIn().getHeader("jsonRequest");

        // manipulando entidade
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("service-provider");

        // criando entidade de gerenciamento
        EntityManager manager = factory.createEntityManager();

        // abrindo transação
        manager.getTransaction().begin();

        Rate rate = new Rate();
        rate.setUserIdFrom(Long.parseLong(rateJSON.getString("userIdFrom")));
        rate.setUserIdTo(Long.parseLong(rateJSON.getString("userIdTo")));
        rate.setRate(rateJSON.getInt("rate"));
        rate.setComment(rateJSON.getString("comment"));

        manager.persist(rate);

        // salvando as alterações no banco
        manager.getTransaction().commit();

        // fechando entidade de gerenciamento
        manager.close();

        // fechando conexao
        factory.close();

        rateJSON.put("id", rate.getId());
        return rateJSON.toString();
    }

    @Transactional
    public String getRate(Long userIdTo) {
        // manipulando entidade
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("service-provider");

        // criando entidade de gerenciamento
        EntityManager manager = factory.createEntityManager();

        // abrindo transação
        manager.getTransaction().begin();

        Query query = manager.createNamedQuery(Rate.Queries.FIND_RATES_BY_USERID);
        query.setParameter("userIdTo", userIdTo);

        List ratesFound = query.getResultList();

        if (ratesFound.isEmpty()) {
            JSONObject jo = new JSONObject();
            jo.put("error", "user_doesnt_have_rating_in_database");
            return jo.toString();
        }

        // fechando entidade de gerenciamento
        manager.close();

        // fechando conexao
        factory.close();

        Gson gson = new Gson();
        String json = gson.toJson(ratesFound);

        return json;
    }
}
