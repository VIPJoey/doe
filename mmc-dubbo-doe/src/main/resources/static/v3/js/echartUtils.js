/**
 * 
 */

	function initLine(divid,name,data,data2) {
	            require([ 'echarts', 'echarts/chart/line' // 使用柱状图就加载bar模块，按需加载
	            ], function(ec) {
		            var dataArray = [];
		            var cityName = [];
		            for(var i=0;i<data.length;i++){
		            	dataArray.push(data[i].rate);
		            	cityName.push(data[i].name);
		            }
	                // 基于准备好的dom，初始化echarts图表
	                var detailChart = ec.init(document.getElementById(divid));
	                var option = {
	                    tooltip : {
	                        trigger : 'axis',
	                        formatter: "{b} <br/>{a}: {c}%"
	                    },
	                    legend : {
	                        data : [name]
	                    },
	                    toolbox : {
	                        feature : {
	                            saveAsImage : {}
	                        }
	                    },
	                    grid : {
	                        left : '3%',
	                        right : '4%',
	                        bottom : '3%',
	                        containLabel : true
	                    },
	                    xAxis : [ {
	                        type : 'category',
	                        boundaryGap : false,
	                        axisLabel: {
	                            interval: 0,
	                            rotate: 0,
	                            formatter:function(val){
	                            	return val.split("").join("\n"); //横轴信息文字竖直显示
	                            }
	                        },
	                        data : cityName
	                    } ],
	                    yAxis : [ {
	                         type : 'value',
	                         name : '同比:%'
	                         
	                     }],
	                    series : [ {
	                        name : name,
	                        type : 'line',
	                        data : dataArray
	                    },{
	                        name : "汇总线",
	                        type : 'line',
	                        label: "汇总线",
	                        symbolSize:0,  //图标尺寸
	                        markPoint: {
	                        	symbolSize: 15,
	                        	data: [{name: '汇总线', value: data2*100, xAxis: dataArray.length, yAxis: data2*100}]
	                        },
	                        itemStyle:{
	                            normal:{
	                            	lineStyle: {
	                            		type: "dashed",
	                            		color: "#F00", //图标颜色
	                            		width: 2
	                            	}
	                            }
	                        },
	                        data : drawSumline(dataArray, data2)
	                    }

	                    ]
	                };
	                // 为echarts对象加载数据 
	                detailChart.setOption(option,true);
	            });

	        }
	
	function drawSumline(dataArray, data2) {
		var data = [];
		for (var i = 0; i < dataArray.length; i++) {
			data[i] = data2 * 100;
		}
		return data;
	}
	
	