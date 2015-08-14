package sales.payment.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextLoader;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import sales.payment.creaditCard.domain.CreditCard;
import sales.payment.dto.data.RegisteredMultiPaymentDTO;
import sales.users.domain.User;
import sales.util.Constants;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by taras on 14.08.15.
 */
@ContextConfiguration(locations =
        {
                "classpath:META-INF/applicationContext.xml",
                "classpath:test-mvc-servlet.xml"
        },
        loader = ContextLoader.class)
@WebAppConfiguration
public class TestPaymentController extends AbstractTestNGSpringContextTests {

    private MvcResult result;
    private MockMvc mockMvc;
    private RegisteredMultiPaymentDTO registeredMultiPaymentDTO;
    private String regPaymentJson;

    @Autowired
    private WebApplicationContext wac;

    @BeforeMethod
    public void initMockMvc() throws JsonProcessingException {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();

        List<Long> goods = new ArrayList<Long>();
        goods.add(1L);
        goods.add(10L);
        goods.add(21L);
        goods.add(31L);
        goods.add(41L);

        CreditCard creditCard = new CreditCard();
        creditCard.setCvv2("cvv2");
        creditCard.setExpireMonth("05");
        creditCard.setExpireYear("2018");
        creditCard.setNumber("4485461978884500");
        creditCard.setType("Visa");

        this.registeredMultiPaymentDTO = new RegisteredMultiPaymentDTO();
        this.registeredMultiPaymentDTO.setGoodsId(goods);
        this.registeredMultiPaymentDTO.setCard(creditCard);
        this.registeredMultiPaymentDTO.setUserId(4L);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        this.regPaymentJson = ow.writeValueAsString(this.registeredMultiPaymentDTO);
    }
    @Test
    public void testRegPay() throws Exception {
        result = mockMvc.perform(post("/payment/regist")
                .content(regPaymentJson)
                .contentType(MediaType.APPLICATION_JSON)
                .sessionAttr("user", this.registeredMultiPaymentDTO))
                .andExpect(status().isCreated())
                .andReturn();
    }
}
