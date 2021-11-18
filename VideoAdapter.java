package com.example.statussaver.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.statussaver.Fragments.VideoFragment;
import com.example.statussaver.Models.StatusModel;
import com.example.statussaver.R;

import java.io.IOException;
import java.util.List;

import at.markushi.ui.CircleButton;
import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder>
{

    private final List<StatusModel> videosList;
    Context context;
    VideoFragment videoFragment;

    public VideoAdapter(Context context,List<StatusModel> videosList, VideoFragment videoFragment)
    {
        this.context = context;
        this.videosList = videosList;
        this.videoFragment = videoFragment;
    }


    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_status,viewGroup, false);
        return new VideoViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder videoViewHolder, int i) {
        StatusModel statusModel = videosList.get(i);
        videoViewHolder.ivThumbnailImgView.setImageBitmap(statusModel.getThumbnail());


    }

    @Override
    public int getItemCount() {
        return videosList.size();
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.ivThumbnail) ImageView ivThumbnailImgView;
        @BindView(R.id.ibSaveToGallery)
        CircleButton imageButtonDownload;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);


            imageButtonDownload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    StatusModel statusModel = videosList.get(getAdapterPosition());

                    if(statusModel!=null)
                    {
                        try {
                            videoFragment.downloadImage(statusModel);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });




        }
    }
}
