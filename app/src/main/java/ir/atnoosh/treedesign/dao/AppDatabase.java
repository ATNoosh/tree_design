package ir.atnoosh.treedesign.dao;

import android.content.Context;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import ir.atnoosh.treedesign.models.Node;

@Database(entities = {Node.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract NodeDao nodeDao();

    private static volatile AppDatabase instance;
    private static final int threadsNumber = 4;
    public static final ExecutorService dbWriteExecutor = Executors.newFixedThreadPool(threadsNumber);

    public static AppDatabase getDatabase(final Context context) {
        if (instance == null) {
            synchronized (AppDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext()
                            , AppDatabase.class
                            , "tree_db").build();
                }
            }
        }
        return instance;
    }

}
