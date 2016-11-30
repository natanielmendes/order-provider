package com.nataniel.api.services;

import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;

import javax.ws.rs.*;

/**
 * Created by natan on 16/11/2016.
 */
@CrossOriginResourceSharing(allowAllOrigins = true, allowHeaders = {"POST", "PUT", "GET", "OPTIONS", "DELETE"})
public class RateServiceRest {
    @POST
    @Path("/")
    @Produces("application/json")
    public String rateUser() {
        return null;
    }

    @GET
    @Path("/{userIdTo}")
    @Produces("application/json")
    public String getRate(@PathParam("userIdTo") Long userIdTo) {
        return null;
    }
}
