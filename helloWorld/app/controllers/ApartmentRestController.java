package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import dto.ApartmentRequest;
import models.Apartment;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import services.ApartmentService;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class ApartmentRestController extends Controller {

    private final ApartmentService apartmentService;

    @Inject
    public ApartmentRestController(ApartmentService apartmentService) {
        this.apartmentService = apartmentService;
    }

    public Result getApartments(Integer page, Integer size) {
        List<Apartment> apartments = apartmentService.getAllApartments();
        if (isOutOfIndex(page, size, apartments.size())) {
            return badRequest("Out of index! (Last Index: " + (apartments.size()-1) + ")");
        }
        List<Apartment> paginatedApartmentList = getPaginatedApartmentList(page, size, apartments);
        return ok(Json.toJson(paginatedApartmentList));
    }

    public Result getApartment(Long id) {
        Apartment apartment = apartmentService.findApartmentById(id);
        if (apartment == null) return notFound("ID doesn't exist."); // 404
        return ok(Json.toJson(apartment));
    }

    public Result addApartment(Http.Request request) {
        // TODO: 201 created
        // TODO: validate only once
        Result jsonValidationResult = validateJsonRequest(request);
        if (jsonValidationResult != null) return jsonValidationResult;
        ApartmentRequest requestedApartment = jsonRequestToApartmentRequest(request);
        Result requestValidationResult = validateApartmentRequest(requestedApartment);
        if (requestValidationResult != null) return requestValidationResult;
        Apartment apartment = apartmentService.saveApartment(requestedApartment);
        return ok(Json.toJson(apartment));
    }

    public Result deleteApartmentById(Long id) {
        Apartment apartment = apartmentService.deleteApartmentById(id);
        if (apartment == null) return notFound("ID doesn't exist."); // TODO: return ok or not found / I dont need to return the deleted obj.
        return ok(Json.toJson(apartment));
    }

    public Result updateApartment(Http.Request request) {
        // TODO: error dto,
        Result apartmentUpdateRequestValidationResult = validateApartmentUpdateRequest(request);
        if (apartmentUpdateRequestValidationResult != null) return apartmentUpdateRequestValidationResult;
        Apartment apartment = jsonRequestToApartment(request);
        Apartment updatedApartment = apartmentService.updateApartment(apartment);
        return ok(Json.toJson(updatedApartment));
    }





    // *** sub methods ***

    public Result validateJsonRequest(Http.Request request) {
        JsonNode requestJson = request.body().asJson();
        if (!requestJson.has("name")) {
            return badRequest("Name should be given!");
        } else if (!requestJson.has("category")) {
            return badRequest("Category should be given!");
        } else if (!requestJson.has("description")) {
            return badRequest("Description should be given!");
        } else if (!requestJson.has("size")) {
            return badRequest("Size should be given!");
        } else if (!requestJson.has("price")) {
            return badRequest("Price should be given!");
        }
        return null;
    }

    public ApartmentRequest jsonRequestToApartmentRequest(Http.Request request) {
        JsonNode requestJson = request.body().asJson();
        String name = requestJson.findValue("name").asText();
        String category = requestJson.findValue("category").asText();
        String description = requestJson.findValue("description").asText();
        Integer size = requestJson.findValue("size").asInt();
        Double price = requestJson.findValue("price").asDouble();
        ApartmentRequest apartmentRequest = new ApartmentRequest(name, category, description, size, price);
        return apartmentRequest;
    }

    public Apartment jsonRequestToApartment(Http.Request request) {

        // TODO: In the service layer, we can retreive the apartment by Id, and you update the obj according to json and pass it back to the repository.

        Apartment apartment = new Apartment();
        JsonNode requestJson = request.body().asJson();
        apartment.setId(requestJson.findValue("id").asLong());
        if (requestJson.has("name")) apartment.setName(requestJson.findValue("name").asText());
        if (requestJson.has("category")) apartment.setCategory(requestJson.findValue("category").asText());
        if (requestJson.has("description")) apartment.setDescription(requestJson.findValue("description").asText());
        if (requestJson.has("size")) apartment.setSize(requestJson.findValue("size").asInt());
        if (requestJson.has("price")) apartment.setPrice(requestJson.findValue("price").asDouble());
        return apartment;
    }

    public Result validateApartmentRequest(ApartmentRequest apartmentRequest) {
        String category = apartmentRequest.getCategory();
        Integer size = apartmentRequest.getSize();
        Double price = apartmentRequest.getPrice();
        if (category == null || !category.equals("A") && !category.equals("B") && !category.equals("C")) {
            return badRequest("Category should either be \"A\" or \"B\" or \"C\"!");
        } else if (size == null || size < 10) {
            return badRequest("Size should not be smaller than 10!");
        } else if (price == null || price < 5) {
            return badRequest("Price should not be smaller than 5!");
        }
        return null;
    }

    public Result validateApartmentUpdateRequest(Http.Request request) {
        // TODO: pass a different type (bad request)
        // TODO: return Optional instead of null
        // TODO: return JSON return object
        // TODO: Or See the play framework validation doc // annotation on your request.
        JsonNode requestJson = request.body().asJson();
        if (!requestJson.has("id")) return badRequest("ID must be given!");
        if (apartmentService.findApartmentById(requestJson.findValue("id").asLong()) == null) {
            return notFound("ID doesn't exist.");
        }
        if (requestJson.has("category")) {
            String category = requestJson.findValue("category").asText();
            if (!category.equals("A") && !category.equals("B") && !category.equals("C")) {
                return badRequest("Category should either be \"A\" or \"B\" or \"C\"!");
            }
        }
        if (requestJson.has("size")) {
            Integer size = requestJson.findValue("size").asInt();
            if (size < 10) return badRequest("Size should not be smaller than 10!");
        }
        if (requestJson.has("price")) {
            Double price = requestJson.findValue("price").asDouble();
            if (price < 5) return badRequest("Price should not be smaller than 5!");
        }
        return null;
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
