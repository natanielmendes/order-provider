package com.nataniel.api.services;

import com.google.gson.Gson;
import com.nataniel.api.BadRequestException;
import com.nataniel.api.domain.OrderDetails;
import com.nataniel.api.domain.ProductOrder;
import org.apache.camel.Exchange;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by natan on 16/11/2016.
 */
@Service("productOrderService")
public class ProductOrderService {
    @Transactional
    public String createProductOrder(Exchange exchange) throws BadRequestException {
        JSONArray productOrderJSON = new JSONArray();
        if (exchange.getIn().getHeader("jsonRequest").toString().startsWith("[")) {
            productOrderJSON = (JSONArray) exchange.getIn().getHeader("jsonRequest");
        }

        // manipulando entidade
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("order-provider");

        // criando entidade de gerenciamento
        EntityManager manager = factory.createEntityManager();

        // abrindo transação
        manager.getTransaction().begin();

        JSONArray responseJSON = new JSONArray();
        if (productOrderJSON.length() > 10) {
            throw new BadRequestException("products_per_request_maxsize_exceeded");
        }
        if (productOrderJSON.length() < 1) {
            throw new BadRequestException("products_per_request_minsize_not_reached");
        }
        for (Object productObj : productOrderJSON) {
            JSONObject productJSON = new JSONObject(productObj.toString());
            ProductOrder productOrder = new ProductOrder();

            // required
            if (productJSON.isNull("controlNumber")) {
                throw new BadRequestException("controlNumber_param_required");
            } else {
                productOrder.setControlNumber(productJSON.getString("controlNumber"));
            }
            // optional
            if (productJSON.has("registrationDate")) {
                productOrder.setRegistrationDate(productJSON.getString("registrationDate"));
            }
            // required
            if (productJSON.isNull("name")) {
                throw new BadRequestException("name_param_required");
            } else {
                productOrder.setName(productJSON.getString("name"));
            }
            // required
            if (productJSON.isNull("price")) {
                throw new BadRequestException("price_param_required");
            } else {
                productOrder.setPrice(productJSON.getDouble("price"));
            }
            // optional
            if (productJSON.has("productQuantity")) {
                productOrder.setProductQuantity(productJSON.getInt("productQuantity"));
                if (productJSON.getInt("productQuantity") > 5) {

                }
            } else {
                productOrder.setProductQuantity(1);
            }
            // required
            if (productJSON.isNull("customerCode")) {
                throw new BadRequestException("customerCode_param_required");
            } else {
                productOrder.setCustomerCode(productJSON.getString("customerCode"));
            }
            manager.persist(productOrder);
            productJSON.put("id", productOrder.getId());
            responseJSON.put(productJSON);

            // UPDATE TOTAL
            Query query = manager.createNamedQuery(OrderDetails.Queries.FIND_DETAILS_BY_CONTROLNUMBER);
            query.setParameter("controlNumber", productJSON.getString("controlNumber"));

            List controlNumbersFound = query.getResultList();

            if (controlNumbersFound.isEmpty()) {
                OrderDetails od = new OrderDetails();
                od.setControlNumber(productJSON.getString("controlNumber"));
                od.setTotal(productOrder.getPrice() * productOrder.getProductQuantity());
                manager.persist(od);
            } else {
                OrderDetails od = (OrderDetails) controlNumbersFound.get(0);
                Query queryUpdate = manager.createNamedQuery(OrderDetails.Queries.UPDATE_TOTAL_BY_CONTROLNUMBER);
                queryUpdate.setParameter("controlNumber", productJSON.getString("controlNumber"));
                queryUpdate.setParameter("total", od.getTotal() + (productOrder.getPrice() * productOrder.getProductQuantity()));
                queryUpdate.executeUpdate();
            }
        }

        // salvando as alterações no banco
        manager.getTransaction().commit();

        // fechando entidade de gerenciamento
        manager.close();

        // fechando conexao
        factory.close();

//        userAccountJSON.put("id", productOrder.getId());
        return responseJSON.toString();
    }

    @Transactional
    public String getAllOrders() {
        // manipulando entidade
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("order-provider");

        // criando entidade de gerenciamento
        EntityManager manager = factory.createEntityManager();

        // abrindo transação
        manager.getTransaction().begin();

        Query query = manager.createNamedQuery(ProductOrder.Queries.FIND_ALL_ORDERS);
        List productOrdersFound = query.getResultList();

        Gson gson = new Gson();
        String productsFoundJSONString = gson.toJson(productOrdersFound);
        JSONArray productsFoundJSONArr = new JSONArray(productsFoundJSONString);

        Query queryOrderDetails = manager.createNamedQuery(OrderDetails.Queries.FIND_DETAILS_BY_CONTROLNUMBER);
        List controNumbers = new ArrayList();
        for (Object o : productsFoundJSONArr) {
            JSONObject jo = (JSONObject) o;
            controNumbers.add(jo.getString("controlNumber"));
        }
        queryOrderDetails.setParameter("controlNumber", controNumbers);
        List ordersFound = queryOrderDetails.getResultList();

        // fechando entidade de gerenciamento
        manager.close();

        // fechando conexao
        factory.close();

        String ordersFoundJSONString = gson.toJson(ordersFound);
        JSONArray ordersFoundJSONArr = new JSONArray(ordersFoundJSONString);
        JSONArray resultJSONArr = new JSONArray();
        for (Object orders : ordersFoundJSONArr) {
            JSONObject ordersJSONObj = (JSONObject) orders;
            JSONArray productsArr = new JSONArray();
            for (Object products : productsFoundJSONArr) {
                JSONObject productsJSONObj = (JSONObject) products;
                if (productsJSONObj.getString("controlNumber").equals(ordersJSONObj.getString("controlNumber"))) {
                    productsArr.put(productsJSONObj);
                }
            }
            ordersJSONObj.put("products", productsArr);
            resultJSONArr.put(ordersJSONObj);
        }
        return resultJSONArr.toString();
    }

    @Transactional
    public String getOrderByControlNumber(String controlNumber) {
        // manipulando entidade
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("order-provider");

        // criando entidade de gerenciamento
        EntityManager manager = factory.createEntityManager();

        // abrindo transação
        manager.getTransaction().begin();

        Query query = manager.createNamedQuery(ProductOrder.Queries.FIND_PRODUCTS_BY_CONTROLNUMBER);
        query.setParameter("controlNumber", controlNumber);
        List productOrdersFound = query.getResultList();

        Gson gson = new Gson();
        String productsFoundJSONString = gson.toJson(productOrdersFound);
        JSONArray productsFoundJSONArr = new JSONArray(productsFoundJSONString);

        Query queryOrderDetails = manager.createNamedQuery(OrderDetails.Queries.FIND_DETAILS_BY_CONTROLNUMBER);
        List controNumbers = new ArrayList();
        for (Object o : productsFoundJSONArr) {
            JSONObject jo = (JSONObject) o;
            controNumbers.add(jo.getString("controlNumber"));
        }
        queryOrderDetails.setParameter("controlNumber", controNumbers);
        List ordersFound = queryOrderDetails.getResultList();

        // fechando entidade de gerenciamento
        manager.close();

        // fechando conexao
        factory.close();

        String ordersFoundJSONString = gson.toJson(ordersFound);
        JSONArray ordersFoundJSONArr = new JSONArray(ordersFoundJSONString);
        JSONArray resultJSONArr = new JSONArray();
        for (Object orders : ordersFoundJSONArr) {
            JSONObject ordersJSONObj = (JSONObject) orders;
            JSONArray productsArr = new JSONArray();
            for (Object products : productsFoundJSONArr) {
                JSONObject productsJSONObj = (JSONObject) products;
                if (productsJSONObj.getString("controlNumber").equals(ordersJSONObj.getString("controlNumber"))) {
                    productsArr.put(productsJSONObj);
                }
            }
            ordersJSONObj.put("products", productsArr);
            resultJSONArr.put(ordersJSONObj);
        }
        return resultJSONArr.toString();
    }

    @Transactional
    public String getOrderByRegistrationDate(String registrationDate) {
        // manipulando entidade
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("order-provider");

        // criando entidade de gerenciamento
        EntityManager manager = factory.createEntityManager();

        // abrindo transação
        manager.getTransaction().begin();

        Query query = manager.createNamedQuery(ProductOrder.Queries.FIND_PRODUCTS_BY_DATE);
        query.setParameter("registrationDate", registrationDate);
        List productOrdersFound = query.getResultList();

        Gson gson = new Gson();
        String productsFoundJSONString = gson.toJson(productOrdersFound);
        JSONArray productsFoundJSONArr = new JSONArray(productsFoundJSONString);

        Query queryOrderDetails = manager.createNamedQuery(OrderDetails.Queries.FIND_DETAILS_BY_CONTROLNUMBER);
        List controNumbers = new ArrayList();
        for (Object o : productsFoundJSONArr) {
            JSONObject jo = (JSONObject) o;
            controNumbers.add(jo.getString("controlNumber"));
        }
        queryOrderDetails.setParameter("controlNumber", controNumbers);
        List ordersFound = queryOrderDetails.getResultList();

        // fechando entidade de gerenciamento
        manager.close();

        // fechando conexao
        factory.close();

        String ordersFoundJSONString = gson.toJson(ordersFound);
        JSONArray ordersFoundJSONArr = new JSONArray(ordersFoundJSONString);
        JSONArray resultJSONArr = new JSONArray();
        for (Object orders : ordersFoundJSONArr) {
            JSONObject ordersJSONObj = (JSONObject) orders;
            JSONArray productsArr = new JSONArray();
            for (Object products : productsFoundJSONArr) {
                JSONObject productsJSONObj = (JSONObject) products;
                if (productsJSONObj.getString("controlNumber").equals(ordersJSONObj.getString("controlNumber"))) {
                    productsArr.put(productsJSONObj);
                }
            }
            ordersJSONObj.put("products", productsArr);
            resultJSONArr.put(ordersJSONObj);
        }
        return resultJSONArr.toString();
    }
}
