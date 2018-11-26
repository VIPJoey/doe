/*  
 * Copyright (c) 2010-2020 Founder Ltd. All Rights Reserved.  
 *  
 * This software is the confidential and proprietary information of  
 * Founder. You shall not disclose such Confidential Information  
 * and shall use it only in accordance with the terms of the agreements  
 * you entered into with Founder.  
 *  
 */ 
/**
 * 
 */
package com.mmc.dubbo.doe.auth;

import java.io.Serializable;

/**
 * 菜单树实体类.
 * @author Joey
 * 2016年5月26日 下午1:20:34
 */
public class MenuTree implements Serializable{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1485485452L;
    
    private Integer uId;
    private Integer roleId;
    private Integer menuId;
    private Integer pmenuId;
    private String menuName;
    private String menuUrl;
    private String menuStyle;
    private Integer mlevel;
    private Integer mleft;
    private Integer mright;
    
    public Integer getuId() {
        return uId;
    }
    public void setuId(Integer uId) {
        this.uId = uId;
    }
    public Integer getMenuId() {
        return menuId;
    }
    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }
    public Integer getPmenuId() {
        return pmenuId;
    }
    public void setPmenuId(Integer pmenuId) {
        this.pmenuId = pmenuId;
    }
    public String getMenuName() {
        return menuName;
    }
    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }
    public String getMenuUrl() {
        return menuUrl;
    }
    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }
    public String getMenuStyle() {
        return menuStyle;
    }
    public void setMenuStyle(String menuStyle) {
        this.menuStyle = menuStyle;
    }
    public Integer getMlevel() {
        return mlevel;
    }
    public void setMlevel(Integer mlevel) {
        this.mlevel = mlevel;
    }
    public Integer getMleft() {
        return mleft;
    }
    public void setMleft(Integer mleft) {
        this.mleft = mleft;
    }
    public Integer getMright() {
        return mright;
    }
    public void setMright(Integer mright) {
        this.mright = mright;
    }
    public Integer getRoleId() {
        return roleId;
    }
    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
    
    
}
