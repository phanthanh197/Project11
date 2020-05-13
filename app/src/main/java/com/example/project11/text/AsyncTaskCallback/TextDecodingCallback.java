package com.example.project11.text.AsyncTaskCallback;

import com.example.project11.text.ImageSteganography;

/**
 * This the callback interface for TextDecoding AsyncTask.
 */

public interface TextDecodingCallback {

    void onCompleteTextEncoding(ImageSteganography result);

}
