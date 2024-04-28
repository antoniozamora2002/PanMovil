package com.example.panmovil_panitasproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.Random;

public class RecuperarCuentaActivity extends AppCompatActivity {

    private EditText emailEditText, phoneEditText;
    private Button resetButton;
    private RequestQueue rqpass;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_cuenta);

        emailEditText = findViewById(R.id.emailEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
        resetButton = findViewById(R.id.resetButton);

        rqpass = Volley.newRequestQueue(this);

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConsultaDeSistema();

            }
        });
    }

    private void ConsultaDeSistema() {
        final String mail = emailEditText.getText().toString().trim();
        final String phone = phoneEditText.getText().toString().trim();

        if(mail.isEmpty() || phone.isEmpty()){
            Toast.makeText(this, "Por favor ingrese los datos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Realizar la solicitud GET para verificar si el correo está registrado
        String url = ConexionApi.URL_BASE + "Usuariosmovil/buscaremail/" + mail + "/" + phone;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String valor = response.get("Detalle").toString();
                            JSONArray arreglo = new JSONArray(valor);

                            JSONObject objeto = new JSONObject(arreglo.get(0).toString());

                            String id = objeto.getString("usumo_id");
                            String correo = objeto.getString("usumo_Correo");
                            String celular = objeto.getString("usumo_telefono");


                            // Si el correo está registrado, enviar el correo de recuperación
                            if (correo.equals(mail) && celular.equals(phone)) {
                                // Generar el código de recuperación
                                Random random = new Random();
                                int codigoAleatorio = random.nextInt(9000) + 1000; // Genera un número aleatorio de 4 dígitos
                                String codigo = String.valueOf(codigoAleatorio);

                                // Enviar el correo de recuperación con el código
                                sendEmail(mail, "Recuperar cuenta", "Tu código es: " + codigo);

                                // Enviar los datos al cambiarContrasena
                                SharedPreferences sharedPreferences = getSharedPreferences("MisPreferencias", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                // Guardar los valores
                                editor.putString("id", id);
                                editor.putString("correo", correo);
                                editor.putString("celular", celular);
                                // Aplicar los cambios
                                editor.apply();

                                CodigoAct(codigo);

                            } else {
                                // Si el correo no está registrado, mostrar un mensaje al usuario
                                Toast.makeText(RecuperarCuentaActivity.this, "El usuario no está registrado.", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(RecuperarCuentaActivity.this, "Error al procesar la respuesta del servidor.", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley Error", "Error en la solicitud GET: " + error.toString());
                        Toast.makeText(RecuperarCuentaActivity.this, "Error en la solicitud GET", Toast.LENGTH_SHORT).show();
                    }
                }
        );

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
                    // Si el correo se envía correctamente, muestra un mensaje de éxito
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(RecuperarCuentaActivity.this, "Correo enviado correctamente", Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (MessagingException e) {
                    e.printStackTrace();
                    // Si hay un error, muestra un mensaje de error al usuario
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(RecuperarCuentaActivity.this, "Error al enviar el correo electrónico", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                // Mostrar un mensaje indicando que la recuperación de contraseña se ha completado
                Toast.makeText(RecuperarCuentaActivity.this, "Recuperación de contraseña completada", Toast.LENGTH_SHORT).show();
            }
        };
        sendTask.execute();
    }

    public void CodigoAct(String codigo){
        Intent codigoact = new Intent(this, CodigoActivity.class);
        codigoact.putExtra("codigo", codigo);
        startActivity(codigoact);
    }

}
