package fit.se.backend.dtos;

import com.neovisionaries.i18n.CountryCode;

import java.io.Serializable;

/**
 * DTO for {@link fit.se.backend.models.Address}
 */
public record AddressDto(
      Long id,
      String street,
      String city,
      CountryCode country,
      String number,
      String zipcode
) implements Serializable {
}