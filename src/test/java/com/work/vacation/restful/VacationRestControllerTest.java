package com.work.vacation.restful;

import com.work.vacation.service.LoginService;
import com.work.vacation.service.VacationService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.HashMap;

@WebMvcTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@ContextConfiguration(locations={"file:src/main/resources/mybatis/**.xml"})
@ComponentScan(basePackages = {"com.work.vacation.restful"}) //(excludeFilters = @ComponentScan.Filter(type = FilterType.CUSTOM, classes = TypeExcludeFilter.class))
class VacationRestControllerTest {

    @MockBean
    private LoginService loginService;

    @MockBean
    private VacationService vacationService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void vacationResister() throws Exception{
        HashMap<String, Object> result = new HashMap<>();

        result.put("userId", "test");
        result.put("startDate", "20210509");
        result.put("endDate", "20210510");
        result.put("comment", "test comment");

        RequestBuilder reqBuilder = MockMvcRequestBuilders.post("/vacation/register")
                                                          .param(String.valueOf(result), String.valueOf(result))
                                                          .content(String.valueOf(result))
                                                          .characterEncoding("UTF-8")
                                                          .contentType(MediaType.APPLICATION_JSON); //Body 삽입

        mockMvc.perform(reqBuilder)
               .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void vacationRegCancel() throws Exception{
        HashMap<String, Object> result = new HashMap<>();

        result.put("userId", "test");
        result.put("index", "1");

        RequestBuilder reqBuilder = MockMvcRequestBuilders.post("/vacation/cancel")
                .param(String.valueOf(result), String.valueOf(result))
                .content(String.valueOf(result))
                .contentType(MediaType.APPLICATION_JSON); //Body 삽입

        mockMvc.perform(reqBuilder)
                .andDo(MockMvcResultHandlers.print());
    }
}