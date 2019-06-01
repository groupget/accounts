//package com.agh.groupget.accounts.infrastructure;
//
//import com.agh.groupget.accounts.domain.InvitationRepository;
//import com.sendgrid.Method;
//import com.sendgrid.Request;
//import com.sendgrid.Response;
//import com.sendgrid.SendGrid;
//import com.sendgrid.helpers.mail.Mail;
//import com.sendgrid.helpers.mail.objects.Content;
//import com.sendgrid.helpers.mail.objects.Email;
//import com.sendgrid.helpers.mail.objects.Personalization;
//import org.springframework.stereotype.Repository;
//
//@Repository
//class InvitationSendGridRepository implements InvitationRepository {
//
//    private final String sendGridApiKey;
//
//    InvitationSendGridRepository() {
//        sendGridApiKey = "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";
////        sendGridApiKey = System.getenv("SEND_GRID_API_KEY");
//    }
//
//    @Override
//    public void inviteUserToGroup(String groupName, String username) {
//        Email from = new Email("test@example.com");
//        String subject = "Sending with SendGrid is Fun";
//        Email to = new Email(username);
//        Content content = new Content("text/plain", "and easy to do anywhere, even with Java");
//        Mail mail = new Mail();
//        mail.setTemplateId("d-ddf25d6b84f6457eb5885002334dd55e");
//        mail.setFrom(from);
//        mail.setReplyTo(to);
//        Personalization personalization = new Personalization();
//        personalization.addTo(to);
////        personalization.addSubstitution();
//        mail.addPersonalization(personalization);
//        SendGrid sg = new SendGrid(sendGridApiKey);
////        SendGrid sg = new SendGrid(System.getenv("SENDGRID_API_KEY"));
//        Request request = new Request();
//        try {
//            request.setMethod(Method.POST);
//            request.setEndpoint("mail/send");
//            request.setBody(mail.build());
//            Response response = sg.api(request);
//            System.out.println(response.getStatusCode());
//            System.out.println(response.getBody());
//            System.out.println(response.getHeaders());
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }
//}
