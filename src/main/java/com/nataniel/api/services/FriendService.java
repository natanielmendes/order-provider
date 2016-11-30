package com.nataniel.api.services;

import com.google.gson.Gson;
import com.nataniel.api.domain.Friend;
import com.nataniel.api.domain.User;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by natan on 16/11/2016.
 */
@Service("friendService")
public class FriendService {

    @Transactional
    public String getFriends(Long userIdFrom) {
        // manipulando entidade
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("service-provider");

        // criando entidade de gerenciamento
        EntityManager manager = factory.createEntityManager();

        // abrindo transação
        manager.getTransaction().begin();

        Query query = manager.createNamedQuery(Friend.Queries.FIND_FRIENDS_BY_USERIDFROM);
        query.setParameter("userIdFrom", userIdFrom);

        List<Friend> friendsFound = query.getResultList();

        if (friendsFound.isEmpty()) {
            JSONObject jo = new JSONObject();
            jo.put("error", "user_doesnt_have_friends_in_database");
            return jo.toString();
        }

        List<Long> friendsFoundIds = new ArrayList<Long>();
        for (Friend friend : friendsFound) {
            friendsFoundIds.add(friend.getUserIdTo());
        }

        Query userQuery = manager.createNamedQuery(User.Queries.FIND_USER_BY_USERID);
        userQuery.setParameter("userIds", friendsFoundIds);

        List<User> usersFound = userQuery.getResultList();

        // fechando entidade de gerenciamento
        manager.close();

        // fechando conexao
        factory.close();

        Gson gson = new Gson();
        String json = gson.toJson(usersFound);

        return json;
    }
}
