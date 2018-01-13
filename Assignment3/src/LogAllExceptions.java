import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status.Family;
import javax.ws.rs.ext.Provider;

import org.glassfish.jersey.spi.ExtendedExceptionMapper;

@Provider
public class LogAllExceptions implements ExtendedExceptionMapper<Throwable> {


    @Override
    public boolean isMappable(Throwable thro) {
        /* Primarily, we don't want to log client errors (i.e. 400's) as an error. */
        if (isServerError(thro)) thro.printStackTrace(System.out);
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

    @Override
    public Response toResponse(Throwable throwable) {
        //assert false;
        System.err.println("ThrowableLogger_ExceptionMapper.toResponse: This should not have been called.");
        throw new RuntimeException("This should not have been called");
    }

}
