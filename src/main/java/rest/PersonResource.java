package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.PersonDTO;
import facades.PersonFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Denne klasse repræsenterer en REST API-ressource til Person.
 * Det giver klienter mulighed for at udføre CRUD-operationer på Person.
 * Denne klasse bruger JAX-RS annoteringer til at definere HTTP metoder og ressourcer.
 * PhoneResource-klassen bruger PersonFacade-klassen til at interagere med databasen.
 */
@Path("person")
public class PersonResource {

    /**
     * Opret EntityManagerFactory ved hjælp af hjælpeklassen EMF_Creator
     */
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

    /**
     * Opret en instans af PersonFacade-klassen for at interagere med databasen
     */
    private static final PersonFacade FACADE =  PersonFacade.getFacadeExample(EMF);

    /**
     * Opret et Gson-objekt til at konvertere mellem JSON- og Java-objekter.
     */
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    /**
     * Hent alle Persons fra databasen og returner dem som en JSON-streng
     */
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAll() {
        return Response.ok().entity(GSON.toJson(FACADE.getAllPersons())).build();
    }

    /**
     * Få en Person ved dens id og returner den som en JSON-streng
     */
    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getPersonById(@PathParam("id") int id) {
        return Response.ok().entity(GSON.toJson(FACADE.getPersonById(id))).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String deletePerson(@PathParam("id") int id) {
        PersonDTO personDTO = FACADE.deletePerson(id);
        return GSON.toJson(personDTO);
    }


    @PUT
    @Path("/edit")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response editPerson(String person) {
        try {
            PersonDTO personDTO = GSON.fromJson(person, PersonDTO.class);
            PersonDTO updatedPersonDTO = FACADE.editPerson(personDTO);
            return Response.ok().entity(GSON.toJson(updatedPersonDTO)).build();
        } catch (Exception e) {
            // Log the exception
            e.printStackTrace();
            // Return an appropriate error response
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("/add")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response addPerson(String person) {
        try {
            PersonDTO personDTO = GSON.fromJson(person, PersonDTO.class);
            FACADE.addPerson(personDTO);
            return Response.ok().entity(GSON.toJson(personDTO)).build();
        } catch (Exception e) {
            // Log the exception
            e.printStackTrace();
            // Return an appropriate error response
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
}