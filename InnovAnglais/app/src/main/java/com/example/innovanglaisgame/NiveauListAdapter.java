package com.example.innovanglaisgame;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

public class NiveauListAdapter extends RecyclerView.Adapter<NiveauListAdapter.ViewHolder> {

    List<Niveaux> listNiveaux;
    OnNiveauListClickListener listener;

    public NiveauListAdapter (List<Niveaux> listNiveaux, OnNiveauListClickListener listener){
        this.listNiveaux = listNiveaux;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View listView = LayoutInflater.from(parent.getContext()).inflate(R.layout.niveau_cell,parent,false);
        ViewHolder viewHolder = new ViewHolder(listView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.difficulteTextView.setText(listNiveaux.get(position).getLibelle());
        Picasso.get().load(listNiveaux.get(position).getImage_url()).into(holder.imageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnNiveauListClickListener(listNiveaux.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return listNiveaux.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView difficulteTextView;
        ImageView imageView;

        public ViewHolder(@NonNull View listNiveaux){
            super(listNiveaux);
            this.difficulteTextView = listNiveaux.findViewById(R.id.difficulteTextView);
            this.imageView = listNiveaux.findViewById(R.id.niveauImageView);
        }

    }
}
