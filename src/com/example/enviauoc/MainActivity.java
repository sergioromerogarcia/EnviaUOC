package com.example.enviauoc;

import java.io.IOException;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;

public class MainActivity extends Activity {
	BaseDatos bd;
	TextView textUsuario, textPassword;
	String nombreUsuario;
	SQLiteDatabase db;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_enviauoc);

		//Creamos el objeto usuariodb de nuestra clase de Base de datos que utilizamos para crear la base de datos 
		//los metodos principales para trabajar con los datos.
		//Los parametros los pasamos a null ya que los valores los tenemos definidos como constantes en la clase de Base de Datos
		bd = new BaseDatos(this, null, null);
		
		if (!bd.hayDatos()){
			//Insertamos las datos de los usuarios
			bd.insertarUsuario("user1", "passuser1", "user1@prueba.es");
			bd.insertarUsuario("user2", "passuser2", "user2@prueba.es");
			bd.insertarUsuario("user3", "passuser3", "user3@prueba.es");
			bd.insertarUsuario("user4", "passuser4", "user4@prueba.es");
			//Insertamos los datos de los envios para cada usuario
			bd.insertarEnvio("1111", "Direccion de envio 1", 1);
			bd.insertarEnvio("2222", "Direccion de envio 2", 1);
			bd.insertarEnvio("3333", "Direccion de envio 3", 2);
			bd.insertarEnvio("4444", "Direccion de envio 4", 2);
			bd.insertarEnvio("5555", "Direccion de envio 5", 3);
			bd.insertarEnvio("6666", "Direccion de envio 6", 3);
			bd.insertarEnvio("7777", "Direccion de envio 7", 4);
			bd.insertarEnvio("8888", "Direccion de envio 8", 4);

			/*
			 * Ya que hemos utilizado SQLiteOpenHelper no es posible guardar directamente la base de datos al almacenamiento externo.Para ello deberiamos 
			 * utilizar las funciones de SQLiteDatabase e implementar manualmente la creaci—n as’ como el upgrade.
			 */
			try {
				bd.copiarBD(this.getBaseContext());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// Define el campo de Usuario y lo relaciona con el id del fichero activity.xml
		textUsuario = (EditText) findViewById(R.id.editText1);
		// Define el campo del Passw y lo relaciona con el id del fichero activity.xml
		textPassword = (EditText) findViewById(R.id.editText2);
		
		
		// Define el Button i lo relaciona con el id del fichero activity.xml
		final Button button = (Button) findViewById(R.id.button1);
		
		// Cuando se clica el boton se llama al metodo send()
		button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				//Deberemos comprobar que existe el usuario  
				if (bd.existeUsuario(textUsuario.getText().toString())){
					//Deberemos comprobar que coincide el password
					if (textPassword.getText().toString().equals(bd.recuperarUsuarioPorNombre(textUsuario.getText().toString()).getUsuario_password())){
						textPassword.setText("");
						//Enviamos por par‡metro el ID del usuario logado
						send(bd.recuperarUsuarioPorNombre(textUsuario.getText().toString()).getUsuario_ID());
					}else{
						Toast.makeText(getApplicationContext(),"Error!! Password incorrecto", Toast.LENGTH_LONG).show();
						textPassword.setText("");
					}
				}else{
					Toast.makeText(getApplicationContext(),"Error!! No existe el usuario", Toast.LENGTH_LONG).show();
				}
			}
		});

	}
	/** Envia los datos del editText a la segunda pantalla (Nuevo_Envio) **/
	public void send(int id) {
		Intent i = new Intent(MainActivity.this, Nuevo_Envio.class);
		i.putExtra("id", id+"");
		startActivity(i);
	}
}
