package musicbeans.dataaccess;


import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


public class ImageManager extends AsyncTask<Void,Void,Void>
{
    public String url;
    public ImageView view;

    public ImageManager(String url, ImageView view) {
        this.url = url;
        this.view = view;
    }

    public void loadImages()
    {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

            String _url =url;
            ImageView img = view;
            try {
                final ImageView _img = img;
                StorageReference _ref = storageRef.child(_url);
                final long ONE_MEGABYTE = 1024 * 1024*10;
                _ref.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        _img.setImageBitmap(Bitmap.createBitmap(bmp));

                    }
                });
            }catch (Exception e) {
                System.err.println(e.toString());
            }
    }

    @Override
    protected Void doInBackground(Void... voids) {
        loadImages();
        return null;
    }

    public static musicbeans.dataaccess.Status uploadImage(Uri uri, String name)
    {
        try
        {
            if(uri!=null)
            {
                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference storageRef = storage.getReference();

                StorageReference ref = storageRef.child("img/"+name);
                ref.putFile(uri);
                return musicbeans.dataaccess.Status.REGISTERED;
            }
        }
        catch (Exception e)
        {
            System.err.println(e.toString());
        }
        return musicbeans.dataaccess.Status.IMG_FAILED;
    }
}
