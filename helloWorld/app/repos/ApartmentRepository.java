package repos;

import dto.ApartmentRequest;
import io.ebean.Ebean;
import io.ebean.EbeanServer;
import io.ebean.Finder;
import models.Apartment;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ApartmentRepository {

    public static Finder<Long, Apartment> find = new Finder<>(Apartment.class);
    private final EbeanServer server;

    @Inject
    public ApartmentRepository() {
        this.server = Ebean.getDefaultServer();
    }

    public Apartment addApartment(ApartmentRequest apartmentRequest) {
        Apartment apartment = new Apartment();
        apartment.setName(apartmentRequest.getName());
        apartment.setCategory(apartmentRequest.getCategory());
        apartment.setDescription(apartment.getDescription());
        apartment.setSize(apartmentRequest.getSize());
        apartment.setPrice(apartmentRequest.getPrice());
        server.save(apartment);
        return apartment;
    }

    public Apartment deleteApartmentById(Long apartmentId) {
        Apartment apartment = Ebean.find(Apartment.class, apartmentId);
        Ebean.delete(apartment);
        return apartment;
    }

    public Apartment updateApartment(Apartment apartment) {
        server.save(apartment);
        return apartment;
    }

}
