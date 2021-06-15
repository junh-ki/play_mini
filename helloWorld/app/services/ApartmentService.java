package services;

import com.fasterxml.jackson.databind.JsonNode;
import models.Apartment;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import repos.ApartmentRepository;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.Optional;

import static play.mvc.Results.*;

@Singleton
public class ApartmentService {

    private final ApartmentRepository apartmentRepository;

    @Inject
    public ApartmentService(ApartmentRepository apartmentRepository) {
        this.apartmentRepository = apartmentRepository;
    }

    public Optional<Apartment> findApartmentById(Long apartmentId) {
        Apartment apartment = apartmentRepository.findApartmentById(apartmentId);
        return Optional.ofNullable(apartment);
    }

    // TODO: SQL query Ebean.
    public List<Apartment> getAllApartments() {
        return apartmentRepository.getAllApartments();
    }

    public Apartment addApartment(Apartment apartment) {
        Apartment addedApartment = apartmentRepository.addApartment(apartment);
        return addedApartment;
    }

    public Result deleteApartmentById(Long apartmentId) {
        int deleteResult = apartmentRepository.deleteApartmentById(apartmentId);
        if (deleteResult == 0) {
            return notFound(Json.toJson("ID doesn't exist!"));
        } else if (deleteResult == 1) {
            return ok(Json.toJson("Successfully deleted!"));
        }
        return internalServerError(Json.toJson("Identical ID exists!"));
    }

    public Apartment updateApartment(Http.Request request) {
        JsonNode jsonRequest = request.body().asJson();
        Apartment foundApartment = apartmentRepository.findApartmentById(jsonRequest.findValue("id").asLong());
        Apartment updatedApartment = updateApartmentObjWithJson(foundApartment, jsonRequest);
        apartmentRepository.updateApartment(updatedApartment);
        return updatedApartment;
    }

    public Apartment updateApartmentObjWithJson(Apartment apartment, JsonNode jsonRequest) {
        if (jsonRequest.has("id")) apartment.setId(jsonRequest.findValue("id").asLong());
        if (jsonRequest.has("name")) apartment.setName(jsonRequest.findValue("name").asText());
        if (jsonRequest.has("category")) apartment.setCategory(jsonRequest.findValue("category").asText());
        if (jsonRequest.has("description")) apartment.setDescription(jsonRequest.findValue("description").asText());
        if (jsonRequest.has("size")) apartment.setSize(jsonRequest.findValue("size").asInt());
        if (jsonRequest.has("price")) apartment.setPrice(jsonRequest.findValue("price").asDouble());
        return apartment;
    }

}
