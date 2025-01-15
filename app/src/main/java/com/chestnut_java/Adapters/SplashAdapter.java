package com.chestnut_java.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chestnut_java.R;
import com.chestnut_java.SplashItem;

import java.util.List;

public class SplashAdapter extends RecyclerView.Adapter<SplashAdapter.SplashViewHolder> {

    private List<SplashItem> splashItems;

    public SplashAdapter(List<SplashItem> splashItems) {
        this.splashItems = splashItems;
    }

    @NonNull
    @Override
    public SplashViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_slide, parent, false);
        return new SplashViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SplashViewHolder holder, int position) {
        SplashItem item = splashItems.get(position);
        holder.title.setText(item.getTitle());
        holder.description.setText(item.getDescription());
        holder.image.setImageResource(item.getImage());
    }

    @Override
    public int getItemCount() {
        return splashItems.size();
    }

    static class SplashViewHolder extends RecyclerView.ViewHolder {
        TextView title, description;
        ImageView image;

        public SplashViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            image = itemView.findViewById(R.id.imageView);
        }
    }
}
