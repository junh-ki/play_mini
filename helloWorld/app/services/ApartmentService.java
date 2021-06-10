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

    @Inject
    public ApartmentService(ApartmentRepository apartmentRepository) {
        this.apartmentRepository = apartmentRepository;
    }

    public Apartment findApartmentById(Long apartmentId) {
        return apartmentRepository.find.byId(apartmentId);
    }

    public List<Apartment> getAllApartments() {
        return apartmentRepository.find.all();
    }

    public Apartment saveApartment(ApartmentRequest apartmentRequest) {
        return apartmentRepository.addApartment(apartmentRequest);
    }

    public Apartment deleteApartmentById(Long apartmentId) {
        return apartmentRepository.deleteApartmentById(apartmentId);
    }

    public Apartment updateApartment(Apartment apartment) {
        return apartmentRepository.updateApartment(apartment);
    }

}
