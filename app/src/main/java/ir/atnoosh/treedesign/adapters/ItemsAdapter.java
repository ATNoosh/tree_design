package ir.atnoosh.treedesign.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import ir.atnoosh.treedesign.R;
import ir.atnoosh.treedesign.viewholders.ErrorViewHolder;
import ir.atnoosh.treedesign.viewholders.LoadingViewHolder;
import ir.atnoosh.treedesign.viewholders.NoItemViewHolder;

public class ItemsAdapter<T> extends ListAdapter<T, RecyclerView.ViewHolder> implements Filterable {


    //protected List<T> items = new ArrayList<>();
    protected List<T> allItems = new ArrayList<>();
    protected Context context;
    protected onItemClickListener myItemEventListener;
    protected SparseBooleanArray selectedItems;

    protected ItemsAdapter(@NonNull @NotNull DiffUtil.ItemCallback<T> diffCallback) {
        super(diffCallback);
    }

    public boolean isLoading() {
        return loading;
    }

    public void setLoading(boolean loading) {
        this.loading = loading;
    }

    public boolean loading = false;

    protected int current_selected_idx = -1;
/*
    @Override
    public void submitList(final List<T> list) {
        super.submitList(list != null ? new ArrayList<>(list) : null);
    }
*/
    /*
    public List<T> getItems() {
        return items;
    }*/
    protected onReloadButtonListener myReloadListener;

    public void setMyReloadListener(onReloadButtonListener myReloadListener) {
        this.myReloadListener = myReloadListener;
    }

    private final IListType.LIST_ITEM_TYPES itemType = IListType.LIST_ITEM_TYPES.SIMPLE;
    private int virtualItemIndex = -1;


    public void addVirtualItem(Class<T> aClass, IListType.LIST_ITEM_TYPES itemType) {
        try {
            T virtualUser = aClass.newInstance();
            ((IListType) virtualUser).setType(itemType);
            ArrayList<T> currList = new ArrayList<>(this.getCurrentList());
            currList.add(virtualUser);
            virtualItemIndex = this.getCurrentList().size() - 1;
            submitList(currList);
        } catch (Exception e) {
        }
    }

    public void removeVirtualItem() {
        if (this.getCurrentList() != null && this.getCurrentList().size() > 0 && virtualItemIndex>0)  {
            ArrayList<T> currList = new ArrayList<>(this.getCurrentList());
            for(int i=currList.size()-1;i>=0;i--)
            {
                if(((IListType)currList.get(i)).getType("")!= IListType.LIST_ITEM_TYPES.SIMPLE)
                    currList.remove(i);
            }
            submitList(currList);
        }
    }


    public List<T> getAllItems() {
        return allItems;
    }


    public void setMyItemEventListener(onItemClickListener myItemEventListener) {
        this.myItemEventListener = myItemEventListener;
    }

    @NonNull
    @NotNull
    @Override
    @CallSuper
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        this.context = parent.getContext();
        if (viewType == IListType.LIST_ITEM_TYPES.LOADING.ordinal()) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_loading, parent, false);
            LoadingViewHolder vh = new LoadingViewHolder(view);

            return vh;
        }
        if (viewType == IListType.LIST_ITEM_TYPES.NO_ITEM.ordinal()) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_error, parent, false);
            NoItemViewHolder vh = new NoItemViewHolder(view);
            vh.binding.tvNoItem.setText(R.string.no_item);
            vh.binding.ibReload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    myReloadListener.onReloadClick();
                }
            });
            return vh;
        }
        if (viewType == IListType.LIST_ITEM_TYPES.ERROR.ordinal()) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_error, parent, false);
            ErrorViewHolder vh = new ErrorViewHolder(view);
            vh.binding.tvError.setText(R.string.unforeseen_error);
            vh.binding.ibReload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    myReloadListener.onReloadClick();
                }
            });
            return vh;
        }
        if (viewType == IListType.LIST_ITEM_TYPES.NO_INTERNET.ordinal()) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_error, parent, false);
            ErrorViewHolder vh = new ErrorViewHolder(view);
            vh.binding.tvError.setText(R.string.no_connection);
            vh.binding.ibReload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    myReloadListener.onReloadClick();
                }
            });
            return vh;
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {

    }

    public void appendItems(AndroidViewModel viewModel, List<T> newList) {
        if (this.allItems == null)
            this.allItems = new ArrayList<>();
        this.allItems.addAll(newList);
    }


    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence query) {
            List<T> filteredList = new ArrayList<>();
            if (query == null || query.length() == 0) {
                filteredList.addAll(allItems);
            } else {

            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            List<T> currList = new ArrayList<>();

            currList.addAll((List) filterResults.values);
            submitList(currList);
        }
    };


    public interface onReloadButtonListener {
        void onReloadClick();
    }

    public interface onItemClickListener<T> {
        void onItemClick(View view, int pos);
    }

    public interface onItemLongClickListener<T> {
        void onItemLongClick(View view, int pos);
    }

    public Context getContext() {
        return context;
    }


    public static class ItemDiff<T> extends DiffUtil.ItemCallback<T>
    {
        @Override
        public boolean areItemsTheSame(@NonNull @NotNull T oldItem, @NonNull @NotNull T newItem) {
            return oldItem == newItem;
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull @NotNull T oldItem, @NonNull @NotNull T newItem) {
            return oldItem.equals(newItem);
        }

        @Nullable
        @org.jetbrains.annotations.Nullable
        @Override
        public Object getChangePayload(@NonNull @NotNull T oldItem, @NonNull @NotNull T newItem) {
            return super.getChangePayload(oldItem, newItem);
        }
    }

}
