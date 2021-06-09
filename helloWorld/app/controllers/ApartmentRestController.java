package controllers;

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

}
