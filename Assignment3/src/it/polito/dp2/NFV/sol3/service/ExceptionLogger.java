package it.polito.dp2.NFV.sol3.service;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status.Family;
import javax.ws.rs.ext.Provider;

import org.glassfish.hk2.utilities.reflection.Logger;
import org.glassfish.jersey.spi.ExtendedExceptionMapper;

@Provider
public class ExceptionLogger implements ExtendedExceptionMapper<Throwable> {

	
    @Override
    public boolean isMappable(Throwable thro) {
        if (isServerError(thro)) Logger.printThrowable(thro);
        if (isClientError(thro)) return true;
        
        return false;
    }

    private boolean isServerError(Throwable thro) {
        /* Note: We consider anything that is not an instance of WebApplicationException a server error. */
        return thro instanceof WebApplicationException
            && isServerError((WebApplicationException)thro);
    }

    private boolean isServerError(WebApplicationException exc) {
        return exc.getResponse().getStatusInfo().getFamily().equals(Family.SERVER_ERROR);
    }
    
    
    private boolean isClientError(Throwable thro) {
        /* Note: We consider anything that is not an instance of WebApplicationException a server error. */
        return thro instanceof WebApplicationException
            && isClientError((WebApplicationException)thro);
    }

    private boolean isClientError(WebApplicationException exc) {
        return exc.getResponse().getStatusInfo().getFamily().equals(Family.CLIENT_ERROR);
    }

    @Override
    public Response toResponse(Throwable throwable) {
    	WebApplicationException exc = (WebApplicationException) throwable;
    	
        return Response.status(exc.getResponse().getStatus()).entity(throwable.getMessage()).type("text/plain").build();
    }

}
