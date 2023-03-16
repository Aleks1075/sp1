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
import java.util.List;

@Path("person")
public class PersonResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

    private static final PersonFacade FACADE =  PersonFacade.getFacadeExample(EMF);

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAll() {
        return Response.ok().entity(GSON.toJson(FACADE.getAllPersons())).build();
    }

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

    @GET
    @Path("/phone/{phoneNumber}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPersonInfoByPhoneNumber(@PathParam("phoneNumber") String phoneNumber) {
        PersonDTO personInfoDTO = FACADE.getPersonByPhoneNumber(phoneNumber);
        if (personInfoDTO == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Person not found").build();
        }
        return Response.ok().entity(GSON.toJson(personInfoDTO)).build();
    }

    @GET
    @Path("/hobby/{hobby}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPersonsByHobby(@PathParam("hobby") String hobby) {
        List<PersonDTO> persons = FACADE.getPersonsByHobby(hobby);
        if (persons.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("No persons found with the given hobby").build();
        }
        return Response.ok().entity(GSON.toJson(persons)).build();
    }

    @GET
    @Path("/personByHobby/{hobby}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNumberOfPersonsByHobby(@PathParam("hobby") String hobby) {
        long numberOfPersons = FACADE.getNumberOfPersonsByHobby(hobby);
        if (numberOfPersons == 0) {
            return Response.status(Response.Status.NOT_FOUND).entity("No persons found with the given hobby").build();
        }
        return Response.ok().entity(GSON.toJson(numberOfPersons)).build();
    }

    @GET
    @Path("/zipCode/{zipCode}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPersonsByZipCode(@PathParam("zipCode") int zipCode) {
        List<PersonDTO> persons = FACADE.getPersonsByZipCode(zipCode);
        if (persons.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("No persons found living in the given zip code").build();
        }
        return Response.ok().entity(GSON.toJson(persons)).build();
    }
}