package ir.atnoosh.treedesign.viewholders;

import android.view.View;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import ir.atnoosh.treedesign.databinding.ItemParentBinding;

public class ParentViewHolder extends RecyclerView.ViewHolder {

   public ItemParentBinding binding;
   public ParentViewHolder(@NonNull View itemView) {
      super(itemView);
      binding = ItemParentBinding.bind(itemView);
   }

}
