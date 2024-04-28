package com.example.panmovil_panitasproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CodigoActivity extends AppCompatActivity {

    private EditText codigoEditText;
    private Button verificarButton;
    private String codigoRecuperacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codigo);

        codigoEditText = findViewById(R.id.editTextCodigo);
        verificarButton = findViewById(R.id.btnEnviar);

        // Obtener el código de recuperación enviado desde RecuperarCuentaActivity
        Intent intent = getIntent();
        codigoRecuperacion = intent.getStringExtra("codigo");
        verificarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verificarCodigo();
            }
        });

    }

    private void verificarCodigo() {
        String codigoIngresado = codigoEditText.getText().toString().trim();

        // Verificar si el código ingresado coincide con el código de recuperación
        if (codigoIngresado.equals(codigoRecuperacion)) {

            // Aquí puedes iniciar otra actividad o realizar cualquier otra acción necesaria
            Toast.makeText(this, "Código correcto", Toast.LENGTH_SHORT).show();
            CambiarPass();

        } else {
            // Código incorrecto, mostrar un mensaje de error al usuario
            Toast.makeText(this, "Código incorrecto", Toast.LENGTH_SHORT).show();
        }
    }

    public void CambiarPass(){
        Intent camPass = new Intent(this, CambiarContrasena.class);
        startActivity(camPass);
    }

}