package com.sbz.retrofitandlocaldb.database;

import static com.sbz.retrofitandlocaldb.utils.Constants.DATABASE_NAME;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.sbz.retrofitandlocaldb.dao.ProductDAO;
import com.sbz.retrofitandlocaldb.model.Product;

@Database(entities = {Product.class}, version = 1)
public abstract class MyDatabase extends RoomDatabase {

    public abstract ProductDAO productDAO();

    private static volatile MyDatabase INSTANCE;

    public static MyDatabase getDbInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (MyDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context,
                                    MyDatabase.class,
                                    DATABASE_NAME)
                            .addCallback(callback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    static Callback callback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateAsyncTask(INSTANCE);
        }
    };

    static class PopulateAsyncTask extends AsyncTask<Void, Void, Void> {

        private ProductDAO productDAO;

        PopulateAsyncTask(MyDatabase database) {
            productDAO = database.productDAO();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            productDAO.deleteAll();
            return null;
        }
    }
}
