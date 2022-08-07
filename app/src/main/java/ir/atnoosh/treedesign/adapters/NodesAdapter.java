package ir.atnoosh.treedesign.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import ir.atnoosh.treedesign.R;
import ir.atnoosh.treedesign.models.Node;
import ir.atnoosh.treedesign.viewholders.NodeViewHolder;
import ir.atnoosh.treedesign.viewmodels.NodeViewModel;

public class NodesAdapter<T> extends ItemsAdapter{

   AndroidViewModel viewModel;

   public AndroidViewModel getViewModel() {
      return viewModel;
   }

   public NodesAdapter(@NonNull DiffUtil.ItemCallback diffCallback) {
      super(diffCallback);
   }

   public NodesAdapter(@NonNull DiffUtil.ItemCallback diffCallback, AndroidViewModel viewModel) {
      super(diffCallback);
      this.viewModel = viewModel;
      setHasStableIds(true);
   }




   @NonNull
   @NotNull
   @Override
   public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
      RecyclerView.ViewHolder parentVH = super.onCreateViewHolder(parent, viewType);
      if (parentVH != null) return parentVH;
      if (viewType == IListType.LIST_ITEM_TYPES.SIMPLE.ordinal()) {
         View view = LayoutInflater.from(parent.getContext())
                 .inflate(R.layout.item_node, parent, false);
         NodeViewHolder vh = new NodeViewHolder(view);

         return vh;
      }

      return null;
   }

   @Override
   public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {

      if (holder instanceof NodeViewHolder) {
         NodeViewHolder curHolder = (NodeViewHolder) holder;
         Node curNode = (Node)(getCurrentList().get(position));
         curHolder.binding.tvName.setText(curNode.getName());
         curHolder.binding.cardViewNodeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               myItemEventListener.onItemClick(view, curHolder.getAdapterPosition());
            }
         });
         curHolder.binding.cardViewNodeItem.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
               return false;
            }
         });
         curHolder.binding.btnRemoveNode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(viewModel instanceof NodeViewModel)
               {
                  ((NodeViewModel) viewModel).removeNode((Node)getCurrentList().get(position));
               }
            }
         });
      }


   }

   @Override
   public long getItemId(int position) {
      Node curNode = (Node) getCurrentList().get(position);
      return curNode.getId();
   }
}
