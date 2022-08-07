package ir.atnoosh.treedesign.viewholders;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import ir.atnoosh.treedesign.databinding.ItemNodeBinding;

public class NodeViewHolder extends RecyclerView.ViewHolder {

   public ItemNodeBinding binding;
   public NodeViewHolder(@NonNull View itemView) {
      super(itemView);
      binding = ItemNodeBinding.bind(itemView);
   }

}
