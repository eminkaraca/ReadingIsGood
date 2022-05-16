package com.readingisgood.Service.Controller;

import com.readingisgood.Service.Exception.RigCustomeException;
import com.readingisgood.Service.Exception.RigException;
import com.readingisgood.Service.Model.Request.OrderBookRequest;
import com.readingisgood.Service.Model.Response.Order.OrderBookResponse;
import com.readingisgood.Service.Model.Response.Order.QueryOrderResponse;
import com.readingisgood.Service.Service.Interface.IOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Api(value = "Order Api documentation")
public class OrderController {
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
    private final IOrderService orderService;

    /**
     * Adds a new order in to database
     * @param orderRequest {@link OrderBookRequest} order informations
     * @return orders informations including total book and total price {@link OrderBookResponse}
     */
    @PostMapping("/new")
    @ApiOperation(value = "New Order adding method")
    public ResponseEntity<OrderBookResponse> newOrder(@RequestBody @Valid OrderBookRequest orderRequest
                                                                        , BindingResult result)
    {
        logger.info(String.format("newOrder Request : [%s]", orderRequest.toString()));
        if(result.hasErrors())
            throw new RigCustomeException(HttpStatus.BAD_REQUEST,"Incorrect Parameters");
        try {
            OrderBookResponse newOrder = orderService.persist(orderRequest);
            return new ResponseEntity<>(newOrder, HttpStatus.CREATED);
        }catch (RigException re){
            logger.error("newOrder Persist error ",re);
            throw  re;
        }catch (Exception e){
            logger.error("newOrder Persist exception ",e);
            throw e;
        }
    }

    /**
     * Gets the informations of an order
     * @param orderId order id
     * @return informations of the order {@link QueryOrderResponse}
     */
    @GetMapping("/{orderId}")
    @ApiOperation(value = "Query Order method")
    public ResponseEntity<QueryOrderResponse> queryOrder(@PathVariable String orderId) {

        logger.info("queryOrder orderId [{}]", orderId);

        try {
            QueryOrderResponse order = orderService.getOrderById(orderId);
            return new ResponseEntity<>(order, HttpStatus.OK);
        } catch (RigException rex) {
            logger.error("queryOrder error", rex);
            throw  rex;
        }catch (Exception e) {
            logger.error("queryOrder exception ", e);
            throw e;
        }
    }

    /**
     * Query all the orders with in the given date range
     * @param startDate start date int the format yyyyMMdd(not included to search)
     * @param endDate end date int the format yyyyMMdd(not included to search)
     * @return
     */
    @GetMapping()
    @ApiOperation(value = "Query Order with in time range method")
    public ResponseEntity<List<QueryOrderResponse>> getOrdersByDate(@RequestParam String startDate,
                                                                    @RequestParam String  endDate)
    {
        logger.info("getOrdersByDate startDate-endDate : [{}-{}]", startDate, endDate);

        try {
            List<QueryOrderResponse> orders = orderService.getOrdersByDate(startDate, endDate);
            return new ResponseEntity<>(orders, HttpStatus.OK);

        } catch (RigException rex) {
            logger.error("getOrdersByDate error", rex);
            throw  rex;
        }catch (Exception e) {
            logger.error("getOrdersByDate exception ", e);
            throw e;
        }
    }
}
