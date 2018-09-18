package musicbeans.dataaccess;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class ImageManager extends AsyncTask<Void,Void,Void>
{
    private String url[];
    private ImageView[] view;

    public ImageManager(String[] url, ImageView[] view) {
        this.url = url;
        this.view = view;
    }

    public void loadImages()
    {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        for(int i=0;i<url.length;i++)
        {
            String _url =url[i];
            ImageView img = view[i];
            final ImageView _img = img;
            StorageReference _ref = storageRef.child(_url);
            final  long ONE_MEGABYTE= 1024*1024;
            _ref.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap bmp = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                    _img.setImageBitmap(Bitmap.createScaledBitmap(bmp,_img.getWidth(),_img.getHeight(),false));

                }
            });
        }
    }

    @Override
    protected Void doInBackground(Void... voids) {
        loadImages();
        return null;
    }
}
