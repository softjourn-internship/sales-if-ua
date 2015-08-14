package sales.notification.service;

import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;
import sales.payment.dto.data.AnonymMultyPaymentDTO;
import sales.payment.dto.data.RegisteredMultiPaymentDTO;
import sales.storage.domain.Storage;
import sales.storage.service.StorageService;
import sales.users.domain.User;
import sales.users.service.UserService;
import sales.util.Constants;

import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by taras on 31.07.15.
 */
@Service("notificationService")
public class NotificationServiceImpl implements NotificationService {
    final static Logger logger = LoggerFactory.getLogger(NotificationServiceImpl.class);

    @Autowired
    private StorageService storageService;

    @Autowired
    private UserService userService;

    private JavaMailSender mailSender;
    private VelocityEngine velocityEngine;
    private List<Storage> storages;

    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void setVelocityEngine(VelocityEngine velocityEngine) {
        this.velocityEngine = velocityEngine;
    }

    public void register(User user) {
        sendConfirmationEmail(user);
    }

    public void notificateOrderAnonym(AnonymMultyPaymentDTO anonymMultyPaymentDTO) {
        getGoods(anonymMultyPaymentDTO.getGoodsId());
        sendNotificationOrder(anonymMultyPaymentDTO.getEmail(), anonymMultyPaymentDTO.getFirstName());
    }

    public void notificateOrderRegist(RegisteredMultiPaymentDTO registeredMultiPaymentDTO) {
        getGoods(registeredMultiPaymentDTO.getGoodsId());
        User user = userService.getById(registeredMultiPaymentDTO.getUserId());
        sendNotificationOrder(user.getEmail(), user.getFirstName());
    }

    private void getGoods(List<Long> ids) {
        storages = new ArrayList<Storage>();
        for (Long id : ids) {
            storages.add(storageService.get(id));
        }
    }

    private void sendConfirmationEmail(final User user) {
        logger.debug("Creating mail text for email confirmation");
        MimeMessagePreparator preparator = new MimeMessagePreparator() {
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
                message.setTo(user.getEmail());
                message.setFrom("salesifua@gmail.com");
                message.setSubject("Confirm your registration");
                Map model = new HashMap();
                model.put("user", user);
                model.put("confirmURL", Constants.CONFIRM_URL);
                String text = VelocityEngineUtils.mergeTemplateIntoString(
                        velocityEngine, "email-template/registration-confirmation.vm", model);
                message.setText(text, true);
            }
        };
        this.mailSender.send(preparator);
    }

    private void sendNotificationOrder(String email, String firstName) {
        logger.debug("Creating mail text for order information");

        MimeMessagePreparator preparator = new MimeMessagePreparator() {
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
                message.setTo(email);
                message.setFrom("salesifua@gmail.com");
                message.setSubject("Order details");
                Map model = new HashMap();
                model.put("firstName", firstName);
                String goods = new String("");
                double amount = 0;
                for(int i = 0; i < storages.size(); i++) {
                    goods += storages.get(i).getGood().getName() + "(" + storages.get(i).getPrice() + "$)" + "<br>";
                    amount += storages.get(i).getPrice();
                }
                model.put("goods", goods);
                model.put("totalPrice", amount);
                String text = VelocityEngineUtils.mergeTemplateIntoString(
                        velocityEngine, "email-template/order-notification.vm", model);
                message.setText(text, true);
            }
        };
        this.mailSender.send(preparator);
    }
}
