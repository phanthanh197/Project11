package com.example.project11.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.box.androidsdk.content.BoxApiFile;
import com.box.androidsdk.content.BoxApiFolder;
import com.box.androidsdk.content.BoxConstants;
import com.box.androidsdk.content.BoxException;
import com.box.androidsdk.content.BoxFutureTask;
import com.box.androidsdk.content.auth.BoxAuthentication;
import com.box.androidsdk.content.models.BoxEntity;
import com.box.androidsdk.content.models.BoxError;
import com.box.androidsdk.content.models.BoxFile;
import com.box.androidsdk.content.models.BoxFolder;
import com.box.androidsdk.content.models.BoxItem;
import com.box.androidsdk.content.models.BoxIteratorItems;
import com.box.androidsdk.content.models.BoxSession;
import com.box.androidsdk.content.requests.BoxRequestsFile;
import com.box.androidsdk.content.requests.BoxResponse;
import com.example.project11.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;

/**
 * Sample content app that demonstrates session creation, and use of file api.
 */
public class AppBoxActivity extends AppCompatActivity implements BoxAuthentication.AuthListener {

    BoxSession mSession = null;
    BoxSession mOldSession = null;

    private ListView mListView;

    private ProgressDialog mDialog;

    private ArrayAdapter<BoxItem> mAdapter;

    private BoxApiFolder mFolderApi;
    private BoxApiFile mFileApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_app_box);
        mListView = (ListView) findViewById(android.R.id.list);
        mAdapter = new BoxItemAdapter(this);
        mListView.setAdapter(mAdapter);
        initSession();
    }

    /**
     * Set required config parameters. Use values from your application settings in the box developer console.
     */

    /**
     * Create a BoxSession and authenticate.
     */
    private void initSession() {
        clearAdapter();
        mSession = new BoxSession(this);
        mSession.setSessionAuthListener(this);
        mSession.authenticate(this);
    }

    @Override
    public void onRefreshed(BoxAuthentication.BoxAuthenticationInfo info) {
        // do nothing when auth info is refreshed
    }

    @Override
    public void onAuthCreated(BoxAuthentication.BoxAuthenticationInfo info) {
        //Init file, and folder apis; and use them to fetch the root folder
        mFolderApi = new BoxApiFolder(mSession);
        mFileApi = new BoxApiFile(mSession);
        loadRootFolder();
    }

    @Override
    public void onAuthFailure(BoxAuthentication.BoxAuthenticationInfo info, Exception ex) {
        if (ex != null) {
            clearAdapter();
        } else if (info == null && mOldSession != null) {
            mSession = mOldSession;
            mSession.setSessionAuthListener(this);
            mOldSession = null;
            onAuthCreated(mSession.getAuthInfo());
        }
    }

    @Override
    public void onLoggedOut(BoxAuthentication.BoxAuthenticationInfo info, Exception ex) {
        clearAdapter();
        initSession();
    }


    //Method to demonstrate fetching folder items from the root folder
    private void loadRootFolder() {
        new Thread() {
            @Override
            public void run() {
                try {
                    //Api to fetch root folder
                    final BoxIteratorItems folderItems = mFolderApi.getItemsRequest(BoxConstants.ROOT_FOLDER_ID).send();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter.clear();
                            for (BoxItem boxItem: folderItems) {
                                mAdapter.add(boxItem);
                            }
                        }
                    });
                } catch (BoxException e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }

    /**
     * Method demonstrates a sample file being uploaded using the file api
     */
    private void uploadSampleFile() {
        mDialog = ProgressDialog.show(AppBoxActivity.this, getText(R.string.boxsdk_Please_wait), getText(R.string.boxsdk_Please_wait));
        new Thread() {
            @Override
            public void run() {
                try {
                    String uploadFileName = "box_logo.png";
                    InputStream uploadStream = getResources().getAssets().open(uploadFileName);
                    String destinationFolderId = "0";
                    String uploadName = "BoxSDKUpload.png";
                    BoxRequestsFile.UploadFile request = mFileApi.getUploadRequest(uploadStream, uploadName, destinationFolderId);
                    final BoxFile uploadFileInfo = request.send();
                    showToast("Uploaded " + uploadFileInfo.getName());
                    loadRootFolder();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (BoxException e) {
                    e.printStackTrace();
                    BoxError error = e.getAsBoxError();
                    if (error != null && error.getStatus() == HttpURLConnection.HTTP_CONFLICT) {
                        ArrayList<BoxEntity> conflicts = error.getContextInfo().getConflicts();
                        if (conflicts != null && conflicts.size() == 1 && conflicts.get(0) instanceof BoxFile) {
                            uploadNewVersion((BoxFile) conflicts.get(0));
                            return;
                        }
                    }
                    showToast("Upload failed");
                } finally {
                    mDialog.dismiss();
                }
            }
        }.start();

    }

    /**
     * Method demonstrates a new version of a file being uploaded using the file api
     * @param file
     */
    private void uploadNewVersion(final BoxFile file) {
        new Thread() {
            @Override
            public void run() {
                try {
                    String uploadFileName = "box_logo.png";
                    InputStream uploadStream = getResources().getAssets().open(uploadFileName);
                    BoxRequestsFile.UploadNewVersion request = mFileApi.getUploadNewVersionRequest(uploadStream, file.getId());
                    final BoxFile uploadFileVersionInfo = request.send();
                    showToast("Uploaded new version of " + uploadFileVersionInfo.getName());
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (BoxException e) {
                    e.printStackTrace();
                    showToast("Upload failed");
                } finally {
                    mDialog.dismiss();
                }
            }
        }.start();
    }

    private void showToast(final String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(AppBoxActivity.this, text, Toast.LENGTH_LONG).show();
            }
        });
    }


    private void clearAdapter() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mAdapter.clear();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        int numAccounts = BoxAuthentication.getInstance().getStoredAuthInfo(this).keySet().size();
        menu.findItem(R.id.logoutAll).setVisible(numAccounts > 1);
        menu.findItem(R.id.logout).setVisible(numAccounts > 0);
        menu.findItem(R.id.switch_accounts).setTitle(numAccounts > 0 ? "sd" : "login");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.upload) {
            uploadSampleFile();
            return true;
        } else if (id == R.id.switch_accounts) {
            switchAccounts();
            return true;
        } else if (id == R.id.logout) {
            mSession.logout();
            return true;
        } else if (id == R.id.logoutAll) {
            new Thread() {
                @Override
                public void run() {
                    BoxAuthentication.getInstance().logoutAllUsers(getApplicationContext());
                }
            }.start();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void switchAccounts() {
        mOldSession = mSession;
        // when switching accounts we don't care about events for the old session.
        mOldSession.setSessionAuthListener(null);
        mSession = new BoxSession(this, null);
        mSession.setSessionAuthListener(this);
        mSession.authenticate(this).addOnCompletedListener(new BoxFutureTask.OnCompletedListener<BoxSession>() {
            @Override
            public void onCompleted(BoxResponse<BoxSession> response) {
                if (response.isSuccess()) {
                    clearAdapter();
                }
            }
        });
    }



    private class BoxItemAdapter extends ArrayAdapter<BoxItem> {
        public BoxItemAdapter(Context context) {
            super(context, 0);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            BoxItem item = getItem(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.boxsdk_list_item, parent, false);
            }

            TextView name = (TextView) convertView.findViewById(R.id.name);
            name.setText(item.getName());

            ImageView icon = (ImageView) convertView.findViewById(R.id.icon);
            if (item instanceof BoxFolder) {
                icon.setImageResource(R.drawable.boxsdk_icon_folder_yellow);
            } else {
                icon.setImageResource(R.drawable.boxsdk_generic);
            }

            return convertView;
        }

    }
}
