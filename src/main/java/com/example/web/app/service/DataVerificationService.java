package com.example.web.app.service;

import com.example.web.app.dao.DbSqlite;
import com.example.web.app.dao.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class DataVerificationService {
    private final  DbSqlite dbSqlite;

    public  DataVerificationService(DbSqlite dbSqlite) {this.dbSqlite = dbSqlite;}

    public List<String> getErrors(User user) {
        List<String> errors = new ArrayList<>();
        if(!getNickname(user.getNickname()).isEmpty()) {
            errors.add("Ник существует");
        }
        if(!getName(user.getName())) {
            errors.add("Поле 'имя' не заполнено");
        }
        if(!checkBirth(user.getBirthday())) {
            errors.add("ДР заполнено не правильно");
        }
        if(!checkPhone(user.getNumberPhone())) {
            errors.add("Номер телефона заполнен не правильно");
        }
        return  errors;
    }

    public List<String> getEditErrors(User user) {
        List<String> errors = new ArrayList<>();
        if(!getName(user.getName())) {
            errors.add("Поле 'имя' не заполнено");
        }
        if(!checkBirth(user.getBirthday())) {
            errors.add("ДР заполнено не правильно");
        }
        if(!checkPhone(user.getNumberPhone())) {
            errors.add("Номер телефона заполнен не правильно");
        }
        return  errors;
    }

    public String getNickname(String nick) {
        String error = "";
        if(dbSqlite.getNickname(nick)) {
            error = "Ник существует";
        }
        return error;
    }

    public Boolean getName(String name){
        return !name.isEmpty(); //true - не пусто
    }

    public Boolean checkBirth(Date date) {
        Date current_date = new Date();
        Date min_date = new Date();
        min_date.setYear(min_date.getYear() - 150);
        return date.before(current_date) && date.after(min_date); //true - правильно
    }

    public Boolean checkPhone(String phone){
        Pattern p = Pattern.compile("^[+0-9]{9,11}[^A-Za-zА-ЯА-я]$");
        Matcher m = p.matcher(phone);
        return m.matches(); //true - правильно
    }
}
