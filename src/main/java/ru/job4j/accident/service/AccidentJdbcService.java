package ru.job4j.accident.service;

import org.springframework.stereotype.Service;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;
import ru.job4j.accident.repository.AccidentJdbcTemplate;
import ru.job4j.accident.repository.AccidentTypeJdbcTemplate;
import ru.job4j.accident.repository.RuleJdbcTemplate;


import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

//@Service
public class AccidentJdbcService {
    private final AccidentJdbcTemplate db;
    private final AccidentTypeJdbcTemplate typeDB;
    private final RuleJdbcTemplate ruleDB;

    public AccidentJdbcService(AccidentJdbcTemplate db, AccidentTypeJdbcTemplate typeDB, RuleJdbcTemplate ruleDB) {
        this.db = db;
        this.typeDB = typeDB;
        this.ruleDB = ruleDB;
    }

    public Collection<Accident> getAll() {
        return db.findAll();
    }

    public Collection<AccidentType> findAllTypes() {
        return typeDB.findAll();
    }

    public Collection<Rule> findAllRules() {
        return ruleDB.findAll();
    }

    public void createWithTypeAndRules(Accident accident, String typeId, String[] rIds) {
        accident.setType(typeDB.findById(Integer.parseInt(typeId)));
        if (rIds != null) {
            accident.setRules(Arrays.stream(rIds)
                    .map(Integer::parseInt)
                    .map(ruleDB::findById)
                    .collect(Collectors.toSet()));
        }
        db.createOrUpdate(accident);
    }

    public Accident findById(int id) {
        return db.findById(id);
    }
}