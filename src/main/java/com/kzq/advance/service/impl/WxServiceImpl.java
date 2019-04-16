package com.kzq.advance.service.impl;


import com.kzq.advance.common.utils.HttpUtils;
import com.kzq.advance.common.utils.MD5Util;
import com.kzq.advance.domain.TCustomer;
import com.kzq.advance.domain.TPwBill;
import com.kzq.advance.domain.TUser;
import com.kzq.advance.domain.TWsBill;
import com.kzq.advance.domain.vo.PwBillVo;
import com.kzq.advance.mapper.TPwBillMapper;
import com.kzq.advance.mapper.TUserMapper;
import com.kzq.advance.mapper.TWsBillMapper;
import com.kzq.advance.service.IWxService;
import com.taobao.api.internal.toplink.embedded.websocket.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zmn
 * @since 2018-08-16
 */

@Service("IWxService")
public class WxServiceImpl implements IWxService {
    @Autowired
    private TUserMapper userMapper;
    @Autowired
    private TWsBillMapper wsBillMapper;
    @Autowired
    private TPwBillMapper tpwBillMapper;

    /**
     * 登录验证
     * @param name
     * @param password
     * @param openId
     * @return
     */
    public int doLogin(String name,String password,String openId) {
        if (StringUtils.isNotBlank(name)){
            String tpassword=userMapper.doLogin(name);
               MD5Util md5=new MD5Util();
               if (md5.encryption(password).equals(tpassword)){
                   return 1;
               }

        }
        else if (StringUtils.isNotBlank(openId)){
            TUser user=findUserByOpenId(openId);
            if (StringUtils.isNotBlank(user.getId())){
                return 1;

            }
        }


        return 0;
    }

    /**
     * 根据微信openid获取用户
     * @param openId
     * @return
     */
    public TUser findUserByOpenId(String openId){
        TUser u=new TUser();
        u.setOpenId(openId);
        return userMapper.findUser(u);
    }
    public TUser findUserByloginName(String loginName){
        TUser u=new TUser();
        u.setLoginName(loginName);
        return userMapper.findUser(u);
    }

    public void updateUserOpenId(TUser u){
        userMapper.updateUserOpenId(u);
    }
/**
 *     出库单列表
 */

    public List<TWsBill> findTWsBill(Integer status){
        return wsBillMapper.findTWsBill(status);
    }

    public TWsBill findDetailById(String id){
        return wsBillMapper.findDetailById(id);
    }

    public TCustomer findCustomerById(String id){
        return wsBillMapper.findCustomerById(id);
    }
    public int updateTWsBill(TWsBill wsBill){
         try {
             wsBillMapper.updateTWsBill(wsBill);
            return 1;
         }catch (Exception e){

          e.printStackTrace();
         }
         return 0;
    }
    public List<TWsBill> findGoodsByBillId(String id){
       return wsBillMapper.findGoodsByBillId(id);
    }
    /**
     *   查询采购入库单
     */

    public   List<PwBillVo> PWBillList(){

        return tpwBillMapper.findPwBillList(null);
    }
    public PwBillVo findTPwBillById(String id){
        return tpwBillMapper.findTPwBillById(id);
    }
    public List<PwBillVo> findPwBillDetailById(String id){
        return tpwBillMapper.findDetailById(id);
    }
    public void updateTPwBill(TPwBill bill){
         tpwBillMapper.updateTPwBill(bill);

    }


    public List<TWsBill> findTWsBillbyCondition(String condition){
        return  wsBillMapper.findTWsBillbyCondition(condition);
    }

    public  List<PwBillVo> findPwBillList(String keyword){
        return tpwBillMapper.findPwBillList(keyword);
    }

    /**
     * 判断是否是仓库管理员
     */
    public String isWarehouse(String userId){
        //查询该用户的部门名称是否有所对应的仓库

           return userMapper.findUserWarehouse(userId);


    }
    /**
     * 根据warehouse查询出库单总单
     */
    public List<TWsBill> getBillByWarehouse(String id){
        Map<String,String> map=new HashMap();
        map.put("warehouseId",id);
        return  wsBillMapper.getBillByWarehouse(map);
    }
    /**
     * 根据warehouse查询出库单详单
     */
    public List<TWsBill> getBillDetailByWarehouse(String id,String warehouseId){
         TWsBill bill=new TWsBill();
         bill.setId(id);
         bill.setWarehouseId(warehouseId);
         return  wsBillMapper.findDetailByBillIdAndWarehouse(bill);

    }

}
