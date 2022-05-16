package com.readingisgood.Service.Service;

import com.readingisgood.Service.Data.DTO.BookOrder;
import com.readingisgood.Service.Data.Entity.Book;
import com.readingisgood.Service.Data.Entity.Order;
import com.readingisgood.Service.Data.Repository.IOrderRepository;
import com.readingisgood.Service.Exception.RigCustomeException;
import com.readingisgood.Service.Model.Request.OrderBookRequest;
import com.readingisgood.Service.Model.Response.Customer.CustomerOrdersResponse;
import com.readingisgood.Service.Model.Response.Order.OrderBookResponse;
import com.readingisgood.Service.Model.Response.Order.QueryOrderResponse;
import com.readingisgood.Service.Service.Interface.IBookService;
import com.readingisgood.Service.Service.Interface.ICustomerService;
import com.readingisgood.Service.Service.Interface.IOrderService;
import com.readingisgood.Service.Util.Dater;
import lombok.RequiredArgsConstructor;
import org.javers.spring.annotation.JaversAuditable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OrderService implements IOrderService {
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);
    private final IOrderRepository orderRepository;
    private final ICustomerService customerService;
    private final IBookService bookService;
    private final ReentrantLock reentrantLock = new ReentrantLock();

    /**
     * Gets orders of a customer
     * @param customerId customer id
     * @param pageable paging info
     * @return list of {@link CustomerOrdersResponse}
     */
    @Override
    public List<CustomerOrdersResponse> getOrdersByCustomerId(String customerId, Pageable pageable) {
        Page<Order> pageOrders = orderRepository.findByCustomerId(customerId, pageable);
        List<Order> customerOrders = pageOrders.getContent();

        return customerOrders.stream()
                .map(CustomerOrdersResponse::fromOrder)
                .collect(Collectors.toList());
    }

    /**
     * Adds a new order in to database
     * @param orderRequest order informations {@link OrderBookRequest}
     * @return created order {@link OrderBookResponse}
     */

    @JaversAuditable
    @Override
    public OrderBookResponse persist(OrderBookRequest orderRequest) {

        //check if user exist
        customerService.loadUserById(orderRequest.getCustomerId());
        //check if books exist and has stock and get all related books as list
        if(orderRequest.getOrders() == null || orderRequest.getOrders().isEmpty())
            throw new RigCustomeException(HttpStatus.NOT_ACCEPTABLE,"Orders are missing");

        try {
            //to prevent ordering same book at the same time
            if (reentrantLock.tryLock(10, TimeUnit.SECONDS)) {

                List<Book> bookList = orderRequest.getOrders().stream()
                        .map(bookOrder -> bookService.checkBookExist(bookOrder))
                        .collect(Collectors.toList());

                int totalBooks = orderRequest
                        .getOrders()
                        .stream()
                        .map(BookOrder::getCount)
                        .mapToInt(Integer::intValue)
                        .sum();

                //Cast Order List to a map to make search faster
                Map<String, Integer> map = orderRequest.getOrders().stream()
                        .collect(Collectors.toMap(BookOrder::getBookId, BookOrder::getCount));
                // update each book stock
                bookList.forEach(book -> book.setStock(book.getStock() -map.get(book.getId())));

                //update stocks
                for (Book book:bookList) {
                    bookService.save(book);
                }

                Double totalPrice = bookList.stream()
                        .map(book -> map.get(book.getId()) * book.getPrice())
                        .reduce(0.0, Double::sum);

                Order order = Order.builder()
                        .customerId(orderRequest.getCustomerId())
                        .orders(orderRequest.getOrders())
                        .totalBooks(totalBooks)
                        .totalPrice(totalPrice)
                        .status("1")
                        .build();

                orderRepository.save(order);

                return OrderBookResponse.fromOrder(order);
            }
        } catch (Exception e) {
            logger.error("Order created error [{}]", e.getMessage());
            throw new RigCustomeException(HttpStatus.INTERNAL_SERVER_ERROR,"Could not creat order");
        } finally {
            reentrantLock.unlock();
        }
        return null;

    }

    /**
     * Query orders information by order id
     * @param orderId order id string
     * @return order informations {@link QueryOrderResponse}
     */
    @Override
    public QueryOrderResponse getOrderById(String orderId) {

        Optional<Order> order = orderRepository.findById(orderId);
        if (order.isEmpty()) {
            throw  new RigCustomeException(HttpStatus.NOT_FOUND
                    ,String.format("Could not find order for id %s",orderId));
        }

        return QueryOrderResponse.mapFromOrder(order.get());
    }

    /**
     * Query all orders within a date range
     * @param startDate start dat in the format of yyyyMMdd
     * @param endDate end date int the format yyyyMMdd
     * @return list of {@Link QueryOrderResponse}
     */
    @Override
    public List<QueryOrderResponse> getOrdersByDate(String startDate, String endDate) {

        Integer sDate = Integer.parseInt(startDate);
        Integer eDate = Integer.parseInt(endDate);

        Dater.checkDate(sDate);
        Dater.checkDate(eDate);

        List<Order> orderList = orderRepository.findByInsertDateBetween(sDate,eDate);
        if (orderList == null || orderList.isEmpty()) {
            throw  new RigCustomeException(HttpStatus.NOT_FOUND
                    ,String.format("Could not find any order in the time interval [%s - %s]",startDate,endDate));
        }

        return orderList.stream()
                .map(QueryOrderResponse::mapFromOrder)
                .collect(Collectors.toList());
    }
}
