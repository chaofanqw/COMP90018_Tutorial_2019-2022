package com.example.databasedemo;

import android.content.Context;

import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

public class DBManager {
    private static DBManager dbManager;
    private DaoSession daoSession;
    private UserObjectDao userObjectDao;

    private DBManager(Context context) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "user-db");
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
        userObjectDao = daoSession.getUserObjectDao();
    }

    public static synchronized DBManager getDbManager(Context context) {
        if (dbManager == null) {
            dbManager = new DBManager(context);
        }
        return dbManager;
    }

    public void insertUser(UserObject user) {
        daoSession.insert(user);
    }

    public void deleteUser(UserObject user) {
        daoSession.delete(user);
    }

    public void deleteUsers(List<UserObject> users) {
        for (UserObject user : users) {
            daoSession.delete(user);
        }
    }

    public void updateUser(UserObject user) {
        daoSession.update(user);
    }

    public List<UserObject> queryUser(String name, Long age) {
        QueryBuilder<UserObject> query = userObjectDao.queryBuilder();
        query.where(query.and(UserObjectDao.Properties.Name.eq(name),
                UserObjectDao.Properties.Age.eq(age)));
        return query.list();
    }

    public List<UserObject> queryUser(String name) {
        QueryBuilder<UserObject> query = userObjectDao.queryBuilder();
        query.where(UserObjectDao.Properties.Name.eq(name));
        return query.list();
    }

    public List<UserObject> queryUser(Long age) {
        QueryBuilder<UserObject> query = userObjectDao.queryBuilder();
        query.where(UserObjectDao.Properties.Age.eq(age));
        return query.list();
    }

    public List<UserObject> queryAllUser() {
        QueryBuilder<UserObject> query = userObjectDao.queryBuilder();
        return query.list();
    }
}
