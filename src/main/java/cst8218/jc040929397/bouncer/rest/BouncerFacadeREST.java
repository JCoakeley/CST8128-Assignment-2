/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cst8218.jc040929397.bouncer.rest;

import cst8218.jc040929397.bouncer.business.Bouncer;
import cst8218.jc040929397.bouncer.business.BouncerFacade;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

/**
 *
 * @author joey
 */
@Stateless
@Path("cst8218.jc040929397.bouncer.business.bouncer")
public class BouncerFacadeREST {

    @EJB
    private BouncerFacade bouncerFacade;

    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response create(Bouncer entity) {
        if (entity.getId() == null)
        {
            bouncerFacade.create(entity);
            
            return Response.status(Response.Status.CREATED).entity(entity).build();
        }
        
        Bouncer oldBouncer = bouncerFacade.find(entity.getId());
        
        if(oldBouncer == null)
        {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("No Bouncer exists with id " + entity.getId()).build();
        }
        
        entity.updateNonNull(oldBouncer);
        bouncerFacade.edit(oldBouncer);
        
        return Response.ok(oldBouncer).build();
    }
    
    @POST
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response replaceBouncer(@PathParam("id") Long id, Bouncer entity) {

        Bouncer existing = bouncerFacade.find(id);

        // No Bouncer exists with URL id
        if (existing == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("No Bouncer exists with id " + id)
                    .build();
        }

        // Body id exists but does not match URL id
        if (entity.getId() != null && !entity.getId().equals(id)) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Body id does not match URL id")
                    .build();
        }

        // Force the URL id onto the replacement object
        entity.setId(id);

        // Fill missing non-nullable fields with defaults
        entity.applyDefaults();

        bouncerFacade.edit(entity);

        return Response.ok(entity).build();
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateBouncer(@PathParam("id") Long id, Bouncer entity) {

        Bouncer existing = bouncerFacade.find(id);

        if (existing == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("No Bouncer exists with id " + id)
                    .build();
        }

        if (entity.getId() != null && !entity.getId().equals(id)) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Body id does not match URL id")
                    .build();
        }

        entity.updateNonNull(existing);

        bouncerFacade.edit(existing);

        return Response.ok(existing).build();
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response putRoot(Bouncer incoming) {
        return Response.status(Response.Status.METHOD_NOT_ALLOWED)
                .entity("PUT on the root Bouncer resource is not allowed.")
                .build();
    }

    @DELETE
    @Path("{id}")
    public Response remove(@PathParam("id") Long id) {
        Bouncer existing = bouncerFacade.find(id);

        if (existing == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        bouncerFacade.remove(existing);
        return Response.noContent().build();
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response find(@PathParam("id") Long id) {
        Bouncer bouncer = bouncerFacade.find(id);

        if (bouncer == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(bouncer).build();
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Bouncer> findAll() {
        return bouncerFacade.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Bouncer> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return bouncerFacade.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(bouncerFacade.count());
    }
    
}
