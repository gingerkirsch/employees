package com.employees.app.model;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.NaturalId;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(uniqueConstraints={@UniqueConstraint(columnNames = {"username" , "email"})})
public class Employee {
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String username;
    private String password;
    private String firstName;
    private String lastName;

    @NotNull
    private String email;
    private float salary;
    private String image = "uploads/no-image.jpg";

}
