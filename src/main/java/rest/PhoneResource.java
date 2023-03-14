package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.CityInfoDTO;
import dtos.PhoneDTO;
import facades.CityInfoFacade;
import facades.PhoneFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

//Todo Remove or change relevant parts before ACTUAL use
@Path("phone")
public class PhoneResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
       
    private static final PhoneFacade FACADE =  PhoneFacade.getFacadeExample(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
            
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAll() {
        return Response.ok().entity(GSON.toJson(FACADE.getAllPhones())).build();
    }

    @GET
    @Path("/{PhoneNumber}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getCityInfoByZipCode(@PathParam("PhoneNumber") String phoneNumber) {
        PhoneDTO phoneDTO = FACADE.getPhoneByNumber(phoneNumber);
        return Response.ok().entity(GSON.toJson(phoneDTO)).build();
    }

    @GET
    @Path("/phoneByDescription/{descriptionPhone}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getCityInfoByCity(@PathParam("descriptionPhone") String description) {
        PhoneDTO phoneDTO = FACADE.getPhoneByDescription(description);
        return Response.ok().entity(GSON.toJson(phoneDTO)).build();
    }

    @PUT
    @Path("/{PhoneNumber}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response putPhone(@PathParam("PhoneNumber") String phoneNumber, String description) {
        PhoneDTO phoneDTO = GSON.fromJson(description, PhoneDTO.class);
        phoneDTO.setId(phoneNumber);
        phoneDTO = FACADE.editPhone(phoneDTO);
        return Response.ok().entity(GSON.toJson(phoneDTO)).build();
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response postPhone(String input){
        PhoneDTO phoneDTO = GSON.fromJson(input, PhoneDTO.class);
        phoneDTO = FACADE.addPhone(phoneDTO);
        return Response.ok().entity(GSON.toJson(phoneDTO)).build();
    }
}
