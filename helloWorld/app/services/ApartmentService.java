package services;

import dto.ApartmentRequest;
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
        return apartmentRepository.findApartmentById(apartmentId);
    }

    public List<Apartment> getAllApartments() {
        return apartmentRepository.getAllApartments();
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
