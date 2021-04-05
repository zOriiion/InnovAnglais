package com.example.innovanglaisgame;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class TestListAdapter extends RecyclerView.Adapter<TestListAdapter.ViewHolder>{

    List<Tests> listOfTests;
    OnTestListClickListener listener;

    public TestListAdapter (List<Tests> listOfTests, OnTestListClickListener listener){
        this.listOfTests = listOfTests;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View listTestView = LayoutInflater.from(parent.getContext()).inflate(R.layout.niveau_cell,parent,false);
        TestListAdapter.ViewHolder viewHolder = new TestListAdapter.ViewHolder(listTestView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TestListAdapter.ViewHolder holder, int position) {
        holder.testTextView.setText(listOfTests.get(position).getLibelle());
        Picasso.get().load(listOfTests.get(position).getImageUrl()).into(holder.testImageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnTestListClickListener(listOfTests.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return listOfTests.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView testTextView;
        ImageView testImageView;

        public ViewHolder(@NonNull View listTests){
            super(listTests);
            this.testTextView = listTests.findViewById(R.id.difficulteTextView);
            this.testImageView = listTests.findViewById(R.id.niveauImageView);
        }

    }
}
