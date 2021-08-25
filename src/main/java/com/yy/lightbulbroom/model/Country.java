package com.yy.lightbulbroom.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@EqualsAndHashCode
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String countryName;

    @OneToMany(mappedBy = "country", fetch = FetchType.EAGER)
    private List<Room> roomList;

    public String toString() {
        return " " + this.getCountryName();
    }
}
