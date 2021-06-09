package controllers;

import models.Apartment;
import play.mvc.*;

import services.ApartmentService;
import views.html.*;

import javax.inject.Inject;
import java.util.List;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {

    private final ApartmentService apartmentService;
    private final AssetsFinder assetsFinder;

    @Inject
    public HomeController(ApartmentService apartmentService, AssetsFinder assetsFinder) {
        this.apartmentService = apartmentService;
        this.assetsFinder = assetsFinder;
    }

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public Result index() {
        return ok(
            index.render(
                "Hello World!",
                assetsFinder
            ));
    }

    public Result about() {
        return ok(
                about.render(assetsFinder)
        );
    }

    public Result apartments() {
        List<Apartment> apartmentList = apartmentService.getAllApartments();
        return ok(apartments.render(apartmentList, assetsFinder));
    }

}
