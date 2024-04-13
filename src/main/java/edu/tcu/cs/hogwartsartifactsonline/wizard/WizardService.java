package edu.tcu.cs.hogwartsartifactsonline.wizard;

import jakarta.transaction.Transactional;

import java.util.List;

import org.springframework.stereotype.Service;

import edu.tcu.cs.hogwartsartifactsonline.system.exception.ObjectNotFoundException;

@Service
@Transactional
public class WizardService {
    
    private final WizardRepository wizardRepository;

    public WizardService(WizardRepository wizardRepository) { this.wizardRepository = wizardRepository; }

    public List<Wizard> findAll() { return this.wizardRepository.findAll(); }

    
    public Wizard findById(Integer wizardId) {
        return this.wizardRepository.findById(wizardId)
            .orElseThrow(() -> new ObjectNotFoundException("wizard", wizardId));
    }

    public Wizard save(Wizard newWizard) { return this.wizardRepository.save(newWizard); }

    public Wizard update(Integer wizardId, Wizard update) {
        return this.wizardRepository.findById(wizardId)
            .map(oldWizard -> {
                oldWizard.setName(update.getName());
                return this.wizardRepository.save(oldWizard);
            })
            .orElseThrow(() -> new ObjectNotFoundException("wizard", wizardId));
    }

    public void delete(Integer wizardId) {
        Wizard wizardToBeDeleted = this.wizardRepository.findById(wizardId)
            .orElseThrow(() -> new ObjectNotFoundException("wizard", wizardId));

        // pre-deletion, we unassign owned artifacts
        wizardToBeDeleted.removeAllArtifact();
        this.wizardRepository.deleteById(wizardId);
    }
}
