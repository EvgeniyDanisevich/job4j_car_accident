package ru.job4j.accident.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class AccidentMem {
    private Map<Integer, Accident> accidents = new HashMap<>();

    private final AtomicInteger id = new AtomicInteger(0);

    public AccidentMem() {
        accidents.put(id.incrementAndGet(),
                new Accident(id.get(), "First Name", "First text", "First address"));
        accidents.put(id.incrementAndGet(),
                new Accident(id.get(), "Second Name", "Second text", "Second address"));
        accidents.put(id.incrementAndGet(),
                new Accident(id.get(), "Third Name", "Third text", "Third address"));
    }

    public Collection<Accident> findAll() {
        return accidents.values();
    }
}
