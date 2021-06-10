package services;

import dto.ApartmentRequest;
import io.ebean.Ebean;
import io.ebean.EbeanServer;
import models.Apartment;
import repos.ApartmentRepository;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

@Singleton
public class ApartmentService {

    private final ApartmentRepository apartmentRepository;
    private final EbeanServer server;

    @Inject
    public ApartmentService(ApartmentRepository apartmentRepository) {
        this.apartmentRepository = apartmentRepository;
        this.server = Ebean.getDefaultServer();
    }

    public Apartment findApartmentById(Long apartmentId) {
        return apartmentRepository.find.byId(apartmentId);
    }

    public List<Apartment> getAllApartments() {
        return apartmentRepository.find.all();
    }

    public Apartment saveApartment(ApartmentRequest apartmentRequest) {
        Long nextId = (Long) Ebean.nextId(Apartment.class);
        Apartment apartment = new Apartment(nextId, apartmentRequest.getName(), apartmentRequest.getCategory(),
                apartmentRequest.getDescription(), apartmentRequest.getSize(), apartmentRequest.getPrice());
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
