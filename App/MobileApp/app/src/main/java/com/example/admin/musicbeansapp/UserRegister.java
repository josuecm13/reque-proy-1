package com.example.admin.musicbeansapp;

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

import musicbeans.dataaccess.Account;
import musicbeans.dataaccess.Status;
import musicbeans.entities.Client;


public class UserRegister extends AppCompatActivity {

    EditText name;
    EditText username;
    EditText password;
    EditText confirm;
    Uri path=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);
        name = (EditText)findViewById(R.id.txtName);
        username = (EditText)findViewById(R.id.txtUsername);
        password = (EditText)findViewById(R.id.txtPassword);
        confirm = (EditText)findViewById(R.id.txtConfirm);
    }
    public void uploadPhoto(View v)
    {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto,1);

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
        }
    }
    public void insertUser(View v)
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
        if(name.getText().toString().trim().isEmpty())
        {
            name.setError("Campo requerido");
            _continue=false;
        }
        if(!confirm.getText().toString().equals(password.getText().toString()))
        {
            password.setError("Las contraseñas no coinciden");
            _continue=false;
        }

        if(_continue)
        {
           RegisterUser registerUser = new RegisterUser();
           registerUser.execute(username.getText().toString(),username.getText().toString(),password.getText().toString());
        }
    }
    private byte[] getBytes()
    {

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
    class RegisterUser extends AsyncTask<Object,Void,Status>
    {
        public RegisterUser(){}
        protected musicbeans.dataaccess.Status doInBackground(Object... fields)
        {
            musicbeans.dataaccess.Status status =null;
            String name = (String) fields[0];
            String user = (String )fields[1];
            String password = (String) fields[2];
            byte[] photo = getBytes();
            Account account = new Account();
            status = account.registerClient(new Client(user,password,photo,name));
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
