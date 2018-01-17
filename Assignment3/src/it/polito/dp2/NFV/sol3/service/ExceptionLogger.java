package it.polito.dp2.NFV.sol3.service;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status.Family;
import javax.ws.rs.ext.Provider;

import org.glassfish.jersey.spi.ExtendedExceptionMapper;

@Provider
public class ExceptionLogger implements ExtendedExceptionMapper<Throwable> {


    @Override
    public boolean isMappable(Throwable thro) {
        /* Primarily, we don't want to log client errors (i.e. 400's) as an error. */
        if (isServerError(thro)) thro.printStackTrace(System.out);
        if (isClientError(thro)) return true;
        return false;
    }

    private boolean isClientError(Throwable thro) {
        return thro instanceof WebApplicationException
                && isClientError((WebApplicationException)thro);
	}

	private boolean isServerError(Throwable thro) {
        /* Note: We consider anything that is not an instance of WebApplicationException a server error. */
        return thro instanceof WebApplicationException
            && isServerError((WebApplicationException)thro);
    }

    private boolean isServerError(WebApplicationException exc) {
        return exc.getResponse().getStatusInfo().getFamily().equals(Family.SERVER_ERROR);
    }
    
    private boolean isClientError(WebApplicationException exc) {
        return exc.getResponse().getStatusInfo().getFamily().equals(Family.CLIENT_ERROR);
    }

    @Override
    public Response toResponse(Throwable t) {
    	
    	if (t instanceof NotFoundException) return Response.status(404).entity(t.getMessage()).type("text/plain").build();
    	if (t instanceof UnprocessableEntityException) return Response.status(422).entity(t.getMessage()).type("text/plain").build();
    	if (t instanceof ConflictException) return Response.status(409).entity(t.getMessage()).type("text/plain").build();	
    	if (t instanceof BadRequestException) return Response.status(409).entity(t.getMessage()).type("text/plain").build();
    	
        throw new InternalServerErrorException(t);
    }

}
