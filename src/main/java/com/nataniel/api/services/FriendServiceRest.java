package com.nataniel.api.services;

import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;

import javax.ws.rs.*;

/**
 * Created by natan on 16/11/2016.
 */
@CrossOriginResourceSharing(allowAllOrigins = true, allowHeaders = {"POST", "PUT", "GET", "OPTIONS", "DELETE"})
public class FriendServiceRest {
    @GET
    @Path("/{userIdFrom}")
    @Produces("application/json")
    public String getFriends(@PathParam("userIdFrom") Long userIdFrom) {
        return null;
    }
}
