import dto.ApartmentRequest;
import models.Apartment;
import org.junit.Test;
import repos.ApartmentRepository;
import services.ApartmentService;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit testing does not require Play application start up.
 *
 * https://www.playframework.com/documentation/latest/JavaTest
 */

public class ApartmentServiceTests {

    public static ApartmentService apartmentService = new ApartmentService(new ApartmentRepository());

    @Test
    public void testApartmentService() {
        ApartmentRequest apartmentRequest = new ApartmentRequest();
        apartmentRequest.setName("Test Flat");
        apartmentRequest.setCategory("A");
        apartmentRequest.setDescription("This is a description string.");
        apartmentRequest.setSize(35);
        apartmentRequest.setPrice(30D);
        Apartment savedApartment = apartmentService.saveApartment(apartmentRequest);
        assertThat(savedApartment.getName()).isEqualTo("Test Flat");
        assertThat(savedApartment.getCategory()).isEqualTo("A");
        assertThat(savedApartment.getDescription()).isEqualTo("This is a description string.");
        assertThat(savedApartment.getSize()).isEqualTo(35);
        assertThat(savedApartment.getPrice()).isEqualTo(30D);

        Apartment foundApartment = apartmentService.findApartmentById(savedApartment.getId());
        assertThat(foundApartment.getId()).isEqualTo(savedApartment.getId());
        assertThat(foundApartment.getName()).isEqualTo("Test Flat");
        assertThat(foundApartment.getCategory()).isEqualTo("A");
        assertThat(foundApartment.getDescription()).isEqualTo("This is a description string.");
        assertThat(foundApartment.getSize()).isEqualTo(35);
        assertThat(foundApartment.getPrice()).isEqualTo(30D);
    }

}
