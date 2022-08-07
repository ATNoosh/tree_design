package ir.atnoosh.treedesign.viewholders;

import android.view.View;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import ir.atnoosh.treedesign.databinding.ItemLoadingBinding;

public class LoadingViewHolder extends RecyclerView.ViewHolder {

    public ItemLoadingBinding binding;

    public LoadingViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);
        binding = ItemLoadingBinding.bind(itemView);
    }
}
