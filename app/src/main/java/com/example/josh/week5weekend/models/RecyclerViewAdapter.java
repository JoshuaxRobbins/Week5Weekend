package com.example.josh.week5weekend.models;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.josh.week5weekend.R;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    List<Contact> contactList;
    public static final String TAG = "_TAG";
    private OnItemClicked onClick;

    public interface OnItemClicked{
        void onItemClick(int position);
    }

    public RecyclerViewAdapter(List<Contact> contactList) {
        this.contactList = contactList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        int itemViewLayout = R.layout.recycler_layout;
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(itemViewLayout,viewGroup,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder,final int i) {
        Contact contact = contactList.get(i);
        viewHolder.tvName.setText(contact.getName());
        viewHolder.tvPhone.setText(contact.getNumber());
        viewHolder.tvAddress.setText(contact.getAddress());
        viewHolder.llClicked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.onItemClick(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private final TextView tvAddress;
        private final TextView tvPhone;
        private final TextView tvName;
        private final LinearLayout llClicked;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            llClicked = itemView.findViewById(R.id.llClicked);
            tvName = itemView.findViewById(R.id.tvName);
            tvPhone = itemView.findViewById(R.id.tvPhone);
            tvAddress = itemView.findViewById(R.id.tvAddress);


        }
    }

    public void setOnClick(OnItemClicked onClick){
        this.onClick = onClick;
    }
}
