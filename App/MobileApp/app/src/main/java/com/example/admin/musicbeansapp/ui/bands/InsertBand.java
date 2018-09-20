package com.example.admin.musicbeansapp.ui.bands;


import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import com.example.admin.musicbeansapp.R;
import musicbeans.dataaccess.Account;
import musicbeans.dataaccess.Status;
import musicbeans.entities.Band;
import musicbeans.entities.Client;


public class InsertBand extends AppCompatActivity {

    EditText name;
    EditText username;
    EditText password;
    EditText confirm;
    EditText description;
    public Uri path=null;
    public Uri path_banner=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_band);
        username = (EditText)findViewById(R.id.txtUsername);
        password = (EditText)findViewById(R.id.txtPassword);
        confirm = (EditText)findViewById(R.id.txtConfirm);
        description = (EditText)findViewById(R.id.txtDescription);
    }
    public void uploadPhoto(View v)
    {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto,1);

    }
    public void uploadPhoto_banner(View v)
    {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto,2);

    }
    @Override
    public void onActivityResult(int requestCode,int resultCode, Intent imageReturnedIntent)
    {
        super.onActivityResult(requestCode,resultCode,imageReturnedIntent);
        switch (requestCode)
        {
            case 0:
                if(resultCode == RESULT_OK){
                    Log.i("MainActivity","case 0");
                }
                break;
            case 1:
                if(resultCode == RESULT_OK){
                    ImageView img = (ImageView)findViewById(R.id.imgPhoto);
                    Uri selectedImage = imageReturnedIntent.getData();

                    Log.i("UserRegister","Selected image = "+selectedImage);
                    img.setImageURI(selectedImage);
                    this.path=selectedImage;
                }
                break;
            case 2:
                if(resultCode == RESULT_OK){
                    ImageView img = (ImageView)findViewById(R.id.imgBanner);
                    Uri selectedImage = imageReturnedIntent.getData();

                    Log.i("UserRegister","Selected image = "+selectedImage);
                    img.setImageURI(selectedImage);
                    this.path_banner=selectedImage;
                }
                break;
        }
    }
    public void insertBand(View v)
    {
        boolean _continue=true;
        if(username.getText().toString().trim().isEmpty())
        {
            username.setError("Campo requerido");
            _continue=false;
        }
        if(password.getText().toString().trim().isEmpty())
        {
            password.setError("Campo requerido");
            _continue=false;
        }
        if(confirm.getText().toString().trim().isEmpty())
        {
            confirm.setError("Campo requerido");
            _continue=false;
        }
        if(description.getText().toString().trim().isEmpty())
        {
            description.setError("Campo requerido");
            _continue=false;
        }
        if(!confirm.getText().toString().equals(password.getText().toString()))
        {
            password.setError("Las contraseñas no coinciden");
            _continue=false;
        }

        if(_continue)
        {
            RegisterBand registerBand = new RegisterBand();
            registerBand.execute(username.getText().toString(),password.getText().toString(),description.getText().toString());
        }
    }
    private byte[] getBytes(Uri uri)
    {
        Uri path = uri;
        if(path==null)return null;
        try
        {
            InputStream iStream = getContentResolver().openInputStream(path);
            ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
            int bufferSize = 1024;
            byte[] buffer = new byte[bufferSize];
            int len =0;
            while((len = iStream.read(buffer))!= -1){
                byteBuffer.write(buffer,0,len);
            }
            return byteBuffer.toByteArray();
        }
        catch (Exception e)
        {
            System.err.println(e.toString());
        }
        return  null;

    }
    class RegisterBand extends AsyncTask<Object,Void,Status>
    {
        public RegisterBand(){}
        protected musicbeans.dataaccess.Status doInBackground(Object... fields)
        {
            musicbeans.dataaccess.Status status =null;
            String user = (String) fields[0];
            String password = (String )fields[1];
            String description = (String) fields[2];
            byte[] photo = getBytes(path);
            byte[] banner = getBytes(path_banner);
            Account account = new Account();
            status = account.registerBand(path,new Band(user, password, photo, "", description, (byte) 5, banner));
            return status;
        }
        protected void onPostExecute(musicbeans.dataaccess.Status status)
        {
            if(status== musicbeans.dataaccess.Status.REPEATED_USER)
            {
                Toast.makeText(getApplicationContext(),"Ese usuario ya existe", Toast.LENGTH_SHORT).show();
            }
            if(status== musicbeans.dataaccess.Status.REGISTERED)
            {
                Toast.makeText(getApplicationContext(),"Registro correcto", Toast.LENGTH_SHORT).show();

            }
            if(status== musicbeans.dataaccess.Status.NETWORK_ERROR)
            {
                Toast.makeText(getApplicationContext(),"Error de conexión", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
