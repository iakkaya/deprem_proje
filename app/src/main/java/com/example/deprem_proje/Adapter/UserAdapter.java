package com.example.deprem_proje.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deprem_proje.Message.MessageActivity;
import com.example.deprem_proje.Model.User;
import com.example.deprem_proje.R;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.Viewholder> {

    private Context mContext;
    private List<User> mUsers;


    public UserAdapter(Context mContext, List<User> mUsers) {
        this.mContext = mContext;
        this.mUsers = mUsers;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.user_item, parent, false);
        return new UserAdapter.Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        User user = mUsers.get(position);
        holder.userName.setText(user.getUserName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MessageActivity.class);
                intent.putExtra("recieverUid", user.getId());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }


    public  class Viewholder extends RecyclerView.ViewHolder {
        public TextView userName;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.username);
        }
    }
}
