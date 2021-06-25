package ru.job4j.accident.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.AccidentType;

import java.util.Collection;

@Repository
public class AccidentTypeJdbcTemplate {
    private final JdbcTemplate db;

    private final RowMapper<AccidentType> accidentTypeRowMapper = (resultSet, i) -> {
        AccidentType accidentType = new AccidentType();
        accidentType.setId(resultSet.getInt("id"));
        accidentType.setName(resultSet.getString("name"));
        return accidentType;
    };

    public AccidentTypeJdbcTemplate(JdbcTemplate db) {
        this.db = db;
    }

    public Collection<AccidentType> findAll() {
        return db.query("select * from type", accidentTypeRowMapper);
    }

    public AccidentType findById(int id) {
        return db.queryForObject("select * from type where id = ?", accidentTypeRowMapper, id);
    }

    public void  create(AccidentType accidentType) {
        db.update("insert into type (name) values (?)", accidentType.getName());
    }
}
