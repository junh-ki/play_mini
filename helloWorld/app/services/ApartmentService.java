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

    private final List<Apartment> apartments;

    @Inject
    public ApartmentService(ApartmentRepository apartmentRepository) {
        this.apartmentRepository = apartmentRepository;
        this.apartments = apartmentRepository.getAllApartments();
    }

    public Apartment findApartmentById(Long apartmentId) {
        Apartment apartment = apartments.stream().filter(a -> a.getId().longValue() == apartmentId.longValue())
                .iterator().next();
        return apartment;
    }

    public List<Apartment> getAllApartments() {
        return apartments;
    }

    public Apartment saveApartment(ApartmentRequest apartmentRequest) {
        Apartment apartment = apartmentRepository.addApartment(apartmentRequest);
        apartments.add(apartment);
        return apartment;
    }

    public Apartment deleteApartmentById(Long apartmentId) {
        Apartment apartmentToDelete = apartmentRepository.deleteApartmentById(apartmentId);
        Apartment deletedApartment = apartments.stream().filter(a -> a.getId().longValue() == apartmentToDelete.getId().longValue())
                .iterator().next();
        apartments.remove(deletedApartment);
        return apartmentToDelete;
    }

    public Apartment updateApartment(Apartment apartment) {
        Apartment apartmentToUpdate = apartmentRepository.updateApartment(apartment);
        Apartment updatedApartment = apartments.stream().filter(a -> a.getId().longValue() == apartmentToUpdate.getId().longValue())
                .iterator().next();
        return updateApp(apartmentToUpdate, updatedApartment);
    }

    private Apartment updateApp(Apartment prev, Apartment updated) {
        prev.setName(updated.getName());
        prev.setCategory(updated.getCategory());
        prev.setDescription(updated.getDescription());
        prev.setSize(updated.getSize());
        prev.setPrice(updated.getPrice());
        return prev;
    }

}
