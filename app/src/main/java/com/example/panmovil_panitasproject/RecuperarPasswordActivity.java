package com.example.panmovil_panitasproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.panmovil_panitasproject.conexionApi.ConexionApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class RecuperarPasswordActivity extends AppCompatActivity {

    private EditText emailEditText, phoneEditText;
    private TextView passView;
    private Button resetButton;
    private RequestQueue rqpass;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_password);

        emailEditText = findViewById(R.id.emailEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
        resetButton = findViewById(R.id.resetButton);
        passView = findViewById(R.id.textViewContra);

        rqpass = Volley.newRequestQueue(this);

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obtenerYEnviarContraseña();
            }
        });
    }

    private boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void obtenerYEnviarContraseña() {
        String mail = emailEditText.getText().toString().trim();
        String phone = phoneEditText.getText().toString().trim();

        String url = ConexionApi.URL_BASE + "usuariosmovil/" + phone + "&" + mail + "&recuperarcontrasena";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                response -> {
                    try {
                        String valor = response.get("Detalle").toString();
                        JSONArray arreglo = new JSONArray(valor);
                        JSONObject objeto = new JSONObject(arreglo.get(0).toString());

                        String correo = objeto.getString("usumo_Correo");
                        String password = objeto.getString("usumo_Contrasena");
                        String idUsuario = objeto.getString("usumo_id");

                        // Mostrar la contraseña en el TextView
                        //passView.setText("Contraseña: " + password);

                        // Enviar el correo con la contraseña
                        sendEmail(correo, "Recuperación de Contraseña", "Su contraseña es: " + password);

                        Toast.makeText(getApplicationContext(), "Contraseña recuperada y correo enviado", Toast.LENGTH_SHORT).show();

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Datos erroneos", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    Log.e("Volley Error", "Error en la solicitud GET: " + error.toString());
                    Toast.makeText(RecuperarPasswordActivity.this, "Error en la solicitud GET", Toast.LENGTH_SHORT).show();
                });

        // Agregar la solicitud a la cola
        rqpass.add(jsonObjectRequest);
    }

    private void sendEmail(final String email, final String subject, final String message) {
        AsyncTask<Void, Void, Void> sendTask = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                final String username = "manapandelcielo2023@gmail.com";  // Reemplaza con tu correo
                final String password = "mlsb ssmi xsqc zhyc";         // Reemplaza con tu contraseña

                Properties props = new Properties();
                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.starttls.enable", "true");
                props.put("mail.smtp.host", "smtp.gmail.com");
                props.put("mail.smtp.port", "587");

                Session session = Session.getInstance(props,
                        new javax.mail.Authenticator() {
                            protected PasswordAuthentication getPasswordAuthentication() {
                                return new PasswordAuthentication(username, password);
                            }
                        });

                try {
                    Message mimeMessage = new MimeMessage(session);
                    mimeMessage.setFrom(new InternetAddress(username));
                    mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
                    mimeMessage.setSubject(subject);
                    mimeMessage.setText(message);

                    Transport.send(mimeMessage);

                } catch (MessagingException e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(RecuperarPasswordActivity.this, "Correo enviado correctamente", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };
        sendTask.execute();
    }
}
