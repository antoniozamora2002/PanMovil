package com.example.panmovil_panitasproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class RegistroActivity extends AppCompatActivity {

    private EditText mailTxt;
    private EditText phoneTxt;
    private EditText passwordTxt;
    private EditText passwordconfirmTxt;
    RequestQueue requestQueue;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        mailTxt = (EditText) findViewById(R.id.MailTxt);
        phoneTxt = (EditText) findViewById(R.id.PhoneTxt);
        passwordTxt = (EditText) findViewById(R.id.PasswordTxt);
        passwordconfirmTxt = (EditText) findViewById(R.id.Password2Txt);

        requestQueue = Volley.newRequestQueue(this);

    }

    public void MainAct(View view){
        Intent mainAct = new Intent(this, MainActivity.class);
        startActivity(mainAct);
    }

    private void enviarCorreoVerificacion(String correo) {
        Intent veriAct = new Intent(this, VerificarCorreoActivity.class);
        startActivity(veriAct);
    }

    public void Registraruser(View view){

        // Verificar que las contraseñas no estén vacías
        String password = passwordTxt.getText().toString().trim();
        String confirmPassword = passwordconfirmTxt.getText().toString().trim();

        if (password.isEmpty() || confirmPassword.isEmpty()) {
            // Mostrar un mensaje de error si alguna de las contraseñas está vacía
            Toast.makeText(this, "Por favor ingrese ambas contraseñas", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validar que las contraseñas coincidan
        if (!password.equals(confirmPassword)) {
            // Mostrar un mensaje de error si las contraseñas no coinciden
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            return;
        }

        // Verificar que el correo electrónico no esté vacío
        String email = mailTxt.getText().toString().trim();
        if (email.isEmpty()) {
            // Mostrar un mensaje de error si el correo electrónico está vacío
            Toast.makeText(this, "Por favor ingrese su correo electrónico", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validar el formato del correo electrónico
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            // Mostrar un mensaje de error si el correo electrónico no tiene un formato válido
            Toast.makeText(this, "Ingrese un correo electrónico válido", Toast.LENGTH_SHORT).show();
            return;
        }

        // Verificar que el número de teléfono no esté vacío
        String phone = phoneTxt.getText().toString().trim();
        if (phone.isEmpty()) {
            // Mostrar un mensaje de error si el número de teléfono está vacío
            Toast.makeText(this, "Por favor ingrese su número de teléfono", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validar el formato del número de teléfono (puedes agregar tu propia lógica de validación según el formato requerido)
        if (!phone.matches("\\d{10}")) {
            // Mostrar un mensaje de error si el número de teléfono no tiene un formato válido (por ejemplo, 10 dígitos)
            Toast.makeText(this, "Ingrese un número de teléfono válido", Toast.LENGTH_SHORT).show();
            return;
        }

        String url = ConexionApi.URL_BASE + "usuariosmovil";

        // Crea el objeto JSON que enviarás en la solicitud POST
        JSONObject jsonBody = new JSONObject();
        try {
            // Agrega todos los parámetros que necesites
            jsonBody.put("usumo_correo", mailTxt.getText().toString());
            jsonBody.put("usumo_contrasena", passwordconfirmTxt.getText().toString());
            jsonBody.put("usumo_telefono", phoneTxt.getText().toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, url, jsonBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Maneja la respuesta del servidor aquí
                        // Por ejemplo, si esperas un JSON de respuesta:
                        try {
                            int totalRegistros = response.getInt("Total de registros");
                            Toast.makeText(getApplicationContext(), "es: " + totalRegistros, Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Maneja errores de la solicitud
                        Toast.makeText(getApplicationContext(), "Error en la solicitud: " + error.getMessage(), Toast.LENGTH_SHORT).show();

                        // Imprime el cuerpo de la respuesta de error para obtener detalles específicos
                        if (error.networkResponse != null && error.networkResponse.data != null) {
                            String errorBody = new String(error.networkResponse.data);
                            Log.e("Error Body", errorBody);
                        }
                    }
                }
        )
        {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", ConexionApi.AUTH);
                return headers;
            }
        };

        // Almacena el valor de mailTxt en SharedPreferences
        String mailText = mailTxt.getText().toString();
        SharedPreferences sharedMail = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editorMail = sharedMail.edit();
        editorMail.putString("mail", mailText);
        editorMail.apply();

        // Añade la solicitud a la cola de Volley
        requestQueue.add(postRequest);

        // Llama a la función para enviar el correo de verificación
        enviarCorreoVerificacion(mailText);

    }
}