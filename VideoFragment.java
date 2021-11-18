package com.example.statussaver.Fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.statussaver.Adapters.ImageAdapter;
import com.example.statussaver.Adapters.VideoAdapter;
import com.example.statussaver.Models.StatusModel;
import com.example.statussaver.R;
import com.example.statussaver.Utils.MyConstants;
import com.jeevandeshmukh.glidetoastlib.GlideToast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoFragment extends Fragment
{

    @BindView(R.id.recyclerViewVideo) RecyclerView recyclerView;
    @BindView(R.id.progressBarVideo) ProgressBar progressBar;

    Handler handler = new Handler();
    ArrayList<StatusModel> videoModelArrayList;
    VideoAdapter videoAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        videoModelArrayList = new ArrayList<>();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        getStatusVideos();
    }

    private void getStatusVideos() {

            if(MyConstants.STATUS_DIRECTORY.exists())
            {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        File[] statusFiles = MyConstants.STATUS_DIRECTORY.listFiles();

                        if(statusFiles!=null && statusFiles.length>0) {
                            Arrays.sort(statusFiles);

                            for(final File statusFile:statusFiles) {
                                StatusModel statusmodel = new StatusModel(statusFile,
                                        statusFile.getName(),statusFile.getAbsolutePath());

                                statusmodel.setThumbnail(getThumbnail(statusmodel));
                                if(statusmodel.isVideo()) {
                                    videoModelArrayList.add(statusmodel);
                                }
                            }
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    progressBar.setVisibility(View.GONE);

                                    videoAdapter = new VideoAdapter(getContext(), videoModelArrayList,
                                            VideoFragment.this);
                                    recyclerView.setAdapter(videoAdapter);
                                    videoAdapter.notifyDataSetChanged();
                                }
                            });


                        }
                        else {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    progressBar.setVisibility(View.GONE);
                                    new GlideToast.makeToast(getActivity(),"Directory does not exist..Please Check for app permissions",GlideToast.LENGTHLONG,GlideToast.FAILTOAST,GlideToast.BOTTOM).show();
                                   // Toast.makeText(getContext(), "dir does not exist", Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }
                }).start();
            }
        }

    private Bitmap getThumbnail(StatusModel statusModel) {
        if(statusModel.isVideo())
        {
            return ThumbnailUtils.createVideoThumbnail(statusModel.getFile().getAbsolutePath(),
                    MediaStore.Video.Thumbnails.MICRO_KIND);
        }
        else
        {
            return ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(statusModel.getFile().getAbsolutePath()),
                    MyConstants.THUMBSIZE,
                    MyConstants.THUMBSIZE);
        }
    }

    public void downloadImage(StatusModel statusModel) throws IOException {
        File file = new File(MyConstants.APP_DIR);
        if(!file.exists())
        {
            file.mkdirs();

        }
        File destFile = new File(file+File.separator+ statusModel.getTitle());

        if(destFile.exists())
        {
            destFile.delete();
        }
        copyFile(statusModel.getFile(),destFile);

       // Toast.makeText(getActivity(), "Download completed", Toast.LENGTH_LONG).show();
        new GlideToast.makeToast(getActivity(),"Video Download Complete",GlideToast.LENGTHLONG,GlideToast.SUCCESSTOAST,GlideToast.BOTTOM).show();

        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intent.setData(Uri.fromFile(destFile));
        getActivity().sendBroadcast(intent);
    }
    private void copyFile(File file, File destFile) throws IOException {
        if(!destFile.getParentFile().exists())
        {
            destFile.getParentFile().mkdirs();
        }
        if(!destFile.exists())
        {
            destFile.createNewFile();
        }

        FileChannel source = null;
        FileChannel destination = null;

        source= new FileInputStream(file).getChannel();
        destination = new FileOutputStream(destFile).getChannel();
        destination.transferFrom(source,0,source.size());

        source.close();
        destination.close();
    }



}

