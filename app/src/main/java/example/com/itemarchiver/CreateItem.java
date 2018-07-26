package example.com.itemarchiver;

import android.Manifest;
import android.content.Intent;

import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class CreateItem extends AppCompatActivity {

    Button createItem, cancel;
    EditText name, description, serialNumber, DoP;
    ImageView itemPicture;
    Item item = new Item();
    private DatabaseDataManager databaseDataManager;

    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_item);
        setTitle("Create Item");

        databaseDataManager = new DatabaseDataManager(this);

        createItem = (Button) findViewById(R.id.createItem);
        cancel = (Button) findViewById(R.id.cancel);
        name = (EditText) findViewById(R.id.name);
        description = (EditText) findViewById(R.id.description);
        serialNumber = (EditText) findViewById(R.id.serialNum);
        DoP = (EditText) findViewById(R.id.dateOfPurchase);
        itemPicture = (ImageView) findViewById(R.id.itemPicture);

        itemPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isCameraAllowed()){
                    Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, 0);
                }

                requestCameraPermission();
            }
        });

        createItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                item.setName(name.getText().toString());
                item.setDescription(description.getText().toString());
                item.setSerialNumber(serialNumber.getText().toString());
                item.setDateOfPurchased(DoP.getText().toString());

                if (item.getItemImage() == null){
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.item_default);
                    ByteArrayOutputStream stream=new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] image=stream.toByteArray();
                    String img_str = Base64.encodeToString(image, Base64.DEFAULT);

                    item.setItemImage(img_str);
                }

                Log.d("demo", String.valueOf(item));

                Log.d("demo", String.valueOf(databaseDataManager.getAllItems()));
                Toast.makeText(CreateItem.this, "Item created successfully!", Toast.LENGTH_SHORT).show();

                if (databaseDataManager.saveItem(item) > 0){
                    Intent intent = new Intent(CreateItem.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateItem.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    //We are calling this method to check the permission status
    private boolean isCameraAllowed() {
        //Getting the permission status
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);

        //If permission is granted returning true
        if (result == PackageManager.PERMISSION_GRANTED)
            return true;

        //If permission is not granted returning false
        return false;
    }

    //Requesting permission
    private void requestCameraPermission(){

        if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.CAMERA)){
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }

        //And finally ask for the permission
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
        itemPicture.setImageBitmap(bitmap);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte [] arr = baos.toByteArray();
        String result = Base64.encodeToString(arr, Base64.DEFAULT);
        item.setItemImage(result);

        Log.d("demo", String.valueOf(item.getItemImage()));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        databaseDataManager.close();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}
