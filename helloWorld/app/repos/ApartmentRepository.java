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
        // TODO: Implement Ebean Query
        // finder.q
        // Ebean.createQuery(Apartment.class, "");
        // 1 2 4 5 7 // Limit,  where category equals C
        // ascending or descending order.
        // random order // specify the offset. (apartment from 20 to ~)
        // select * from apartments where category=‘C’ offset 10 limit 5;
        // select * from apartments where category=‘C’ order by created_date DESC offset 10 limit 5;
        // offset will be the current page + and limit will be your page size.
        return finder.all();
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
