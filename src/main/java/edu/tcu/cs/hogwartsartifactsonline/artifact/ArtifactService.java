package edu.tcu.cs.hogwartsartifactsonline.artifact;

import edu.tcu.cs.hogwartsartifactsonline.artifact.utils.IdWorker;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional //each method has own transaction
public class ArtifactService {

    private final ArtifactRepository artifactRepository;
    private final IdWorker idWorker;

    //Constructor
    public ArtifactService(ArtifactRepository artifactRepository, IdWorker idWorker) {
        this.artifactRepository = artifactRepository;
        this.idWorker = idWorker;
    }

    public Artifact findById(String artifactId){
        return this.artifactRepository.findById(artifactId) //return if found
                .orElseThrow(() -> new ArtifactNotFoundException(artifactId)); //throw exception if not found
    }


    //returns an empty list if no artifact is present. due to the artifact List declaration, we avoid null pointer error
    public List<Artifact> findAll(){
        return this.artifactRepository.findAll();
    }

    public Artifact save(Artifact newArtifact){
        newArtifact.setId(idWorker.nextId() + "");
        return this.artifactRepository.save(newArtifact);

    }

    public Artifact update(String artifactId, Artifact update){
            return this.artifactRepository.findById(artifactId)
                .map(oldArtifact ->{ //if found, run the below
                    oldArtifact.setName(update.getName());
                    oldArtifact.setDescription((update.getDescription()));
                    oldArtifact.setImageUrl(update.getImageUrl());
                    return this.artifactRepository.save(oldArtifact);
                })
                .orElseThrow(() -> new ArtifactNotFoundException(artifactId)); //if not found, throw exception



    }

    public void delete(String artifactId){
        this.artifactRepository.findById(artifactId)
                .orElseThrow(() -> new ArtifactNotFoundException(artifactId));
        this.artifactRepository.deleteById(artifactId);


    }


}
