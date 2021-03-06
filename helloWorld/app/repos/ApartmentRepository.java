package repos;

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
        return server.find(Apartment.class, apartmentId);
    }

    public List<Apartment> getAllApartments() {
        return finder.all();
    }

    public List<Apartment> getApartments(int offset, int size) {
        List<Apartment> apartments = finder.nativeSql("select * from apartment order by id asc limit " + size + " offset " + offset).findList();
        return apartments;
    }

    public Apartment addApartment(Apartment apartment) {
        server.save(apartment);
        return apartment;
    }

    public int deleteApartmentById(Long apartmentId) {
        int deleteResult = server.delete(Apartment.class, apartmentId);
        return deleteResult;
    }

    public Apartment updateApartment(Apartment apartment) {
        server.update(apartment);
        return apartment;
    }

}
