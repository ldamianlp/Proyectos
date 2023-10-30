package gadma.gob.ec.appcollectorview;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import ec.gob.gadma.obj.ObjTablaLocal;
import ec.gob.gadma.obj.Util;
import ec.gob.gadma.serv.DataBaseSQLite;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Clase ObjTablaLocal
 */
public class CargarImagenesActivity extends AppCompatActivity {
    EditText txtClaveCatastralImg;
    EditText txtIDPoliticoImg;
    EditText txtUsuarioConnectIMG;
    ImageView ivImagenPredio;
    ImageView ivDibujo;
    int anchoImagen = 400;
    int altoImagen = 400;
    //BigDecimal totalMaximo = new BigDecimal(2000000);
    int totalMaximo = 1000000;//2000000;
   // private final int PICKER = 1;

    private String photoPath;
    private static final int REQUEST_CAMERA = 3;
    AlertDialog.Builder dlgAlert;
    Integer v_calidad_img;

    PhotoViewAttacher photoView;
    PhotoViewAttacher photoViewLevantamiento;

    protected boolean shouldAskPermissions() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cargar_imagenes);
        if (shouldAskPermissions()) {
            askPermissions();
        }

        dlgAlert = new AlertDialog.Builder(this);
        dlgAlert.setTitle("Mensaje de Error...");
        dlgAlert.setCancelable(false);
        dlgAlert.setPositiveButton(android.R.string.ok,null);
        try {
            v_calidad_img = Integer.valueOf(Util.getProperty("CALIDAD_IMG", CargarImagenesActivity.this));
        }catch (IOException e) {
            dlgAlert.setTitle("Mensaje de Error...");
            dlgAlert.setMessage(e.getMessage());
            dlgAlert.create().show();
        }
        txtUsuarioConnectIMG = (EditText)findViewById(R.id.txtUsuarioConnectIMG);
        //Localizar los controles
        txtClaveCatastralImg = (EditText)findViewById(R.id.txtClaveCatastralImg);
        txtIDPoliticoImg = (EditText)findViewById(R.id.txtIDPoliticoImg);
        ivImagenPredio = (ImageView)findViewById(R.id.ivImagenPredio);
        ivDibujo = (ImageView)findViewById(R.id.ivDibujo);



        Button btnCargarImagen = (Button)findViewById(R.id.btnCargarImagen);
        btnCargarImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subirImagenPredio(1);
            }
        });
        Button btnCargarDibujo = (Button)findViewById(R.id.btnCargarDibujo);
        btnCargarDibujo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subirImagenPredio(2);
            }
        });

        //Recuperamos la información pasada en el intent
        Bundle bundle = this.getIntent().getExtras();

        //Construimos el mensaje a mostrar
        txtUsuarioConnectIMG.setText(bundle.getString("USER_CONNECT"));
        txtClaveCatastralImg.setText(bundle.getString("txtClaveCatastral"));
        txtIDPoliticoImg.setText(bundle.getString("txtIDPolitico"));
        cargarImagenesLocales();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        getSupportActionBar().setTitle("Carga de Imagenes");
        //getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void subirImagenPredio(int inTipo){
//        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//        intent.setType("*/*");
//        intent.addCategory(Intent.CATEGORY_OPENABLE);

//        try{
//            startActivityForResult(Intent.createChooser(intent,"Seleccione una Imagen para sibir"),PICKER);
//        }catch (android.content.ActivityNotFoundException e){
//            Toast.makeText(this,"Por favor instale un administrador de archivos",Toast.LENGTH_SHORT);
//        }
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,
                "Seleccione la imagen"), inTipo);
    }
    /*
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            // if (requestCode == 1){//PICKER) {
            Uri imageChoisieUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageChoisieUri);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, v_calidad_img, stream);
                byte[] imagen = stream.toByteArray();
                DataBaseSQLite db = new DataBaseSQLite(this);

                // Inserting Shop/Rows
                ObjTablaLocal contact = new ObjTablaLocal();
                //contact.setDim_codigo(cursor.getInt(0));
                contact.setDim_clave_catastral(txtClaveCatastralImg.getText().toString());
                contact.setDim_id_politico(txtIDPoliticoImg.getText().toString());
                if(requestCode==1){
                    contact.setDim_imagen_pred(imagen);
                    contact.setDim_nombre_ima_pred(txtIDPoliticoImg.getText().toString()+txtClaveCatastralImg.getText().toString()+".jpg");
                    contact.setDim_mimetype_ima_pred("image/jpg");
                }else{
                    contact.setDim_dibujo_pred(imagen);
                    contact.setDim_nombre_dib_pred(txtIDPoliticoImg.getText().toString()+txtClaveCatastralImg.getText().toString()+".jpg");
                    contact.setDim_mimetype_dib_pred("image/jpg");
                }
                contact.setDim_usuario(txtUsuarioConnectIMG.getText().toString());
                contact.setDim_estado("C");
                //db.deleteAllDatos(contact);
                db.addImagen(contact,requestCode,CargarImagenesActivity.this);
                cargarImagenesLocales();
                //db.close();
            }
            catch (IOException e) {
                dlgAlert.setMessage(e.getMessage());
                dlgAlert.create().show();
            }
            //}
        }
    }

    */
    private void cargarImagenesLocales(){
        DataBaseSQLite db = new DataBaseSQLite(this);
        // Reading all shops
        List<ObjTablaLocal> shops = db.getAllDatosByClaveAndIdPoli(
                txtClaveCatastralImg.getText().toString(),
                txtIDPoliticoImg.getText().toString(),
                CargarImagenesActivity.this);
        if(shops!=null) {
            for (ObjTablaLocal shop : shops) {
                if (shop.getDim_imagen_pred() != null) {
                    Bitmap bmp = BitmapFactory.decodeByteArray(shop.getDim_imagen_pred(), 0, shop.getDim_imagen_pred().length);

                    Bitmap tmp = Bitmap.createScaledBitmap(bmp, anchoImagen, altoImagen, false);
                    ivImagenPredio.setImageBitmap(tmp);
                }
                if (shop.getDim_dibujo_pred() != null) {
                    Bitmap bmpDib = BitmapFactory.decodeByteArray(shop.getDim_dibujo_pred(), 0, shop.getDim_dibujo_pred().length);
                    ivDibujo.setImageBitmap(Bitmap.createScaledBitmap(bmpDib, anchoImagen, altoImagen, false));
                }
            }
        }
        photoView = new PhotoViewAttacher(ivImagenPredio);
        //photoView.setMinimumScale(PhotoViewAttacher.DEFAULT_MID_SCALE);
        photoView.update();

        photoViewLevantamiento = new PhotoViewAttacher(ivDibujo);
        photoViewLevantamiento.update();
    }
    /*protected void onActivityResulta(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case PICKER:
                if(resultCode==RESULT_OK){
                        Uri path=data.getData();
                    File myFile = new File(path.getPath());
                    //FilePath=myFile.getAbsolutePath();

                    Uri imageUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                            "://" + getApplicationContext().getResources().getResourcePackageName(1));

                    String FilePath = data.getData().getPath();
                    Toast.makeText(this,FilePath,Toast.LENGTH_SHORT);

                    try {
                        File file = new File(FilePath);

                        FileInputStream fis = new FileInputStream(file);
                        //System.out.println(file.exists() + "!!");
                        //InputStream in = resource.openStream();
                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        byte[] buf = new byte[1024];
                        try {
                            for (int readNum; (readNum = fis.read(buf)) != -1; ) {
                                bos.write(buf, 0, readNum); //no doubt here is 0
                                //Writes len bytes from the specified byte array starting at offset off to this byte array output stream.
                                System.out.println("read " + readNum + " bytes,");
                            }
                        } catch (IOException ex) {
                            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT);
                        }
                        byte[] bytes = bos.toByteArray();
                    }catch (Exception e){
                        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT);
                    }
                    txtImagen.setText(FilePath);
                }
                break;
        }
    }*/
    public void camera(View view)
    {
        switch (view.getId())
        {
            case R.id.btnCamera:

                String state = Environment.getExternalStorageState();
                Log.i("state", state);
                if (Environment.MEDIA_MOUNTED.equals(state))
                {
                    //long captureTime = System.currentTimeMillis();
                    photoPath = Environment.getExternalStorageDirectory() + "/DCIM/Camera/Predio" +
                            txtIDPoliticoImg.getText().toString()+txtClaveCatastralImg.getText().toString() + ".jpg";
                    try
                    {
                        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                        Integer versionSDK = Build.VERSION.SDK_INT;
                        File photo = new File(photoPath);
                        if (versionSDK>=24){ //para verdiones mayores o igual a android 8
                            Uri photoURI = FileProvider.getUriForFile(CargarImagenesActivity.this,
                                    "com.example.android.fileprovider",
                                    photo);
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        }else{
                            intent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(photo));
                        }
                        //intent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(photo));
                        startActivityForResult
                                (Intent.createChooser(intent, "Capture Image"), REQUEST_CAMERA);
                    }
                    catch (Exception e){
                        dlgAlert.setMessage(e.getMessage());
                        dlgAlert.create().show();
                    }
                }
                break;
        }
    }
    protected void askPermissions() {
        String[] permissions = {
                "android.permission.READ_EXTERNAL_STORAGE",
                "android.permission.WRITE_EXTERNAL_STORAGE"
        };
        int requestCode = 200;
        requestPermissions(permissions, requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        //TextView path=(TextView)findViewById(R.id.txtPath);

        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==-1)
            switch (requestCode)
            {
                case REQUEST_CAMERA:
                    if (photoPath!=null && !photoPath.equals(""))
                    {

                        try {
                            FileInputStream fis = null;
                            fis = new FileInputStream(photoPath);

                            Bitmap bitmap = BitmapFactory.decodeStream(fis);
                            //Bitmap bitmap = BitmapFactory.decodeFile(photoPath);
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, v_calidad_img, stream);

                            int resol=100;
                            int total = stream.size();
                            int total1=0;
                            if(total>totalMaximo) {//2097152
                                ByteArrayOutputStream stream1;
                                do {
                                    stream1 = new ByteArrayOutputStream();
                                    bitmap.compress(Bitmap.CompressFormat.JPEG, resol, stream1);
                                    resol=resol-15;
                                    total1 = stream1.size();
                                } while (total1 >= totalMaximo);
                                stream = stream1;
                            }
                            int totalFinal = stream.size();

                            byte[] imagen = stream.toByteArray();
                            DataBaseSQLite db = new DataBaseSQLite(this);

                            // Inserting Shop/Rows
                            ObjTablaLocal contact = new ObjTablaLocal();
                            //contact.setDim_codigo(cursor.getInt(0));
                            contact.setDim_clave_catastral(txtClaveCatastralImg.getText().toString());
                            contact.setDim_id_politico(txtIDPoliticoImg.getText().toString());

                            contact.setDim_imagen_pred(imagen);
                            contact.setDim_nombre_ima_pred(txtIDPoliticoImg.getText().toString()+txtClaveCatastralImg.getText().toString()+".jpg");
                            contact.setDim_mimetype_ima_pred("image/jpg");

                            contact.setDim_usuario(txtUsuarioConnectIMG.getText().toString());
                            contact.setDim_estado("C");
                            //db.deleteAllDatos(contact);
                            db.addImagen(contact,1,CargarImagenesActivity.this);
                            cargarImagenesLocales();
                            //db.close();
                        }catch (FileNotFoundException e){
                            dlgAlert.setMessage(e.getMessage());
                            dlgAlert.create().show();
                        }
                        catch (RuntimeException e) {
                            dlgAlert.setMessage(e.getMessage());
                            dlgAlert.create().show();
                        }
                        /*
                        dlgAlert.setMessage("La foto fue almacenada correctamente");
                        dlgAlert.create().show();*/
                    }


                    break ;
                default:
                    Uri imageChoisieUri = data.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageChoisieUri);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        if(requestCode==1) {
                            bitmap.compress(Bitmap.CompressFormat.JPEG, v_calidad_img, stream);
                        }else{
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                        }
                        int resol=100;
                        int total = stream.size();
                        int total1=0;
                        if(total>totalMaximo) {//2097152
                            ByteArrayOutputStream stream1;
                            do {
                                stream1 = new ByteArrayOutputStream();
                                bitmap.compress(Bitmap.CompressFormat.JPEG, resol, stream1);
                                resol=resol-15;
                                total1 = stream1.size();
                            } while (total1 >= totalMaximo);
                            stream = stream1;
                        }
                        int totalFinal = stream.size();
                        byte[] imagen = stream.toByteArray();
                        DataBaseSQLite db = new DataBaseSQLite(this);

                        // Inserting Shop/Rows
                        ObjTablaLocal contact = new ObjTablaLocal();
                        //contact.setDim_codigo(cursor.getInt(0));
                        contact.setDim_clave_catastral(txtClaveCatastralImg.getText().toString());
                        contact.setDim_id_politico(txtIDPoliticoImg.getText().toString());
                        if(requestCode==1){
                            contact.setDim_imagen_pred(imagen);
                            contact.setDim_nombre_ima_pred(txtIDPoliticoImg.getText().toString()+txtClaveCatastralImg.getText().toString()+".jpg");
                            contact.setDim_mimetype_ima_pred("image/jpg");
                        }else{
                            contact.setDim_dibujo_pred(imagen);
                            contact.setDim_nombre_dib_pred(txtIDPoliticoImg.getText().toString()+txtClaveCatastralImg.getText().toString()+".jpg");
                            contact.setDim_mimetype_dib_pred("image/jpg");
                        }
                        contact.setDim_usuario(txtUsuarioConnectIMG.getText().toString());
                        contact.setDim_estado("C");
                        //db.deleteAllDatos(contact);
                        db.addImagen(contact,requestCode,CargarImagenesActivity.this);
                        cargarImagenesLocales();
                        //db.close();
                    }
                    catch (IOException e) {
                        dlgAlert.setMessage(e.getMessage());
                        dlgAlert.create().show();
                    }
                    break;
            }
        else
            photoPath=null;
    }
}
