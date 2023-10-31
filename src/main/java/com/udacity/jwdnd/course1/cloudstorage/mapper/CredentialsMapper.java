package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.CredentialsModel;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;
@Mapper
public interface CredentialsMapper {

    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userId}")
    ArrayList<CredentialsModel> getCredentials(int userId);

    @Insert("INSERT INTO CREDENTIALS (url, username, key, password, userid) VALUES(#{url}, #{username}, #{key}, #{password}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    int insert(CredentialsModel credentialsModel);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialid = #{credentialId}")
    void delete(int id);

    @Update("Update CREDENTIALS SET url = #{url}, username = #{username}, password = #{password}, key = #{key} WHERE credentialid = #{credentialId}")
    void update(CredentialsModel credentialsModel);
}
