package com.sparta.plan.repository;

import com.sparta.plan.dto.PlanResponseDto;
import com.sparta.plan.entity.Plan;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@Repository
public class PlanRepository {
    private final JdbcTemplate jdbcTemplate;

    public PlanRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Plan save(Plan plan) {
        //DB 저장
        KeyHolder keyHolder = new GeneratedKeyHolder(); // 기본 키를 반환받기 위한 객체

        String sql = "INSERT INTO plan (toDo, username, password, createdDate, updatedDate) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(con -> { //insert+update+delete=update
                    PreparedStatement preparedStatement = con.prepareStatement(sql,
                            Statement.RETURN_GENERATED_KEYS);

                    preparedStatement.setString(1, plan.getToDo()); //첫번째 물음표 1
                    preparedStatement.setString(2, plan.getUsername()); //첫번째 물음표 2
                    preparedStatement.setString(3, plan.getPassword()); //첫번째 물음표 3
                    preparedStatement.setString(4, String.valueOf(plan.getCreatedDate())); //첫번째 물음표 4
                    preparedStatement.setString(5, String.valueOf(plan.getUpdatedDate())); //첫번째 물음표 5
                    return preparedStatement;
                },
                keyHolder);

        // DB Insert 후 받아온 기본키 확인
        Long id = keyHolder.getKey().longValue();
        plan.setId(id);

        return plan;
    }

    public void update(Long id, Plan plan) {
        String sql = "UPDATE plan SET toDo = ?, username = ?, updatedDate = ?  WHERE id = ?";
        jdbcTemplate.update(sql, plan.getToDo(), plan.getUsername(), plan.getUpdatedDate(), id);
    }

    public void delete(Long id, String password) {
        String sql = "DELETE FROM plan WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }


    public List<PlanResponseDto> findByUsername(String username) {
        String sql = "SELECT * FROM plan WHERE username = ?";

        //select
        return jdbcTemplate.query(sql, new Object[]{username}, new RowMapper<PlanResponseDto>() {
            @Override
            public PlanResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                // SQL 의 결과로 받아온 Memo 데이터들을 ResponseDto 타입으로 변환해줄 메서드
                String toDo = rs.getString("toDo");
                String username = rs.getString("username");
                String password = rs.getString("password");
                // 날짜를 LocalDateTime으로 변환
                String createdDateString = rs.getString("createdDate");
                String updatedDateString = rs.getString("updatedDate");

                LocalDateTime createdDate = createdDateString != null ? LocalDateTime.parse(createdDateString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : null;
                LocalDateTime updatedDate = updatedDateString != null ? LocalDateTime.parse(updatedDateString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : null;
                return new PlanResponseDto(toDo, username, createdDate, updatedDate);
            }
        });
    }

    public List<PlanResponseDto> findAll() {
        // DB 조회
        String sql = "SELECT * FROM plan ORDER BY updatedDate DESC";
        //select
        return jdbcTemplate.query(sql, new RowMapper<PlanResponseDto>() {
            @Override
            public PlanResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                String toDo = rs.getString("toDo");
                String username = rs.getString("username");
                String password = rs.getString("password");
                // 날짜를 LocalDateTime으로 변환
                String createdDateString = rs.getString("createdDate");
                String updatedDateString = rs.getString("updatedDate");

                LocalDateTime createdDate = createdDateString != null ? LocalDateTime.parse(createdDateString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : null;
                LocalDateTime updatedDate = updatedDateString != null ? LocalDateTime.parse(updatedDateString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : null;
                return new PlanResponseDto(toDo, username, createdDate, updatedDate);
            }
        });
    }


    public Plan findById(Long id) {
        String sql = "SELECT * FROM plan WHERE id = ?";

        return jdbcTemplate.query(sql, resultSet -> {
            if (resultSet.next()) {
                Plan plan = new Plan();
                plan.setToDo(resultSet.getString("toDo"));
                plan.setUsername(resultSet.getString("username"));
                plan.setPassword(resultSet.getString("password"));

                // 날짜 및 시간 문자열을 LocalDateTime으로 변환
                String createdDateString = resultSet.getString("createdDate");
                String updatedDateString = resultSet.getString("updatedDate");

                DateTimeFormatter fullFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); // 시간 포함 형식

                try {
                    LocalDateTime createdDateTime = createdDateString != null && !createdDateString.isEmpty()
                            ? LocalDateTime.parse(createdDateString, fullFormatter)
                            : null;
                    LocalDateTime updatedDateTime = updatedDateString != null && !updatedDateString.isEmpty()
                            ? LocalDateTime.parse(updatedDateString, fullFormatter)
                            : null;

                    // LocalDateTime을 그대로 저장
                    plan.setCreatedDate(createdDateTime);
                    plan.setUpdatedDate(updatedDateTime);
                } catch (DateTimeParseException e) {
                    // 날짜 형식이 잘못된 경우 처리
                    plan.setCreatedDate(null); // 또는 적절한 기본값 설정
                    plan.setUpdatedDate(null); // 또는 적절한 기본값 설정
                }

                return plan;
            } else {
                return null;
            }
        }, id);
    }
}