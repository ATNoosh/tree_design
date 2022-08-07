package ir.atnoosh.treedesign.repositories;

import android.app.Application;


import java.util.Date;
import java.util.List;

import androidx.lifecycle.LiveData;
import ir.atnoosh.treedesign.dao.AppDatabase;
import ir.atnoosh.treedesign.dao.NodeDao;
import ir.atnoosh.treedesign.models.Node;

public class NodeRepository {
    AppDatabase db;
    private final NodeDao nodeDao;

    public NodeRepository(Application application) {
        db = AppDatabase.getDatabase(application);
        nodeDao = db.nodeDao();
    }

    public void insert(Node node) {
        AppDatabase.dbWriteExecutor.execute(() -> {
            if (node.getCreatedAt() == null) {
                node.setCreatedAt(new Date());
                nodeDao.insert(node);
            } else {
                node.setUpdatedAt(new Date());
                nodeDao.update(node);
            }
        });
    }

    public void update(Node node) {
        AppDatabase.dbWriteExecutor.execute(() -> {
            node.setUpdatedAt(new Date());
            nodeDao.update(node);
        });
    }

    public void remove(Node node) {
        AppDatabase.dbWriteExecutor.execute(() -> {
            nodeDao.removeNodeAndGenerations(node.getId());
        });
    }

    public void insertAllNodes(List<Node> nodes) {
        AppDatabase.dbWriteExecutor.execute(() -> {
            nodeDao.insertAllNodes(nodes);
        });
    }

    public LiveData<List<Node>> getAllNodes() {
        return nodeDao.getAllNodes();
    }

    public LiveData<List<Node>> getAllChildren(int parent_id) {
        return nodeDao.getAllChildren(parent_id);
    }

    public LiveData<List<Node>> getAllParents(int parent_id) {
        return nodeDao.getAllParentsToRoot(parent_id);
    }

    public List<Node> getAllParentsNotLive(int parent_id) {
        return nodeDao.getAllParentsToRootNotLive(parent_id);
    }

}
