package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.CityInfoDTO;
import dtos.RenameMeDTO;
import facades.CityInfoFacade;
import facades.FacadeExample;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Denne klasse repræsenterer en REST API-ressource for CityInfo.
 * Det giver klienter mulighed for at udføre CRUD-operationer på CityInfo.
 * Denne klasse bruger JAX-RS annoteringer til at definere HTTP metoder og ressourcer.
 * CityInfoResource-klassen bruger CityInfoFacade-klassen til at interagere med databasen.
 */
@Path("cityinfo")
public class CityInfoResource {

    /**
     * Opret EntityManagerFactory ved hjælp af hjælpeklassen EMF_Creator
     */
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

    /**
     * Opret en instans af CityInfoFacade-klassen for at interagere med databasen
     */
    private static final CityInfoFacade FACADE =  CityInfoFacade.getFacadeExample(EMF);

    /**
     * Opret et Gson-objekt til at konvertere mellem JSON- og Java-objekter.
     */
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    /**
     * Hent alle CityInfo fra databasen og returner dem som en JSON-streng
     */
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAll() {
        return Response.ok().entity(GSON.toJson(FACADE.getAllCityInfo())).build();
    }

    /**
     * Få en CityInfo ved dens ZipCode og returner den som en JSON-streng
     */
    @GET
    @Path("/{zipCode}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getCityInfoByZipCode(@PathParam("zipCode") int zipCode) {
        CityInfoDTO cityInfoDTO = FACADE.getCityByZipCode(zipCode);
        return Response.ok().entity(GSON.toJson(cityInfoDTO)).build();
    }

    /**
     * Få en CityInfo ved dens City og returner den som en JSON-streng
     */
    @GET
    @Path("/cityByName/{city}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getCityInfoByCity(@PathParam("city") String city) {
        CityInfoDTO cityInfoDTO = FACADE.getCityByName(city);
        return Response.ok().entity(GSON.toJson(cityInfoDTO)).build();
    }
}
