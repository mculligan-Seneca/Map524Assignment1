/*
Mitchell Culligan
161293170
mculligan@myseneca.ca
Nov 17th,2020
Lab 4
This class reads/stores the average from previous quizes
 */
package com.example.map524assignment1;
import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.BufferedInputStream;


public class StorageManager {

    private static final String FILE_NAME="quiz_data.txt";
    //This method saves average to file internally
    public  void saveAverageInternal(Activity activity,Double average){

        try(FileOutputStream fout = activity.openFileOutput(FILE_NAME, Context.MODE_APPEND)){
            fout.write((average.toString()+" ").getBytes());
            Toast.makeText(activity,R.string.save_message,Toast.LENGTH_SHORT).show();
        }catch(IOException ioe){
            ioe.printStackTrace();
        }


    }

    public  String readAverageDataInternal(Activity activity){
        StringBuffer sb = new StringBuffer("");
        byte[] b = new byte[10];

        int n;
        try(BufferedInputStream fin = new BufferedInputStream(activity.openFileInput(FILE_NAME))){
           while((n=fin.read(b,0,b.length))!=-1){
               for(int i=0;i<n;i++)
                   sb.append((char) b[i]);

           }

        }catch(IOException ioe){
            ioe.printStackTrace();
        }
        return sb.toString();
    }


    public void fileReset(Activity activity){
        try(FileOutputStream fout = activity.openFileOutput(FILE_NAME, Context.MODE_PRIVATE)){
            fout.write("".getBytes());
            Toast.makeText(activity,R.string.save_message,Toast.LENGTH_SHORT);
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
    }
}
