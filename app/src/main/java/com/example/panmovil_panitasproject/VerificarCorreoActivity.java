package com.example.panmovil_panitasproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class VerificarCorreoActivity extends AppCompatActivity {

    private TextView MailUser;
    private EditText NumberRandom;
    private Button btnVerificar, btnRandom;

    private int verificationCode;  // Declarar la variable a nivel de clase

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verificar_correo);

        MailUser = findViewById(R.id.txtMail);
        NumberRandom = findViewById(R.id.txtNumberRandom);
        btnVerificar = findViewById(R.id.buttonVerificar);

        // Genera un número aleatorio de 4 dígitos
        verificationCode = generateRandomCode(1000, 9999);

        // Obtiene el correo electrónico almacenado en SharedPreferences
        SharedPreferences sharedMail = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String correo = sharedMail.getString("mail", "");
        MailUser.setText(correo);

        String verificationCodeString = String.valueOf(verificationCode);

        sendEmail(correo, "Número de verficación de cuenta.", verificationCodeString);


        btnVerificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String enteredCode = NumberRandom.getText().toString();

                //verificarCodigo(view);
                /*RegistroActivity registro = new RegistroActivity();

                registro.Registraruser(view);*/

                if (enteredCode.equals(String.valueOf(verificationCode))) {

                MainAct(view);
                }else {
                    Toast.makeText(getApplicationContext(), "Número incorrecto", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private int generateRandomCode(int min, int max) {
        // Genera un número aleatorio entre min y max
        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }

    public void MainAct(View view){
        Intent mainAct = new Intent(this, MainActivity.class);
        startActivity(mainAct);
    }

    private void sendEmail(final String email, final String subject, final String message) {
        AsyncTask<Void, Void, Void> sendTask = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                // Configuración del servidor SMTP y credenciales
                final String username = "manapandelcielo2023@gmail.com";
                final String password = "mlsb ssmi xsqc zhyc";

                Properties props = new Properties();
                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.starttls.enable", "true");
                props.put("mail.smtp.host", "smtp.gmail.com");
                props.put("mail.smtp.port", "587");

                // Crear una sesión de correo
                Session session = Session.getInstance(props,
                        new javax.mail.Authenticator() {
                            protected PasswordAuthentication getPasswordAuthentication() {
                                return new PasswordAuthentication(username, password);
                            }
                        });

                try {
                    // Crear un mensaje de correo
                    Message mimeMessage = new MimeMessage(session);
                    mimeMessage.setFrom(new InternetAddress(username));
                    mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
                    mimeMessage.setSubject(subject);
                    mimeMessage.setText(message);

                    // Enviar el mensaje
                    Transport.send(mimeMessage);

                } catch (MessagingException e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                // Operaciones después de enviar el correo (puedes actualizar la interfaz de usuario)
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(VerificarCorreoActivity.this, "Correo enviado correctamente", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };
        // Ejecutar la tarea asíncrona para enviar el correo
        sendTask.execute();
    }

    /*private void verificarCodigo(View view) {
        String enteredCode = NumberRandom.getText().toString();

        if (enteredCode.equals(String.valueOf(verificationCode))) {
            // El código ingresado es correcto
            RegistroActivity registro = new RegistroActivity();
            registro.Registraruser(view);  // Pasa la vista actual al método Registraruser
            //MainAct();
        } else {
            Toast.makeText(getApplicationContext(), "Número incorrecto", Toast.LENGTH_SHORT).show();
        }
    }*/

}