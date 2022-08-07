package ir.atnoosh.treedesign.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import org.jetbrains.annotations.NotNull;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import ir.atnoosh.treedesign.R;
import ir.atnoosh.treedesign.models.Node;
import ir.atnoosh.treedesign.viewholders.ParentViewHolder;

public class ParentsAdapter<T> extends ItemsAdapter {

    AndroidViewModel viewModel;

    public AndroidViewModel getViewModel() {
        return viewModel;
    }

    public ParentsAdapter(@NonNull DiffUtil.ItemCallback diffCallback) {
        super(diffCallback);
    }

    public ParentsAdapter(@NonNull DiffUtil.ItemCallback diffCallback, AndroidViewModel viewModel) {
        super(diffCallback);
        this.viewModel = viewModel;
    }


    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder parentVH = super.onCreateViewHolder(parent, viewType);
        if (parentVH != null) return parentVH;
        if (viewType == IListType.LIST_ITEM_TYPES.SIMPLE.ordinal()) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_parent, parent, false);
            ParentViewHolder vh = new ParentViewHolder(view);

            return vh;
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof ParentViewHolder) {
            ParentViewHolder newHolder = (ParentViewHolder) holder;
            Node curNode = (Node) getCurrentList().get(position);
            newHolder.binding.tvName.setText(curNode.getName());
            newHolder.binding.cardViewNodeItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    myItemEventListener.onItemClick(view, newHolder.getAdapterPosition());
                }
            });
        }


    }
}
