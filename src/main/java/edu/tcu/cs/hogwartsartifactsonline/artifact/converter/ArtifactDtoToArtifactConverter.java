package edu.tcu.cs.hogwartsartifactsonline.artifact.converter;

import edu.tcu.cs.hogwartsartifactsonline.artifact.Artifact;
import edu.tcu.cs.hogwartsartifactsonline.artifact.dto.ArtifactDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component //spring will handle this class. create an instance and inject it
public class ArtifactDtoToArtifactConverter implements Converter<ArtifactDto, Artifact>{

    @Override
    public Artifact convert(ArtifactDto source) {

        Artifact artifact = new Artifact();
        artifact.setId(source.id()); //just access by .id, dont need getters bc its source
        artifact.setName(source.name());
        artifact.setDescription(source.description());
        artifact.setImageUrl(source.imageUrl());
        return artifact;
    }
}