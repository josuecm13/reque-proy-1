package com.example.admin.musicbeansapp;

import android.net.Credentials;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import musicbeans.dataaccess.Account;
import musicbeans.dataaccess.Status;
import musicbeans.entities.Client;
import musicbeans.entities.Credential;

public class UserLoginActivity extends AppCompatActivity {

    EditText username;
    EditText password;
    Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_user_login);
        username = (EditText)findViewById(R.id.txtUsername);
        password = (EditText)findViewById(R.id.txtPassword);
        login = (Button)findViewById(R.id.btnLogin);
    }
    public void loguserin (View v)
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
        if(_continue)
        {
            LoginUser loginUser = new LoginUser();
            loginUser.execute(username.getText().toString(),password.getText().toString());
        }
    }

    class LoginUser extends AsyncTask<String,Void,Status>
    {
        public LoginUser(){}
        protected musicbeans.dataaccess.Status doInBackground(String... fields)
        {
            musicbeans.dataaccess.Status status =null;
            String user = fields[0];
            String password = fields[1];
            Account account = new Account();
            status = account.login(new Client(user,password,""));
            return status;
        }
        protected void onPostExecute(musicbeans.dataaccess.Status status)
        {
            if(status== musicbeans.dataaccess.Status.WRONG_CREDENTIALS)
            {
                Toast.makeText(getApplicationContext(),"User or password incorrect", Toast.LENGTH_SHORT).show();
            }
            if(status== musicbeans.dataaccess.Status.CLIENT)
            {
                Toast.makeText(getApplicationContext(),"Client", Toast.LENGTH_SHORT).show();

            }
            if(status== musicbeans.dataaccess.Status.ADMIN)
            {
                Toast.makeText(getApplicationContext(),"Admin", Toast.LENGTH_SHORT).show();
            }
            if(status== musicbeans.dataaccess.Status.BAND)
            {
                Toast.makeText(getApplicationContext(),"Band", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
