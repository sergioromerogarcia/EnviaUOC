package com.example.enviauoc;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Nuevo_Envio extends Activity implements OnClickListener, android.location.LocationListener{
	//Variables de Base de datos
	SQLiteDatabase db;
	BaseDatos bd;
	//Geocoder
	Geocoder geoCoder;
	//Variables Google Maps
	private static GoogleMap map;
	boolean retrieved_completed = false; 
	private String proveedor;
	private LocationManager lm;
	//Variables globales programa
	private static final long TIEMPO_MIN = 10 * 1000 ; // 10 segundos
	private static final long DISTANCIA_MIN = 5 ; // 5 metros
	TextView textNombreCliente, textDireccion, textNumeroEnvio;
	int idUsuario;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Fichero xml que define la interfaz grafica
		setContentView(R.layout.nuevo_envio);
		
		//Recuperamos el parametro de la anterior actividad
		Bundle bundle = getIntent().getExtras();
		String idUsuario_string = bundle.getString("id");
		//Parsea el valor a int
		idUsuario = Integer.parseInt(idUsuario_string);
		textDireccion = (EditText) findViewById(R.id.editText1);
		textNombreCliente = (EditText) findViewById(R.id.editText3);
		textNumeroEnvio = (TextView) findViewById(R.id.textView4);
		//Generamos el nœmero de envio
		int rand = randInt(1111, 9999);
		textNumeroEnvio.setText(rand+"");
		// Recuperaci—n del Objeto Mapa 
		configurarMapa();
		geoCoder = new Geocoder(getBaseContext(), Locale.getDefault());
		//Definimos el location manager asi como el listener que ir‡ tomando las actualizaciones de ubicaci—n. 
		lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		//Defici—n de los criterios para la selecci—n del proveedor.
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE); 
		criteria.setAltitudeRequired(false); 
		criteria.setBearingRequired(false); 
		criteria.setCostAllowed(true); 
		criteria.setPowerRequirement(Criteria.POWER_LOW); 
		proveedor = lm.getBestProvider(criteria, true);
		//Recuperamos la œltima posici—n que nos ha ofrecido el proveedor.
		Location location = lm.getLastKnownLocation(proveedor);

		if (location != null){				
			onLocationChanged(location);
		}
		// Define el Button i lo relaciona con el id del fichero activity.xml
		final Button botonConsultar = (Button) findViewById(R.id.button2);
		final Button botonNuevo = (Button) findViewById(R.id.button1);
		final Button botonLocalizar = (Button) findViewById(R.id.button3);
		botonConsultar.setOnClickListener(this);
		botonNuevo.setOnClickListener(this);
		botonLocalizar.setOnClickListener(this);
	}
	/**
	 * Creamos el objeto mapa si no existe y configuramos los par‡metros necesarios
	 * @author sergioromero
	 */

	private void configurarMapa() {

		if (map == null) {
			map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
			// Verficamos que tenemos el objeto mapa creado correctamente.
			if (map != null) {

				map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
				map.setMyLocationEnabled(true);
				map.setTrafficEnabled(true);
				// Permitimos realizar zoom con los gestos en el mapa
				map.getUiSettings().setZoomGesturesEnabled(true);

			}else
				Toast.makeText(getApplicationContext(),"Error!, No es posible crear el mapa.", Toast.LENGTH_SHORT).show();		
		}
	}
	@Override
	public void onLocationChanged(Location location) {
		map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 13));
	}


	@Override
	public void onProviderDisabled(String arg0) {
		Toast.makeText(this, "Proveedor  " + proveedor + " deshabilitado.",Toast.LENGTH_SHORT).show();
	}


	@Override
	public void onProviderEnabled(String arg0) {		
		Toast.makeText(this, "Habilitado el nuevo proveedor  " + proveedor,Toast.LENGTH_SHORT).show();
	}


	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub

	}
	/* actualizamos la ubicacion */
	@Override
	protected void onResume() {
		super.onResume();
		lm.requestLocationUpdates(proveedor, TIEMPO_MIN, DISTANCIA_MIN, this);
	}

	/* detenemos el listener  */
	@Override
	protected void onPause() {
		super.onPause();
		lm.removeUpdates(this);

	}
	@Override
	public void onClick(View v) {

		//Segœn el Bot—n que pulsemos se ejecutar‡ el c—digo correspondiente.
		switch(v.getId()){
		case R.id.button1:
			//Daremos de Alta el registro en la base de datos y blanquearemos los campos
			/*
			bd = new BaseDatos(this, null, null);
			bd.insertarEnvio(textNumeroEnvio.getText().toString(), textDireccion.getText().toString(), idUsuario);
			try {
				bd.copiarBD(this.getBaseContext());
				Toast.makeText(getApplicationContext(),"Se ha actualizado la base de datos.", Toast.LENGTH_LONG).show();
			} catch (IOException e) {
				e.printStackTrace();
			}
			*/
			int rand = randInt(1111, 9999);
			textNumeroEnvio.setText(rand+"");
			//Limpiamos los datos de la pantalla
			textNombreCliente.setText("");
			textDireccion.setText("");
			map.clear();
			textNombreCliente.requestFocus();
			
			break;
		case R.id.button2:
			//Abrimos la pantalla de Consulta
			Intent i = new Intent(Nuevo_Envio.this, Consulta_Envio.class);
			startActivity(i);
			break;	
		case R.id.button3:
			//Grabar registro nuevo
			String searchFor = textDireccion.getText().toString();
            try {
                List<Address> addresses = geoCoder.getFromLocationName(searchFor, 1);
                if (addresses.size() > 0) {
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng((addresses.get(0).getLatitude()),(addresses.get(0).getLongitude())), 15));
                	LatLng Envio = new LatLng((addresses.get(0).getLatitude()), (addresses.get(0).getLongitude()));
                	MarkerOptions marker = new MarkerOptions().position(Envio).title("Envio " + textNumeroEnvio);					 
    				// Declaraci—n del color del marcador.
    				marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE));
    				// Visualizaci—n del objeto Marcador en el mapa.
    				map.addMarker(marker);
    				//Ocultamos el teclado virtual
    				InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
    				imm.hideSoftInputFromWindow(textDireccion.getWindowToken(), 0);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
			break;
		}    
	}
	/** Genera numeros aleatorios enteros entre dos valores pasados por parametro **/
	public static int randInt(int min, int max) {

	    Random rand = new Random();
	    int randomNum = rand.nextInt((max - min) + 1) + min;

	    return randomNum;
	}
}
