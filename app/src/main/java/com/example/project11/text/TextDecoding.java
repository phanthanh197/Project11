package com.example.project11.text;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import com.example.project11.text.AsyncTaskCallback.TextDecodingCallback;
import com.example.project11.utils.Utility;

import java.util.List;

public class TextDecoding extends AsyncTask<ImageSteganography, Void, ImageSteganography> {


    private final static String TAG = TextDecoding.class.getName();

    private final ImageSteganography result
    private final TextDecodingCallback textDecodingCallback;
    private ProgressDialog progressDialog;

    public TextDecoding(Activity activity, TextDecodingCallback textDecodingCallback) {
        super();
        this.progressDialog = new ProgressDialog(activity);
        this.textDecodingCallback = textDecodingCallback;
        //making result object
        this.result = new ImageSteganography();
    }

    //pre execution of method
    @Override
    protected void onPreExecute() {
        super.onPreExecute();


        if (progressDialog != null) {
            progressDialog.setMessage("Loading, Please Wait...");
            progressDialog.setTitle("Decoding Message");
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
            progressDialog.show();

        }
    }

    @Override
    protected void onPostExecute(ImageSteganography imageSteganography) {
        super.onPostExecute(imageSteganography);

        //dismiss progress dialog
        if (progressDialog != null)
            progressDialog.dismiss();

        //sending result to callback
        textDecodingCallback.onCompleteTextEncoding(result);
    }

    @Override
    protected ImageSteganography doInBackground(ImageSteganography... imageSteganographies) {

        //If it is not already decoded
        if (imageSteganographies.length > 0) {

            ImageSteganography imageSteganography = imageSteganographies[0];

            //getting bitmap image from file
            Bitmap bitmap = imageSteganography.getImage();

            //splitting images
            List<Bitmap> srcEncodedList = Utility.splitImage(bitmap);

            //decoding encrypted zipped message
            String decoded_message = EncodeDecode.decodeMessage(srcEncodedList);

            Log.d(TAG, "Decoded_Message : " + decoded_message);

            //text decoded = true
            if (!Utility.isStringEmpty(decoded_message)) {
                result.setDecoded(true);
            }

            //decrypting the encoded message
            String decrypted_message = ImageSteganography.decryptMessage(decoded_message, imageSteganography.getSecret_key());
            Log.d(TAG, "Decrypted message : " + decrypted_message);

            //If decrypted_message is null it means that the secret key is wrong otherwise secret key is right.
            if (!Utility.isStringEmpty(decrypted_message)) {

                //secret key provided is right
                result.setSecretKeyWrong(false);

                // Set Results

                result.setMessage(decrypted_message);


                //free memory
                for (Bitmap bitm : srcEncodedList)
                    bitm.recycle();

                //Java Garbage Collector
                System.gc();
            }
        }
        return result;
    }
}
