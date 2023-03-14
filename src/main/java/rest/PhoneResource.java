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

/**
 * Denne klasse repræsenterer en REST API-ressource til Phone.
 * Det giver klienter mulighed for at udføre CRUD-operationer på Phone.
 * Denne klasse bruger JAX-RS annoteringer til at definere HTTP metoder og ressourcer.
 * PhoneResource-klassen bruger PhoneFacade-klassen til at interagere med databasen.
 */

@Path("phone")
public class PhoneResource {

    /**
     * Opret EntityManagerFactory ved hjælp af hjælpeklassen EMF_Creator
     */
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

    /**
     * Opret en instans af PhoneFacade-klassen for at interagere med databasen
     */
    private static final PhoneFacade FACADE =  PhoneFacade.getFacadeExample(EMF);

    /**
     * Opret et Gson-objekt til at konvertere mellem JSON- og Java-objekter.
     */
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    /**
     * Hent alle Phones fra databasen og returner dem som en JSON-streng
     */
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAll() {
        return Response.ok().entity(GSON.toJson(FACADE.getAllPhones())).build();
    }

    /**
     * Få en Phone ved dens PhoneNumber og returner den som en JSON-streng
     */
    @GET
    @Path("/{PhoneNumber}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getPhoneByNumber(@PathParam("PhoneNumber") String phoneNumber) {
        PhoneDTO phoneDTO = FACADE.getPhoneByNumber(phoneNumber);
        return Response.ok().entity(GSON.toJson(phoneDTO)).build();
    }

    /**
     * Få en Phone ved dens description og returner den som en JSON-streng
     */
    @GET
    @Path("/phoneByDescription/{descriptionPhone}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getPhoneByDescription(@PathParam("descriptionPhone") String description) {
        PhoneDTO phoneDTO = FACADE.getPhoneByDescription(description);
        return Response.ok().entity(GSON.toJson(phoneDTO)).build();
    }

    /**
     * Rediger en Phone efter dens PhoneNumber, og returner den opdaterede telefon som en JSON-streng
     */
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

    /**
     * Tilføj en Phone til databasen og returner den som en JSON-streng.
     */
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response postPhone(String input){
        PhoneDTO phoneDTO = GSON.fromJson(input, PhoneDTO.class);
        phoneDTO = FACADE.addPhone(phoneDTO);
        return Response.ok().entity(GSON.toJson(phoneDTO)).build();
    }
}