package ec.gob.gadma.obj;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import gadma.gob.ec.appcollectorview.R;

public class Util {
    //private static String ruta1 = Environment.getExternalStorageDirectory() + "/Android/data/CatastrosConfig" ;
    private static File ruta = Environment.getExternalStorageDirectory() ;
    private static Integer versionSDK = Build.VERSION.SDK_INT;

    public static String getProperty(String key,Context context) throws IOException {
        Properties properties = new Properties();
        //File myDir  = new File(ruta);
        File myDir  ;
        if (versionSDK>=24){
            myDir  = new File(ruta.getAbsolutePath());
        }else{
            myDir  = new File(ruta.getAbsolutePath()+ "/Android/data/CatastrosConfig");
        }
        //File myDir = new File(ruta.getAbsolutePath() + "/Android/data/CatastrosConfig");
        myDir.mkdirs();
        File file = new File (myDir, "config.properties");
        if(file.exists()){
            /*AssetManager assetManager = context.getAssets();
            InputStream inputStream = assetManager.open("config.properties");*/
            FileInputStream inputStream = new FileInputStream(file);
            properties.load(inputStream);
            return properties.getProperty(key);
        }else{
            Resources res = context.getResources();
            //http://10.10.0.63:8080/catastros/interno/WsCatastros?wsdl
            properties.setProperty("URL_WS", res.getString(R.string.URL_WS));
            properties.setProperty("URL_WS_SYNCRO", res.getString(R.string.URL_WS_SYNCRO));
            properties.setProperty("CALIDAD_IMG", res.getString(R.string.CALIDAD_IMG));
            //String ruta = android.os.Environment.getExternalStorageDirectory().getAbsolutePath();
            //ruta = Environment.getExternalStorageDirectory() + "/Android/data/CatastrosConfig" ;SISISIIS

            //File myDirCreate  = new File(ruta);
            File myDirCreate  = new File(ruta.getAbsolutePath());
            myDirCreate.mkdirs();
            File fileNew = new File (myDirCreate, "config.properties");
            FileOutputStream fileOut = new FileOutputStream(fileNew);
            properties.store(fileOut, "Datos almacenados Correctamente");

            Properties propReturn = new Properties();
            FileInputStream inputStream = new FileInputStream(fileNew);
            propReturn.load(inputStream);
            return propReturn.getProperty(key);
        }



    }


    public static void setProperty(String key,String value,Context context) throws IOException {
        //String propertiesPath = context.getFilesDir().getPath().toString() + "/config.properties";
        Properties properties = new Properties();

        //File myDir  = new File(ruta);
        //File myDir  = new File(ruta.getAbsolutePath());

        System.out.println("Version SDK"+versionSDK);
        File myDir  ;
        if (versionSDK>=24){
            myDir  = new File(ruta.getAbsolutePath());
        }else{
            myDir  = new File(ruta.getAbsolutePath()+ "/Android/data/CatastrosConfig");
        }

        myDir .mkdirs();
        File file = new File (myDir, "config.properties");
        FileInputStream inputStream = new FileInputStream(file);
        properties.load(inputStream);
        /*AssetManager assetManager = context.getAssets();
        AssetManager am = context.getResources().getAssets();
        InputStream inputStream = am.open("config.properties",Context.MODE_PRIVATE);
        properties.load(inputStream);*/
        properties.setProperty(key,value);

        //String ruta = android.os.Environment.getExternalStorageDirectory().getAbsolutePath();
        //String ruta = Environment.getExternalStorageDirectory() + "/Android/data/CatastrosConfig" ;
        /*File myDir  = new File(ruta);
        myDir .mkdirs();
        File file = new File (myDir, "config.properties");*/
        FileOutputStream fileOut = new FileOutputStream(file);
        properties.store(fileOut, "Datos almacenado correctamente");
    }
}