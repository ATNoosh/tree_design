package ir.atnoosh.treedesign.viewmodels;

import android.app.Application;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import ir.atnoosh.treedesign.models.Node;
import ir.atnoosh.treedesign.repositories.NodeRepository;

public class NodeViewModel extends AndroidViewModel {

    private final NodeRepository nodeRepository;
    private final MutableLiveData<Node> mainNode = new MutableLiveData<>();
    private final LiveData<List<Node>> mainNodeChildren;
    private final LiveData<List<Node>> mainNodeParents;

    public NodeViewModel(@NonNull @NotNull Application application) {
        super(application);
        nodeRepository = new NodeRepository(application);
        mainNodeChildren = Transformations.switchMap(mainNode, new Function<Node, LiveData<List<Node>>>() {
            @Override
            public LiveData<List<Node>> apply(Node node) {
                return nodeRepository.getAllChildren(node == null ? 0 : node.getId());
            }
        });
        mainNodeParents = Transformations.switchMap(mainNode, new Function<Node, LiveData<List<Node>>>() {
            @Override
            public LiveData<List<Node>> apply(Node node) {
                return nodeRepository.getAllParents(node == null ? 0 : node.getId());
            }
        });

    }

    public void setMainNode(Node newNode) {
        this.mainNode.setValue(newNode);
    }

    public MutableLiveData<Node> getMainNode() {
        return mainNode;
    }

    public LiveData<List<Node>> getMainNodeChildren() {
        return mainNodeChildren;
    }

    public LiveData<List<Node>> getMainNodeParents() {
        return mainNodeParents;
    }

    public void addNode(Node newNode) {
        nodeRepository.insert(newNode);
    }

    public void addNodes(List<Node> newNodes) {
        nodeRepository.insertAllNodes(newNodes);
    }

    public void removeNode(Node newNode) {
        nodeRepository.remove(newNode);
    }


    public List<Node> getNodeParents(int node_id) {
        return nodeRepository.getAllParentsNotLive(node_id);
    }


}