package com.example.web.app.controllers;

import com.example.web.app.api.request.UserByIdRequest;
import com.example.web.app.dao.DbSqlite;
import com.example.web.app.dao.model.User;
import com.example.web.app.service.DataVerificationService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/select/user")
public class SelectUserController {
    private final DbSqlite dbSqlite;
    private final PasswordEncoder passwordEncoder;

    public SelectUserController(DbSqlite dbSqlite, PasswordEncoder passwordEncoder) {
        this.dbSqlite = dbSqlite;
        this.passwordEncoder = passwordEncoder;
    }

    @ApiOperation(value = "get first user")
    @RequestMapping(value = "get/first/user", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getFirstUser() {
        User user = dbSqlite.getFirstUser();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(user, headers, HttpStatus.OK);
    }

    @ApiOperation(value = "next user")
    @RequestMapping(value = "next/user", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> nextUser(@RequestBody UserByIdRequest id) {
        User user = dbSqlite.nextUser(id.getId());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(user, headers, HttpStatus.OK);
    }

    @ApiOperation(value = "prev user")
    @RequestMapping(value = "prev/user", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> prevUser(@RequestBody UserByIdRequest id) {
        User user = dbSqlite.prevUser(id.getId());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(user, headers, HttpStatus.OK);
    }

    @ApiOperation(value = "Проверка существует ли ник")
    @RequestMapping(value = "get/nick", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> getNickname(@RequestBody UserByIdRequest user) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        DataVerificationService verification = new DataVerificationService();
        String error = verification.getNickname(user.getNickname());
        if (!error.isEmpty()) {
            return new ResponseEntity<>(false, headers, HttpStatus.BAD_REQUEST);
        } else return new ResponseEntity<>(true, headers, HttpStatus.OK);
    }

    @ApiOperation(value = "Добавить")
    @RequestMapping(value = "add/user", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> createNewUser(@RequestBody User user) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String passwordEncode = passwordEncoder.encode(user.getPassword());
        user.setPassword(passwordEncode);
        user.setNickname(user.getNickname().toLowerCase());
        DataVerificationService verification = new DataVerificationService();
        List<String> errors = verification.getErrors(user);
        if (errors.isEmpty()) {
            return new ResponseEntity<>(dbSqlite.addUser(user), headers, HttpStatus.OK);
        } else return new ResponseEntity<>(errors, headers, HttpStatus.BAD_REQUEST);
    }

    @ApiOperation(value = "Выбрать зарегистрированного пользователя")
    @RequestMapping(value = "get/user", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> selectUser() {
        String nickname = SecurityContextHolder.getContext().getAuthentication().getName();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(dbSqlite.getLoginUser(nickname), headers, HttpStatus.OK);
    }
}
