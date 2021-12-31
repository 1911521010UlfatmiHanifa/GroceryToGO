package com.example.grocerytogo.adapter;

import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grocerytogo.R;
import com.example.grocerytogo.model.Avatar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AvatarAdapter
        extends RecyclerView.Adapter<AvatarAdapter.AvatarViewHolder>{

    ArrayList<Avatar> listAvatar = new ArrayList<>();

    public void setListAvatar(ArrayList<Avatar> listAvatar){
        this.listAvatar = listAvatar;
    }

    public interface KlikAvatar{
        void onClick(View view, Avatar avatar);
    }

    AvatarAdapter.KlikAvatar listener = null;

    public void setListener(AvatarAdapter.KlikAvatar listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public AvatarAdapter.AvatarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_avatar, parent, false);
        AvatarAdapter.AvatarViewHolder viewHolder = new AvatarAdapter.AvatarViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AvatarAdapter.AvatarViewHolder viewHolder, int position) {
        Avatar avatar = listAvatar.get(position);
        Picasso.get().load(avatar.url+avatar.gambar).into(viewHolder.avatar);
    }

    @Override
    public int getItemCount() {
        return listAvatar.size();
    }

    public class AvatarViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView avatar;

        public AvatarViewHolder(@NonNull View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.logo_avatar);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(listener != null){
                listener.onClick(view, listAvatar.get(getAdapterPosition()));
            }
        }
    }
}
