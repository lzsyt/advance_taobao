package com.kzq.advance.mapper;


import com.kzq.advance.domain.TUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author zmn
 * @since 2018-08-22
 */
@Mapper
public interface TUserMapper {


     String doLogin(String name);
     public void updateUserOpenId(TUser u);
     public TUser findUser(TUser tUser);
     public String findUserWarehouse(String userId);

     public TUser findUserByUserId(String userId);


}