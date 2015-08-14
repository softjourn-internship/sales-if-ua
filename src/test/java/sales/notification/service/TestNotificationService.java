package sales.notification.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextLoader;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testng.annotations.Test;
import sales.payment.creaditCard.domain.CreditCard;
import sales.payment.dto.data.AnonymMultyPaymentDTO;
import sales.payment.dto.data.RegisteredMultiPaymentDTO;
import sales.roles.service.RoleService;
import sales.users.domain.User;
import sales.users.service.CityService;
import sales.util.Constants;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by taras on 07.08.15.
 */
@ContextConfiguration(locations =
        {
                "classpath:META-INF/applicationContext.xml",
                "classpath:test-mvc-servlet.xml"
        },
        loader = ContextLoader.class)
@WebAppConfiguration
public class TestNotificationService extends AbstractTestNGSpringContextTests {

    @Autowired
    NotificationService notificationService;

    @Autowired
    CityService cityService;

    @Autowired
    RoleService roleService;

    @Test
    public void testSendEmail() {

        User user = new User("1a1dc91c907325c69271ddf0c944bc72",
                "taras",
                "client",
                cityService.getOneByName("смт Зуя"),
                "tarik.danylyuk@gmail.com",
                "12342535",
                new Date(),
                roleService.getRoleByValue(Constants.CLIENT));

        logger.info("unique" + user);

        notificationService.register(user);
    }

    @Test
    public void testSendOrderAnonymEmail() {
        List<Long> goods = new ArrayList<Long>();
        goods.add(1L);
        goods.add(10L);
        goods.add(21L);
        goods.add(31L);
        goods.add(41L);
        AnonymMultyPaymentDTO anonymMultyPaymentDTO = new AnonymMultyPaymentDTO();
        anonymMultyPaymentDTO.setEmail("tarik.danylyuk@gmai.com");
        anonymMultyPaymentDTO.setFirstName("Anonym");
        anonymMultyPaymentDTO.setLastName("Surname");
        anonymMultyPaymentDTO.setGoodsId(goods);
        anonymMultyPaymentDTO.setCard(new CreditCard());

        notificationService.notificateOrderAnonym(anonymMultyPaymentDTO);
    }

    @Test
    public void testSendOrderRegistEmail() {
        List<Long> goods = new ArrayList<Long>();
        goods.add(1L);
        goods.add(10L);
        goods.add(21L);
        goods.add(31L);
        goods.add(41L);
        RegisteredMultiPaymentDTO registeredMultiPaymentDTO = new RegisteredMultiPaymentDTO();
        registeredMultiPaymentDTO.setCard(new CreditCard());
        registeredMultiPaymentDTO.setGoodsId(goods);
        registeredMultiPaymentDTO.setUserId(4L);

        notificationService.notificateOrderRegist(registeredMultiPaymentDTO);
    }
}
