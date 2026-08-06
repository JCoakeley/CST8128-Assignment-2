package cst8218.jc040929397.bouncer;

import jakarta.security.enterprise.authentication.mechanism.http.BasicAuthenticationMechanismDefinition;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

/**
 * Configures Jakarta RESTful Web Services for the application.
 * @author Juneau
 */
@ApplicationPath("resources")
@BasicAuthenticationMechanismDefinition
public class JakartaRestConfiguration extends Application {
    
}
