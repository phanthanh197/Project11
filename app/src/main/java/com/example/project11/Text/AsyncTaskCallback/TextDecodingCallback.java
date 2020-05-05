package com.example.project11.Text.AsyncTaskCallback;

import com.example.project11.Text.ImageSteganography;

/**
 * This the callback interface for TextDecoding AsyncTask.
 */

public interface TextDecodingCallback {

    void onCompleteTextEncoding(ImageSteganography result);

}
