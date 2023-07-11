package com.example.javaadv_task_5.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import lombok.Setter;

@Entity
@Table(name = "users")
@AllArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String country;
    private String email;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id")
    private Set<Address> addresses = new HashSet<>();
    @ManyToMany
    @JoinTable(name = "users_work_places",
        joinColumns = @JoinColumn(name = "employees_id"),
        inverseJoinColumns = @JoinColumn(name = "work_places_id")
    )
    private Set<WorkPlace> workPlaces = new HashSet<>();

    @Enumerated(EnumType.STRING)
    private Gender gender;
    private Boolean isDeleted = false;

    public static class Builder {
        private Integer id;
        private String name;
        private String country;
        private String email;
        private Set<Address> addresses = new HashSet<>();
        private Set<WorkPlace> workPlaces = new HashSet<>();
        private Gender gender;
        private Boolean isDeleted = false;

        public Builder id(Integer id) {
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

        public Builder workPlaces(Set<WorkPlace> workPlaces) {
            this.workPlaces = workPlaces;
            return this;
        }

        public Builder workPlace(WorkPlace workPlace) {
            this.workPlaces.add(workPlace);
            return this;
        }

        public Builder gender(Gender gender) {
            this.gender = gender;
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

    public Employee() {

    }

    private Employee(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.country = builder.country;
        this.email = builder.email;
        this.addresses = builder.addresses;
        this.gender = builder.gender;
        this.isDeleted = builder.isDeleted;
        this.workPlaces = builder.workPlaces;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean deleted) {
        isDeleted = deleted;
    }
    public Employee addWorkPlace(WorkPlace workPlace) {
        this.workPlaces.add(workPlace);
        return this;
    }
    public Employee removeWorkPlace(WorkPlace workPlace) {
        this.workPlaces.remove(workPlace);
        return this;
    }

    public Set<WorkPlace> getWorkPlaces() {
        return this.workPlaces;
    }
}
