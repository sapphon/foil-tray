package org.sapphon.foiltray.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DevelopmentTeam {

    @Id
    @GeneratedValue
    private int id;

    private String name;
    @ManyToMany
    private List<Participant> members;
    @ManyToMany
    private List<Role> composition;

}
