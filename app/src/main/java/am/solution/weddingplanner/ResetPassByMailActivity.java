package am.solution.weddingplanner;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class ResetPassByMailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pass_by_mail);
        Button send = findViewById(R.id.buttonResetPass);

        class SendEmailAsyncTask extends AsyncTask<Void, Void, Boolean>{


            String stringReceiverEmail = "ancaancuta20@gmail.com";
            Properties props = System.getProperties();

            //                props.put("mail.smtp.host", "smtp.gmail.com");
//                props.put("mail.smtp.starttls.enable", true);
//                props.put("mail.smtp.port", "25");


            javax.mail.Session session = Session.getDefaultInstance(props);
            MimeMessage msg = new MimeMessage(session);

            public SendEmailAsyncTask() throws MessagingException {
                if (BuildConfig.DEBUG) Log.v(SendEmailAsyncTask.class.getName(), "SendEmailAsyncTask()");

                msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
                msg.addHeader("format", "flowed");
                msg.addHeader("Content-Transfer-Encoding", "8bit");
                msg.setFrom(new InternetAddress("weddplanningapp@gmail.com"));
                msg.setSubject("SimpleEmail Testing Subject", "UTF-8");
                msg.setText("SimpleEmail Testing Body", "UTF-8");
                msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(stringReceiverEmail, false));
            }

            @Override
            protected Boolean doInBackground(Void... params) {
                if (BuildConfig.DEBUG) Log.v(SendEmailAsyncTask.class.getName(), "doInBackground()");
                try {

                    System.out.println("Message is ready");
                    Transport.send(msg);
                    System.out.println("EMail Sent Successfully!!");
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            }

        }

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  sendEmailAsyncTask().execute();

               // try {
                    String stringSenderEmail = "weddplanningapp@gmail.com";
                    String stringReceiverEmail = "ancaancuta20@gmail.com";
                    String stringPasswordSenderEmail = "aplicatie1234";

                    //  String stringHost = "smtp.gmail.com";

                 

//                    props.put("mail.smtp.host", "smtp.gmail.com");
//                    props.put("mail.smtp.socketFactory.port", "465");
//                    props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//                    props.put("mail.smtp.auth", "true");
//                    props.put("mail.smtp.port", "465");


              
//
//                    try {
//                        MimeMessage msg = new MimeMessage(session);
//                        msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
//                        msg.addHeader("format", "flowed");
//                        msg.addHeader("Content-Transfer-Encoding", "8bit");
//                        msg.setFrom(new InternetAddress("weddplanningapp@gmail.com"));
//                        msg.setSubject("SimpleEmail Testing Subject", "UTF-8");
//                        msg.setText("SimpleEmail Testing Body", "UTF-8");
//                        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(stringReceiverEmail, false));
//                        System.out.println("Message is ready");
//                        Transport.send(msg);
//                        System.out.println("EMail Sent Successfully!!");
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }


//                    MimeMessage mimeMessage = new MimeMessage(session);
//                    mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(stringReceiverEmail));
//
//                    mimeMessage.setSubject("Subject: Android App email");
//                    mimeMessage.setText("Hello Programmer, \n\nProgrammer World has sent you this 2nd email. \n\n Cheers!\nProgrammer World");
//
//                    Thread thread = new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                            try {
//                                Transport.send(mimeMessage);
//                            } catch (MessagingException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    });
//                    thread.start();
//
//                } catch (AddressException e) {
//                    e.printStackTrace();
//                } catch (MessagingException e) {
//                    e.printStackTrace();
//                }

                }



        });

     

    }



    }