package services;

import dto.ApartmentRequest;
import models.Apartment;
import repos.ApartmentRepository;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.Optional;

@Singleton
public class ApartmentService {

    private final ApartmentRepository apartmentRepository;

    @Inject
    public ApartmentService(ApartmentRepository apartmentRepository) {
        this.apartmentRepository = apartmentRepository;
    }

    public Optional<Apartment> findApartmentById(Long apartmentId) {
        Apartment apartment = apartmentRepository.findApartmentById(apartmentId);
        return Optional.ofNullable(apartment);
    }

    public List<Apartment> getAllApartments() {
        return apartmentRepository.getAllApartments();
    }

    public List<Apartment> getApartments(int page, int size) {
        return apartmentRepository.getApartments(getOffset(page, size), size);
    }

    public Apartment addApartment(ApartmentRequest apartmentRequest) {
        Apartment addedApartment = apartmentRepository.addApartment(requestToApartment(apartmentRequest));
        return addedApartment;
    }

    public int deleteApartmentById(Long apartmentId) {
        return apartmentRepository.deleteApartmentById(apartmentId);
    }

    public Apartment updateApartment(ApartmentRequest apartmentRequest) {
        Apartment updatedApartment = apartmentRepository.updateApartment(requestToApartment(apartmentRequest));
        return updatedApartment;
    }

    // *** sub methods ***

    private int getOffset(int page, int size) {
        return (page - 1) * size;
    }

    private Apartment requestToApartment(ApartmentRequest apartmentRequest) {
        Apartment apartment = new Apartment();
        if (apartmentRequest.getId() != null) apartment.setId(apartmentRequest.getId());
        if (apartmentRequest.getName() != null) apartment.setName(apartmentRequest.getName());
        if (apartmentRequest.getCategory() != null) apartment.setCategory(apartmentRequest.getCategory());
        if (apartmentRequest.getDescription() != null) apartment.setDescription(apartmentRequest.getDescription());
        if (apartmentRequest.getSize() != null) apartment.setSize(apartmentRequest.getSize());
        if (apartmentRequest.getPrice() != null) apartment.setPrice(apartmentRequest.getPrice());
        return apartment;
    }

}
