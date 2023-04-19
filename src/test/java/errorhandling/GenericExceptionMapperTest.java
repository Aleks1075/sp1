package errorhandling;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;

public class GenericExceptionMapperTest {

    private final GenericExceptionMapper mapper = new GenericExceptionMapper();

    @Test
    @DisplayName("Test af BadRequestException")
    public void testBadRequestException() {
        BadRequestException ex = new BadRequestException("Bad request");
        Response response = mapper.toResponse(ex);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        assertEquals("{\"code\":400,\"message\":\"Bad request\"}", response.getEntity());
    }

    @Test
    @DisplayName("Test af NotFoundException")
    public void testNotFoundException() {
        NotFoundException ex = new NotFoundException("Not found");
        Response response = mapper.toResponse(ex);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
        assertEquals("{\"code\":404,\"message\":\"Not found\"}", response.getEntity());
    }

    @Test
    @DisplayName("Test af RuntimeException")
    public void testRuntimeException() {
        RuntimeException ex = new RuntimeException("Internal server error");
        Response response = mapper.toResponse(ex);
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
        assertEquals("{\"code\":500,\"message\":\"Internal server error\"}", response.getEntity());
    }
}