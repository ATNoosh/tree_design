package ir.atnoosh.treedesign.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import ir.atnoosh.treedesign.R;
import ir.atnoosh.treedesign.databinding.AddNodeFragmentBinding;
import ir.atnoosh.treedesign.models.Node;
import ir.atnoosh.treedesign.viewmodels.NodeViewModel;

public class AddNodeFragment extends BottomSheetDialogFragment {

    AddNodeFragmentBinding binding;
    private NodeViewModel myNodeViewModel;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.add_node_fragment, container, false);
        binding.setLifecycleOwner(this);
        return binding.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myNodeViewModel = new ViewModelProvider(requireActivity()).get(NodeViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindElements();
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }

    private void bindElements() {
        binding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.etName.getText().toString().length() == 0)
                    return;
                Node newNode = new Node();
                newNode.setName(binding.etName.getText().toString());
                newNode.setParentId(myNodeViewModel.getMainNode().getValue() == null ? 0 : myNodeViewModel.getMainNode().getValue().getId());
                myNodeViewModel.addNode(newNode);
                dismiss();
            }
        });
    }

}
