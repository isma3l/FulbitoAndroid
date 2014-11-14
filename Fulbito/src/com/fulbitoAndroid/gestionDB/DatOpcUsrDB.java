/* ----------------------------------------------------------------------------- 
Nombre: 		DatOpcUsrDB
Descripci�n:	Clase que maneja el acceso a la BD para mantener los datos de la 
				tabla DATOS_OPC_USUARIO

Log de modificaciones:

Fecha		Autor		Descripci�n
13/11/2014	MAC			Creaci�n
----------------------------------------------------------------------------- */
package com.fulbitoAndroid.gestionDB;

import java.util.ArrayList;
import java.util.List;

import com.fulbitoAndroid.clases.Usuario;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DatOpcUsrDB {
	
	FulbitoSQLiteHelper dbHelper;

	public DatOpcUsrDB() {
		dbHelper = SingletonFulbitoDB.getInstance();
	}	
	
	//Metodo para insertar un registro en la tabla DATOS_OPC_USUARIO
	public boolean bInsertarDatOpcUsr(Usuario usr){
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		 
        //Si hemos abierto correctamente la base de datos
        if(db != null)
        {
        	ContentValues values = new ContentValues();
            
        	values.put(FulbitoSQLiteHelper.DAT_OPC_USR_ID			, usr.getId());
        	values.put(FulbitoSQLiteHelper.DAT_OPC_USR_FECHA_NAC	, usr.getFechaNacimiento());
        	values.put(FulbitoSQLiteHelper.DAT_OPC_USR_UBICACION	, usr.getUbicacion());
        	values.put(FulbitoSQLiteHelper.DAT_OPC_USR_LATITUD		, usr.getUbicacionLatitud());
        	values.put(FulbitoSQLiteHelper.DAT_OPC_USR_LONGITUD		, usr.getUbicacionLongitud());
        	values.put(FulbitoSQLiteHelper.DAT_OPC_USR_SEXO			, usr.getSexo());
        	values.put(FulbitoSQLiteHelper.DAT_OPC_USR_TELEFONO		, usr.getTelefono());
        	values.put(FulbitoSQLiteHelper.DAT_OPC_USR_RADIO_BUSQ	, usr.getRadioBusqueda());
        	
        	db.insert(FulbitoSQLiteHelper.TABLA_DAT_OPC_USR, null, values);        	
        	
            //Cerramos la base de datos
            db.close();
        }

        return true;
	}
	
	//Metodo para eliminar toda la tabla DATOS_OPC_USUARIO
	public boolean bDeleteAllDatOpcUsr(){
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		 
        //Si hemos abierto correctamente la base de datos
        if(db != null)
        {
        	db.delete(FulbitoSQLiteHelper.TABLA_DAT_OPC_USR, null, null);
        	
			//Cerramos la base de datos
			db.close();  
        }

        return true;
	}
	
	//Metodo para eliminar un registro de la tabla DATOS_OPC_USUARIO filtrando por ID
	public boolean bDeleteDatOpcUsrById(int iIdUsuario){
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		 
        //Si hemos abierto correctamente la base de datos
        if(db != null)
        {        
        	String FILTROS = FulbitoSQLiteHelper.DAT_OPC_USR_ID + " = " + Integer.toString(iIdUsuario);
        	
        	db.delete(FulbitoSQLiteHelper.TABLA_DAT_OPC_USR, FILTROS, null);
			
			//Cerramos la base de datos
			db.close(); 
        }

        return true;
	}
	
	//Metodo para obtener un registro de la tabla DATOS_OPC_USUARIO filtrando por ID
	public Usuario usrSelectDatOpcUsrById(int iIdUsuario){
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Usuario usr = new Usuario();
		
        //Si hemos abierto correctamente la base de datos
        if(db != null)
        {        	        
        	String[] COLUMNAS = {
        			FulbitoSQLiteHelper.DAT_OPC_USR_ID, 
        			FulbitoSQLiteHelper.DAT_OPC_USR_FECHA_NAC, 
        			FulbitoSQLiteHelper.DAT_OPC_USR_UBICACION, 
        			FulbitoSQLiteHelper.DAT_OPC_USR_LATITUD,
        			FulbitoSQLiteHelper.DAT_OPC_USR_LONGITUD,
        			FulbitoSQLiteHelper.DAT_OPC_USR_SEXO, 
        			FulbitoSQLiteHelper.DAT_OPC_USR_TELEFONO,
        			FulbitoSQLiteHelper.DAT_OPC_USR_RADIO_BUSQ
        		};
        	
        	String FILTROS = FulbitoSQLiteHelper.DAT_OPC_USR_ID + " = " + Integer.toString(iIdUsuario);
        	
        	Cursor cursor = 
                    db.query(
                    		FulbitoSQLiteHelper.TABLA_DAT_OPC_USR, // a. table
                    		COLUMNAS, // b. column names
                    		FILTROS, // c. selections 
                    		null, // d. selections args
                    		null, // e. group by
                    		null, // f. having
                    		null, // g. order by
                    		null // h. limit
                    	); 
        	
        	if (cursor != null)
        	{
        		cursor.moveToFirst();

                usr.setId(cursor.getInt(0));               
                usr.setFechaNacimiento(cursor.getString(1));
                usr.setUbicacion(cursor.getString(2));
                usr.setUbicacionLatitud(cursor.getString(3));
                usr.setUbicacionLongitud(cursor.getString(4));
                usr.setSexo(cursor.getString(5));
                usr.setTelefono(cursor.getString(6));
                usr.setRadioBusqueda(cursor.getInt(7));
                
                cursor.close();
        	}
                    	
            //Cerramos la base de datos
            db.close();
 
        }

        return usr;
	}
	
	//Metodo para obtener todos los registros de la tabla DATOS_OPC_USUARIO
	public List<Usuario> usrSelectAllDatOpcUsr(){
		SQLiteDatabase db = dbHelper.getReadableDatabase();		
		
		List<Usuario> listaUsuarios = new ArrayList<Usuario>();
		
        //Si hemos abierto correctamente la base de datos
        if(db != null)
        {        	        
        	String[] COLUMNAS = {
        			FulbitoSQLiteHelper.DAT_OPC_USR_ID, 
        			FulbitoSQLiteHelper.DAT_OPC_USR_FECHA_NAC, 
        			FulbitoSQLiteHelper.DAT_OPC_USR_UBICACION, 
        			FulbitoSQLiteHelper.DAT_OPC_USR_LATITUD,
        			FulbitoSQLiteHelper.DAT_OPC_USR_LONGITUD,
        			FulbitoSQLiteHelper.DAT_OPC_USR_SEXO, 
        			FulbitoSQLiteHelper.DAT_OPC_USR_TELEFONO,
        			FulbitoSQLiteHelper.DAT_OPC_USR_RADIO_BUSQ
        		};   	
        	
        	Cursor cursor = 
                    db.query(
                    		FulbitoSQLiteHelper.TABLA_DAT_OPC_USR, // a. table
                    		COLUMNAS, // b. column names
                    		null, // c. selections 
                    		null, // d. selections args
                    		null, // e. group by
                    		null, // f. having
                    		null, // g. order by
                    		null // h. limit
                    	);                 	
        	
        	if (cursor != null)
        	{
        		if(cursor .moveToFirst()) 
        		{
                    while (cursor.isAfterLast() == false) 
                    {
                    	Usuario usr = new Usuario();
                    	
                    	usr.setId(cursor.getInt(0));               
                        usr.setFechaNacimiento(cursor.getString(1));
                        usr.setUbicacion(cursor.getString(2));
                        usr.setUbicacionLatitud(cursor.getString(3));
                        usr.setUbicacionLongitud(cursor.getString(4));
                        usr.setSexo(cursor.getString(5));
                        usr.setTelefono(cursor.getString(6));
                        usr.setRadioBusqueda(cursor.getInt(7));
                    	
                        listaUsuarios.add(usr);
                        cursor.moveToNext();
                    }
                }
                
                cursor.close();
        	}
                    	
            //Cerramos la base de datos
            db.close();
 
        }

        return listaUsuarios;
	}
	
	//Metodo para retornar la cantidad de registros de la tabla DATOS_OPC_USUARIO
	public int iGetDatOpcUsrCount(){
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		
		String countQuery = "SELECT 1 FROM " + FulbitoSQLiteHelper.TABLA_DAT_OPC_USR;				
		int iCount = 0;
		
        //Si hemos abierto correctamente la base de datos
        if(db != null)
        {
        	Cursor cursor = db.rawQuery(countQuery, null);
    		iCount = cursor.getCount();
        	cursor.close();
            db.close();            
        }

        return iCount;
	}
	
	//Metodo para updatear un registro en la tabla DATOS_OPC_USUARIO
	public boolean bUpdateUsuario(Usuario usr){
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		 
        //Si hemos abierto correctamente la base de datos
        if(db != null)
        {
        	ContentValues values = new ContentValues();
            
        	values.put(FulbitoSQLiteHelper.DAT_OPC_USR_ID			, usr.getId());
        	values.put(FulbitoSQLiteHelper.DAT_OPC_USR_FECHA_NAC	, usr.getFechaNacimiento());
        	values.put(FulbitoSQLiteHelper.DAT_OPC_USR_UBICACION	, usr.getUbicacion());
        	values.put(FulbitoSQLiteHelper.DAT_OPC_USR_LATITUD		, usr.getUbicacionLatitud());
        	values.put(FulbitoSQLiteHelper.DAT_OPC_USR_LONGITUD		, usr.getUbicacionLongitud());
        	values.put(FulbitoSQLiteHelper.DAT_OPC_USR_SEXO			, usr.getSexo());
        	values.put(FulbitoSQLiteHelper.DAT_OPC_USR_TELEFONO		, usr.getTelefono());
        	values.put(FulbitoSQLiteHelper.DAT_OPC_USR_RADIO_BUSQ	, usr.getRadioBusqueda());
        	
        	String FILTROS = FulbitoSQLiteHelper.DAT_OPC_USR_ID + " = " + Integer.toString(usr.getId());
        	
        	db.update(FulbitoSQLiteHelper.TABLA_DAT_OPC_USR, values, FILTROS, null);        	

            //Cerramos la base de datos
            db.close();
        }

        return true;
	}
}