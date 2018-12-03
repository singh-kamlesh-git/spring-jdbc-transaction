package hello.hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

@Component
public class OrderItemJdbcTemplateDao implements Dao<OrderItem> {
    @Autowired
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    private void postConstruct() {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public long save(OrderItem orderItem) {
        String sql = "insert into ORDER_ITEM (ITEM, QTY) values (?, ?)";
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection)
                    throws SQLException {
                PreparedStatement ps = connection.prepareStatement(sql.toString(),
                        Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, orderItem.getItem());
                ps.setInt(2, orderItem.getQty());
                return ps;
            }
        }, holder);
        Number key = holder.getKey();
        if (key != null) {
            return key.longValue();
        }
        throw new RuntimeException("No generated primary key returned.");
    }

    @Override
    public OrderItem load(long id) {
        List<OrderItem> persons = jdbcTemplate.query("select * from Person where id =?",
                new Object[]{id}, (resultSet, i) -> {
                    return toOrderItem(resultSet);
                });

        if (persons.size() == 1) {
            return persons.get(0);
        }
        throw new RuntimeException("No item found for id: " + id);
    }

    @Override
    public int delete(long id) {
        return 0; //todo
    }

    @Override
    public int update(OrderItem customer) {
        return 0;//todo
    }

    @Override
    public List<OrderItem> loadAll() {
        return jdbcTemplate.query("select * from ORDER_ITEM", (resultSet, i) -> {
            return toOrderItem(resultSet);
        });
    }

    private OrderItem toOrderItem(ResultSet resultSet) throws SQLException {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(resultSet.getLong("ID"));
        orderItem.setItem(resultSet.getString("ITEM"));
        orderItem.setQty(resultSet.getInt("QTY"));
        return orderItem;
    }
}
