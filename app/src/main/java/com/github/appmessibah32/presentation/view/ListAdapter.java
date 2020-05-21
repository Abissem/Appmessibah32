package com.github.appmessibah32.presentation.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.appmessibah32.Constants;
import com.github.appmessibah32.R;
import com.github.appmessibah32.presentation.model.Dragonball;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private List<Dragonball> values;
    private OnItemClickListener listener;
    private Context context;

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

    public ListAdapter(List<Dragonball> myDataset, OnItemClickListener listener, Context context) {
        this.values = myDataset;
        this.listener = listener;
        this.context = context;
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
        if(currentCharacter.getImage().charAt(0) == '.') {
            Glide.with(context).applyDefaultRequestOptions(
                    new RequestOptions()
                            .placeholder(R.drawable.ic_refresh_green_100dp)
                            .error(R.drawable.ic_healing_red_100dp))
                            .load(Constants.BASE_URL + currentCharacter.getImage())
                            .centerCrop()
                            .into(holder.Image);
        }else Glide.with(context).applyDefaultRequestOptions(
                new RequestOptions()
                            .placeholder(R.drawable.ic_refresh_green_100dp)
                            .error(R.drawable.ic_healing_red_100dp))
                            .load(currentCharacter.getImage())
                            .centerCrop()
                            .into(holder.Image);

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
