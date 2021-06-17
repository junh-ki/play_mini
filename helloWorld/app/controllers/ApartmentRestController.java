package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import dto.ApartmentRequest;
import dto.ValidationError;
import models.Apartment;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import services.ApartmentService;
import services.ValidationService;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ApartmentRestController extends Controller {

    private final ApartmentService apartmentService;
    private final ValidationService validationService;

    @Inject
    public ApartmentRestController(ApartmentService apartmentService, ValidationService validationService) {
        this.apartmentService = apartmentService;
        this.validationService = validationService;
    }

    public Result getApartments(Integer page, Integer size) {
        List<Apartment> apartments = apartmentService.getAllApartments();
        if (isOutOfIndex(page, size, apartments.size())) {
            return badRequest(Json.toJson("Out of index! (Last Index: " + (apartments.size()-1) + ")"));
        }
        List<Apartment> paginatedApartmentList = getPaginatedApartmentList(page, size, apartments);
        return ok(Json.toJson(paginatedApartmentList));
    }

    public Result getApartment(Long id) {
        Optional<Apartment> nullableApartment = apartmentService.findApartmentById(id);
        if (nullableApartment.isEmpty()) return notFound(Json.toJson("ID doesn't exist."));
        return ok(Json.toJson(nullableApartment.get()));
    }

    public Result addApartment(Http.Request request) {
        List<ValidationError> validationResult = validationService.validateAddRequest(httpRequestToApartmentRequest(request));
        if (validationResult.size() > 0) return badRequest(Json.toJson(validationResult));
        ApartmentRequest apartmentRequest = httpRequestToApartmentRequest(request);
        Apartment addedApartment = apartmentService.addApartment(apartmentRequest);
        return created(Json.toJson(addedApartment));
    }

    public Result deleteApartmentById(Long id) {
        int deleteResult = apartmentService.deleteApartmentById(id);
        if (deleteResult == 0) {
            return notFound(Json.toJson("ID doesn't exist!"));
        } else if (deleteResult == 1) {
            return ok(Json.toJson("Successfully deleted!"));
        }
        return internalServerError(Json.toJson("Identical ID exists!"));
    }

    public Result updateApartment(Http.Request request) {
        List<ValidationError> validationResult = validationService.validateUpdateRequest(httpRequestToApartmentRequest(request));
        if (validationResult.size() > 0) return badRequest(Json.toJson(validationResult));
        ApartmentRequest apartmentRequest = httpRequestToApartmentRequest(request);
        Apartment updatedApartment = apartmentService.updateApartment(apartmentRequest);
        return ok(Json.toJson(updatedApartment));
    }

    // *** sub methods ***

    private ApartmentRequest httpRequestToApartmentRequest(Http.Request request) {
        JsonNode jsonRequest = request.body().asJson();
        ApartmentRequest apartmentRequest = new ApartmentRequest();
        if (jsonRequest.has("id")) apartmentRequest.setId(jsonRequest.findValue("id").asLong());
        if (jsonRequest.has("name")) apartmentRequest.setName(jsonRequest.findValue("name").asText());
        if (jsonRequest.has("category")) apartmentRequest.setCategory(jsonRequest.findValue("category").asText());
        if (jsonRequest.has("description")) apartmentRequest.setDescription(jsonRequest.findValue("description").asText());
        if (jsonRequest.has("size")) apartmentRequest.setSize(jsonRequest.findValue("size").asInt());
        if (jsonRequest.has("price")) apartmentRequest.setPrice(jsonRequest.findValue("price").asDouble());
        return apartmentRequest;
    }

    public boolean isOutOfIndex(Integer page, Integer size, Integer length) {
        int startIndex = 0;
        if (page > 1) startIndex = startIndex + (page - 1) * size;
        int lastIndex = startIndex + size;
        if (lastIndex > length) return true;
        return false;
    }

    public List<Apartment> getPaginatedApartmentList(Integer page, Integer size, List<Apartment> apartments) {
        // TODO: SQL QUERY (Ebean) don't use cache, you can return an empty list. or you can return a bad request (400.. 404 or something)
        int startIndex = 0;
        if (page > 1) startIndex = startIndex + (page - 1) * size;
        List<Apartment> paginatedApartmentList = new ArrayList<Apartment>();
        for (int i = startIndex; i < startIndex + size; i++) {
            Apartment app = apartments.get(i);
            paginatedApartmentList.add(app);
        }
        return paginatedApartmentList;
    }

}
