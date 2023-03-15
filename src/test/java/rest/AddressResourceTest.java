//package rest;
//
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import javax.ws.rs.core.Response;
//
//public class AddressResourceTest {
//
//    @Test
//    public void testGetAllAddressErrorHandling() {
//        AddressResource addressResource = new AddressResource();
//        Response response = addressResource.getAll();
//        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
//        assertTrue(response.getEntity().toString().contains("An error occurred while getting all addresses"));
//    }
//
//    @Test
//    public void testGetCityInfoByZipCodeErrorHandling() {
//        AddressResource addressResource = new AddressResource();
//        Response response = addressResource.getCityInfoByZipCode("invalid_address");
//        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
//        assertTrue(response.getEntity().toString().contains("An error occurred while getting city info by zipcode"));
//    }
//
//    @Test
//    public void testGetCityInfoByCityErrorHandling() {
//        AddressResource addressResource = new AddressResource();
//        Response response = addressResource.getCityInfoByCity(0);
//        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
//        assertTrue(response.getEntity().toString().contains("City info not found"));
//    }
//}