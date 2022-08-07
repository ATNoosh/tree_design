package ir.atnoosh.treedesign.viewholders;

import android.view.View;


import org.jetbrains.annotations.NotNull;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import ir.atnoosh.treedesign.databinding.ItemNoItemBinding;

public class NoItemViewHolder extends RecyclerView.ViewHolder {

    public ItemNoItemBinding binding;

    public NoItemViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);
        binding = ItemNoItemBinding.bind(itemView);
    }
}
