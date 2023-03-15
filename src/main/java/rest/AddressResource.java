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

//Todo Remove or change relevant parts before ACTUAL use
@Path("address")
public class AddressResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
       
    private static final AddressFacade FACADE =  AddressFacade.getAddressFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
            
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAll() {
        return Response.ok().entity(GSON.toJson(FACADE.getAllAddresses())).build();
    }

    @GET
    @Path("/{adress}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getCityInfoByZipCode(@PathParam("adress") String adress) {
        CityInfoDTO cityInfoDTO = FACADE.getAddressByName(adress);
        return Response.ok().entity(GSON.toJson(cityInfoDTO)).build();
    }

    @GET
    @Path("/addressByZipCode/{CityInfo_zipCode}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getCityInfoByCity(@PathParam("CityInfo_zipCode") int zipCode) {
        CityInfoDTO cityInfoDTO = FACADE.getAddressByZipCode(zipCode);
        return Response.ok().entity(GSON.toJson(cityInfoDTO)).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String editAddress(String addressJson) {
        AddressDTO addressDTO = GSON.fromJson(addressJson, AddressDTO.class);
        AddressDTO updatedAddress = FACADE.editAddress(addressDTO);
        return GSON.toJson(updatedAddress);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String createAddress(String addressJson) {
        AddressDTO addressDTO = GSON.fromJson(addressJson, AddressDTO.class);
        AddressDTO newAddress = FACADE.addAddress(addressDTO);
        return GSON.toJson(newAddress);
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteAddress(@PathParam("id") String id) {
        AddressDTO deletedAddress = FACADE.deleteAddress(id);
        return GSON.toJson(deletedAddress);
    }
}
