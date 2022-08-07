package ir.atnoosh.treedesign.viewholders;

import android.view.View;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import ir.atnoosh.treedesign.databinding.ItemErrorBinding;

public class ErrorViewHolder extends RecyclerView.ViewHolder {

    public ItemErrorBinding binding;

    public ErrorViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);
        binding = ItemErrorBinding.bind(itemView);
    }
}
