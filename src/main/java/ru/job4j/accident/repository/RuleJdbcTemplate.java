package ru.job4j.accident.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.Rule;
import ru.job4j.accident.model.RuleAccident;

import java.sql.PreparedStatement;
import java.util.Collection;

//@Repository
public class RuleJdbcTemplate {
    private final JdbcTemplate db;

    private final RowMapper<Rule> ruleRowMapper = (resultSet, i) -> {
        Rule rule = new Rule();
        rule.setId(resultSet.getInt("id"));
        rule.setName(resultSet.getString("name"));
        return rule;
    };

    public RuleJdbcTemplate(JdbcTemplate db) {
        this.db = db;
    }

    public Collection<Rule> findAll() {
        return db.query("select * from rule", ruleRowMapper);
    }

    public Collection<Rule> findByAccident(Accident accident) {
        return db.query("select rule.id, rule.name from rule " +
                "inner join rule_accident on rule.id = rule_accident.rule_id " +
                "where rule_accident.accident_id = ?", ruleRowMapper, accident.getId());
    }

    public Rule findById(int id) {
        return db.queryForObject("select * from rule where id = ?", ruleRowMapper, id);
    }

    public void deleteByAccident(Accident accident) {
        db.update("delete from rule_accident where accident_id = ?", accident.getId());
    }

    public void createRule(Rule rule) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "insert into rule(name) values (?)";
        db.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, rule.getName());
            return ps;
        }, keyHolder);
        if (keyHolder.getKey() != null) {
            rule.setId((Integer) keyHolder.getKey());
        }
    }

    public void createRuleAccident(RuleAccident ruleAccident) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "insert into rule_accident(accident_id, rule_id) values (?, ?)";
        db.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[] {"id"});
            ps.setInt(1, ruleAccident.getAccident().getId());
            ps.setInt(2, ruleAccident.getRule().getId());
            return ps;
        }, keyHolder);
        if (keyHolder.getKey() != null) {
            ruleAccident.setId((Integer) keyHolder.getKey());
        }
    }
}
