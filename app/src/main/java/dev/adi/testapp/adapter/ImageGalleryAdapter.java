package dev.adi.testapp.adapter;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import dev.adi.testapp.R;


public class ImageGalleryAdapter extends RecyclerView.Adapter<ImageGalleryAdapter.ViewHolder> {

    Context context;
    FragmentManager fragmentManager;

    public ImageGalleryAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_image, null, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Inside alert", Toast.LENGTH_SHORT).show();

                View v = LayoutInflater.from(context).inflate(R.layout.item_image_full, null, false);

                v.findViewById(R.id.iv_btn_close).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(context, "Close Clicked", Toast.LENGTH_SHORT).show();
                    }
                });

                new AlertDialog.Builder(context)
                    .setView(v)
                    .show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return 11;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImage;

        public ViewHolder(View itemView) {
            super(itemView);
            ivImage = (ImageView) itemView.findViewById(R.id.iv_image_thumbnail);
        }
    }

}
