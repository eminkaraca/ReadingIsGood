package com.readingisgood.Service.Controller;

import com.readingisgood.Service.Exception.RigException;
import com.readingisgood.Service.Model.Response.Statistics.CustomerMonthlyOrderResponse;
import com.readingisgood.Service.Service.Interface.IStatisticsService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/statistics")
@RequiredArgsConstructor
public class StatisticsController {
    private static final Logger logger = LoggerFactory.getLogger(StatisticsController.class);
    private final IStatisticsService staticsService;

    /**
     * Query a customer's montly order report
     * @param customerId customer id
     * @return report list of {@link CustomerMonthlyOrderResponse}
     */
    @GetMapping("/{customerId}")
    public ResponseEntity<List<CustomerMonthlyOrderResponse>> getCustomerMonthlyOrder(@PathVariable String customerId) {

        logger.info("getCustomerMonthlyOrder customerId={}", customerId);

        try {
            List<CustomerMonthlyOrderResponse> customerMonthlyOrders = staticsService.getCustomerMonthlyOrders(customerId);
            return new ResponseEntity<>(customerMonthlyOrders, HttpStatus.OK);

        } catch (RigException re){
            logger.error("getCustomerMonthlyOrder statistics error ",re);
            throw  re;
        }catch (Exception e){
            logger.error("getCustomerMonthlyOrder statistics exception ",e);
            throw e;
        }

    }
}
