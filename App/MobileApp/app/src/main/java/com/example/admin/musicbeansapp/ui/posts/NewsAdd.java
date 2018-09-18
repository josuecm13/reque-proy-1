package com.example.admin.musicbeansapp.ui.posts;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.admin.musicbeansapp.R;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import musicbeans.dataaccess.Account;
import musicbeans.dataaccess.Posts;
import musicbeans.dataaccess.Status;
import musicbeans.entities.Client;
import musicbeans.entities.NewsItem;


public class NewsAdd extends AppCompatActivity {

    EditText title;
    EditText body;
    Uri path=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_add);
        title = (EditText)findViewById(R.id.txtTitle);
        body = (EditText)findViewById(R.id.txtBody);
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
    public void insertNews(View v)
    {
        boolean _continue=true;
        if(title.getText().toString().trim().isEmpty())
        {
            title.setError("Campo requerido");
            _continue=false;
        }
        if(body.getText().toString().trim().isEmpty())
        {
            body.setError("Campo requerido");
            _continue=false;
        }

        if(_continue)
        {
           RegisterUser registerUser = new RegisterUser();
           registerUser.execute(title.getText().toString(),body.getText().toString());
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
            String title = (String) fields[0];
            String body = (String )fields[1];
            byte[] photo = getBytes();
            Account account = new Account();
            status = Posts.publishNews(path, new NewsItem(title,body,photo));
            return status;
        }
        protected void onPostExecute(musicbeans.dataaccess.Status status)
        {
            if(status== musicbeans.dataaccess.Status.REGISTERED)
            {
                Toast.makeText(getApplicationContext(),"Noticias publicada", Toast.LENGTH_SHORT).show();
                title.setText("");
                body.setText("");
                path=null;

            }
            if(status== musicbeans.dataaccess.Status.NETWORK_ERROR)
            {
                Toast.makeText(getApplicationContext(),"Error de conexión", Toast.LENGTH_SHORT).show();
            }
            if(status== musicbeans.dataaccess.Status.IMG_FAILED)
            {
                Toast.makeText(getApplicationContext(),"Se guardó la noticia pero no su imagen", Toast.LENGTH_SHORT).show();
                title.setText("");
                body.setText("");
                path=null;
            }
        }
    }
}
