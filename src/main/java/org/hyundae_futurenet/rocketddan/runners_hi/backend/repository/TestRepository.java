package org.hyundae_futurenet.rocketddan.runners_hi.backend.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TestRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public String now() {
        return jdbcTemplate.queryForObject("SELECT TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS') FROM DUAL", String.class);
    }
}
