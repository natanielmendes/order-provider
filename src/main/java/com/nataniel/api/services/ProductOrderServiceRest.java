package com.nataniel.api.services;

import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;
import javax.ws.rs.*;

/**
 * Created by natan on 16/11/2016.
 */
@CrossOriginResourceSharing(allowAllOrigins = true, allowHeaders = {"POST", "PUT", "GET", "OPTIONS", "DELETE"})
public class ProductOrderServiceRest {
    @GET
    @Path("/")
    @Produces("application/json")
    public String getAllOrders() {
        return null;
    }

    @GET
    @Path("/filterByControlNumber/")
    @Produces("application/json")
    public String getOrderByControlNumber(@QueryParam("controlNumber") String controlNumber) {
        return null;
    }

    @GET
    @Path("/filterByDate/")
    @Produces("application/json")
    public String getOrderByRegistrationDate(@QueryParam("registrationDate") String registrationDate) {
        return null;
    }

    @POST
    @Path("/")
    @Produces("application/json")
    public String createProductOrder() {
        return null;
    }
}
