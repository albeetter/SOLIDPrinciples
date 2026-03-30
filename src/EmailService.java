public class EmailService implements NotificationService {
    @Override
    public void sendEmailNotification(String email) {
        System.out.println("Email notification sent to: " + email);
    }
}