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

import java.util.ArrayList;
import java.util.List;

/**
 * 菜单节点.
 * @author Joey
 * 2016年6月24日 下午4:50:47
 */
public class MenuNode extends MenuTree{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1456456456L;
    
    private List<MenuNode> children = new ArrayList<MenuNode>();

    public List<MenuNode> getChildren() {
        return children;
    }

    public void setChildren(List<MenuNode> children) {
        this.children = children;
    }
    
    

}
