package com.nataniel.api.services;

import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;
import javax.ws.rs.*;

/**
 * Created by natan on 16/11/2016.
 */
@CrossOriginResourceSharing(allowAllOrigins = true, allowHeaders = {"POST", "PUT", "GET", "OPTIONS", "DELETE"})
public class UserServiceRest {
    @GET
    @Path("/")
    @Produces("application/json")
    public String getAllUsers() {
        return null;
    }

    @GET
    @Path("/{userId}")
    @Produces("application/json")
    public String getUserById(@PathParam("userId") Long userId) {
        return null;
    }

    @PUT
    @Path("/")
    @Produces("application/json")
    public String updateUserById() {
        return null;
    }

    @POST
    @Path("/")
    @Produces("application/json")
    public String createUser() {
        return null;
    }

    @GET
    @Path("/login")
    @Produces("application/json")
    public String login() {
        return null;
    }
}
