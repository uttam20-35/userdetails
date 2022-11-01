package com.birthdayemail.userdetails.entity;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity(name="person_details")
@Table(name="person_details")
public class PersonEntity {
    @Id
    @SequenceGenerator(name = "seq_person_id", sequenceName = "seq_person_id", initialValue = 100, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_person_id")
    @Column(name = "id", nullable = false)
    private Long id;
    private String firstName;
    private String lastName;
    private String bloodGroup;
    private Long mobileNumber;
    private String emailId;
    @Temporal(TemporalType.DATE)
    private Date dayMonth;
}
