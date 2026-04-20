package com.example.contact;



import android.view.*;
import android.widget.*;
import androidx.recyclerview.widget.RecyclerView;
import java.util.*;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {

    List<Contact> list;
    DBHelper db;

    public ContactAdapter(List<Contact> list, DBHelper db) {
        this.list = list;
        this.db = db;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, mobile;
        ImageButton deleteBtn;

        public ViewHolder(View v) {
            super(v);
            name = v.findViewById(R.id.nameText);
            mobile = v.findViewById(R.id.mobileText);
            deleteBtn = v.findViewById(R.id.deleteBtn);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Contact c = list.get(position);
        holder.name.setText(c.name);
        holder.mobile.setText(c.mobile);

        holder.deleteBtn.setOnClickListener(v -> {
            db.deleteData(c.mobile);
            list.remove(position);
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
