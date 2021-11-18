package com.example.statussaver.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.statussaver.Fragments.ImageFragment;
import com.example.statussaver.Models.StatusModel;
import com.example.statussaver.R;
import com.roger.catloadinglibrary.CatLoadingView;

import java.io.IOException;
import java.util.List;

import at.markushi.ui.CircleButton;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder>
{

    private final List<StatusModel> imageList;
    Context context;
    ImageFragment imageFragment;
    CatLoadingView mView;

    public ImageAdapter(Context context,List<StatusModel> imageList, ImageFragment imageFragment)
    {
        this.context = context;
        this.imageList = imageList;
        this.imageFragment = imageFragment;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_status,viewGroup, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder imageViewHolder, int i) {
        StatusModel statusModel = imageList.get(i);
        imageViewHolder.ivThumbnailImgView.setImageBitmap(statusModel.getThumbnail());


    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.ivThumbnail) ImageView ivThumbnailImgView;
        @BindView(R.id.ibSaveToGallery)
        CircleButton imageButtonDownload;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

            imageButtonDownload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    StatusModel statusModel = imageList.get(getAdapterPosition());

                    if(statusModel!=null)
                    {
                        try {
                            imageFragment.downloadImage(statusModel);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
    }
}
