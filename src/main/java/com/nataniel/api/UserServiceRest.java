package com.nataniel.api;

import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;

import javax.ws.rs.*;

/**
 * Created by natan on 16/11/2016.
 */
@CrossOriginResourceSharing(allowAllOrigins = true, allowHeaders = {"POST", "PUT", "GET", "OPTIONS", "DELETE"})
public class UserServiceRest {
    @GET
    @Path("/list-users")
    @Produces("application/json")
    public String listUsers(@QueryParam("nome") String nome) {
        return null;
    }

    @POST
    @Path("/")
    @Produces("application/json")
    public String createUser() {
        return null;
    }
}
