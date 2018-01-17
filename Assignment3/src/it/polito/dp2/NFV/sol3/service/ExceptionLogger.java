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
        if (isClientMappedError(thro)) return true;
        return false;
    }

    private boolean isClientMappedError(Throwable thro) {
        return thro instanceof WebApplicationException
                && isClientMappedError((WebApplicationException)thro);
	}

	private boolean isServerError(Throwable thro) {
        /* Note: We consider anything that is not an instance of WebApplicationException a server error. */
        return thro instanceof WebApplicationException
            && isServerError((WebApplicationException)thro);
    }

    private boolean isServerError(WebApplicationException exc) {
        return exc.getResponse().getStatusInfo().getFamily().equals(Family.SERVER_ERROR);
    }
    
    private boolean isClientMappedError(WebApplicationException exc) {
    	int[] mappedCodes = { 400, 404, 409, 422 };
        for (int n : mappedCodes) {
            if (exc.getResponse().getStatusInfo().getStatusCode() == n) {
               return true;
            }
         }
         return false;
    }

    @Override
    public Response toResponse(Throwable t) {
    	
    	if (t instanceof NotFoundException) return Response.status(404).entity(t.getCause().getMessage()).type("text/plain").build();
    	if (t instanceof UnprocessableEntityException) return Response.status(422).entity(t.getCause().getMessage()).type("text/plain").build();
    	if (t instanceof ConflictException) return Response.status(409).entity(t.getCause().getMessage()).type("text/plain").build();	
    	if (t instanceof BadRequestException) return Response.status(400).entity(t.getCause().getMessage()).type("text/plain").build();
    	
        throw new InternalServerErrorException(t);
    }

}
