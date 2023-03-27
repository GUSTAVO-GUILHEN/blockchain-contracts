package com.blockchain.registry.repository;

import com.blockchain.registry.model.Contract;
import com.blockchain.registry.model.ContractStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ContractRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ContractRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Contract> findAll() {
        String sql = "SELECT * FROM contract";
        return jdbcTemplate.query(sql, new ContractRowMapper());
    }

    public Contract findById(Long id) {
        String sql = "SELECT * FROM contract WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new ContractRowMapper(), id);
    }

    public Contract save(Contract contract) {
        String sql = "INSERT INTO contract (id, title, description, status) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, contract.getId(), contract.getTitle(), contract.getDescription(), contract.getStatus());
        return contract;
    }

    public Contract update(Contract contract) {
        String sql = "UPDATE contract SET title = ?, description = ?, status = ? WHERE id = ?";
        jdbcTemplate.update(sql, contract.getTitle(), contract.getDescription(), contract.getStatus(), contract.getId());
        return contract;
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM contract WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    private static class ContractRowMapper implements RowMapper<Contract> {

        @Override
        public Contract mapRow(ResultSet resultSet, int i) throws SQLException {
            Contract contract = new Contract();
            contract.setId(resultSet.getLong("id"));
            contract.setTitle(resultSet.getString("title"));
            contract.setDescription(resultSet.getString("description"));
            contract.setStatus(ContractStatus.valueOf(resultSet.getString("status")));
            return contract;
        }
    }
}
