package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.AddressDTO;
import dtos.CityInfoDTO;
import facades.AddressFacade;
import facades.CityInfoFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Denne klasse repræsenterer en REST API-ressource for Address.
 * Det giver klienter mulighed for at udføre CRUD-operationer på Address.
 * Denne klasse bruger JAX-RS annoteringer til at definere HTTP metoder og ressourcer.
 * AddressResource-klassen bruger AddressFacade-klassen til at interagere med databasen.
 */
@Path("address")
public class AddressResource {

    /**
     * Opret EntityManagerFactory ved hjælp af hjælpeklassen EMF_Creator
     */
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

    /**
     * Opret en instans af AddressFacade-klassen for at interagere med databasen
     */
    private static final AddressFacade FACADE =  AddressFacade.getAddressFacade(EMF);

    /**
     * Opret et Gson-objekt til at konvertere mellem JSON- og Java-objekter.
     */
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    /**
     * Hent alle Addresses fra databasen og returner dem som en JSON-streng
     */
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAll() {
        return Response.ok().entity(GSON.toJson(FACADE.getAllAddresses())).build();
    }

    /**
     * Få en Address ved dens Name og returner den som en JSON-streng
     */
    @GET
    @Path("/{adress}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAddressByName(@PathParam("adress") String adress) {
        CityInfoDTO cityInfoDTO = FACADE.getAddressByName(adress);
        return Response.ok().entity(GSON.toJson(cityInfoDTO)).build();
    }

    /**
     * Få en Address ved dens CityInfo_zipCode og returner den som en JSON-streng
     */
    @GET
    @Path("/addressByZipCode/{CityInfo_zipCode}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAddressByZipCode(@PathParam("CityInfo_zipCode") int zipCode) {
        CityInfoDTO cityInfoDTO = FACADE.getAddressByZipCode(zipCode);
        return Response.ok().entity(GSON.toJson(cityInfoDTO)).build();
    }

    /**
     * Opret en ny Address i databasen og returner den som en JSON-streng
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String createAddress(String addressJson) {
        AddressDTO addressDTO = GSON.fromJson(addressJson, AddressDTO.class);
        AddressDTO newAddress = FACADE.addAddress(addressDTO);
        return GSON.toJson(newAddress);
    }
}
