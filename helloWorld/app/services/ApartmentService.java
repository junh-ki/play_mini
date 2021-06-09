package services;

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

    public Apartment saveApartment(Apartment apartment) {
        return null;
    }

    public Apartment findApartmentById(Long apartmentId) {
        return apartmentRepository.find.byId(apartmentId);
    }

    public List<Apartment> getAllApartments() {
        return apartmentRepository.find.all();
    }

    public List<Apartment> updateApartment(Apartment apartment) {
        return null;
    }

    public void deleteApartment(Apartment apartment) {

    }

}
