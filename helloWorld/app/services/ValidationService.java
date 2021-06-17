package services;

import dto.ApartmentRequest;
import dto.ValidationError;
import models.Apartment;
import repos.ApartmentRepository;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ValidationService {

    private ApartmentService apartmentService;

    @Inject
    public ValidationService(ApartmentService apartmentService) {
        this.apartmentService = apartmentService;
    }

    public List<ValidationError> validateAddRequest(ApartmentRequest apartmentRequest) {
        List<ValidationError> errors = new ArrayList<ValidationError>();
        if (apartmentRequest.getId() != null) errors.add(new ValidationError("id", "ID is not required!"));
        if (apartmentRequest.getName() == null) errors.add(new ValidationError("name", "Name should be given!"));
        if (apartmentRequest.getCategory() == null) {
            errors.add(new ValidationError("category", "Category should be given!"));
        } else {
            String category = apartmentRequest.getCategory();
            if (!category.equals("A") && !category.equals("B") && !category.equals("C")) {
                errors.add(new ValidationError("category", "Category should either be \"A\" or \"B\" or \"C\"!"));
            }
        }
        if (apartmentRequest.getDescription() == null) errors.add(new ValidationError("description", "Description should be given!"));
        if (apartmentRequest.getSize() == null) {
            errors.add(new ValidationError("size", "Size should be given!"));
        } else {
            if (apartmentRequest.getSize() < 10) errors.add(new ValidationError("size", "Size should not be smaller than 10!"));
        }
        if (apartmentRequest.getPrice() == null) {
            errors.add(new ValidationError("price", "Price should be given!"));
        } else {
            if (apartmentRequest.getPrice() < 5) errors.add(new ValidationError("price", "Price should not be smaller than 5!"));
        }
        return errors;
    }

    public List<ValidationError> validateUpdateRequest(ApartmentRequest apartmentRequest) {
        List<ValidationError> errors = new ArrayList<ValidationError>();
        if (apartmentRequest.getId() != null) {
            Optional<Apartment> nullableApartment = apartmentService.findApartmentById(apartmentRequest.getId());
            if (nullableApartment.isEmpty()) errors.add(new ValidationError("id", "The passed ID is not found!"));
        } else {
            errors.add(new ValidationError("id", "ID must be given!"));
        }
        if (apartmentRequest.getCategory() != null) {
            String category = apartmentRequest.getCategory();
            if (!category.equals("A") && !category.equals("B") && !category.equals("C")) {
                errors.add(new ValidationError("category", "Category should either be 'A' or 'B' or 'C'!"));
            }
        }
        if (apartmentRequest.getSize() != null) {
            if (apartmentRequest.getSize() < 10) errors.add(new ValidationError("size", "Size should not be smaller than 10!"));
        }
        if (apartmentRequest.getPrice() != null) {
            if (apartmentRequest.getPrice() < 5) errors.add(new ValidationError("price", "Price should not be smaller than 5!"));
        }
        return errors;
    }

}
