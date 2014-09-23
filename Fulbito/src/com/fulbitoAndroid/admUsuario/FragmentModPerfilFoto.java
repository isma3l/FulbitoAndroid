package com.fulbitoAndroid.admUsuario;

import java.io.File;

import com.fulbitoAndroid.clases.SingletonAppSettings;
import com.fulbitoAndroid.clases.SingletonUsuarioLogueado;
import com.fulbitoAndroid.clases.Usuario;
import com.fulbitoAndroid.fulbito.R;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import eu.janmuller.android.simplecropimage.CropImage;

public class FragmentModPerfilFoto extends Fragment {
	
	private static final String TAG="FragmentModPerfilFoto"; 
	// no se puede traer la constante desde import android.app.Activity;
	private static final int RESULT_OK = -1;
	
	//Request codes
	private static final int CAMERA_INTENT_REQUEST = 2000;
	private static final int GALLERY_INTENT_REQUEST = 2001;
	private static final int CROP_INTENT_REQUEST = 2002;
	
	//Solo para cuando se saca una foto
	private File     mFileTemp;
	public static final String TEMP_PHOTO_FILE_NAME = "PrfPic.jpg";
	
	@Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
			

		return inflater.inflate(R.layout.fragment_mod_perfil_foto, container, false);
        
    }
 
	
	//Solo utilizado para el crop de la foto tomada por camara
    @Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		ImageView imgAvatar = (ImageView)getView().findViewById(R.id.imageView1);
		String sLocalPath="";
	
		if(sLocalPath.equals(""))
		{
			imgAvatar.setImageResource(R.drawable.no_avatar);
		}
		else
		{
			Bitmap photo = BitmapFactory.decodeFile(sLocalPath);
			imgAvatar.setImageBitmap(photo);
		}
	}



	private void startCropImage() {
       
		Intent intent = new Intent(getActivity().getApplicationContext(), CropImage.class);
    	 
        intent.putExtra(CropImage.IMAGE_PATH, mFileTemp.getPath());
        intent.putExtra(CropImage.OUTPUT_X,200);
        intent.putExtra(CropImage.OUTPUT_Y,200);
        intent.putExtra(CropImage.SCALE, true);
        intent.putExtra(CropImage.ASPECT_X, 3);
        intent.putExtra(CropImage.ASPECT_Y, 3);
		try{
        getActivity().startActivityForResult(intent, CROP_INTENT_REQUEST);
		}catch(ActivityNotFoundException anfe)
		{
			Log.d(TAG,anfe.getMessage());			
		}
    }
	
    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);
        
		
    	String estado = Environment.getExternalStorageState();
    	if (Environment.MEDIA_MOUNTED.equals(estado)) {
    		String sPath = SingletonAppSettings.getAppSettings().getsBasePath();
    		sPath = sPath + SingletonAppSettings.getAppSettings().getsMediaPerfilPath();
    		mFileTemp = new File(sPath, TEMP_PHOTO_FILE_NAME);
    	}
    	else 
    	{
    		//Me traigo el getfilesDir desde el singleton, ya que desde ahi puedo tener el conexto de la aplicacion
    		mFileTemp = new File(SingletonAppSettings.getFilesDir(), TEMP_PHOTO_FILE_NAME);
    	}
        
		ImageButton btnBuscarFoto = (ImageButton) getView().findViewById(R.id.btnBuscarFoto);
		btnBuscarFoto.setOnClickListener(new OnClickListener() {
	        @Override
	        public void onClick(View v) {
	        	Intent pickPhoto = new Intent(Intent.ACTION_PICK,
	        	           android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
	        	pickPhoto.putExtra("crop", "true");
	        	pickPhoto.putExtra("outputX", 200);
	        	pickPhoto.putExtra("outputY", 200);
	        	pickPhoto.putExtra("aspectX", 1);
	        	pickPhoto.putExtra("aspectY", 1);
	        	pickPhoto.putExtra("scale", true);
	        	getActivity().startActivityForResult(pickPhoto , GALLERY_INTENT_REQUEST);	
	        }
	    }); 
		
		ImageButton btnTomarFoto = (ImageButton) getView().findViewById(R.id.btnTomarFoto);
		btnTomarFoto.setOnClickListener(new OnClickListener() {
	        @Override
	        public void onClick(View v) {
        	
	            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

	            try {
	            	Uri mImageCaptureUri = null;
	            	String state = Environment.getExternalStorageState();
	            	if (Environment.MEDIA_MOUNTED.equals(state)) {
	            		mImageCaptureUri = Uri.fromFile(mFileTemp);
	            	}
	            	else 
	            	{
	    	        	//The solution is taken from here: http://stackoverflow.com/questions/10042695/how-to-get-camera-result-as-a-uri-in-data-folder
	    	        	mImageCaptureUri = InternalStorageContentProvider.CONTENT_URI;
	            	}	
	                intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
	                intent.putExtra("return-data", true);
	                getActivity().startActivityForResult(intent, CAMERA_INTENT_REQUEST);
	            } catch (ActivityNotFoundException e) {

	                Log.d(TAG, "No se puede tomar una foto", e);
	            }	        	

	        	
	        }
	    });

      
    }

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch(requestCode) 
		{
		//Camara
		case CAMERA_INTENT_REQUEST:
		    if(resultCode == RESULT_OK){  
		    	//Para ver los extras de un intent
		    	/*Bundle extras = data.getExtras();
		    	String result="";
		        if (extras != null)
		        {
		        	result += "key count: " + extras.keySet().size();
		            for (String key : extras.keySet())
		            {
		            	result += "\n" + key + extras.get(key);
		            }
		        }*/
		    	startCropImage();
		    }

		break; 
		//Galeria
		case GALLERY_INTENT_REQUEST:
		    if(resultCode == RESULT_OK){  
		    	Uri uri = data.getData();
		    	((ImageView) getView().findViewById(R.id.imageView1)).setImageURI(uri);
		    }
		break;
        case CROP_INTENT_REQUEST:

            String path = data.getStringExtra(CropImage.IMAGE_PATH);
            if (path == null) 
            {
                return;
            }
            Bitmap photo = BitmapFactory.decodeFile(mFileTemp.getPath());
            ((ImageView) getView().findViewById(R.id.imageView1)).setImageBitmap(photo);
            break;		
	   }
	}	
	
}
