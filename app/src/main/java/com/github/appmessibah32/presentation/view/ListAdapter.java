package com.github.appmessibah32.presentation.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.github.appmessibah32.R;
import com.github.appmessibah32.presentation.model.Dragonball;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private List<Dragonball> values;
    private OnItemClickListener listener;

    public interface  OnItemClickListener {
        void onItemClick(Dragonball item);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtHeader;
        TextView txtFooter;
        TextView txtLast;
        ImageView Image;
        View layout;

         ViewHolder(View v) {
            super(v);
            layout = v;
            txtHeader = (TextView) v.findViewById(R.id.firstLine);
            txtFooter = (TextView) v.findViewById(R.id.secondLine);
            txtLast = (TextView) v.findViewById(R.id.thirdLine);
            Image = (ImageView) v.findViewById(R.id.icon);
        }
    }

    public void add(int position, Dragonball item) {
        values.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        values.remove(position);
        notifyItemRemoved(position);
    }

    public ListAdapter(List<Dragonball> myDataset, OnItemClickListener listener) {
        this.values = myDataset;
        this.listener = listener;
    }

    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(
            ViewGroup parent,
            int viewType
    ) {
        //On crée une nouvelle vue
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.row_layout, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    //Remplace le contenu de la vue
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        final Dragonball currentCharacter = values.get(position);

        holder.txtHeader.setText(currentCharacter.getName());
        holder.txtFooter.setText("Origin planet : " + currentCharacter.getOriginPlanet());
        holder.txtLast.setText(currentCharacter.getGender());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                listener.onItemClick(currentCharacter);
            }
        });
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

}
