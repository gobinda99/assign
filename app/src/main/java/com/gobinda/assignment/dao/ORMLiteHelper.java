package com.gobinda.assignment.dao;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

import com.gobinda.assignment.dao.models.User;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;


public class ORMLiteHelper extends OrmLiteSqliteOpenHelper {

    private final static String DB_NAME = "sprinkles.db";
    private final static int DB_VERSION = 1;


    public ORMLiteHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
          /*Enable foreign key constraints */
        if (!db.isReadOnly() && !(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)) {
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }

    @Override
    @TargetApi(16)
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, User.class);

        } catch (SQLException e) {
//            Timber.e("Error in creating tables ", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    @Override
    public ConnectionSource getConnectionSource() {
        return super.getConnectionSource();
    }
}
