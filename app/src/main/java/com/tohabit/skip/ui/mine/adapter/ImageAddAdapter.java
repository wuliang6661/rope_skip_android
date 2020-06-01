package com.tohabit.skip.ui.mine.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tohabit.skip.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class ImageAddAdapter extends RecyclerView.Adapter<ImageAddAdapter.ViewHodler> {

    private List<String> imageBOS;
    private Context context;
    private int positionNum = 6;  //默认最大数量6个

    public ImageAddAdapter(Context context, List<String> imageBOS) {
        this.context = context;
        this.imageBOS = imageBOS;
    }


    public void setNum(int num){
        this.positionNum = num;
    }


    public void setDatas(List<String> imageBOS){
        this.imageBOS = imageBOS;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHodler onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_image_grid_fragment_common, null);
        return new ViewHodler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHodler viewHodler, int i) {
        if (imageBOS.size() == 0 || i == imageBOS.size()) {
            viewHodler.deleteImg.setVisibility(View.GONE);
//            viewHodler.imageName.setText("");
            viewHodler.imageView.setImageResource(R.mipmap.ic_menu_add_picture);
        } else {
            viewHodler.deleteImg.setVisibility(View.VISIBLE);
            Glide.with(context).load(imageBOS.get(i)).into(viewHodler.imageView);
        }
        viewHodler.imageView.setOnClickListener(v -> {
            if (imageBOS.size() == 0 || i == imageBOS.size()) {
                if (listener != null) {
                    listener.addImage();
                }
            } else {
                if (listener != null) {
                    listener.editName(i);
                }
            }
        });
        viewHodler.deleteImg.setOnClickListener(v -> {
            if (imageBOS.size() != 0 && i != imageBOS.size()) {
                if (listener != null) {
                    listener.deleteImage(i, imageBOS.get(i));
                }
            }
        });
//        viewHodler.imageName.setOnClickListener(v -> {
//            if (listener != null) {
//                listener.editName(i);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        if (imageBOS.size() == 0) {
            return 1;
        }
        if (imageBOS.size() < positionNum) {
            return imageBOS.size() + 1;
        }
        return positionNum;
    }

    class ViewHodler extends RecyclerView.ViewHolder {


        RoundedImageView imageView;
        ImageView deleteImg;
        TextView imageName;


        public ViewHodler(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_layout_image_grid_fragment_task_order_feedback);
            deleteImg = itemView.findViewById(R.id.delete_img);
//            imageName = itemView.findViewById(R.id.edit_image_name);
        }
    }

    private onAddImageAdapterListener listener;

    public void setListener(onAddImageAdapterListener listener) {
        this.listener = listener;
    }


    public interface onAddImageAdapterListener {

        void addImage();

        void deleteImage(int position, String imageBO);

        void editName(int position);

    }

}
