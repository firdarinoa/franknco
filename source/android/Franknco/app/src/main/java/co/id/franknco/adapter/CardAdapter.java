package co.id.franknco.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import co.id.franknco.R;
import co.id.franknco.ui.main.MainActivity;

import java.util.ArrayList;

/**
 * Created by FirdaRinoa on 9/27/17.
 */
public class CardAdapter extends RecyclerView.Adapter<CardAdapter.MyHolder> {
    ArrayList<String> list;
    Context context;

    public CardAdapter(ArrayList<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_layout,parent,false);
        MyHolder myHolder = new MyHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        holder.textView.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder{
        TextView textView;
        public MyHolder(View itemView){
            super(itemView);
            textView = (TextView)itemView.findViewById(R.id.textView);
        }
    }
}
