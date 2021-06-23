package ru.job4j.accident.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class AccidentMem {
    private final Map<Integer, Accident> accidents = new HashMap<>();

    private final AtomicInteger id = new AtomicInteger(0);

    private final Map<Integer, AccidentType> types = new HashMap<>();

    private final AtomicInteger typeId = new AtomicInteger(0);

    private final Map<Integer, Rule> rules = new HashMap<>();

    private final AtomicInteger ruleId = new AtomicInteger(0);

    public AccidentMem() {
        accidents.put(id.incrementAndGet(),
                new Accident( "First Name", "First text", "First address"));
        accidents.put(id.incrementAndGet(),
                new Accident( "Second Name", "Second text", "Second address"));
        accidents.put(id.incrementAndGet(),
                new Accident( "Third Name", "Third text", "Third address"));

        types.put(typeId.incrementAndGet(), AccidentType.of(typeId.get(), "Две машины"));
        types.put(typeId.incrementAndGet(), AccidentType.of(typeId.get(), "Машина и человек"));
        types.put(typeId.incrementAndGet(), AccidentType.of(typeId.get(), "Машина и велосипед"));

        rules.put(ruleId.incrementAndGet(), Rule.of(ruleId.get(), "Статья. 1"));
        rules.put(ruleId.incrementAndGet(), Rule.of(ruleId.get(), "Статья. 2"));
        rules.put(ruleId.incrementAndGet(), Rule.of(ruleId.get(), "Статья. 3"));
    }

    public Collection<Accident> findAll() {
        return accidents.values();
    }

    public Collection<AccidentType> findAllTypes() {
        return types.values();
    }

    public Collection<Rule> findAllRules() {
        return rules.values();
    }

    public void create(Accident accident) {
        if (accident.getId() == 0) {
            accident.setId(id.incrementAndGet());
            accidents.put(id.get(), accident);
        } else {
            accidents.put(accident.getId(), accident);
        }
    }

    public Accident findById(int id) {
        return accidents.get(id);
    }

    public AccidentType findTypeById(int id) {
        return types.get(id);
    }

    public Rule findRuleById(int id) {
        return rules.get(id);
    }
}
