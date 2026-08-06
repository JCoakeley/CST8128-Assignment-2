package cst8218.jc040929397.bouncer.rest;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;
import java.io.IOException;

/**
 * CORS Filter
 *
 * Allows the React application running on localhost:5174
 * to communicate with the REST API.
 *
 * @author Alex
 */
@Provider
public class CorsFilter implements ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext requestContext,
                       ContainerResponseContext responseContext)
            throws IOException {

        // Allow requests from React (Vite)
        responseContext.getHeaders().add(
                "Access-Control-Allow-Origin",
                "http://localhost:5173");

        // Allow credentials if authentication is used
        responseContext.getHeaders().add(
                "Access-Control-Allow-Credentials",
                "true");

        // Allowed HTTP methods
        responseContext.getHeaders().add(
                "Access-Control-Allow-Methods",
                "GET, POST, PUT, DELETE, OPTIONS, HEAD");

        // Allowed request headers
        responseContext.getHeaders().add(
                "Access-Control-Allow-Headers",
                "Origin, Content-Type, Accept, Authorization");

        // Headers exposed to the browser
        responseContext.getHeaders().add(
                "Access-Control-Expose-Headers",
                "Authorization, Location");

        // Cache preflight request for 24 hours
        responseContext.getHeaders().add(
                "Access-Control-Max-Age",
                "86400");
    }
}