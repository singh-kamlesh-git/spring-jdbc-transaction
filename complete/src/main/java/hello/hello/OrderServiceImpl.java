package hello.hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private Dao<OrderItem> dao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void persistOrders(List<OrderItem> orderItems) throws Exception {
        for (OrderItem orderItem : orderItems) {
            if (orderItem.getQty() > 100) {
                throw new Exception(
                        "Order quantity cannot be more than 100, found: "
                                + orderItem.getQty());
            }
            long id = dao.save(orderItem);
            System.out.println("id generated: " + id);
        }
    }

    @Override
    public List<OrderItem> getAllOrders() {
        return dao.loadAll();
    }
}