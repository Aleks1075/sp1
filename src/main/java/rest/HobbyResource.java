package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.CityInfoDTO;
import dtos.HobbyDTO;
import facades.CityInfoFacade;
import facades.HobbyFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("hobby")
public class HobbyResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

    private static final HobbyFacade FACADE =  HobbyFacade.getFacadeExample(EMF);

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAll() {
        return Response.ok().entity(GSON.toJson(FACADE.getAllHobbies())).build();
    }

    @GET
    @Path("/{name}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getHobbyByName(@PathParam("name") String name) {
        HobbyDTO hobbyDTO = FACADE.getHobbyByName(name);
        return Response.ok().entity(GSON.toJson(hobbyDTO)).build();
    }

    @GET
    @Path("/hobbyByType/{type}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getHobbyByType(@PathParam("type") String type) {
        HobbyDTO hobbyDTO = FACADE.getHobbyByType(type);
        return Response.ok().entity(GSON.toJson(hobbyDTO)).build();
    }

    @PUT
    @Path("/{name}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response putHobby(@PathParam("name") String name, String hobby){
        HobbyDTO hobbyDTO = GSON.fromJson(hobby, HobbyDTO.class);
        hobbyDTO.setName(name);
        hobbyDTO = FACADE.editHobby(hobbyDTO);
        return Response.ok().entity(hobbyDTO).build();
    }

    @DELETE
    @Path("/{name}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response deleteHobby(@PathParam("name") String name){
        HobbyDTO hobbyDTO = FACADE.deleteHobby(name);
        return Response.ok().entity(hobbyDTO).build();
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response postHobby(String name){
        HobbyDTO hobbyDTO = GSON.fromJson(name, HobbyDTO.class);
        hobbyDTO = FACADE.createHobby(hobbyDTO);
        return Response.ok().entity(hobbyDTO).build();
    }
}
