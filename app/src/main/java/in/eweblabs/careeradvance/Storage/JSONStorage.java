package in.eweblabs.careeradvance.Storage;

import android.content.Context;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * Created by Akash.Singh on 6/4/2015.
 * This Class use for Save xml string to file on memory
 */
public abstract class JSONStorage {

    protected abstract void setFileName();
    private Context context;
    protected String FILE_NAME;
    protected int mAssetsFileID;

    JSONStorage(Context context){
        this.context = context;
    }

    public String LoadXmlFromFile(){
        StringBuilder stringBuilder =  new StringBuilder();
        FileReader fileReader;
        BufferedReader bufferedReader;
        Object obj;

        try {
            fileReader = new FileReader(new File(context.getFilesDir(), FILE_NAME));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        bufferedReader =  new BufferedReader(fileReader);
        String line;

        try {
            while ((line=bufferedReader.readLine())!=null){
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
         }finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                    fileReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return stringBuilder.toString();
    }

    public String LoadXmlFromAssets(){
        StringBuilder stringBuilder =  new StringBuilder();
        BufferedReader bufferedReader;
        InputStream inputStream = context.getResources().openRawResource(mAssetsFileID);
        try {
            bufferedReader=new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
        String line;
     try {
            while ((line=bufferedReader.readLine())!=null){
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return stringBuilder.toString();
    }

    public boolean SaveXmlToFile(String xmlString){
        BufferedWriter bufferedWriter = null;
        File file =  new File(context.getFilesDir(),FILE_NAME);
        try {
            file.createNewFile();
            bufferedWriter = new BufferedWriter(new FileWriter(file));
            bufferedWriter.write(xmlString);
            bufferedWriter.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
