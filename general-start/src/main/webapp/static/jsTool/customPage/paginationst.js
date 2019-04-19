var njqpage = {
	//总页码数
	total:0,
	//显示分页条的地方
	excId:'pageId',
	size:10,
	//总数据量
	totalNum:300,
	//加载第几页
	index:1,
	param:{
		a:"<a href='?page={0}",
		b:"onclick=",
		c:"'njqpage.selectIndex(this)'",
		d:">",
		e:"</a>",
		str:"",
		reqParam:""
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
	configParam:function(){
		this.joinParam.pre=(this.param.f+"id='customPrevious'"+this.param.d+'上一页'+this.param.e).replace("{0}",(this.index-1));
		this.joinParam.next=(this.param.f+"id='customNext'"+this.param.d+'下一页'+this.param.e).replace("{0}",(this.index+1));
		this.joinParam.omit=this.param.f+this.param.d+"..."+this.param.e;
		this.joinParam.first=this.param.f+this.param.d+1+this.param.e;
		this.joinParam.before=this.param.f+this.param.d;
		this.joinParam.select=this.param.f+"class='selectIndex'"+this.param.d
	},
	init:function(config){
		this.total = config.total;
		this.excId = config.excId;
		this.index = config.index;
		this.param.reqParam=config.req;
		if(this.param.reqParam != ""){
			this.param.f=this.param.a+"&"+this.param.reqParam+"'";
		}else{
			this.param.f=this.param.a+"'";
		}
		this.configParam();
	},
	makePage:function(config,type){
		this.init(config);
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
					this.param.str+= this.joinParam.select.replace("{0}",i)+i+this.param.e;
				}else{
					this.param.str+= this.joinParam.before.replace("{0}",i)+i+this.param.e;
				}
			}
			return;
		}else if(type==2){
			for(var i=1;i<=7;i++){
				if(this.index==i){
					this.param.str+=this.joinParam.select.replace("{0}",i)+i+this.param.e;
				}else{
					this.param.str+=this.joinParam.before.replace("{0}",i)+i+this.param.e;
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
					this.param.str+=this.joinParam.select.replace("{0}",i)+i+this.param.e;
				}else{
					this.param.str+=this.joinParam.before.replace("{0}",i)+i+this.param.e;
				}
			}
			this.param.str+=this.joinParam.omit;
			this.param.str+=this.param.f+this.param.d+this.total+this.param.e;
			return;
		}else if(type==4){
			this.param.str+=this.joinParam.first;
			this.param.str+=this.joinParam.omit;
			for(var i=(this.total-6);i<=this.total;i++){
				if(this.index==i){
					this.param.str+=this.joinParam.select.replace("{0}",i)+i+this.param.e;
				}else{
					this.param.str+=this.joinParam.before.replace("{0}",i)+i+this.param.e;
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
	showTotal:function(){
		this.param.str+="当前第"+this.index+"页/共"+this.total+"页"
			+"转到<input type='text' maxlength='2' onkeyup='' onafterpaste=''"
			+" id='customJumpValue' style='width: 15px;'>"
			+"页 <a href='javascript:void(0)' type='button'  onclick=''  style='width: 42px;' >确定</a>";
	}
}