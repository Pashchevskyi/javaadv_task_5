package com.example.javaadv_task_5.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.example.javaadv_task_5.util.annotations.entity.Name;
import com.example.javaadv_task_5.util.annotations.entity.ToLowerCase;
import lombok.AllArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "users")
@AllArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String country;
    private String email;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id")
    private Set<Address> addresses = new HashSet<>();
    @OneToMany(mappedBy = "employee")
    @JsonManagedReference
    private Set<EmployeeWorkPlace> workPlaces = new HashSet<>();

    @Enumerated(EnumType.STRING)
    private Gender gender;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "passport_id", referencedColumnName = "id")
    private EmployeePassport workPass;
    private Boolean isDeleted = false;

    public Employee() {
    }

    public static class Builder {
        private Long id;
        @Name
        private String name;
        private String country;

        @ToLowerCase
        private String email;
        private Set<Address> addresses = new HashSet<>();
        private Set<EmployeeWorkPlace> workPlaces = new HashSet<>();
        private Gender gender;
        private EmployeePassport workPass;
        private Boolean isDeleted = false;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder country(String country) {
            this.country = country;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder addresses(Set<Address> addresses) {
            this.addresses = addresses;
            return this;
        }

        public Builder workPlaces(Set<EmployeeWorkPlace> workPlaces) {
            this.workPlaces = workPlaces;
            return this;
        }

        public Builder workPlace(EmployeeWorkPlace workPlace) {
            this.workPlaces.add(workPlace);
            return this;
        }

        public Builder gender(Gender gender) {
            this.gender = gender;
            return this;
        }

        public Builder workPass(EmployeePassport workPass) {
            this.workPass = workPass;
            return this;
        }

        public Builder isDeleted(Boolean deleted) {
            isDeleted = deleted;
            return this;
        }

        public Employee build() {
            return new Employee(this);
        }
    }

    private Employee(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.country = builder.country;
        this.email = builder.email;
        this.addresses = builder.addresses;
        this.gender = builder.gender;
        this.workPass = builder.workPass;
        this.isDeleted = builder.isDeleted;
        this.workPlaces = builder.workPlaces;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public EmployeePassport getWorkPass() {
        return workPass;
    }

    public void setWorkPass(EmployeePassport workPass) {
        this.workPass = workPass;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean deleted) {
        isDeleted = deleted;
    }
    public Employee addWorkPlace(EmployeeWorkPlace workPlace) {
        this.workPlaces.add(workPlace);
        return this;
    }
    public Employee removeWorkPlace(EmployeeWorkPlace workPlace) {
        this.workPlaces.remove(workPlace);
        return this;
    }

    public Set<EmployeeWorkPlace> getWorkPlaces() {
        return this.workPlaces;
    }

    public void setWorkPlaces(Set<EmployeeWorkPlace> employeeWorkPlaces) {
        this.workPlaces = employeeWorkPlaces;
    }
}
