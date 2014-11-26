/**
 * @author sergioromero
 * Clase Helper para manejar la base de datos y poder llamarla desde nuestra aplicación.
 * En lugar de importarla ya hecha, aprovecharemos esta clase para dar de lata los registros necesarios para la práctica.
 * Crearemos dos tablas relacionadas (1->n) donde cada tienda tendrá asociada 5 ofertas.
 */
package com.example.enviauoc;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
/*
 * Tiendas = Usuarios
 * Ofertas = Envíos
 */
public class BaseDatos extends SQLiteOpenHelper{
	//*********** Definición de la base de datos ************
	//Nombre de la base de datos que almacenarán todos los datos
	static final String NOMBRE_BASEDATOS ="datos.sqlite";
	//Nombre de la tabla que almacenará todos los usuarios
	static final String NOMBRE_TABLA_USUARIOS = "usuarios";
	//Nombre de la tabla que almacenará los envíos
	static final String NOMBRE_TABLA_ENVIOS = "envios";
	//Versión de la Base de Datos. En el caso que debamos extenderla en el futuro deberemo cambiar la versión.
	static final int VERSION_BASEDATOS = 1;
	//Ubicación de la base de datos
	static final String DATABASE_FILE_PATH = Environment.getExternalStorageState();
	static final String Separador = File.separator;
	static final String Folder = "/enviaUOC/";
	static final String EXTERNAL_FILE_PATH = "/sdcard";
	//*******************************************************

	//*********** Definición tabla de Usuarios ***************
	static final String usuario_ID = "_id";
	static final String usuario_nombre = "nombreUsuario";
	static final String usuario_password = "passwordUsuario";
	static final String usuario_email = "emailUsuario";
	//*******************************************************

	//********** Definición tabla de Envios ****************
	static final String envio_id = "_id";
	static final String envio_track = "trackEnvio";
	static final String envio_direccion = "direccionEnvio";
	static final String envio_idUsuario = "_idUsuario"; //Usuario al que pertenece el envio
	//*******************************************************

	//********** Sentencia SQL para crear la tabla de Usuarios *****
	//Asignación de la primera columna como Clave única
	private static final String TABLA_USUARIOS = "CREATE TABLE "+ NOMBRE_TABLA_USUARIOS + 
			" (" + usuario_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
			usuario_nombre +  " TEXT," + 
			usuario_password + " TEXT," + 
			usuario_email + " TEXT)";

	//********** Sentencia SQL para crear la tabla de envíos *******
	//Asignamos la primera columna como clave única.
	//Utilizamos el campo usuario_id como clave externa con el fín de poder ligar ambas tablas, donde un usuario puede 
	//tener n envios asociados.
	private static final String TABLA_ENVIOS = "CREATE TABLE "+ NOMBRE_TABLA_ENVIOS  +
			"("+ envio_id +" INTEGER PRIMARY KEY AUTOINCREMENT," + 
			envio_idUsuario + " INTEGER, " + 
			envio_track + " TEXT, " + 
			envio_direccion + " TEXT,FOREIGN KEY(" + envio_idUsuario + ") REFERENCES tienda(" + usuario_ID + "))";

	//**************************************************************** 

	public BaseDatos(Context context, String name, CursorFactory factory) {
		super(context,NOMBRE_BASEDATOS, factory, VERSION_BASEDATOS);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		//Ejecutamos las sentencias para la creación de las tablas, solo se ejecutará cuando sea necesario, es decir cunado no existan las tablas
		db.execSQL(TABLA_USUARIOS);
		db.execSQL(TABLA_ENVIOS);
	}
	/**
	 * Insertar Registros en la tabla de Usuarios
	 * @param id
	 * @param nombreUsuario
	 * @param passwordUsuario
	 * @param emailUsuario
	 */
	public void insertarUsuario(String nombreUsuario, String passwordUsuario, String emailUsuario) {
		SQLiteDatabase db = getWritableDatabase();
		if(db != null){
			ContentValues valores = new ContentValues();
			valores.put(usuario_nombre, nombreUsuario);
			valores.put(usuario_password,passwordUsuario );
			valores.put(usuario_email, emailUsuario);
			db.insert(NOMBRE_TABLA_USUARIOS, null, valores);
			db.close();   
		}
	}
	/**
	 * Insertar Registros en la tabla de Envios.
	 * @param id
	 * @param trackEnvio
	 * @param direccionEnvio
	 * @param idUsuario
	 */
	public void insertarEnvio(String trackEnvio, String direccionEnvio, int idUsuario) {
		SQLiteDatabase db = getWritableDatabase();
		if(db != null){
			ContentValues valores = new ContentValues();
			valores.put(envio_track, trackEnvio);
			valores.put(envio_direccion, direccionEnvio);
			valores.put(envio_idUsuario, idUsuario);
			db.insert(NOMBRE_TABLA_ENVIOS, null, valores);
			db.close();   
		}
	}
	
	/**
	 * Recuperamos todos los usuarios en objetos.
	 * @return Lista de Usuarios, cada item es un objeto.
	 */
	
	public List<usuario> recuperarUsuarios() {
	    SQLiteDatabase db = getReadableDatabase();
	    List<usuario> lista_usuarios = new ArrayList<usuario>();
	    //Definimos las columnas a recuperar.
	    String[] valores_recuperar = {usuario_ID, usuario_nombre, usuario_password, usuario_email};
	    //Definimos un cursor para poder movernos por los registros de la tabla ususarios.
	    Cursor c = db.query(NOMBRE_TABLA_USUARIOS, valores_recuperar, null, null, null, null, null, null);
	    //Movemos el cursor a la primera posición de la tabla.
	  
	    c.moveToFirst();	    
	    do {
	    	//Creamos el objeto con los datos del registro leido.
	    	usuario objusuario = new usuario(c.getInt(0), c.getString(1), c.getString(2), c.getString(3));
	    	//Añadimos el objeto a la lista.
	    	lista_usuarios.add(objusuario);
		} while (c.moveToNext());
	    
	    //Cerramos la base de datos
        db.close();
        //Cerramos el cursor
        c.close();
        
        return lista_usuarios;
	}
	
	/**
	 * Comprobación si hay datos en las tablas.
	 * @return
	 */
	public boolean hayDatos () {
		Boolean ret = false;
		
		SQLiteDatabase db = getReadableDatabase();
		//Definimos las columnas a recuperar.
	    String[] valores_recuperar = {usuario_ID, usuario_nombre, usuario_password, usuario_email};
	    //Definimos un cursor para poder movernos por los registros de la tabla tienda.
	    Cursor c = db.query(NOMBRE_TABLA_USUARIOS, valores_recuperar, null, null, null, null, null, null);
	    if (c.moveToFirst()) ret=true;
	    
		return ret; 
	}

	public usuario recuperarTienda(int id) {
	    SQLiteDatabase db = getReadableDatabase();
	    String[] valores_recuperar = {usuario_ID, usuario_nombre, usuario_password, usuario_email};
	    Cursor c = db.query(NOMBRE_TABLA_USUARIOS, valores_recuperar, "_id=" + id, null, null, null, null, null);
	    if(c != null) {
	        c.moveToFirst();
	    }
		usuario objusuario = new usuario(c.getInt(0), c.getString(1), c.getString(2), c.getString(3));
        db.close();
        c.close();
        return objusuario;
	}

	public List<envio> recuperarEnvios() {
	    SQLiteDatabase db = getReadableDatabase();
	    List<envio> lista_envios = new ArrayList<envio>();
	    //Definimos las columnas a recuperar.
	    String[] valores_recuperar = {envio_id, envio_track, envio_direccion, envio_idUsuario};
	    //Definimos un cursor para poder movernos por los registros de la tabla tienda.
	    Cursor c = db.query(NOMBRE_TABLA_ENVIOS, valores_recuperar, null, null, null, null, null, null);
	    //Movemos el cursor a la primera posición de la tabla.
	    
	    c.moveToFirst();	    
	    do {
	    	//Creamos el objeto con los datos del registro leido.
	    	envio objenvio = new envio(c.getInt(0), c.getString(1), c.getString(2), c.getString(3));
	    	//Añadimos el objeto a la lista.
	    	lista_envios.add(objenvio);
		} while (c.moveToNext());
	    
	    //Cerramos la base de datos
        db.close();
        //Cerramos el cursor
        c.close();
        
        return lista_envios;
	}
	
	/**
	 * Método que reptorna las ofertas de la tienda pasada por parámetro.
	 * @param idTienda
	 * @return
	 */
	
	public List<envio> getOfertasTienda(String idUsuario){
		//Ligamos la tabla de usuarios y de envios medinante el _id
		String queryEnvios = "SELECT " + NOMBRE_TABLA_ENVIOS+ "." + envio_id+ "," + envio_track+ "," + envio_direccion+ "," + 
		envio_idUsuario+ " from " + NOMBRE_TABLA_ENVIOS+ "," + NOMBRE_TABLA_USUARIOS+" WHERE " + 
		NOMBRE_TABLA_USUARIOS+ "." +usuario_ID + "=" + NOMBRE_TABLA_ENVIOS+ "." + envio_idUsuario+ " AND " + 
				NOMBRE_TABLA_USUARIOS+ "."+ usuario_ID + "=" + idUsuario;
				
		SQLiteDatabase db = getReadableDatabase();
	    List<envio> lista_envios = new ArrayList<envio>();
	    //Utilizamos el método rawquery para facilitar el paso de argumentos de la SELECT
	    Cursor c = db.rawQuery(queryEnvios, null);

	    //Movemos el cursor a la primera posición de la tabla.	    
	    c.moveToFirst();	    
	    do {
	    	//Creamos el objeto con los datos del registro leido.
	    	envio  objenvio = new envio(c.getInt(0), c.getString(1), c.getString(2),c.getString(3));
	    	//Añadimos el objeto a la lista.
	    	lista_envios.add(objenvio);
		} while (c.moveToNext());
	    
	    //Cerramos la base de datos
        db.close();
        //Cerramos el cursor
        c.close();
        
        return lista_envios;		
	}
	
	@Override	
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		//Este método se lanzará automáticamente cuando sea necesaria una actualización de la estructura de la base de datos o una conversión de los datos.
		//Un ejemplo puede ser añadir un nuevo campo en la tabla.		
		//Para la presente práctica tan solo nos limitamos a borrar la base de datos actual y crear de nuevo con la nueva estructura.
		db.execSQL("DROP TABLE IF EXISTS " + NOMBRE_BASEDATOS);
		onCreate(db);

	}
	/**
	 * Método para copiar la Base de datos de SQLITE a sdcard.
	 * @throws IOException
	 */
	public void copiarBD(Context ctx) throws IOException{
		
		InputStream myInput = new FileInputStream("/data/data/" + ctx.getPackageName() + "/databases/" + NOMBRE_BASEDATOS);

		File directory = new File(EXTERNAL_FILE_PATH  + Folder + NOMBRE_BASEDATOS);
		if (!directory.exists()) {
			directory.mkdirs();
		}

		OutputStream myOutput = new FileOutputStream(directory.getPath() + "/" + NOMBRE_BASEDATOS);

		byte[] buffer = new byte[1024];
		int length;
		while ((length = myInput.read(buffer)) > 0) {
			myOutput.write(buffer, 0, length);
		}

		myOutput.flush();
		myOutput.close();
		myInput.close();
	}
}
	


