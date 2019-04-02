<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>概况总览</title>
<jsp:include page="${path}/commonTopLink"></jsp:include>
</head>
<body>
	<!-- 通用顶部 -->
	<jsp:include page="${path}/commonTop"></jsp:include>
	<!-- 中下部分 -->
	<div id="centerPlace">
		<!-- 通用左边菜单 -->	
		<jsp:include page="${path}/commonLeft"></jsp:include>
		<!-- 正文区域 -->
		<div id="rightContext">
			<div class="menu-box barAreaDiv"  >
			
				<div class="barTopw">
					<h2 class="barLeftDiv">
                        <i class="icon-ambulance leftLogo"></i> 总览概况
                    </h2>
				</div>
				<div class="barAreaContextDiv">
					<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
                    <div id="main" style="width: 1000px;height:400px;"></div>
<%--                     ${powerMap } --%>
<%--                     <c:if test="${powerMap[1]==true}">11111</c:if> --%>
                    <div id="xsCharts" style="width: 1000px;height:400px;"></div>
				</div>
			</div>
		</div>
		<!-- 通用底部 -->	
		<jsp:include page="${path}/commonBottom"></jsp:include>
	</div>
</body>
<jsp:include page="${path}/commonBottomLink"></jsp:include>
<script type="text/javascript" src="${resPath }/back/js/echarts.min.js"></script>
<script type="text/javascript">
	//基于准备好的dom，初始化echarts实例
	var myChart = echarts.init(document.getElementById('main'));
	
	$.ajax({
		url:"${path}/admin/totalInfo/queryXlCharts",
		type:"post",
		success:function(data){
			createBarCharts(data);
		}
	})
	function createBarCharts(data){
		var option = {
		    title : {
		        text: '近12月份发表文章统计',
		        subtext: '仅供参考'
		    },
		    tooltip : {
		        trigger: 'axis'
		    },
		    legend: {
		        data:['非系列','系列']
		    },
		    toolbox: {
		        show : true,
		        feature : {
		            dataView : {show: true, readOnly: false},
		            magicType : {show: true, type: ['line', 'bar']},
		            restore : {show: true},
		            saveAsImage : {show: true}
		        }
		    },
		    calculable : true,
		    xAxis : [
		        {
		            type : 'category',
		            data : data.time
		        }
		    ],
		    yAxis : [
		        {
		            type : 'value'
		        }
		    ],
		    series : [
		        {
		            name:'非系列',
		            type:'bar',
		            data:data.dl,
		            markPoint : {
		                data : [
		                    {type : 'max', name: '最大值'},
		                    {type : 'min', name: '最小值'}
		                ]
		            },
		            markLine : {
		                data : [
		                    {type : 'average', name: '平均值'}
		                ]
		            }
		        },
		        {
		            name:'系列',
		            type:'bar',
		            data:data.xl,
		            markPoint : {
		                data : [
		                    
		                     {type : 'max', name: '最大值'},
		                    {type : 'min', name: '最小值'}
		                ]
		            },
		            markLine : {
		                data : [
		                    {type : 'average', name : '平均值'}
		                ]
		            }
		        }
		    ]
		};
		
		// 使用刚指定的配置项和数据显示图表。
		myChart.setOption(option);
	}
	
	
	
	
	
	var xsChart = echarts.init(document.getElementById('xsCharts'));
	
	$.ajax({
		url:"${path}/admin/totalInfo/queryXsCharts",
		type:"post",
		success:function(data){
			createXsCharts(data);
		}
	})
	
	function createXsCharts(data){
	    // 指定图表的配置项和数据
	    var option1 = {
		    title : {
		        text: '近12月份发表小说统计',
		        subtext: '仅供参考'
		    },
		    tooltip : {
		        trigger: 'axis'
		    },
		    legend: {
		        data:['小说标题']
		    },
		    toolbox: {
		        show : true,
		        feature : {
		            dataView : {show: true, readOnly: false},
		            magicType : {show: true, type: ['line', 'bar']},
		            restore : {show: true},
		            saveAsImage : {show: true}
		        }
		    },
		    calculable : true,
		    xAxis : [
		        {
		            type : 'category',
		            data : data.time
		        }
		    ],
		    yAxis : [
		        {
		            type : 'value'
		        }
		    ],
		    series : [
		        {
		            name:'非系列',
		            type:'bar',
		            data:data.nums,
		            markPoint : {
		                data : [
		                    {type : 'max', name: '最大值'},
		                    {type : 'min', name: '最小值'}
		                ]
		            },
		            markLine : {
		                data : [
		                    {type : 'average', name: '平均值'}
		                ]
		            }
		        }
		    ]
		};
	
	    // 使用刚指定的配置项和数据显示图表。
	    xsChart.setOption(option1);
	}
    
</script>
</html>