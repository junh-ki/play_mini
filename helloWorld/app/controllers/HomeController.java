package controllers;

import models.Apartment;
import play.mvc.*;

import views.html.*;

import javax.inject.Inject;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {

    private final AssetsFinder assetsFinder;

    @Inject
    public HomeController(AssetsFinder assetsFinder) {
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

        // Create new apartments
        Apartment a1 = new Apartment();
        a1.setId(1L);
        a1.setName("Munich Apartment");
        a1.setCategory("A");
        a1.setDescription("This is a nice apartment");
        a1.setSize(32);
        a1.setPrice(54.5);

        Apartment a2 = new Apartment(2L, "Stuttgart Apartment", "B", "This is a nice apartment", 32, 44.5);

        return ok(
                apartments.render(a2, assetsFinder)
        );
    }

}
