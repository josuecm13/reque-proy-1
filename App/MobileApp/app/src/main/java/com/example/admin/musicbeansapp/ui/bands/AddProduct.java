package com.example.admin.musicbeansapp.ui.bands;

import android.app.ProgressDialog;
import android.content.Context;
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
import com.example.admin.musicbeansapp.adapters.ProductProfileAdapter;
import com.example.admin.musicbeansapp.ui.bands.fragments.ProductProfile;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import musicbeans.dataaccess.Account;
import musicbeans.dataaccess.Product;
import musicbeans.dataaccess.Status;
import musicbeans.entities.Client;
import musicbeans.entities.ViewBag;


public class AddProduct extends AppCompatActivity {

    EditText name;
    EditText type;
    EditText price;
    EditText stock;
    Uri path=null;
    ProductProfileAdapter adpater=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        name = (EditText)findViewById(R.id.txtName);
        type = (EditText)findViewById(R.id.txtType);
        price = (EditText)findViewById(R.id.txtPrice);
        stock = (EditText)findViewById(R.id.txtStock);
        adpater = (ProductProfileAdapter) ViewBag.get("product");
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
        if(name.getText().toString().trim().isEmpty())
        {
            name.setError("Campo requerido");
            _continue=false;
        }
        if(type.getText().toString().trim().isEmpty())
        {
            type.setError("Campo requerido");
            _continue=false;
        }
        if(stock.getText().toString().trim().isEmpty())
        {
            stock.setError("Campo requerido");
            _continue=false;
        }
        if(price.getText().toString().trim().isEmpty())
        {
            price.setError("Campo requerido");
            _continue=false;
        }

        if(_continue)
        {
            double _price = Double.parseDouble(price.getText().toString());
            int _stock = Integer.parseInt(stock.getText().toString());
            String _name = name.getText().toString();
            String _type = type.getText().toString();
            RegisterUser registerUser = new RegisterUser(this);
            registerUser.execute(_name,_type,_price,_stock);
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
        ProgressDialog progressDialog ;
        musicbeans.entities.Product p;
        public RegisterUser(){}
        public RegisterUser(Context context)
        {
            progressDialog = new ProgressDialog(context);
        }
        protected musicbeans.dataaccess.Status doInBackground(Object... fields)
        {
            musicbeans.dataaccess.Status status =null;
            String name = (String) fields[0];
            String type = (String )fields[1];
            double price = (Double) fields[2];
            int stock = (Integer) fields[3];
            byte[] photo =null;
            p=new musicbeans.entities.Product(name,type,price,stock,photo);
            status = Product.addProduct(path,p);
            return status;
        }

        protected void onPostExecute(musicbeans.dataaccess.Status status)
        {
            progressDialog.dismiss();
            if(status== musicbeans.dataaccess.Status.REPEATED_USER)
            {
                Toast.makeText(getApplicationContext(),"Ese usuario ya existe", Toast.LENGTH_SHORT).show();
            }
            if(status== musicbeans.dataaccess.Status.IMG_FAILED)
            {
                Toast.makeText(getApplicationContext(),"El producto se insertó pero no se cargó su imagen", Toast.LENGTH_SHORT).show();
                path=null;
                name.setText("");
                type.setText("");
                price.setText("");
                stock.setText("");
            }
            if(status== musicbeans.dataaccess.Status.REGISTERED)
            {
                p.setID((Integer)ViewBag.get("product_id"));
                adpater.addProduct(p);
                Toast.makeText(getApplicationContext(),"Registro correcto", Toast.LENGTH_SHORT).show();
                path=null;
                name.setText("");
                type.setText("");
                price.setText("");
                stock.setText("");

            }
            if(status== musicbeans.dataaccess.Status.NETWORK_ERROR)
            {
                Toast.makeText(getApplicationContext(),"Error de conexión", Toast.LENGTH_SHORT).show();
            }
        }

        protected void onProgressUpdate(Void... values)
        {
            Toast.makeText(getApplicationContext(),"Subiendo...", Toast.LENGTH_SHORT).show();
        }
    }
}
