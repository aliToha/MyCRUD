package com.iak.ali.mycrud;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProdukUserActivity extends AppCompatActivity implements ProdukAdapterOnClickListener{
    List<Produk> produks;
    ProdakAdapter adapter;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    SwipeRefreshLayout swipeRefreshLayout;
    String id;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produk_user);

        id = getIntent().getStringExtra("iduser");

        recyclerView = (RecyclerView) findViewById(R.id.rv_produk);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe);

        layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                loadProduk();
            }
        });

        loadProduk();



    }

    private void loadProduk() {
        ApiInterface apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
        Call<List<Produk>> call = apiInterface.getProdukUser(id);
        call.enqueue(new Callback<List<Produk>>() {
            @Override
            public void onResponse(Call<List<Produk>> call, Response<List<Produk>> response) {
                if (response.body().isEmpty()){
                    Toast.makeText(ProdukUserActivity.this, "Anda belum memiliki barang untuk dijual", Toast.LENGTH_SHORT).show();
                }else {
                    produks = response.body();
                    adapter = new ProdakAdapter(produks, ProdukUserActivity.this);
                    recyclerView.setAdapter(adapter);
                    adapter.setClickListener(ProdukUserActivity.this);
                }
            }

            @Override
            public void onFailure(Call<List<Produk>> call, Throwable t) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tambah,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int idItem = item.getItemId();
        switch (idItem){
            //case R.id.opt_tambah:
            //  startActivity(new Intent(this,AddprodukActivity.class));
            // break;
            case R.id.opt_tambah_gambar:
                Intent intent = new Intent(this,AddProdukWithImage.class);
                intent.putExtra("id_user",id);
                startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view, int position) {
        final Produk produk = produks.get(position);
        Intent intent = new Intent(ProdukUserActivity.this, ProdukUserDetail.class);
        intent.putExtra("produk", produk);
        intent.putExtra("id_user",id);
        startActivity(intent);
    }

}
