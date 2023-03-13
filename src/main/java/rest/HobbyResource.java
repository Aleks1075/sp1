package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.CityInfoDTO;
import facades.CityInfoFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

//Todo Remove or change relevant parts before ACTUAL use
@Path("cityinfo")
public class HobbyResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
       
    private static final CityInfoFacade FACADE =  CityInfoFacade.getFacadeExample(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
            
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAll() {
        return Response.ok().entity(GSON.toJson(FACADE.getAllCityInfo())).build();
    }

    @GET
    @Path("/{zipCode}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getCityInfoByZipCode(@PathParam("zipCode") int zipCode) {
        CityInfoDTO cityInfoDTO = FACADE.getCityByZipCode(zipCode);
        return Response.ok().entity(GSON.toJson(cityInfoDTO)).build();
    }

    @GET
    @Path("/cityByName/{city}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getCityInfoByCity(@PathParam("city") String city) {
        CityInfoDTO cityInfoDTO = FACADE.getCityByName(city);
        return Response.ok().entity(GSON.toJson(cityInfoDTO)).build();
    }
}
