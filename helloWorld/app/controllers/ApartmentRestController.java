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
        List<Apartment> apartments = apartmentService.getAllApartments();
        return ok(Json.toJson(apartments));
    }

    public Result getApartment(Long id) {
        Apartment apartment = apartmentService.findApartmentById(id);
        return ok(Json.toJson(apartment));
    }

    public Result addApartment(String name, String category, String description, Integer size, Double price) {
        ApartmentRequest request = new ApartmentRequest(name, category, description, size, price);
        Apartment apartment = apartmentService.saveApartment(request);
        return ok(Json.toJson(apartment));
    }

    public Result deleteApartmentById(Long id) {
        Apartment apartment = apartmentService.deleteApartmentById(id);
        return ok(Json.toJson(apartment));
    }

}
