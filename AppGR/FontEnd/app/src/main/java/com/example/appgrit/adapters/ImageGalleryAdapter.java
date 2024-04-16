package com.example.appgrit.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appgrit.R;

import java.util.List;

public class ImageGalleryAdapter extends RecyclerView.Adapter<ImageGalleryAdapter.ImageViewHolder> {
    private List<String> imageUrls;
    private LayoutInflater inflater;
    private OnImageClickListener onImageClickListener;

    public ImageGalleryAdapter(Context context, List<String> imageUrls) {
        this.inflater = LayoutInflater.from(context);
        this.imageUrls = imageUrls;
    }
    public void setOnImageClickListener(OnImageClickListener listener) {
        this.onImageClickListener = listener;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.gallery_item, parent, false); // Tạo layout cho mục gallery của bạn
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        String imageUrl = imageUrls.get(position);
        Glide.with(holder.imageView.getContext())
                .load(imageUrl)
                .placeholder(R.drawable.add)
                .into(holder.imageView);

        // Gọi sự kiện khi người dùng bấm vào ảnh
        holder.imageView.setOnClickListener(v -> {
            if (onImageClickListener != null) {
                onImageClickListener.onImageClick(position);
            }
        });
    }


    @Override
    public int getItemCount() {
        return imageUrls.size();
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_gallery_item); // ID này nên khớp với ID ImageView trong layout mục gallery của bạn
        }
    }
}
