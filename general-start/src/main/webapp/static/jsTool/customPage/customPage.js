/**
 * 自定义分页
 * 每点击一个页码，全部重新生成一次所有页码
 */
var njqpage={
	//总页码数
	total:0,
	//显示分页条的地方
	excId:'pageId',
	//每页显示的数据量
	size:10,
	//总数据量
	totalNum:0,
	//加载第几页
	index:1,
	//页面打开立即加载数据，若为false则打开页面不进行立即加载数据，默认true
	loding:true,
	param:{
		a:"<a href='javascript:void(0)'",
		b:"onclick=",
		c:"'njqpage.selectIndex(this)'",
		d:">",
		e:"</a>",
		str:""
	},
	joinParam:{
		//上一页
		pre:"",
		//下一页
		next:"",
		//省略
		omit:"",
		//第一页
		first:"",
		//最后一页
		last:"",
		//标签前半部分
		before:""
	},
	click:{},
	init:function(config){
		this.total = config.total;
		this.excId = config.excId;
		this.click=config.click;
		if(config.loading==false){
			this.loding=false;
		}
		this.configParam();
		if(config.size!=null&&config.size!=""){
			this.size=config.size;			
		}
	},
	configParam:function(){
		this.joinParam.pre=this.param.a+"id='customPrevious'"+this.param.b+this.param.c+this.param.d+'上一页'+this.param.e;
		this.joinParam.next=this.param.a+"id='customNext'"+this.param.b+this.param.c+this.param.d+'下一页'+this.param.e;
		this.joinParam.omit=this.param.a+this.param.d+"..."+this.param.e;
		this.joinParam.first=this.param.a+this.param.b+this.param.c+this.param.d+1+this.param.e;
		this.joinParam.before=this.param.a+this.param.b+this.param.c+this.param.d;
		this.joinParam.select=this.param.a+this.param.b+this.param.c+"class='selectIndex'"+this.param.d
	},
	makePage:function(config,type){
		this.param.str="";
		//判断click是否初始化了，如果初始化了那么执行事件
		if(typeof(this.click)=="function"){
			if(type==5){
				this.index=1;
			}
			//进行的ajax操作
			this.click(this.index,this.size);
			//计算出总共要分多少页
			this.total=Math.ceil(this.totalNum/this.size);
			
		}
		if(type==undefined){
			//初始化页码变量
			this.init(config);
			if(this.loding==false){
				return;
			}
			//进行的ajax操作
			this.click(this.index,this.size);
			//计算出总共要分多少页
			this.total=Math.ceil(this.totalNum/this.size);
			//如果整个页码小于2，那么不生成页码
			if(this.total<2){
				this.pageFor0();
				return;
			}else {
				//生成上一页
				this.pageForPre();
				if(this.total<10){
					//如果整个页码总数小于8，不需要生成"..."这个符号
					this.pageForOther(1);
				}else{
					//如果整个页码总数大于8，需要生成"..."这个符号
					this.pageForOther(2);
				}
				//生成下一页
				this.pageForNext();
				this.showTotal();
				$(this.excId).html("<div class='customPage'>"+this.param.str+"</div>");
				$("#customPrevious").attr("class","selectIndex");
			}
		}else if(type==1){
			//如果整个页码总数小于10，不需要生成"..."这个符号
			if(this.total<10&&this.total>1){
				//生成上一页
				this.pageForPre();
				this.pageForOther(1);
				//生成下一页
				this.pageForNext();
				this.showTotal();
				$(this.excId).html("<div class='customPage'>"+this.param.str+"</div>");
			}
		}else if(type==5){
			this.index=1;
			if(this.total<=1){
				$(this.excId).html("");
				return;
			}
			//生成上一页
			this.pageForPre();
			if(this.total<10){
				//如果整个页码总数小于8，不需要生成"..."这个符号
				this.pageForOther(1);
			}else{
				//如果整个页码总数大于8，需要生成"..."这个符号
				this.pageForOther(2);
			}
			//生成下一页
			this.pageForNext();
			this.showTotal();
			$(this.excId).html("<div class='customPage'>"+this.param.str+"</div>");
			$("#customPrevious").attr("class","selectIndex");
		}else{
			//生成上一页
			this.pageForPre();
			//生成页码
			this.pageForOther(type);
			//生成下一页
			this.pageForNext();
			this.showTotal();
			$(this.excId).html("<div class='customPage'>"+this.param.str+"</div>");
		}
	},
	pageFor0:function(){
		//清空页码
		this.param.str="";
		return;
	},
	pageForOther:function(type){
		if(type==1){
			for(var i=1;i<=this.total;i++){
				if(this.index==i){
					this.param.str+=this.joinParam.select+i+this.param.e;
				}else{
					this.param.str+=this.joinParam.before+i+this.param.e;
				}
			}
			return;
		}else if(type==2){
			for(var i=1;i<=7;i++){
				if(this.index==i){
					this.param.str+=this.joinParam.select+i+this.param.e;
				}else{
					this.param.str+=this.joinParam.before+i+this.param.e;
				}
			}
			this.param.str+=this.joinParam.omit;
			this.param.str+=this.joinParam.before+this.total+this.param.e;
			return;
		}else if(type==3){
			this.param.str+=this.joinParam.first;
			this.param.str+=this.joinParam.omit;
			for(var i=(this.index-2);i<=(this.index+2);i++){
				if(this.index==i){
					this.param.str+=this.joinParam.select+i+this.param.e;
				}else{
					this.param.str+=this.joinParam.before+i+this.param.e;
				}
			}
			this.param.str+=this.joinParam.omit;
			this.param.str+=this.param.a+this.param.b+this.param.c+this.param.d+this.total+this.param.e;
			return;
		}else if(type==4){
			this.param.str+=this.joinParam.first;
			this.param.str+=this.joinParam.omit;
			for(var i=(this.total-6);i<=this.total;i++){
				if(this.index==i){
					this.param.str+=this.joinParam.select+i+this.param.e;
				}else{
					this.param.str+=this.joinParam.before+i+this.param.e;
				}
			}
			return;
		}
	},
	pageForPre:function(){
		//生成上一页
		this.param.str+=this.joinParam.pre;
		return;
	},
	pageForNext:function(){
		//生成下一页
		this.param.str+=this.joinParam.next;
		return;
	},
	selectIndex:function(e,num){
		//点击页码或上、下页重新构建页码
		this.param.str="";
		//num为跳转页码，如果不为空那么代表是手动跳转
		if(num!=undefined){
			this.index=num;
		}else{
			//提取当前点击的内容
			var thisValue=$(e).html();
			if(thisValue=="上一页"){
				//如果当前页是第一页，那么点击上一页就没反应
				if(this.index==1){
					return;
				}else{
					this.index-=1;
				}
			}else if(thisValue=="下一页"){
				//如果当前页是最后一页，那么点击下一页就没反应
				if(this.index==this.total){
					return;
				}else{
					this.index+=1;
				}
			}else{
				//获取点击页码的数字
				this.index=Number(thisValue);
			}
		}
		if(this.total<10){
			//生成页码
			this.makePage({},1);
			//页码生成完成后设置变色按钮
			if(this.index==this.total){
				$("#customNext").attr("class","selectIndex");
				return;
			}
			if(this.index==1){
				$("#customPrevious").attr("class","selectIndex");
				return;
			}
			return ;
		}
		//判断当前页码属于哪种类型（如果大于5同时小于总数-4那么属于两边有省略号）
		if(this.index>5&&this.index<(this.total-4)){
			this.makePage({},3);
			return;
		}
		//判断当前页码属于哪种类型（当前类型属于左边有省略号）
		if(this.index>=(this.total-4)&&this.index<=this.total){
			this.makePage({},4);
			//如果是最后一页，那么下一页要变色
			if(this.index==this.total){
				$("#customNext").attr("class","selectIndex");
			}
			return;
		}
		//判断当前页码属于哪种类型（当前类型属于右边有省略号）
		if(this.index<=5){
			this.makePage({},2);
			//如果是第一页，那么上一页要变色
			if(this.index==1){
				$("#customPrevious").attr("class","selectIndex");
			}
			return ;
		}
	},
	showTotal:function(){
		this.param.str+="当前第"+this.index+"页/共"+this.total+"页"
			+"转到<input type='text' maxlength='2' onkeyup='njqpage.customKeyUp(this)' onafterpaste='njqpage.customKeypaste(this)'"
			+" id='customJumpValue' style='width: 15px;'>"
			+"页 <a href='javascript:void(0)' type='button'  onclick='njqpage.jump(this)'  style='width: 42px;' >确定</a>";
	},
	jump:function(e){
		var v=$("#customJumpValue").val();
		if(v==0){
			return ;
		}
		if(v>this.total){
			$("#customJumpValue").val("");
		}else{
			this.selectIndex("",Number(v));
		}
	},
	customKeyUp:function(e){
		if($(e).val().length==1){
			$(e).val($(e).val().replace(/[^1-9]/g,''));
		}else{
			$(e).val($(e).val().replace(/\D/g,''));
		}
	},
	customKeypaste:function(e){
		if($(e).val().length==1){
			$(e).val($(e).val().replace(/[^1-9]/g,'0'))
		}else{
			$(e).val($(e).val().replace(/\D/g,''))
		}
	},
	reMake:function(){
		this.selectIndex("",Number(1));
	}
};
