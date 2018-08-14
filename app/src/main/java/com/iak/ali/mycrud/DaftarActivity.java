package com.iak.ali.mycrud;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DaftarActivity extends AppCompatActivity {
    @BindView(R.id.email_user)TextView mEmail;
    @BindView(R.id.nama_user)TextView mNama;
    @BindView(R.id.no_tlp)EditText mNoTlp;
    @BindView(R.id.alamat)EditText mAlamat;
    @BindView(R.id.btn_daftar)Button mDaftar;
    @BindView(R.id.btn_cancle)Button mCancle;
    @BindView(R.id.img_user)CircleImageView mImageUser;

    private GoogleSignInClient googleSignInClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);
        ButterKnife.bind(this);

        String img = getIntent().getExtras().getString("photo");
        Picasso.with(this).load(img).into(mImageUser);
        mEmail.setText(getIntent().getExtras().getString("email"));
        mNama.setText(getIntent().getExtras().getString("nama"));
        mDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(mNoTlp.getText().toString())) {
                    mNoTlp.setError("Nomor telphone tidak boleh kosong");
                } else if (TextUtils.isEmpty(mAlamat.getText().toString())) {
                    mAlamat.setError("Alamat tidak boleh kosong");
                } else {
                    simpan();
                }
            }
        });
        mCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
                Intent intent = new Intent(DaftarActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private void simpan() {
        ApiInterface apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
        Call<Value> call = apiInterface.addUser(mNama.getText().toString(),
                mEmail.getText().toString(),
                mNoTlp.getText().toString(),
                mAlamat.getText().toString(),
                mImageUser.toString());
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                String value = response.body().getValue();
                String message = response.body().getMessage();
                if (value.equals("1")) {
                    Toast.makeText(DaftarActivity.this,message, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(DaftarActivity.this,MainActivity.class));
                    finish();
                }else {
                    Toast.makeText(DaftarActivity.this,message, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                Toast.makeText(DaftarActivity.this, "terjadi kesalahan koneksi", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onBackPressed() {
        Toast.makeText(this, "silahkan tekan tombol cancle", Toast.LENGTH_SHORT).show();
            super.onBackPressed();
        }
    private void signOut() {
        googleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                    }
                });
    }
}
