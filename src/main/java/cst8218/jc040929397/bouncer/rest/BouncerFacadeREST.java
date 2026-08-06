/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cst8218.jc040929397.bouncer.rest;

import cst8218.jc040929397.bouncer.business.Bouncer;
import cst8218.jc040929397.bouncer.business.BouncerFacade;
import jakarta.annotation.security.DeclareRoles;
import jakarta.annotation.security.RolesAllowed;
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
 * RESTful web service that exposes HTTP endpoints for managing
 * Bouncer entities.
 * 
 * This resource provides operations to create, retrieve, update, replace,
 * and delete Bouncer entities, as well as obtain the total number of
 * Bouncers stored in the database. Business logic and persistence
 * operations are delegated to the BouncerFacade Enterprise JavaBean.
 *
 * @author Joey Coakeley
 */
@DeclareRoles({"BouncerAdmin","ApiGroup"})
@Stateless
@Path("cst8218.jc040929397.bouncer.business.bouncer")
public class BouncerFacadeREST {

    @EJB
    private BouncerFacade bouncerFacade;

    /**
     * Creates a new Bouncer or partially updates an existing one.
     * 
     * If the supplied Bouncer has no id, a new entity is created and persisted.
     * If an id is supplied, the corresponding Bouncer is updated using only the
     * non-null values from the request body. If no Bouncer exists with the
     * supplied id, a 400 Bad Request response is returned.
     * 
     * @param entity the Bouncer to create or use to update an existing entity
     * @return an HTTP response containing the created or updated Bouncer, or an
     *         error response if the request is invalid
     */
    @RolesAllowed({"BouncerAdmin","ApiGroup"})
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
    
    /**
     * Replaces the Bouncer identified by the specified id.
     * 
     * The existing Bouncer is completely replaced by the supplied Bouncer. If
     * required properties are omitted, default values are applied before the
     * entity is persisted. A 400 Bad Request response is returned if the
     * specified id does not exist or if the id in the request body does not match
     * the id in the request URL.
     * 
     * @param id the identifier of the Bouncer to replace
     * @param entity the replacement Bouncer
     * @return  an HTTP response containing the updated Bouncer or an error
     *         response if the request is invalid
     */
    @RolesAllowed({"BouncerAdmin","ApiGroup"})
    @POST
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response replaceBouncer(@PathParam("id") Long id, Bouncer entity) {

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

        entity.setId(id);
        entity.applyDefaults();
        bouncerFacade.edit(entity);

        return Response.ok(entity).build();
    }

    /**
     * Updates an existing Bouncer using the non-null values supplied in the
     * request body.
     * 
     * Only the non-null properties of the supplied Bouncer overwrite the
     * corresponding properties of the existing entity. Existing values are
     * preserved for any properties omitted from the request. A 400 Bad 
     * Request response is returned if the specified id does not exist or if 
     * the id in the request body does not match the id in the URL.
     *
     * @param id the identifier of the Bouncer to update
     * @param bouncer the Bouncer containing the updated property values
     * @return an HTTP response containing the updated Bouncer or an error
     *         response if the request is invalid
     */
    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
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
    
    /**
     * Rejects PUT requests on the root Bouncer resource.
     *
     * @param bouncer ignored
     * @return an HTTP 405 (Method Not Allowed) response
     */
    @RolesAllowed({"BouncerAdmin","ApiGroup"})
    @PUT
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response putRoot(Bouncer incoming) {
        return Response.status(Response.Status.METHOD_NOT_ALLOWED)
                .entity("PUT on the root Bouncer resource is not allowed.")
                .build();
    }

    /**
     * Deletes the Bouncer with the specified identifier.
     *
     * @param id the identifier of the Bouncer to delete
     * @return an HTTP response indicating whether the deletion was successful
     */
    @RolesAllowed({"BouncerAdmin","ApiGroup"})
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

    /**
     * Retrieves the Bouncer with the specified identifier.
     *
     * @param id the identifier of the Bouncer to retrieve
     * @return the requested Bouncer, or an HTTP 404 response if it does not exist
     */
    
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

    /**
     * Retrieves all Bouncer entities.
     *
     * @return a list containing every Bouncer stored in the database
     */
    @RolesAllowed({"BouncerAdmin","ApiGroup"})
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Bouncer> findAll() {
        return bouncerFacade.findAll();
    }
    
    /**
     * Retrieves a range of Bouncer entities.
     *
     * @param from the index of the first Bouncer in the range
     * @param to the index of the last Bouncer in the range
     * @return a list containing the requested range of Bouncer entities
     */
    @RolesAllowed({"BouncerAdmin","ApiGroup"})
    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Bouncer> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return bouncerFacade.findRange(new int[]{from, to});
    }

    /**
     * Returns the total number of Bouncer entities stored in the database.
     *
     * @return the number of Bouncers as plain text
     */
    @RolesAllowed({"BouncerAdmin","ApiGroup"})
    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(bouncerFacade.count());
    }
    
}
