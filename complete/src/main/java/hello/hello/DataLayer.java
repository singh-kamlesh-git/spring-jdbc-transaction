package hello.hello;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
@Service
public class DataLayer {
	@Autowired
    JdbcTemplate jdbcTemplate;
	
    private static final Logger log = LoggerFactory.getLogger(Application.class);
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int[] batchUpdate(List<Object[]> splitUpNames) {
		return null;
	}
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
	public int update1(Object[] arr) {
		jdbcTemplate.update("INSERT INTO customers(first_name, last_name) VALUES (?,?)", arr);
		return 0;
	}
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
	public int update2(Object[] arr) {
		jdbcTemplate.update("INSERT INTO customers(first_name, last_name) VALUES (?,?)", arr);
		return 0;
	}
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
	public int update4(Object[] arr) {
		jdbcTemplate.update("INSERT INTO customers(first_name, last_name) VALUES (?,?)", arr);
		return 0;
	}
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
	public int update3(Object[] arr) {
		jdbcTemplate.update("INSERT INTO customers(first_name, last_name) VALUES (?,?)", arr);
		throw new RuntimeException();
	}
	
	public void view() {
		jdbcTemplate.query(
                "SELECT id, first_name, last_name FROM customers WHERE first_name = ?", new Object[] { "Josh" },
                (rs, rowNum) -> new Customer(rs.getLong("id"), rs.getString("first_name"), rs.getString("last_name"))
        ).forEach(customer -> log.info(customer.toString()));
	}
}
