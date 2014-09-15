package com.fulbitoAndroid.admUsuario;

import java.util.List;

import org.apache.http.NameValuePair;

import com.fulbitoAndroid.clases.Usuario;
import com.fulbitoAndroid.fulbito.WebServiceFulbito;
import com.fulbitoAndroid.herramientas.CoDecJSON;
import com.fulbitoAndroid.herramientas.CodificadorNameValuePair;
import com.fulbitoAndroid.herramientas.RespuestaWebService;

public class WebServiceRegistrarUsuario extends WebServiceFulbito{
	//WebService Registrar
	private static final String S_WEBSERVICE_REGISTRAR		= "login/registrar_usuario";
	public static final String S_WS_REGISTRAR_PAR_ALIAS		= "alias";
	public static final String S_WS_REGISTRAR_PAR_CORREO 	= "email";
	public static final String S_WS_REGISTRAR_PAR_CLAVE 	= "password";
	
	public WebServiceRegistrarUsuario(){}
		
	public boolean bRegistrarUsuario(Usuario cUsrLogin, RespuestaWebService cRespWS){
		
		String sError = "";
		String sData = "";
		
		//Generamos el parametro a enviar al WebService
		CodificadorNameValuePair cCodNVP = new CodificadorNameValuePair();
    	List<NameValuePair> listaParametros = cCodNVP.CodificarNVP_Registrar(cUsrLogin);
    	//Invocamos el WebService
    	vSetWebservice(S_WEBSERVICE_REGISTRAR);
    	vEjecutarWebservice(listaParametros, cRespWS);
    	//Procesamos la respuesta
    	sError = cRespWS.sGetError();
    	sData = cRespWS.sGetData();
    	
    	if(sError.equalsIgnoreCase(S_RESP_ERROR))
		{
    		//El webservice envio una respuesta con error
			return false;
		}
    	else
		{
			//El webservice envio una respuesta valida con los datos del usuario logueado  
    		CoDecJSON cCodJSON = new CoDecJSON();
    		
			Usuario usrJSON = cCodJSON.usrDecodificarJSON_Login(sData);
			usrJSON.setPassword(cUsrLogin.getPassword());

			cUsrLogin = usrJSON;
			
			return true;
		}
	}	
}