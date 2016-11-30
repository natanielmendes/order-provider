package com.nataniel.api.services;

import com.google.gson.Gson;
import com.nataniel.api.domain.Friend;
import com.nataniel.api.domain.Message;
import com.nataniel.api.domain.Rate;
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
@Service("messageService")
public class MessageService {

    @Transactional
    public String postMessage(Exchange exchange) {
        JSONObject messageJSON = (JSONObject) exchange.getIn().getHeader("jsonRequest");

        // manipulando entidade
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("service-provider");

        // criando entidade de gerenciamento
        EntityManager manager = factory.createEntityManager();

        // abrindo transação
        manager.getTransaction().begin();

        Message message = new Message();
        message.setUserIdFrom(Long.parseLong(messageJSON.getString("userIdFrom")));
        message.setUserIdTo(Long.parseLong(messageJSON.getString("userIdTo")));
        message.setMessage(messageJSON.getString("message"));

        manager.persist(message);

        Query query = manager.createNamedQuery(Friend.Queries.FIND_FRIENDS_BY_USERIDFROM_AND_USERIDTO);
        query.setParameter("userIdFrom", Long.parseLong(messageJSON.getString("userIdFrom")));
        query.setParameter("userIdTo", Long.parseLong(messageJSON.getString("userIdTo")));

        List friendsFound = query.getResultList();

        if (friendsFound.isEmpty()) {
            Friend friend = new Friend();
            friend.setUserIdFrom(Long.parseLong(messageJSON.getString("userIdFrom")));
            friend.setUserIdTo(Long.parseLong(messageJSON.getString("userIdTo")));
            manager.persist(friend);

            Friend friend2 = new Friend();
            friend2.setUserIdFrom(Long.parseLong(messageJSON.getString("userIdTo")));
            friend2.setUserIdTo(Long.parseLong(messageJSON.getString("userIdFrom")));
            manager.persist(friend2);
        }

        // salvando as alterações no banco
        manager.getTransaction().commit();

        // fechando entidade de gerenciamento
        manager.close();

        // fechando conexao
        factory.close();

        messageJSON.put("id", message.getId());
        return messageJSON.toString();
    }

    @Transactional
    public String getMessages(Exchange exchange) {
        JSONObject messageJSON = (JSONObject) exchange.getIn().getHeader("jsonRequest");

        // manipulando entidade
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("service-provider");

        // criando entidade de gerenciamento
        EntityManager manager = factory.createEntityManager();

        // abrindo transação
        manager.getTransaction().begin();

        Query query = manager.createNamedQuery(Message.Queries.FIND_MESSAGES_BY_USERIDFROM_AND_USERIDTO);
        query.setParameter("userIdFrom", messageJSON.getLong("userIdFrom"));
        query.setParameter("userIdTo", messageJSON.getLong("userIdTo"));

        List messagesFound = query.getResultList();

        if (messagesFound.isEmpty()) {
            JSONObject jo = new JSONObject();
            jo.put("error", "user_doesnt_have_messages_in_database");
            return jo.toString();
        }

        // fechando entidade de gerenciamento
        manager.close();

        // fechando conexao
        factory.close();

        Gson gson = new Gson();
        String json = gson.toJson(messagesFound);

        return json;
    }
}
