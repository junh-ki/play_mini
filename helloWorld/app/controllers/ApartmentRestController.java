package controllers;

import dto.ApartmentRequest;
import models.Apartment;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import services.ApartmentService;

import javax.inject.Inject;
import java.util.List;

public class ApartmentRestController extends Controller {

    private final ApartmentService apartmentService;

    @Inject
    public ApartmentRestController(ApartmentService apartmentService) {
        this.apartmentService = apartmentService;
    }

    public Result getApartments() {
        // TODO: current page , page size | page-pagination
        List<Apartment> apartments = apartmentService.getAllApartments();
        return ok(Json.toJson(apartments));
    }

    public Result getApartment(Long id) {
        Apartment apartment = apartmentService.findApartmentById(id);
        if (apartment == null) return notFound("ID doesn't exist."); // 404
        return ok(Json.toJson(apartment));
    }

    public Result addApartment(Http.Request request) {
        Apartment requestedApartment = request.body().as(Apartment.class);
        Result validateResult = validateApartmentRequest(requestedApartment);
        if (validateResult != null) return validateResult;
        Apartment apartment = requestApartment(requestedApartment);
        return ok(Json.toJson(apartment));
    }

    public Result deleteApartmentById(Long id) {
        Apartment apartment = apartmentService.deleteApartmentById(id);
        if (apartment == null) return notFound("ID doesn't exist."); // 404
        return ok(Json.toJson(apartment));
    }

    public Result updateApartment(Http.Request request) {
        Apartment requestedApartment = request.body().as(Apartment.class);
        Result validateResult = validateApartmentUpdate(requestedApartment);
        if (validateResult != null) return validateResult;
        Apartment updatedApartment = apartmentService.updateApartment(requestedApartment);
        return ok(Json.toJson(updatedApartment));
    }

    public Result validateApartmentRequest(Apartment apartment) {
        String category = apartment.getCategory();
        Integer size = apartment.getSize();
        Double price = apartment.getPrice();
        if (category == null || !category.equals("A") && !category.equals("B") && !category.equals("C")) {
            return badRequest("Category should either be \"A\" or \"B\" or \"C\"!");
        } else if (size == null || size < 10) {
            return badRequest("Size should not be smaller than 10!");
        } else if (price == null || price < 5) {
            return badRequest("Price should not be smaller than 5!");
        }
        return null;
    }

    public Result validateApartmentUpdate(Apartment requestedApartment) {
        if (requestedApartment.getId() == null) return badRequest("ID must be given!");
        return validateApartmentRequest(requestedApartment);
    }

    public Apartment requestApartment(Apartment requestedApartment) {
        ApartmentRequest request = new ApartmentRequest(requestedApartment.getName(), requestedApartment.getCategory(),
                requestedApartment.getDescription(), requestedApartment.getSize(), requestedApartment.getPrice());
        return apartmentService.saveApartment(request);
    }

}
