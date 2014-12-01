package com.example.enviauoc;

import java.util.ArrayList;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Consulta_Envio extends Activity {
	BaseDatos bd;
	int idUsuario;
	String[] envioDireccion;
	String[] envioTrack;
	int[] envio_id;
	private ListView lista; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//Fichero xml que define la interfaz grafica
		setContentView(R.layout.consulta_envio);
		bd = new BaseDatos(this, null, null);		
		//Recuperamos el parametro de la anterior actividad
		Bundle bundle = getIntent().getExtras();
		String idUsuario_string = bundle.getString("id");
		//Parsea el valor a int
		idUsuario = Integer.parseInt(idUsuario_string);
		
		//Cargamos los envios en el Array List, nos devuelve un objeto envio en cada posición del ArrayList
		ArrayList<envio> datos = (ArrayList<envio>) bd.getEnviosUsuario(idUsuario_string);
		//Cargamos el objeto List del Layout
		lista = (ListView) findViewById(R.id.listView1);
		//Creamos el adaptador de la lista con el layout del item especificado y los datos que deberemos parsear 
		lista.setAdapter(new Lista_adaptador(this,R.layout.item_envio,datos) {
			//Montamos el item que se mostrará en la lista con los datos del array
			@Override
			public void onEntrada(Object entrada, View view) {
				if (entrada != null) {
					TextView texto_superior_entrada = (TextView) view.findViewById(R.id.textView_superior); 
					if (texto_superior_entrada != null) 
						texto_superior_entrada.setText(((envio) entrada).getEnvio_track()); 

					TextView texto_inferior_entrada = (TextView) view.findViewById(R.id.textView_inferior); 
					if (texto_inferior_entrada != null)
						texto_inferior_entrada.setText(((envio) entrada).getEnvio_direccion()); 			
				}
			}
		});
		//Función para capturar el click en el item de la lista
		lista.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(),"Mostraremos la actividad con los detalles del envio seleccionado ....", Toast.LENGTH_LONG).show();
			}
		});
	}

}
