package com.gobinda.assignment.dao;


import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

public class DaoUtils {

    enum Operation {
        CREATE_OR_UPDATE, SELECT, DELETE
    }

    private static ORMLiteHelper sDbHelper = null;

    public static void init(Context aContext) {
        if (sDbHelper == null) {
            sDbHelper = OpenHelperManager.getHelper(aContext, ORMLiteHelper.class);
        }
    }

    public static void shutdown() {
        if (sDbHelper != null) {
            OpenHelperManager.releaseHelper();
            sDbHelper = null;
        }
    }

    /**
     * connection resource for database transaction
     *
     * @return
     */
    public static ConnectionSource getConnectionSource() {
        return sDbHelper.getConnectionSource();
    }

    @SuppressWarnings("unchecked")
    public static <T> Dao<T, ?> getDao(T ob) throws SQLException {
        Dao<T, ?> dao = (Dao<T, ?>) sDbHelper.getDao(ob.getClass());
        return dao;
    }

    private static void checkIfInited() {
        if (sDbHelper == null) {
            throw new RuntimeException(" ORMLiteHelper is not initialized! Call DaoUtils.init(context) to initialize it.");
        }
    }


    /**
     * Method to Get Dao of the Database Object
     *
     * @param clazz class  of the Model class
     * @return Respective Dao of the Model Class
     * @throws SQLException
     */
    public static <T> Dao<T, ?> getDao(Class<T> clazz) throws SQLException {
        checkIfInited();
        Dao<T, ?> dao = (Dao<T, ?>) sDbHelper.getDao(clazz);
        return dao;
    }

    @SuppressWarnings("unchecked")
    public static <T> T createOrUpdate(T ob) throws SQLException {
        checkIfInited();

        Dao<Object, ?> dao = (Dao<Object, ?>) sDbHelper.getDao(ob.getClass());
        return (T) dao.createOrUpdate(ob);
    }

    @SuppressWarnings("unchecked")
    public static <T> int create(T ob) throws SQLException {
        checkIfInited();

        Dao<Object, ?> dao = (Dao<Object, ?>) sDbHelper.getDao(ob.getClass());
        return (int) dao.create(ob);
    }

    @SuppressWarnings("unchecked")
    public static <T> int update(T ob) throws SQLException {
        checkIfInited();

        Dao<Object, ?> dao = (Dao<Object, ?>) sDbHelper.getDao(ob.getClass());
        return dao.update(ob);
    }

    @SuppressWarnings("unchecked")
    public static <T> int delete(T ob) throws SQLException {
        checkIfInited();

        Dao<Object, ?> dao = (Dao<Object, ?>) sDbHelper.getDao(ob.getClass());
        return dao.delete(ob);
    }

    @SuppressWarnings("unchecked")
    public static <T> int refresh(T ob) throws SQLException {
        checkIfInited();

        Dao<T, ?> dao = (Dao<T, ?>) sDbHelper.getDao(ob.getClass());
        return dao.refresh(ob);
    }


    @SuppressWarnings("unchecked")
    public static <T> T getById(Object aId, Class<T> clazz) throws SQLException {
        checkIfInited();

        Dao<T, Object> dao = sDbHelper.getDao(clazz);
        return dao.queryForId(aId);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getByFieldValue(String column, Object value, Class<T> clazz) throws SQLException {
        checkIfInited();

        Dao<T, Object> dao = sDbHelper.getDao(clazz);

        QueryBuilder<T, Object> queryBuilder = dao.queryBuilder();
        queryBuilder.where().eq(column, value);
        PreparedQuery<T> preparedQuery = queryBuilder.prepare();
        List<T> result = dao.query(preparedQuery);

        if (result == null || result.size() == 0)
            return null;
        else
            return result.get(0);
    }

    public static <T> List<T> getAll(Class<T> clazz) throws SQLException {
        checkIfInited();

        Dao<T, ?> dao = sDbHelper.getDao(clazz);
        return dao.queryForAll();
    }


}

