package com.birthdayemail.userdetails.model;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Person {
    private String firstName;
    private String lastName;
    private String bloodGroup;
    private Long mobileNumber;
    private String emailId;
    private Date dayMonth;
}
