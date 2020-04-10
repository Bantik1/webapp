package com.example.web.app.dao;

import com.example.web.app.api.request.AllUsersId;
import com.example.web.app.dao.model.User;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class DbSqlite implements InitializingBean {
    private Logger log = Logger.getLogger(getClass().getName());

    private String dbPath = "webappp-example.db";

    @Override
    public void afterPropertiesSet() throws Exception {
        initDb();
    }

    public void initDb() {
        try {
            Class.forName("org.sqlite.JDBC");
            try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath)) {
            }
        } catch (ClassNotFoundException | SQLException ex) {
            log.log(Level.WARNING, "База не подключена", ex);
        }
    }

    public Boolean execute(String query) {
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
             Statement stat = conn.createStatement()) {
            return stat.execute(query);
        } catch (SQLException ex) {
            log.log(Level.WARNING, "Не удалось выполнить запрос", ex);
            return false;
        }
    }

    public User selectUserById(int id) {
        String query = "select * from USER where id = " + id;
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
             Statement stat = conn.createStatement()) {
            ResultSet resultSet = stat.executeQuery(query);
            User user = new User();
            user.setId(resultSet.getInt("id"));
            user.setSecondname(resultSet.getString("secondname"));
            user.setBirthday(resultSet.getDate("birthday"));
            user.setName(resultSet.getString("name"));
            user.setNumberPhone(resultSet.getString("phone_number"));
            user.setGroupNumber(resultSet.getString("group_number"));
            user.setInfo(resultSet.getString("info"));
            user.setGender(resultSet.getString("gender"));
            return user;
        } catch (SQLException ex) {
            log.log(Level.WARNING, "Не удалось выполнить запрос", ex);
            return new User();
        }
    }

    public Boolean addUser(User user) {
        StringBuilder query = new StringBuilder("insert into USER (name, secondname, birthday, phone_number, group_number, info, gender) values ('");
        query.append(user.getName());
        query.append("','");
        query.append(user.getSecondname());
        query.append("','");
        query.append(user.getBirthdayTime());
        query.append("','");
        query.append(user.getNumberPhone());
        query.append("','");
        query.append(user.getGroupNumber());
        query.append("','");
        query.append(user.getInfo());
        query.append("','");
        query.append(user.getGender());
        query.append("');");
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
             Statement stat = conn.createStatement()) {
            return stat.execute(query.toString());
        } catch (SQLException ex) {
            log.log(Level.WARNING, "Не удалось выполнить запрос", ex);
            return null;
        }

    }

    public AllUsersId getAllUsersId() {
        String query = "select ID from USER";
        AllUsersId idList = new AllUsersId();
        List<Integer> list = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
             Statement stat = conn.createStatement()) {
            ResultSet resultSet = stat.executeQuery(query);
            while (resultSet.next()) {
                list.add(resultSet.getInt("ID"));
            }
            idList.setListId(list);
            return idList;
        } catch (SQLException ex) {
            log.log(Level.WARNING, "Не удалось выполнить запрос", ex);
            return new AllUsersId();
        }
    }
}