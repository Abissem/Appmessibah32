package com.github.appmessibah32.presentation.view;

import android.content.Context;
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
    private Context context;

    public ListAdapter(Context context){
        this.context=context;
    }


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
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

    // Provide a suitable constructor (depends on the kind of dataset)
    public ListAdapter(List<Dragonball> myDataset) {
        values = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(
            ViewGroup parent,
            int viewType
    ) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.row_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final Dragonball currentCharacter = values.get(position);

        holder.txtHeader.setText(currentCharacter.getName());
        holder.txtFooter.setText("Origin planet : " + currentCharacter.getOriginPlanet());
        holder.txtLast.setText(currentCharacter.getGender());

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return values.size();
    }

}
