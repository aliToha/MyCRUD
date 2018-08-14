package com.iak.ali.mycrud;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.login_button_google)SignInButton btnGoogle;
    @BindView(R.id.nama_user)TextView mNamaUser;
    @BindView(R.id.email_user)TextView mEmailUser;
    @BindView(R.id.text_header)TextView mHeader;
    @BindView(R.id.telphone)TextView mTelphone;
    @BindView(R.id.logout)Button mLogout;
    @BindView(R.id.user_produk)Button mUserProduk;
    @BindView(R.id.img_user)CircleImageView mImgUser;
    @BindView(R.id.frame_user)LinearLayout mFrameUser;

    private GoogleSignInClient googleSignInClient;
    private String TAG="code";
    private static final int RC_SIGN_IN=101;
    private List<User> user = new ArrayList<>();
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        // Configure sign-in to request the user's ID, email address, and basic
// profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this,gso);
        btnGoogle = (SignInButton)findViewById(R.id.login_button_google);
        btnGoogle.setOnClickListener(this);
        mLogout.setOnClickListener(this);
        mUserProduk.setOnClickListener(this);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Login");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    @Override
    protected void onStart() {
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if(account == null){
            btnGoogle.setVisibility(View.VISIBLE);
            mFrameUser.setVisibility(View.INVISIBLE);
        }else{
           cekEmail();
        }
        super.onStart();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login_button_google:
                signIn();
                break;
            case R.id.logout:
                signOut();
                break;
            case R.id.user_produk:
                Intent intent = new Intent(Login.this,ProdukUserActivity.class);
                intent.putExtra("iduser",user.get(0).getIdUser());
                startActivity(intent);
        }
    }

    private void signOut() {
        googleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                        mFrameUser.setVisibility(View.INVISIBLE);
                        mHeader.setVisibility(View.VISIBLE);
                        btnGoogle.setVisibility(View.VISIBLE);
                    }
                });
    }

    private void signIn() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent,RC_SIGN_IN);
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            cekEmail();
            // Signed in successfully, show authenticated UI.
            //updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
           // updateUI(null);
            mFrameUser.setVisibility(View.INVISIBLE);
            Toast.makeText(this, "Gagal Login", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void cekEmail(){
        final GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        String email = account.getEmail();
        ApiInterface apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
        Call<List<User>> call = apiInterface.getUser(email);
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                user = response.body();
                if(response.body().isEmpty()){
                    String foto = String.valueOf(account.getPhotoUrl());
                    Intent intent = new Intent(Login.this,DaftarActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("email",account.getEmail());
                    bundle.putString("nama",account.getDisplayName());
                    bundle.putString("photo",foto);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }else{
                    mFrameUser.setVisibility(View.VISIBLE);
                    mHeader.setVisibility(View.INVISIBLE);
                    btnGoogle.setVisibility(View.INVISIBLE);

                    mNamaUser.setText(account.getDisplayName());
                    mEmailUser.setText(account.getEmail());
                    mTelphone.setText(user.get(0).getNoTlp());
                    Uri foto = account.getPhotoUrl();
                    String photo = String.valueOf(foto);
                    Picasso.with(Login.this).load(photo).into(mImgUser);
                }

            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

            }
        });
    }
}
