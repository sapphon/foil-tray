package org.sapphon.foiltray.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@Entity
@Data
@NoArgsConstructor
public class Participant {
    @Id
    @GeneratedValue
    int id;

    @ManyToOne
    Role role;

    String participantName;
}
