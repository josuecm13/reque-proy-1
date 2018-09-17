package com.example.admin.musicbeansapp.ui.bands.fragments;

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
import java.util.Calendar;

import musicbeans.dataaccess.Account;
import musicbeans.dataaccess.Posts;
import musicbeans.dataaccess.Status;
import musicbeans.entities.Client;
import musicbeans.entities.Event;


public class AddEvent extends AppCompatActivity {

    EditText title;
    EditText location;
    EditText date;
    EditText description;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        title = (EditText)findViewById(R.id.txtTitle);
        location = (EditText)findViewById(R.id.txtLocation);
        date = (EditText)findViewById(R.id.txtDate);
        description = (EditText)findViewById(R.id.txtDescription);
    }


    public void insertEvent(View v)
    {
        boolean _continue=true;
        if(title.getText().toString().trim().isEmpty())
        {
            title.setError("Campo requerido");
            _continue=false;
        }
        if(location.getText().toString().trim().isEmpty())
        {
            location.setError("Campo requerido");
            _continue=false;
        }
        if(description.getText().toString().trim().isEmpty())
        {
            description.setError("Campo requerido");
            _continue=false;
        }
        if(date.getText().toString().trim().isEmpty())
        {
            date.setError("Campo requerido");
            _continue=false;
        }

        if(_continue)
        {
           RegisterUser registerUser = new RegisterUser();
           registerUser.execute(title.getText().toString(),location.getText().toString(),date.getText().toString(),description.getText().toString());
        }
    }

    class RegisterUser extends AsyncTask<Object,Void,Status>
    {
        public RegisterUser(){}
        protected musicbeans.dataaccess.Status doInBackground(Object... fields)
        {
            musicbeans.dataaccess.Status status =null;
            String title = (String) fields[0];
            String location = (String )fields[1];
            String date = (String) fields[2];
            String description = (String) fields[3];

            status = Posts.addEvent(new Event(Calendar.getInstance().getTime(),location,title,description,""));
            return status;
        }
        protected void onPostExecute(musicbeans.dataaccess.Status status)
        {
            if(status== musicbeans.dataaccess.Status.REPEATED_USER)
            {
                Toast.makeText(getApplicationContext(),"Ya existe un evento en esa fecha", Toast.LENGTH_SHORT).show();
            }
            if(status== musicbeans.dataaccess.Status.REGISTERED)
            {
                Toast.makeText(getApplicationContext(),"Registro correcto", Toast.LENGTH_SHORT).show();
                date.setText("");
                location.setText("");
                description.setText("");
                title.setText("");

            }
            if(status== musicbeans.dataaccess.Status.NETWORK_ERROR)
            {
                Toast.makeText(getApplicationContext(),"Error de conexión", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
