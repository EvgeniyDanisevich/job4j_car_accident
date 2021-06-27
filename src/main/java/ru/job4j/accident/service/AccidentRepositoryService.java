package ru.job4j.accident.service;

import org.springframework.stereotype.Service;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;
import ru.job4j.accident.repository.AccidentRepository;
import ru.job4j.accident.repository.RuleRepository;
import ru.job4j.accident.repository.TypeRepository;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class AccidentRepositoryService {
    private final AccidentRepository accidentRepository;
    private final TypeRepository typeRepository;
    private final RuleRepository ruleRepository;

    public AccidentRepositoryService(AccidentRepository accidentRepository,
                                        TypeRepository typeRepository,
                                        RuleRepository ruleRepository) {
        this.accidentRepository = accidentRepository;
        this.typeRepository = typeRepository;
        this.ruleRepository = ruleRepository;

    }

    public Collection<Accident> getAll() {
        return (Collection<Accident>) accidentRepository.findAll();
    }

    public Collection<AccidentType> findAllTypes() {
        return (Collection<AccidentType>) typeRepository.findAll();
    }

    public Collection<Rule> findAllRules() {
        return (Collection<Rule>) ruleRepository.findAll();
    }

    public void createWithTypeAndRules(Accident accident, String typeId, String[] rIds) {
        accident.setType(typeRepository.findById(Integer.parseInt(typeId)).get());
        if (rIds != null) {
            accident.setRules(Arrays.stream(rIds)
                    .map(Integer::parseInt)
                    .map(id -> ruleRepository.findById(id).get())
                    .collect(Collectors.toSet()));
        }
        accidentRepository.save(accident);
    }

    public Accident findById(int id) {
        return accidentRepository.findById(id).get();
    }
}
