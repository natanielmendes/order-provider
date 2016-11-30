package com.nataniel.api.services;

import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;

import javax.ws.rs.*;

/**
 * Created by natan on 16/11/2016.
 */
@CrossOriginResourceSharing(allowAllOrigins = true, allowHeaders = {"POST", "PUT", "GET", "OPTIONS", "DELETE"})
public class MessageServiceRest {
    @POST
    @Path("/")
    @Produces("application/json")
    public String postMessage() {
        return null;
    }

    @GET
    @Path("/")
    @Produces("application/json")
    public String getMessages() {
        return null;
    }
}
