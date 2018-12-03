package hello.hello;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface OrderService {

    void persistOrders(List<OrderItem> orderItems) throws Exception;

    List<OrderItem> getAllOrders();
}
