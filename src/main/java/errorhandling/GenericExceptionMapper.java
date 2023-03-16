package errorhandling;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.json.Json;
import javax.servlet.ServletContext;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.logging.Level;
import java.util.logging.Logger;

@Provider
public class GenericExceptionMapper implements ExceptionMapper<Throwable> {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Context
    ServletContext context;

    @Override
    public Response toResponse(Throwable ex) {
        int statusCode = Response.Status.INTERNAL_SERVER_ERROR.getStatusCode();
        String message = ex.getMessage();
        if (ex instanceof WebApplicationException) {
            statusCode = ((WebApplicationException) ex).getResponse().getStatus();
            message = ex.getCause() != null ? ex.getCause().getMessage() : ex.getMessage();
        }
        return Response.status(statusCode)
                .entity(Json.createObjectBuilder()
                        .add("code", statusCode)
                        .add("message", message)
                        .build().toString())
                .build();
    }

    private Response.StatusType getStatusType(Throwable ex) {
        if (ex instanceof NotFoundException) {
            return Response.Status.NOT_FOUND;
        } else if (ex instanceof ForbiddenException) {
            return Response.Status.FORBIDDEN;
        } else if (ex instanceof BadRequestException) {
            return Response.Status.BAD_REQUEST;
        } else if (ex instanceof NotSupportedException) {
            return Response.Status.UNSUPPORTED_MEDIA_TYPE;
        }
        return Response.Status.INTERNAL_SERVER_ERROR;
    }

}