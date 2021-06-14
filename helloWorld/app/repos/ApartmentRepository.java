package repos;

import dto.ApartmentRequest;
import io.ebean.Ebean;
import io.ebean.EbeanServer;
import io.ebean.Finder;
import models.Apartment;

import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ApartmentRepository {

    public static Finder<Long, Apartment> finder = new Finder<>(Apartment.class);
    private final EbeanServer server;

    @Inject
    public ApartmentRepository() {
        this.server = Ebean.getDefaultServer();
    }

    public Apartment findApartmentById(Long apartmentId) {
        return finder.byId(apartmentId);
    }

    public List<Apartment> getAllApartments() {
        List<Apartment> apartments = finder.all();
        return apartments;
    }

    public Apartment addApartment(ApartmentRequest apartmentRequest) {
        Apartment apartment = new Apartment();
        apartment.setName(apartmentRequest.getName());
        apartment.setCategory(apartmentRequest.getCategory());
        apartment.setDescription(apartmentRequest.getDescription());
        apartment.setSize(apartmentRequest.getSize());
        apartment.setPrice(apartmentRequest.getPrice());
        server.save(apartment);
        return apartment;
    }

    public Apartment deleteApartmentById(Long apartmentId) {
        // TODO: use server.delete()
        Apartment apartment = finder.byId(apartmentId);
        if (apartment != null) Ebean.delete(apartment);
        // TODO: int result = server.delete(Apartment.class, apartmentId);
        // TODO: number of rows affected 0: apartment wasn't found. provided apartmentId was wrong / 1: apartment deleted / >1: multiple apartment has the same id (we are doing auto-increment)
        return apartment;
    }

    public Apartment updateApartment(Apartment apartment) {
        server.update(apartment);
        return apartment;
    }

}
