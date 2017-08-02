package asia.covisoft.bsandroid.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leon on 10/26/2016.
 */

public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected Context context;
    protected List<T> items;

    protected abstract int getItemLayout();

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(getItemLayout(), parent, false);
        return onCreateViewHolder(view);
    }

    public abstract RecyclerView.ViewHolder onCreateViewHolder(View view);

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        T item = getItem(position);
        onBindViewHolder(holder, position, item);
    }

    protected abstract void onBindViewHolder(RecyclerView.ViewHolder holder, int position, T item);

    @Override
    public int getItemCount() {
        return items.size();
    }

    public T getItem(int position) {
        if (position >= 0)
            return items.get(position);
        else
            return null;
    }

    public void insertItem(T newItem) {
        items.add(newItem);
        notifyDataSetChanged();
    }

    public void insertItems(List<T> newItems) {
        items.addAll(newItems);
        notifyDataSetChanged();
    }

    public void removeItem(T itemToRemove) {
        items.remove(itemToRemove);
        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        items.remove(position);
        notifyDataSetChanged();
    }


    public void removeAllItems() {
        items = new ArrayList<>();
        notifyDataSetChanged();
    }

    protected AdapterView.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private AdapterView.OnItemLongClickListener onItemLongClickListener;

    public void setOnItemLongClickListener(AdapterView.OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }
}
