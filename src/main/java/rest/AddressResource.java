package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.AddressDTO;
import dtos.CityInfoDTO;
import facades.AddressFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("address")
public class AddressResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final AddressFacade FACADE =  AddressFacade.getAddressFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Logger LOGGER = Logger.getLogger(AddressResource.class.getName());


    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAll() {
        try {
            return Response.ok().entity(GSON.toJson(FACADE.getAllAddresses())).build();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An error occurred while getting all addresses", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("An error occurred while getting all addresses").build();
        }
    }

    @GET
    @Path("/{adress}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getCityInfoByZipCode(@PathParam("adress") String adress) {
        try {
            CityInfoDTO cityInfoDTO = FACADE.getAddressByName(adress);
            if (cityInfoDTO == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Address not found").build();
            }
            return Response.ok().entity(GSON.toJson(cityInfoDTO)).build();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An error occurred while getting city info by address", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("An error occurred while getting city info by address").build();
        }
    }

    @GET
    @Path("/addressByZipCode/{CityInfo_zipCode}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getCityInfoByCity(@PathParam("CityInfo_zipCode") int zipCode) {
        try {
            CityInfoDTO cityInfoDTO = FACADE.getAddressByZipCode(zipCode);
            if (cityInfoDTO == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("City info not found").build();
            }
            return Response.ok().entity(GSON.toJson(cityInfoDTO)).build();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An error occurred while getting city info by zip code", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("An error occurred while getting city info by zip code").build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editAddress(String addressJson) {
        try {
            AddressDTO addressDTO = GSON.fromJson(addressJson, AddressDTO.class);
            AddressDTO updatedAddress = FACADE.editAddress(addressDTO);
            if (updatedAddress == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Address not found.").build();
            }
            return Response.ok().entity(GSON.toJson(updatedAddress)).build();
        } catch (Exception e) {
            return Response.serverError().entity("An error occurred while editing address.").build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createAddress(String addressJson) {
        try {
            AddressDTO addressDTO = GSON.fromJson(addressJson, AddressDTO.class);
            AddressDTO newAddress = FACADE.addAddress(addressDTO);
            return Response.ok().entity(GSON.toJson(newAddress)).build();
        } catch (Exception e) {
            return Response.serverError().entity("An error occurred while creating address.").build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteAddress(@PathParam("id") String id) {
        try {
            AddressDTO deletedAddress = FACADE.deleteAddress(id);
            return Response.ok().entity(GSON.toJson(deletedAddress)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(GSON.toJson(e.getMessage())).build();
        }
    }
}