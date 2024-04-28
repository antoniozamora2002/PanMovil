package com.example.panmovil_panitasproject;

import static android.widget.Toast.LENGTH_LONG;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.panmovil_panitasproject.conexionApi.ConexionApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class CambiarContrasena extends AppCompatActivity {

    private Button btnEnviar;
    private TextView txtViewId;
    private TextView txtViewCorreo;
    private EditText txtPassword;
    private EditText txtPassword2;
    RequestQueue requestQueue;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambiar_contrasena);

        btnEnviar = findViewById(R.id.BtnSend);
        txtViewId = findViewById(R.id.textViewId);
        txtViewCorreo = findViewById(R.id.textViewCorreo);
        txtPassword = findViewById(R.id.editTextPassword);
        txtPassword2 = findViewById(R.id.editTextPassword2);

        requestQueue = Volley.newRequestQueue(this);

        // Obtener el SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MisPreferencias", MODE_PRIVATE);

        // Obtener los valores
        String id = sharedPreferences.getString("id", "");
        String correo = sharedPreferences.getString("correo", "");

        txtViewId.setText(id); txtViewCorreo.setText(correo);

    }

    public void ActualizarDatos(View view){
        String password = txtPassword.getText().toString().trim();
        String password2 = txtPassword2.getText().toString().trim();
        final String id = txtViewId.getText().toString();

        if(password.isEmpty() || password2.isEmpty()){
            Toast.makeText(this, "Ingrese los datos.", Toast.LENGTH_SHORT).show();
            return;
        }

        if(!password.equals(password2)){
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            return;
        }

        // Realizar el metodo PUT, apara actualizar la contraseña
        String url = ConexionApi.URL_BASE + "Usuariosmovil/actualizarcontrasena/" + id;

        Map<String, String> params = new HashMap<>();
        params.put("usumo_Contrasena", password2);
        Log.d("Password Value", password2);


        String requestBody = getEncodedParams2(params);

        StringRequest putRequest = new StringRequest(Request.Method.PUT, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            int status = jsonResponse.getInt("Status");
                            if (status == 200) {
                                Toast.makeText(getApplicationContext(), "Contraseña actualizada exitosamente", Toast.LENGTH_SHORT).show();
                                LoginActivity();
                            } else {
                                Toast.makeText(getApplicationContext(), "Error al actualizar la contraseña", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            // Manejar errores de análisis JSON si es necesario
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Maneja errores de la solicitud
                        Toast.makeText(getApplicationContext(), "Error en la solicitud: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("Volley Error", "Error en la solicitud: " + error.getMessage(), error);
                    }
                }
        ){
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", ConexionApi.AUTH);
                return headers;
            }
            @Override
            public byte[] getBody() {
                // Convierte la cadena de consulta a un array de bytes
                return requestBody.getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded";
            }

        };

        // llamar el metodo PUT, aplicarlo en el request
        requestQueue.add(putRequest);

    }

    private String getEncodedParams2(Map<String, String> params) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (sb.length() > 0) {
                sb.append("&");
            }
            try {
                sb.append(URLEncoder.encode(entry.getKey(), "UTF-8"))
                        .append("=")
                        .append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    private void  LoginActivity(){
        Intent loginAct = new Intent(this, LoginActivity.class);
        startActivity(loginAct);
    }

}