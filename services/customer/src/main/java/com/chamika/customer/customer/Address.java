package com.chamika.customer.customer;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Validated
public class Address {

    @NotBlank(message = "Street cannot be blank")
    private String street;

    @NotBlank(message = "House number cannot be blank")
    private String houseNumber;

    @NotBlank(message = "Zip-Code cannot be blank")
    private String zipCode;
}
