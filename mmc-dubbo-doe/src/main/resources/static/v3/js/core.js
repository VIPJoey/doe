/** 自定占位符 **/
String.prototype.format=function()  
{  
  if(arguments.length==0) return this;  
  for(var s=this, i=0; i<arguments.length; i++)  
    s=s.replace(new RegExp("\\{"+i+"\\}","g"), arguments[i]);  
  return s;  
};
//对Date的扩展，将 Date 转化为指定格式的String
//月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符， 
//年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字) 
//例子： 
//(new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423 
//(new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18 
Date.prototype.format = function (fmt) { //author: meizz 
 var o = {
     "M+": this.getMonth() + 1, //月份 
     "d+": this.getDate(), //日 
     "h+": this.getHours(), //小时 
     "m+": this.getMinutes(), //分 
     "s+": this.getSeconds(), //秒 
     "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
     "S": this.getMilliseconds() //毫秒 
 };
 if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
 for (var k in o)
 if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
 return fmt; 
}
Date.prototype.addDays = function(d) {
	this.setDate(this.getDate() + d);
	return this;
};
//定义一个全局的关联数组;
menuNodes = new Array();
function buildTree(data, pmenuId){
	menuNodes = new Array(); // 每次都清空.构建树的时候,顺便保留一份nodes数组
    var result = [] , temp;
    for (var i = 0; i < data.length; i++) {
    	menuNodes[data[i].menuId] = data[i]; // 以menuId为key,自身为value
        if (data[i].pmenuId == pmenuId) {
            result.push(data[i]);
            temp = buildTree(data, data[i].menuId);           
            if (temp.length > 0) {
                data[i].children = temp;
            }           
        }       
    }
    return result;
}

//非递归广度优先实现
var iterator1 = function (elmId, treeNodes) {
    if (!treeNodes || !treeNodes.length) return;

    var stack = [];

    //先将第一层节点放入栈
    for (var i = 0, len = treeNodes.length; i < len; i++) {
        stack.push(treeNodes[i]);
    }

    var item;

    while (stack.length) {
        item = stack.shift();
        if (-1 == item.pmenuId) {
            var html = '<ul id="{0}" class="nav nav-list"></ul>';
            html = html.format(item.menuId);
            $("#" + elmId).append(html);
            // elmId = item.menuId;
        } else {
        	if (item.children && item.children.length) {
        		
        		var html = '\
        			<li id="f{0}" > \
	                    <a href="#" class="dropdown-toggle"> \
	                    <i class="{1}"></i> \
	                    <span class="menu-text"> {2} </span> \
	                    <b class="arrow icon-angle-down"></b> \
	                    </a> \
        				<ul id="{0}" class="submenu"></ul>\
        			</li>\
        			';
        		html = html.format(item.menuId, item.menuStyle, item.menuName);
        		$("#" + item.pmenuId).append(html);
                // elmId = item.menuId;
        		
        	} else {
        		var html = '\
        			<li id="f{0}"> \
        			<a href="{1}"> \
        			<i class="{2}"></i> \
        			{3}\
        			</a> \
        			</li> \
        			';
        		//var theRealMenuUrl = item.menuUrl;
        		//if(theRealMenuUrl != '#'){
        		//	theRealMenuUrl = "javascript:loadMenuPage("+item.menuId+");"
        		//}
        		html = html.format(item.menuId, item.menuUrl, item.menuStyle, item.menuName);
        		$("#" + item.pmenuId).append(html);
        		//$("#" + elmId).append(html);
        	}
        }

        //如果该节点有子节点，继续添加进入栈底
        if (item.children && item.children.length) {
            stack = stack.concat(item.children);
        }
    }
};

function viewTree(elmId, datas) {
	iterator1(elmId, datas);
}


