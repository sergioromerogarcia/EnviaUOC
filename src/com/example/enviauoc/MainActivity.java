package com.example.enviauoc;


import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {
	BaseDatos usuariosdb;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_enviauoc);
		
		//Creamos el objeto usuariodb de nuestra clase de Base de datos que utilizamos para crear la base de datos así como 
        //los métodos principales para trabajar con los datos.
        //Los parámetros los pasamos a null ya que los valores los tenemos definidos como constantes en la clase de Base de Datos
		usuariosdb = new BaseDatos(this, null, null);
		
	}
}
