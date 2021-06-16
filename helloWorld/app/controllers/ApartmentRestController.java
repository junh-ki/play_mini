package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.Apartment;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import services.ApartmentService;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ApartmentRestController extends Controller {

    private final ApartmentService apartmentService;

    @Inject
    public ApartmentRestController(ApartmentService apartmentService) {
        this.apartmentService = apartmentService;
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
        if (nullableApartment.isEmpty()) return notFound(Json.toJson("ID doesn't exist.")); // 404
        return ok(Json.toJson(nullableApartment.get()));
    }

    public Result addApartment(Http.Request request) {
        Result validationResult = validateHttpRequestForAddApartment(request);
        if (validationResult.status() != 200) return validationResult;
        Apartment requestedApartment = httpRequestToApartment(request);
        Apartment addedApartment = apartmentService.addApartment(requestedApartment);
        return created(Json.toJson(addedApartment)); // 201
    }

    public Result deleteApartmentById(Long id) {
        return apartmentService.deleteApartmentById(id);
    }

    public Result updateApartment(Http.Request request) {
        Result validationResult = validateHttpRequestForUpdateApartment(request);
        if (validationResult.status() != 200) return validationResult;
        Apartment updatedApartment = apartmentService.updateApartment(request);
        return ok(Json.toJson(updatedApartment));
    }

    // *** sub methods ***

    public Result validateHttpRequestForAddApartment(Http.Request request) {
        // TODO: pass a different type (bad request)
        JsonNode jsonRequest = request.body().asJson();
        if (jsonRequest.has("id")) return badRequest(Json.toJson("ID is not required!"));
        if (!jsonRequest.has("name")) return badRequest(Json.toJson("Name should be given!"));
        if (!jsonRequest.has("category")) {
            return badRequest(Json.toJson(Json.toJson("Category should be given!")));
        } else {
            String category = jsonRequest.get("category").asText();
            if (!category.equals("A") && !category.equals("B") && !category.equals("C")) {
                return badRequest(Json.toJson(Json.toJson("Category should either be \"A\" or \"B\" or \"C\"!")));
            }
        }
        if (!jsonRequest.has("description")) return badRequest(Json.toJson("Description should be given!"));
        if (!jsonRequest.has("size")) {
            return badRequest(Json.toJson(Json.toJson("Size should be given!")));
        } else {
            int size = jsonRequest.findValue("size").asInt();
            if (size < 10) return badRequest(Json.toJson("Size should not be smaller than 10!"));
        }
        if (!jsonRequest.has("price")) {
            return badRequest(Json.toJson("Price should be given!"));
        } else {
            double price = jsonRequest.findValue("price").asDouble();
            if (price < 5) return badRequest(Json.toJson("Price should not be smaller than 5!"));
        }
        return ok();
    }

    public Result validateHttpRequestForUpdateApartment(Http.Request request) {
        // TODO: pass a different type (bad request)
        JsonNode jsonRequest = request.body().asJson();
        if (!jsonRequest.has("id")) {
            return badRequest(Json.toJson("ID must be given!"));
        } else {
            Long id = jsonRequest.findValue("id").asLong();
            Optional<Apartment> foundApartment = apartmentService.findApartmentById(id);
            if (foundApartment.isEmpty() == true) return notFound(Json.toJson("ID doesn't exist!"));
        }
        if (jsonRequest.has("category")) {
            String category = jsonRequest.get("category").asText();
            if (!category.equals("A") && !category.equals("B") && !category.equals("C")) {
                return badRequest(Json.toJson("Category should either be \"A\" or \"B\" or \"C\"!"));
            }
        }
        if (jsonRequest.has("size")) {
            int size = jsonRequest.findValue("size").asInt();
            if (size < 10) return badRequest(Json.toJson("Size should not be smaller than 10!"));
        }
        if (jsonRequest.has("price")) {
            double price = jsonRequest.findValue("price").asDouble();
            if (price < 5) return badRequest(Json.toJson("Price should not be smaller than 5!"));
        }
        return ok();
    }

    public Apartment httpRequestToApartment(Http.Request request) {
        JsonNode jsonRequest = request.body().asJson();
        Apartment emptyApartment = new Apartment();
        Apartment apartment = apartmentService.updateApartmentObjWithJson(emptyApartment, jsonRequest);
        return apartment;
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
