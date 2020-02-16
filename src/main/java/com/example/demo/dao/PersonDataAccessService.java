package com.example.demo.dao;

import com.example.demo.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("postgres")
public class PersonDataAccessService implements PersonDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    PersonDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int addPerson(UUID id, Person person) {
        final String sql = "INSERT INTO person (id, name) VALUES (?, ?)";
        final String name = person.getName();
        jdbcTemplate.update(sql, new Object[]{id, name});
        return 1;
    }

    @Override
    public List<Person> selectAllPersons() {
        final String sql = "SELECT id, name FROM person";
        return jdbcTemplate.query(sql, (resultSet, i) -> new Person(
            UUID.fromString(resultSet.getString("id")),
            resultSet.getString("name")
        ));
    }

    @Override
    public Optional<Person> selectPersonById(UUID id) {
        final String sql = "SELECT id, name FROM person WHERE id = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new Object[]{id}, (resultSet, i) -> new Person(
            UUID.fromString(resultSet.getString("id")),
            resultSet.getString("name")
        )));
    }

    @Override
    public int updatePerson(UUID id, Person person) {
        final String sql = "UPDATE person SET name = ? WHERE id::text = ?";
        final String name = person.getName();
        jdbcTemplate.update(sql, new Object[]{name, id.toString()});
        return 0;
    }

    @Override
    public int deletePerson(UUID id) {
        final String sql = "DELETE FROM person where id::text = ?";
        jdbcTemplate.update(sql, new Object[]{id.toString()});
        return 0;
    }
}
