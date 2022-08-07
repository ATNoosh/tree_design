package ir.atnoosh.treedesign.dao;


import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import ir.atnoosh.treedesign.models.Node;

@Dao
public interface NodeDao {

    @Insert(onConflict = OnConflictStrategy.FAIL)
    void insert(Node node);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Node node);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllNodes(List<Node> nodes);

    @Query("select * from nodes")
    LiveData<List<Node>> getAllNodes();

    @Query("select * from nodes where parent_id = :parent_id")
    LiveData<List<Node>> getAllChildren(int parent_id);

    @Query("WITH RECURSIVE" +
            "  under_node(uid,uparent_id) AS (" +
            "    select id,parent_id from nodes where nodes.id = :pid " +
            "    UNION ALL " +
            "    SELECT id,parent_id " +
            "      FROM nodes JOIN under_node ON nodes.parent_id = under_node.uid " +
            "  )" +
            "delete from nodes where id in (select uid from under_node)")
    void removeNodeAndGenerations(int pid);


    String queryParentsToRoot = "WITH RECURSIVE\n" +
            "  node_parents(uid,uparent_id) AS (" +
            "    select id,parent_id from nodes where nodes.id = :curId" +
            "    UNION ALL" +
            "    SELECT id,parent_id" +
            "      FROM nodes JOIN node_parents ON nodes.id=node_parents.uparent_id" +
            "  )" +
            "select * from nodes where id in (select uid from node_parents)";

    @Query(queryParentsToRoot)
    LiveData<List<Node>> getAllParentsToRoot(int curId);

    @Query(queryParentsToRoot)
    List<Node> getAllParentsToRootNotLive(int curId);

    @Query("select * from nodes")
    List<Node> getAllNodesNotLive();

}
