package com.readingisgood.Service;

import com.google.gson.Gson;
import com.readingisgood.Service.Controller.OrderController;
import com.readingisgood.Service.Controller.StatisticsController;
import com.readingisgood.Service.Service.Interface.IOrderService;
import com.readingisgood.Service.Service.Interface.IStatisticsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class StatisticsControllerTest {

    @Autowired
    private StatisticsController controller;

    @MockBean
    private IStatisticsService service;

    private MockMvc mockMvc;

    @Autowired
    protected Gson gson;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach()
    public void setup()
    {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }


    @Test
    public void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
    }

    @Test
    void getStatisticsController() throws Exception {
        this.mockMvc.perform(get("/api/statistics/{customerId}","111"))
                .andExpect(status().isOk());

    }
}
