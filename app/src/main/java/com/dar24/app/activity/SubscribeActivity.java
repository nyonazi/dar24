package com.dar24.app.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.dar24.app.R;
import com.dar24.app.endpoint.EndPointService;
import com.dar24.app.endpoint.EndPointUrl;
import com.dar24.app.model.mailchimp_status.StatusResponse;
import com.dar24.app.model.mailchimp_subscribe.MergeFields;
import com.dar24.app.model.mailchimp_subscribe.SubscribeRequest;
import com.dar24.app.model.mailchimp_subscribe.SubscribeResponse;
import com.dar24.app.utility.Developer;
import com.dar24.app.utility.Helpers;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Simon Mawole
 * @version 0.1.0
 * @email simonmawole2011@gmail.com
 * @since 0.1.0
 */
public class SubscribeActivity extends BaseAppCompatActivity {

    private final OkHttpClient mClient = new OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build();
    private final Gson gson = new GsonBuilder()
            .serializeNulls()
            .setLenient()
            .create();
    final Retrofit mRetrofit = new Retrofit.Builder()
            .client(mClient)
            .baseUrl(EndPointUrl.MAILCHIMP_API)
            .addConverterFactory(GsonConverterFactory.create(gson))
            //.addConverterFactory(ScalarsConverterFactory.create())
            .build();

    @BindView(R.id.etEmail)
    EditText etEmail;
    @BindView(R.id.etFirstname)
    EditText etFirstName;
    @BindView(R.id.etLastname)
    EditText etLastName;
    @BindView(R.id.etPhone)
    EditText etPhone;
    @BindView(R.id.bnSubscribe)
    Button bnSubscribe;

    private MaterialDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscribe);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(R.string.subscribe_to_dar24);


    }

    @OnClick(R.id.bnSubscribe)
    void onSubscribe() {
        if (!TextUtils.isEmpty(etEmail.getText().toString())) {
            checkStatus();
        } else {
            Toast.makeText(SubscribeActivity.this, R.string.email_required,
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void checkStatus() {
        if (Helpers.isConnected(this)) {
            showProgressDialog(getString(R.string.subscribing), true);
            bnSubscribe.setEnabled(false);
            EndPointService service = mRetrofit.create(EndPointService.class);
            Call<StatusResponse> call = service.getMailChimpEmailStatus(
                    Developer.MAILCHIMP_USERNAME + " " + Developer.MAILCHIMP_KEY,
                    Helpers.md5(etEmail.getText().toString().trim()));
            call.enqueue(new Callback<StatusResponse>() {
                @Override
                public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
                    if (response.isSuccessful()) {
                        bnSubscribe.setEnabled(true);
                        etEmail.setText("");
                        etFirstName.setText("");
                        etLastName.setText("");
                        etPhone.setText("");
                        showDialog(getString(R.string.subscribe_success_message));
                        showProgressDialog("", false);
                    } else {
                        if (response.code() == 404) {
                            subscribe();
                        } else {
                            bnSubscribe.setEnabled(true);
                            showProgressDialog("", false);
                            Toast.makeText(SubscribeActivity.this, R.string.something_went_wrong,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<StatusResponse> call, Throwable t) {
                    bnSubscribe.setEnabled(true);
                    showProgressDialog("", false);
                    t.printStackTrace();
                    Toast.makeText(SubscribeActivity.this, R.string.something_went_wrong,
                            Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, R.string.no_internet_connection,
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void subscribe() {
        if (Helpers.isConnected(this)) {
            EndPointService service = mRetrofit.create(EndPointService.class);
            Call<SubscribeResponse> call = service.postMailChimpSubscribe(
                    Developer.MAILCHIMP_USERNAME + " " + Developer.MAILCHIMP_KEY,
                    new SubscribeRequest(
                            etEmail.getText().toString().trim(),
                            "subscribed",
                            new MergeFields(etLastName.getText().toString().trim(),
                                    "",
                                    etPhone.getText().toString().trim(),
                                    "",
                                    etFirstName.getText().toString().trim()
                            )
                    ));
            call.enqueue(new Callback<SubscribeResponse>() {
                @Override
                public void onResponse(Call<SubscribeResponse> call, Response<SubscribeResponse> response) {
                    bnSubscribe.setEnabled(true);
                    showProgressDialog("", false);
                    if (response.isSuccessful()) {
                        etEmail.setText("");
                        etPhone.setText("");
                        etFirstName.setText("");
                        etLastName.setText("");
                        showDialog(getString(R.string.subscribe_success_message));
                    } else {
                        Toast.makeText(SubscribeActivity.this, R.string.something_went_wrong,
                                Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<SubscribeResponse> call, Throwable t) {
                    bnSubscribe.setEnabled(true);
                    showProgressDialog("", false);
                    t.printStackTrace();
                    Toast.makeText(SubscribeActivity.this, R.string.something_went_wrong,
                            Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            bnSubscribe.setEnabled(true);
            showProgressDialog("", false);
            Toast.makeText(this, R.string.no_internet_connection,
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void showDialog(String message) {
        new MaterialDialog.Builder(this)
                .title(R.string.dar24)
                .content(message)
                .positiveText(R.string.ok)
                .show();
    }

    private void showProgressDialog(String title, boolean visible) {
        if (visible) {
            progressDialog = new MaterialDialog.Builder(this)
                    .title(title)
                    .progress(true, 0)
                    .progressIndeterminateStyle(true)
                    .show();
        } else {
            if (progressDialog != null) progressDialog.dismiss();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
