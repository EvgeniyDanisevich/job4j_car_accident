package ru.job4j.accident.service;

import org.springframework.stereotype.Service;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;
import ru.job4j.accident.repository.AccidentMem;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AccidentService {
    private final AccidentMem accidentMem;

    public AccidentService(AccidentMem accidentMem) {
        this.accidentMem = accidentMem;
    }

    public Collection<Accident> findAll() {
        return accidentMem.findAll();
    }

    public Collection<AccidentType> findAllTypes() {
        return accidentMem.findAllTypes();
    }

    public Collection<Rule> findAllRules() {
        return accidentMem.findAllRules();
    }

    public void create(Accident accident) {
        accidentMem.create(accident);
    }

    public Accident findById(int id) {
        return accidentMem.findById(id);
    }

    public AccidentType findTypeById(int id) {
        return accidentMem.findTypeById(id);
    }

    public Rule findRuleById(int id) {
        return accidentMem.findRuleById(id);
    }

    public void createWithTypeAndRules(Accident accident, String typeId, String[] ids) {
        accident.setType(findTypeById(Integer.parseInt(typeId)));
        if (ids != null) {
            accident.setRules(generateRules(ids));
        }
        create(accident);
    }

    private Set<Rule> generateRules(String[] ids) {
        return Arrays.stream(ids)
                .map(Integer::parseInt)
                .map(this::findRuleById)
                .collect(Collectors.toSet());
    }
}
