package controllers;

import dto.ApartmentRequest;
import models.Apartment;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import services.ApartmentService;

import java.util.List;
import javax.inject.Inject;

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
        if (apartment == null) return badRequest("ID doesn't exist."); // TODO: change the return status (400)
        return ok(Json.toJson(apartment));
    }

    public Result addApartment(String name, String category, String description, Integer size, Double price) {
        System.out.println("Category: " + category);
        // TODO: separate method for validation
        if (!category.equals("A") && !category.equals("B") && !category.equals("C")) {
            return badRequest("Category should either be \"A\" or \"B\" or \"C\"!");
        } else if (size < 10) {
            return badRequest("Size should not be smaller than 10!");
        } else if (price < 5) {
            return badRequest("Price should not be smaller than 5!");
        }
        ApartmentRequest request = new ApartmentRequest(name, category, description, size, price);
        Apartment apartment = apartmentService.saveApartment(request);
        return ok(Json.toJson(apartment));
    }

    public Result deleteApartmentById(Long id) {
        Apartment apartment = apartmentService.deleteApartmentById(id);
        if (apartment == null) return badRequest("ID doesn't exist."); // change the return status (400)
        return ok(Json.toJson(apartment));
    }

    public Result updateApartmentPrice(Long id, Double price) {
        // TODO: update any fields except the id field
        if (price < 5) return badRequest("Price should be bigger than 5!");

        // TODO: business logic should be inside service layer
        Apartment apartment = apartmentService.findApartmentById(id);
        if (apartment == null) return badRequest("ID doesn't exist.");
        apartment.setPrice(price);
        Apartment updatedApartment = apartmentService.updateApartment(apartment);
        // TODO: use Optional

        return ok(Json.toJson(updatedApartment));
    }

}
