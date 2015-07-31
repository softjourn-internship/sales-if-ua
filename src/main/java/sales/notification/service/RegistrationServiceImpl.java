package sales.notification.service;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.ui.velocity.VelocityEngineUtils;
import sales.users.domain.User;

import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by taras on 31.07.15.
 */
public class RegistrationServiceImpl implements RegistrationService {
    private JavaMailSender mailSender;
    private VelocityEngine velocityEngine;

    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void setVelocityEngine(VelocityEngine velocityEngine) {
        this.velocityEngine = velocityEngine;
    }

    public void register(User user) {
        //
        sendConfirmationEmail(user);
    }

    private void sendConfirmationEmail(final User user) {
        MimeMessagePreparator preparator = new MimeMessagePreparator() {
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
                message.setTo(user.getEmail());
                message.setFrom("salesifua@gmail.com");
                Map model = new HashMap();
                model.put("user", user);
                String text = "This is my email notification!";
                /*String text = VelocityEngineUtils.mergeTemplateIntoString(
                        velocityEngine, "resources/registration-confirmation.vm", model);*/
                message.setText(text, true);
            }
        };
        this.mailSender.send(preparator);
    }
}
