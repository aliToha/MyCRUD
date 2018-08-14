package com.iak.ali.mycrud;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by Ali on 1/27/2018.
 */

public class ProdakAdapter extends RecyclerView.Adapter<ProdakAdapter.Prodakholder> {
    List<Produk> produks;
    Context context;

   ProdukAdapterOnClickListener mClickHandler;

    public ProdakAdapter(List<Produk> produks, Context context) {
        this.produks = produks;
        this.context = context;
    }

    public void setClickListener (ProdukAdapterOnClickListener clickHandler){
        this.mClickHandler = clickHandler;
    }

    @Override
    public Prodakholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View produkAdapter = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_produk, parent, false);
        return new Prodakholder(produkAdapter);
    }

    @Override
    public void onBindViewHolder(final Prodakholder holder, final int position) {
       // String urlImage = "http://192.168.1.2/iak_API/image_produk/";
        Locale localeID = new Locale("id","ID");
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(localeID);
        String hargaID = produks.get(position).getHargaProduk();
    final String urlImage = "http://gagalcoding.000webhostapp.com/penjualan/image_produk/";
    holder.hargaProduk.setText(numberFormat.format(Double.parseDouble(hargaID)));
    holder.namaProduk.setText(produks.get(position).getNamaProduk());
        Picasso.with(holder.itemView.getContext())
                .load(urlImage + produks.get(position).getImgProduk())
                .resize(400,500)
                .into(holder.imgProduk);

    }

    @Override
    public int getItemCount() {
        return produks.size();
    }

    class Prodakholder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView imgProduk;
        TextView namaProduk, hargaProduk;
        public Prodakholder(final View itemView) {
            super(itemView);
            imgProduk = (ImageView)itemView.findViewById(R.id.iv_produk);
            namaProduk = (TextView)itemView.findViewById(R.id.tv_nama_produk);
            hargaProduk = (TextView)itemView.findViewById(R.id.tv_hrg_produk);
            itemView.setTag(itemView);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if(mClickHandler !=null) mClickHandler.onClick(view,getAdapterPosition());
        }

    }

}
