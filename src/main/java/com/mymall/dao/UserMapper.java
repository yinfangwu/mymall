package com.mymall.dao;

import com.mymall.pojo.User;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    int updatePasswordByUsername(String username, String md5Password);

    int checkUserName(String username);

    int checkEmail(String email);

    int checkEmailByUserId(Integer id, String email);

    User selectLogin(String username, String md5Password);

    String selectForgetQuestion(String username);

    int selectQuestionAnswer(String username, String question);

    int selectOldPassword(Integer id, String md5PasswordOld);
}