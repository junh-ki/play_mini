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
        List<ValidationError> validationResult = validationService.validateGetRequest(page);
        if (validationResult.size() > 0) return badRequest(Json.toJson(validationResult));
        List<Apartment> apartments = apartmentService.getApartments(page, size);
        return ok(Json.toJson(apartments));
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

}
