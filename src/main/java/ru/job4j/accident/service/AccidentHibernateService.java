package ru.job4j.accident.service;

import org.springframework.stereotype.Service;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;
import ru.job4j.accident.repository.AccidentHibernate;
import ru.job4j.accident.repository.AccidentTypeHibernate;
import ru.job4j.accident.repository.RuleHibernate;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class AccidentHibernateService {
    private final AccidentHibernate accidentHibernate;
    private final AccidentTypeHibernate accidentTypeHibernate;
    private final RuleHibernate ruleHibernate;

    public AccidentHibernateService(
            AccidentHibernate accidentHibernate,
            AccidentTypeHibernate accidentTypeHibernate,
            RuleHibernate ruleHibernate) {
        this.accidentHibernate = accidentHibernate;
        this.accidentTypeHibernate = accidentTypeHibernate;
        this.ruleHibernate = ruleHibernate;
    }

    public Collection<Accident> getAll() {
        return accidentHibernate.findAll();
    }

    public Collection<AccidentType> findAllTypes() {
        return accidentTypeHibernate.findAll();
    }

    public Collection<Rule> findAllRules() {
        return ruleHibernate.findAll();
    }

    public void createWithTypeAndRules(Accident accident, String typeId, String[] rIds) {
        accident.setType(accidentTypeHibernate.findById(Integer.parseInt(typeId)));
        if (rIds != null) {
            accident.setRules(Arrays.stream(rIds)
                    .map(Integer::parseInt)
                    .map(ruleHibernate::findById)
                    .collect(Collectors.toSet()));
        }
        accidentHibernate.createOrUpdate(accident);
    }

    public Accident findById(int id) {
        return accidentHibernate.findById(id);
    }
}
