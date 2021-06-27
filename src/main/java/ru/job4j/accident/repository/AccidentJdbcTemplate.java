package ru.job4j.accident.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;
import ru.job4j.accident.model.RuleAccident;

import java.sql.PreparedStatement;
import java.sql.Types;
import java.util.Collection;
import java.util.HashSet;

//@Repository
public class AccidentJdbcTemplate {
    private final JdbcTemplate jdbc;

    private AccidentTypeJdbcTemplate accidentTypeJdbc;

    private RuleJdbcTemplate ruleJdbc;

    private final RowMapper<Accident> accidentRowMapper = (resultSet, i) -> {
        Accident accident = new Accident();
        accident.setId(resultSet.getInt("id"));
        accident.setName(resultSet.getString("name"));
        accident.setText(resultSet.getString("text"));
        accident.setAddress(resultSet.getString("address"));
        accident.setType(accidentTypeJdbc.findById(resultSet.getInt("type_id")));
        accident.setRules(new HashSet<>(ruleJdbc.findByAccident(accident)));
        return accident;
    };

    public AccidentJdbcTemplate(JdbcTemplate jdbc,
                                AccidentTypeJdbcTemplate accidentTypeJdbc,
                                RuleJdbcTemplate ruleJdbc) {
        this.jdbc = jdbc;
        this.accidentTypeJdbc = accidentTypeJdbc;
        this.ruleJdbc = ruleJdbc;
//        init();
    }

    private void init() {
        AccidentType accidentType1 = AccidentType.of("Две машины");
        AccidentType accidentType2 = AccidentType.of("Машина и человек");
        AccidentType accidentType3 = AccidentType.of("Машина и велосипед");

        accidentTypeJdbc.create(accidentType1);
        accidentTypeJdbc.create(accidentType2);
        accidentTypeJdbc.create(accidentType3);

        Rule rule1 = Rule.of("Статья. 1");
        Rule rule2 = Rule.of("Статья. 2");
        Rule rule3 = Rule.of("Статья. 3");

        ruleJdbc.createRule(rule1);
        ruleJdbc.createRule(rule2);
        ruleJdbc.createRule(rule3);
    }

    public Collection<Accident> findAll() {
        return jdbc.query("select * from accident", accidentRowMapper);
    }

    public void createOrUpdate(Accident accident) {
        if (accident.getId() == 0) {
            create(accident);
        } else {
            update(accident);
        }
    }

    private void create(Accident accident) {
        KeyHolder holder = new GeneratedKeyHolder();
        String sql = "insert into accident(name, text, address, type_id) VALUES (?, ?, ?, ?)";
        jdbc.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, accident.getName());
            ps.setString(2, accident.getText());
            ps.setString(3, accident.getAddress());
            if (accident.getType() == null) {
                ps.setNull(4, Types.INTEGER);
            } else {
                ps.setInt(4, accident.getType().getId());
            }
            return ps;
        }, holder);
        Number id = holder.getKey();
        if (id != null) {
            accident.setId((Integer) id);
            if (accident.getRules() != null) {
                for (Rule rule : accident.getRules()) {
                    ruleJdbc.createRuleAccident(new RuleAccident(accident, rule));
                }
            }
        }
    }

    private void update(Accident accident) {
        ruleJdbc.deleteByAccident(accident);
        jdbc.update(
                "update accident set name = ?, text = ?, address = ?, type_id = ? where id = ?",
                accident.getName(), accident.getText(), accident.getAddress(),
                (accident.getType() == null ? null : accident.getType().getId()),
                accident.getId()
        );
        if (accident.getRules() != null) {
            for (Rule rule : accident.getRules()) {
                ruleJdbc.createRuleAccident(new RuleAccident(accident, rule));
            }
        }
    }

    public Accident findById(int id) {
        return jdbc.queryForObject("select * from accident where id = ?", accidentRowMapper, id);
    }
}