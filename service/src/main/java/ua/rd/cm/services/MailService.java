package ua.rd.cm.services;

import freemarker.template.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
public class MailService {
    private JavaMailSender mailSender;
    private Configuration freemarkerConfiguration;

    @Autowired
    public MailService(JavaMailSender mailSender, Configuration freemarkerConfiguration) {
        this.mailSender = mailSender;
        this.freemarkerConfiguration = freemarkerConfiguration;
    }

    /*
    public void sendEmail(Object object) {
        MimeMessagePreparator preparator = getMessagePreparator(order);

        try {
            mailSender.send(preparator);
            System.out.println("Message has been sent.............................");
        }
        catch (MailException e) {
            System.err.println(e.getMessage());
        }
    }

    private MimeMessagePreparator getMessagePreparator(final ProductOrder order){

        MimeMessagePreparator preparator = new MimeMessagePreparator() {

            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

                helper.setSubject("Your order on Demoapp with Templates");
                helper.setFrom("customerserivces@yourshop.com");
                helper.setTo(order.getCustomerInfo().getEmail());

                Map<String, Object> model = new HashMap<String, Object>();
                model.put("order", order);

                String text = geFreeMarkerTemplateContent(model);//Use Freemarker or Velocity
                System.out.println("Template content : "+text);

                // use the true flag to indicate you need a multipart message
                helper.setText(text, true);

            }
        };
        return preparator;
    }


    public String geFreeMarkerTemplateContent(Map<String, Object> model){
        StringBuffer content = new StringBuffer();
        try{
            content.append(FreeMarkerTemplateUtils.processTemplateIntoString(
                    freemarkerConfiguration.getTemplate("fm_mailTemplate.txt"),model));
            return content.toString();
        }catch(Exception e){
            System.out.println("Exception occured while processing fmtemplate:"+e.getMessage());
        }
        return "";
    }
    */
}
