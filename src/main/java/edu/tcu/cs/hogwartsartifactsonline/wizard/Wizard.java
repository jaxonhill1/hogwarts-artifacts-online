package edu.tcu.cs.hogwartsartifactsonline.wizard;

import edu.tcu.cs.hogwartsartifactsonline.artifact.Artifact;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Wizard implements Serializable {
    @Id
    private Integer id;

    private String name;

    //Wizard giving up maintaining artifacts. Many side is responsible for storing artifacts
    //if we save one wizard in the database using WizzRepository, all associating artifacts will be saved too
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "owner")  //one wizard has many artifacts
    private List<Artifact> artifacts = new ArrayList<>(); //starting with an empty list upon initialization

    public Wizard() {
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

    public List<Artifact> getArtifacts() {
        return artifacts;
    }

    public void setArtifacts(List<Artifact> artifacts) {
        this.artifacts = artifacts;
    }

    //establishing bidirectional relationship btwn artifacts and wizards
    public void addArtifact(Artifact artifact) {
        //set owner
        artifact.setOwner(this);
        //add to wizard's ownership field
        this.artifacts.add(artifact);
    }

    public Integer getNumberOfArtifacts() {
        return this.artifacts.size();
    }
}
