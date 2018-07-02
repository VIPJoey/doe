/**
 * Nora工具包.<br>
 * 请放在jq后加载.
 */

var Nora = window.Nora || {
	/**
	 * 返回整响应结果.
	 * @param url
	 * @param prams
	 * @param successFun
	 * @param failFun
	 */
	"Ajax":  function(url, prams, successFun, failFun) {
	    $.ajax({
	        type : "POST",
	        url : url,
	        data: prams,
	        success : function(result){
	        	if (typeof(successFun) == "function") {
	        		successFun(result);
	        	}
	        },
	        error: function(xhr, msg, e) {
	        	if (typeof(failFun) == "function") {
	        		failFun(xhr, msg, e);
	        	} else {
	        		alert("Nora.request函数调用出错：" + msg + " " + e);
	        	}
	        }
	    });
	},
	/**
	 * 只返回数据部分.
	 * @param url
	 * @param prams
	 * @param successFun
	 * @param failFun
	 */
	"Request":  function(url, prams, successFun, failFun) {
		$.ajax({
			type : "POST",
			url : url,
			data: prams,
			success : function(result){
				if (typeof(successFun) == "function") {
					successFun(result.data);
				}
			},
			error: function(xhr, msg, e) {
				if (typeof(failFun) == "function") {
					failFun(xhr, msg, e);
				} else {
					alert("Nora.request函数调用出错：" + msg + " " + e);
				}
			}
		});
	},
	"Util": {
		"StringUtil": {
			"format": function(source, args) {
				var result = source;
				if (arguments.length > 1) {
					for (var i = 1; i < arguments.length; i++) {
						if (arguments[i] != undefined) {
							result = result.replace("{}", arguments[i]);
						}
					}
				}
				return result;
			}
		},
		"DateUtil": {
			"format": function(source, pattern) {
				 var o = {
					     "M+": source.getMonth() + 1, //月份 
					     "d+": source.getDate(), //日 
					     "h+": source.getHours(), //小时 
					     "m+": source.getMinutes(), //分 
					     "s+": source.getSeconds(), //秒 
					     "q+": Math.floor((source.getMonth() + 3) / 3), //季度 
					     "S": source.getMilliseconds() //毫秒 
					 };
				 if (/(y+)/.test(pattern)) pattern = pattern.replace(RegExp.$1, (source.getFullYear() + "").substr(4 - RegExp.$1.length));
				 for (var k in o)
					 if (new RegExp("(" + k + ")").test(pattern)) 
						 pattern = pattern.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
				 return pattern; 
			}
		},
		"TemplateUtil": {
			"formate": function(dta, tmpl) {
			    var format = {  
			            name: function(x) {  
			                return x  
			            }  
			        };  
		        return tmpl.replace(/{(\w+)}/g, function(m1, m2) {  
		            if (!m2)  
		                return "";  
		            return (format && format[m2]) ? format[m2](dta[m2]) : dta[m2];  
		        });  
			}
		}
	}
};
