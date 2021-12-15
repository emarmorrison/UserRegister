package com.emar.userregister.adapter;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.emar.userregister.R;
import com.emar.userregister.entities.User;
import com.emar.userregister.listener.UserListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class UserRecyclerViewAdapter extends RecyclerView.Adapter<UserRecyclerViewAdapter.ViewHolder>{

    private List<User> users;
    private UserListener listener;
    private Resources resources;

    public UserRecyclerViewAdapter(UserListener listener, Resources resources) {
        users = new ArrayList();
        this.listener = listener;
        this.resources = resources;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        int i = position;
        int imageId  = R.drawable.avatar_1;
        holder.textViewName.setText(users.get(position).getName());
        holder.textViewEmail.setText(users.get(position).getEmail());
        holder.textViewPhone.setText(users.get(position).getPhoneNumber());
        holder.imageView.setImageDrawable(resources.getDrawable(users.get(position).getImageId()));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onUserSelected(users.get(i));
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public void setUserList(List<User> users) {

        this.users = users;

        notifyDataSetChanged();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private  final View mView;
        private final TextView textViewName;
        private final TextView textViewEmail;
        private final TextView textViewPhone;
        private final ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mView = itemView;
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewEmail = itemView.findViewById(R.id.textViewEmail);
            textViewPhone = itemView.findViewById(R.id.textViewPhone);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
