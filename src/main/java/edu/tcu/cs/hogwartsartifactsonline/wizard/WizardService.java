package edu.tcu.cs.hogwartsartifactsonline.wizard;

import jakarta.transaction.Transactional;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
@Transactional
public class WizardService {
    
    private final WizardRepository wizardRepository;

    public WizardService(WizardRepository wizardRepository) { this.wizardRepository = wizardRepository; }

    public List<Wizard> findAll() { return this.wizardRepository.findAll(); }

    
    public Wizard findById(Integer wizardId) {
        return this.wizardRepository.findById(wizardId)
            .orElseThrow(() -> new WizardNotFoundException(wizardId));
    }

    public Wizard save(Wizard newWizard) { return this.wizardRepository.save(newWizard); }

    public Wizard update(Integer wizardId, Wizard update) {
        return this.wizardRepository.findById(wizardId)
            .map(oldWizard -> {
                oldWizard.setName(update.getName());
                return this.wizardRepository.save(oldWizard);
            })
            .orElseThrow(() -> new WizardNotFoundException(wizardId));
    }

    public void delete(Integer wizardId) {
        Wizard wizardToBeDeleted = this.wizardRepository.findById(wizardId)
            .orElseThrow(() -> new WizardNotFoundException(wizardId));

        // pre-deletion, we unassign owned artifacts
        wizardToBeDeleted.removeAllArtifact();
        this.wizardRepository.deleteById(wizardId);
    }
}
