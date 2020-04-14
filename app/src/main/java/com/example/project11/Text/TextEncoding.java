package com.example.project11.Text;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import com.example.project11.Text.AsyncTaskCallback.TextEncodingCallback;
import com.example.project11.Utils.Utility;

import java.util.List;

/**
 * In this class all those method in EncodeDecode class are used to encode secret message in image.
 * All the tasks will run in background.
 */
public class TextEncoding extends AsyncTask<ImageSteganography, Integer, ImageSteganography> {

    //Tag for Log
    private static final String TAG = TextEncoding.class.getName();

    private final ImageSteganography result;
    //Callback interface for AsyncTask
    private final TextEncodingCallback callbackInterface;
    private int maximumProgress;
    private final ProgressDialog progressDialog;

    public TextEncoding(Activity activity, TextEncodingCallback callbackInterface) {
        super();
        this.progressDialog = new ProgressDialog(activity);
        this.callbackInterface = callbackInterface;
        //making result object
        this.result = new ImageSteganography();
    }

    //pre execution of method
    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        //setting parameters of progress dialog
        if (progressDialog != null) {
            progressDialog.setMessage("Loading, Please Wait...");
            progressDialog.setTitle("Encoding Message");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
    }

    @Override
    protected void onPostExecute(ImageSteganography textStegnography) {
        super.onPostExecute(textStegnography);

        //dismiss progress dialog
        if (progressDialog != null) {
            progressDialog.dismiss();
        }

        //Sending result to callback interface
        callbackInterface.onCompleteTextEncoding(result);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);

        //Updating progress dialog
        if (progressDialog != null) {
            progressDialog.incrementProgressBy(values[0]);
        }
    }

    @Override
    protected ImageSteganography doInBackground(ImageSteganography... imageSteganographies) {

        maximumProgress = 0;

        if (imageSteganographies.length > 0) {

            ImageSteganography textStegnography = imageSteganographies[0];

            //lấy ảnh bitmap chưa mã hóa
            Bitmap bitmap = textStegnography.getImage();

            //lấy chiều dài và rộng của bitmap
            int originalHeight = bitmap.getHeight();
            int originalWidth = bitmap.getWidth();

            //chia bitmap chưa mã hóa
            List<Bitmap> src_list = Utility.splitImage(bitmap);

            //mã hóa tin nhắn đã được mã hóa bằng LSB

            List<Bitmap> encoded_list = EncodeDecode.encodeMessage(src_list, textStegnography.getEncrypted_message(), new EncodeDecode.ProgressHandler() {

                //tiến trình xử lý
                @Override
                public void setTotal(int tot) {
                    maximumProgress = tot;
                    progressDialog.setMax(maximumProgress);
                    Log.d(TAG, "Total Length : " + tot);
                }

                @Override
                public void increment(int inc) {
                    publishProgress(inc);
                }

                @Override
                public void finished() {
                    Log.d(TAG, "Message Encoding end....");
                    progressDialog.setIndeterminate(true);
                }
            });

            //xóa src_list giải phóng bộ nhớ
            for (Bitmap bitm : src_list)
                bitm.recycle();

            //Java Garbage collector
            System.gc();

            //hợp hình ảnh đã được mã hóa
            Bitmap srcEncoded = Utility.mergeImage(encoded_list, originalHeight, originalWidth);

            //lấy ảnh đã mã hóa
            result.setEncoded_image(srcEncoded);
            result.setEncoded(true);
        }

        return result;
    }
}