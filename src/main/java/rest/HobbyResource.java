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

/**
 * Denne klasse repræsenterer en REST API-ressource for Hobby.
 * Det giver klienter mulighed for at udføre CRUD-operationer på Hobby.
 * Denne klasse bruger JAX-RS annoteringer til at definere HTTP metoder og ressourcer.
 * HobbyResource-klassen bruger HobbyFacade-klassen til at interagere med databasen.
 */
@Path("hobby")
public class HobbyResource {

    /**
     * Opret EntityManagerFactory ved hjælp af hjælpeklassen EMF_Creator
     */
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

    /**
     * Opret en instans af HobbyFacade-klassen for at interagere med databasen
     */
    private static final HobbyFacade FACADE =  HobbyFacade.getFacadeExample(EMF);

    /**
     * Opret et Gson-objekt til at konvertere mellem JSON- og Java-objekter.
     */
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    /**
     * Hent alle Hobbies fra databasen og returner dem som en JSON-streng
     */
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAll() {
        return Response.ok().entity(GSON.toJson(FACADE.getAllHobbies())).build();
    }

    /**
     * Få en Hobby ved dens Name og returner den som en JSON-streng
     */
    @GET
    @Path("/{name}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getHobbyByName(@PathParam("name") String name) {
        HobbyDTO hobbyDTO = FACADE.getHobbyByName(name);
        return Response.ok().entity(GSON.toJson(hobbyDTO)).build();
    }

    /**
     * Få en Hobby ved dens Type og returner den som en JSON-streng
     */
    @GET
    @Path("/hobbyByType/{type}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getHobbyByType(@PathParam("type") String type) {
        HobbyDTO hobbyDTO = FACADE.getHobbyByType(type);
        return Response.ok().entity(GSON.toJson(hobbyDTO)).build();
    }

    /**
     * Opdater en Hobby i databasen og returner den som en JSON-streng
     */
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

    /**
     * Slet en Hobby fra databasen og returner den som en JSON-streng
     */
    @DELETE
    @Path("/{name}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response deleteHobby(@PathParam("name") String name){
        HobbyDTO hobbyDTO = FACADE.deleteHobby(name);
        return Response.ok().entity(hobbyDTO).build();
    }

    /**
     * Opret en Hobby i databasen og returner den som en JSON-streng
     */
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response postHobby(String name){
        HobbyDTO hobbyDTO = GSON.fromJson(name, HobbyDTO.class);
        hobbyDTO = FACADE.createHobby(hobbyDTO);
        return Response.ok().entity(hobbyDTO).build();
    }
}
