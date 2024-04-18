package com.example.appgrit.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appgrit.R;
import com.example.appgrit.models.ImagePostModel;
import com.example.appgrit.models.PostSellProductModel;
import com.example.appgrit.network.ApiServiceProvider;
import com.example.appgrit.services.MarketplaceApiService;

import java.util.List;

public class MarketplaceAdapter extends RecyclerView.Adapter<MarketplaceAdapter.ViewHolder> {
    private Context context;
    public static List<PostSellProductModel> postList;
    private MarketplaceApiService service;

    public MarketplaceAdapter(Context context, List<PostSellProductModel> postList) {
        this.context = context;
        this.postList = postList;
        this.service = ApiServiceProvider.getMarketplaceApiService();
        ;
    }

    @NonNull
    @Override
    public MarketplaceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.marketplace_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MarketplaceAdapter.ViewHolder holder, int position) {
        PostSellProductModel postSellProductModel = postList.get(position);

        String product = postSellProductModel.getProductName();
        if (product != null && !product.isEmpty()) {
            holder.txt3.setText(product);
        } else {
            holder.txt3.setText("Unknown");
        }

        List<ImagePostModel> imageList = postSellProductModel.getImagePosts();
        String imagePath = imageList.get(0).toString();
        if (imagePath != null && !imagePath.isEmpty()) {
//            Glide.with(holder.itemView.getContext())
//                    .load(imagePath)
//                    .placeholder(R.drawable.profile)
//                    .into(holder.txt1);
            ImagePostModel imagePost = postSellProductModel.getImagePosts().get(0);
            Glide.with(holder.txt1.getContext())
                    .load(imagePost.getImagePath())
                    .into(holder.txt1);
        } else {
            holder.txt1.setImageResource(R.drawable.profile);
        }
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView txt1;
        public TextView txt2, txt3, txt4;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt1 = itemView.findViewById(R.id.ivImage);
            txt2 = itemView.findViewById(R.id.tvPrice);
            txt3 = itemView.findViewById(R.id.tvDes);
            txt4 = itemView.findViewById(R.id.tvLocate);
        }
    }

    public void setData(List<PostSellProductModel> newData) {
        postList.clear();
        postList.addAll(newData);
        notifyDataSetChanged();
    }
}