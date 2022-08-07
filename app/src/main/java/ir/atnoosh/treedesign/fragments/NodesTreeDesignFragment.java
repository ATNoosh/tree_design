package ir.atnoosh.treedesign.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ir.atnoosh.treedesign.R;
import ir.atnoosh.treedesign.adapters.ItemsAdapter;
import ir.atnoosh.treedesign.adapters.NodesAdapter;
import ir.atnoosh.treedesign.adapters.ParentsAdapter;
import ir.atnoosh.treedesign.databinding.DesignFragmentBinding;
import ir.atnoosh.treedesign.models.Node;
import ir.atnoosh.treedesign.viewmodels.NodeViewModel;

public class NodesTreeDesignFragment extends Fragment {

    NavController navController;
    NavBackStackEntry navBackStackEntry;
    private NodeViewModel myNodeViewModel;
    DesignFragmentBinding binding;
    NodesAdapter nodesAdapter;
    ParentsAdapter parentsAdapter;


    public static NodesTreeDesignFragment newInstance() {
        return new NodesTreeDesignFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navController = NavHostFragment.findNavController(this);
        //navBackStackEntry = navController.getBackStackEntry(R.id.navigation_graph);
        initObserver();

    }

    private void initObserver() {
        myNodeViewModel = new ViewModelProvider(requireActivity()).get(NodeViewModel.class);
        myNodeViewModel.getMainNodeChildren().observe(this, new Observer<List<Node>>() {
            @Override
            public void onChanged(List<Node> nodes) {
                nodesAdapter.submitList(nodes);
            }
        });
        myNodeViewModel.getMainNodeParents().observe(this, new Observer<List<Node>>() {
            @Override
            public void onChanged(List<Node> nodes) {
                Node grandNode = new Node();//virtual parent of root node
                grandNode.setName("Root");
                nodes.add(0, grandNode);
                parentsAdapter.submitList(nodes);
            }
        });

        myNodeViewModel.setMainNode(myNodeViewModel.getMainNode().getValue());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.design_fragment, container, false);
        binding.setLifecycleOwner(this);
        initNodesRecyclerView();
        initParentsRecyclerView();
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        myNodeViewModel = new ViewModelProvider(this).get(NodeViewModel.class);
        bindElements();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void bindElements() {
    }


    private void initNodesRecyclerView() {
        RecyclerView.LayoutManager layoutManager;
        nodesAdapter = new NodesAdapter(new ItemsAdapter.ItemDiff(), myNodeViewModel);
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        binding.rvNodes.setAdapter(nodesAdapter);
        binding.rvNodes.setLayoutManager(layoutManager);

        nodesAdapter.setMyItemEventListener(new ItemsAdapter.onItemClickListener() {
            @Override
            public void onItemClick(View view, int pos) {
                ((NodeViewModel) nodesAdapter.getViewModel()).setMainNode((Node) nodesAdapter.getCurrentList().get(pos));
            }
        });

    }

    private void initParentsRecyclerView() {
        RecyclerView.LayoutManager layoutManager;
        parentsAdapter = new ParentsAdapter(new ItemsAdapter.ItemDiff(), myNodeViewModel);
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        binding.rvParents.setAdapter(parentsAdapter);
        binding.rvParents.setLayoutManager(layoutManager);

        parentsAdapter.setMyItemEventListener(new ItemsAdapter.onItemClickListener() {
            @Override
            public void onItemClick(View view, int pos) {
                ((NodeViewModel) parentsAdapter.getViewModel()).setMainNode((Node) parentsAdapter.getCurrentList().get(pos));
            }
        });

    }


}