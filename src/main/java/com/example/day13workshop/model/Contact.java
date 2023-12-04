package com.example.day13workshop.model;

import java.util.Date;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
// @NoArgsConstructor
@AllArgsConstructor
public class Contact {

    @NotEmpty(message="Please enter a name")
    @Size(min=3, max=64)
    private String name;

    @NotEmpty(message="Please enter an email")
    @Email(message="Please enter a valid email")
    private String email;

    @Pattern(regexp = "[0-9]{7,}",message="Invalid phone number")
    //7, -> at least 7 digits
    //notEmpty is not valid check for integer
    private String phone;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Past(message="Birth date must be a past date")
    private Date birthday;

    private String id;

    public Contact() {
        this.id = RandomStringUtils.randomAlphanumeric(8);
    }

}
