package com.nataniel.api.jpa;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by natan on 26/11/2016.
 */
public class GeraTabelas {
    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("service-provider");
        factory.close();
    }
}