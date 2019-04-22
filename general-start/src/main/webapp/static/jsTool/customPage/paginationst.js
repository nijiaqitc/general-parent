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
		a:"<a href='",
		b:"onclick=",
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
		if(this.index <= 1){
			this.joinParam.pre= (this.param.f+"id='customPrevious'"+this.param.d+'上一页'+this.param.e).replace(/href='.*?'/g,"href='javascript:void(0)'");
		}else{
			this.joinParam.pre=this.makeHref((this.param.f+"id='customPrevious'"+this.param.d+'上一页'+this.param.e),(this.index-1));
		}
		if(this.index  >= this.total){
			this.joinParam.next=(this.param.f+"id='customNext'"+this.param.d+'下一页'+this.param.e).replace(/href='.*?'/g,"href='javascript:void(0)'");
		}else{
			this.joinParam.next=this.makeHref((this.param.f+"id='customNext'"+this.param.d+'下一页'+this.param.e),(this.index+1));
		}
		this.joinParam.omit=this.param.a+"javascript:void(0)'"+this.param.d+"..."+this.param.e;
		this.joinParam.first=this.makeHref((this.param.f+this.param.d+1+this.param.e),1);
		this.joinParam.before=this.param.f+this.param.d;
		this.joinParam.select=this.param.f+"class='selectIndex'"+this.param.d
	},
	init:function(config){
		this.excId = config.excId;
		if(config.index){
			this.index = config.index;			
		}
		if(config.size){
			this.size=config.size;			
		}
		this.param.reqParam=config.req;
		if(this.param.reqParam != ""){
			this.param.f=this.param.a+"?page={0}&"+this.param.reqParam+"'";
		}else{
			this.param.f=this.param.a+"?page={0}'";
		}
		if(config.totalNum){
			this.totalNum = config.totalNum;
			//计算出总共要分多少页
			this.total=Math.ceil(this.totalNum/this.size);
		}
		this.configParam();
	},
	makePage:function(config){
		this.init(config);
		if(this.index>this.total){
			this.index = 1;
		}
		if(this.total <= 10){
			this.render(1);
			return;
		}
		//判断当前页码属于哪种类型（如果大于5同时小于总数-4那么属于两边有省略号）
		if(this.index>5&&this.index<(this.total-4)){
			this.render(3);
			return;
		}
		//判断当前页码属于哪种类型（当前类型属于左边有省略号）
		if(this.index>=(this.total-4)&&this.index<=this.total){
			this.render(4);
			//如果是最后一页，那么下一页要变色
			if(this.index==this.total){
				$("#customNext").attr("class","selectIndex");
			}
			return;
		}
		//判断当前页码属于哪种类型（当前类型属于右边有省略号）
		if(this.index<=5){
			this.render(2);
			//如果是第一页，那么上一页要变色
			if(this.index==1){
				$("#customPrevious").attr("class","selectIndex");
			}
			return ;
		}
	},
	render:function(type){
		if(type==1){
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
					this.param.str+=this.makeHref(this.joinParam.select,i)+i+this.param.e;
				}else{
					this.param.str+=this.makeHref(this.joinParam.before,i)+i+this.param.e;
				}
			}
			return;
		}else if(type==2){
			for(var i=1;i<=7;i++){
				if(this.index==i){
					this.param.str+=this.makeHref(this.joinParam.select,i)+i+this.param.e;
				}else{
					this.param.str+=this.makeHref(this.joinParam.before,i)+i+this.param.e;
				}
			}
			this.param.str+=this.joinParam.omit;
			this.param.str+=this.makeHref(this.joinParam.before,this.total)+this.total+this.param.e;
			return;
		}else if(type==3){
			this.param.str+=this.joinParam.first;
			this.param.str+=this.joinParam.omit;
			for(var i=(this.index-2);i<=(this.index+2);i++){
				if(this.index==i){
					this.param.str+=this.makeHref(this.joinParam.select,i)+i+this.param.e;
				}else{
					this.param.str+=this.makeHref(this.joinParam.before,i)+i+this.param.e;
				}
			}
			this.param.str+=this.joinParam.omit;
			this.param.str+=this.makeHref(this.param.f,this.total)+this.param.d+this.total+this.param.e;
			return;
		}else if(type==4){
			this.param.str+=this.joinParam.first;
			this.param.str+=this.joinParam.omit;
			for(var i=(this.total-6);i<=this.total;i++){
				if(this.index==i){
					this.param.str+=this.makeHref(this.joinParam.select,i)+i+this.param.e;
				}else{
					this.param.str+=this.makeHref(this.joinParam.before,i)+i+this.param.e;
				}
			}
			return;
		}
	},
	makeHref:function(href,value){
		return href.replace("{0}",value);
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
			+"转到<input type='text' maxlength='2' onkeyup='njqpage.customKeyUp(this)' onafterpaste='njqpage.customKeypaste(this)'"
			+" id='customJumpValue' style='width: 20px;'>"
			+"页 <a id='customSureBtn' href='javascript:void(0)' type='button'  onclick=''  style='width: 42px;' >确定</a>";
	},
	customKeyUp:function(e){
		if($(e).val().length==1){
			$(e).val($(e).val().replace(/[^1-9]/g,''));
		}else{
			$(e).val($(e).val().replace(/\D/g,''));
		}
		this.setBtnValue($(e).val());
	},
	customKeypaste:function(e){
		if($(e).val().length==1){
			$(e).val($(e).val().replace(/[^1-9]/g,'0'))
		}else{
			$(e).val($(e).val().replace(/\D/g,''))
		}
		this.setBtnValue($(e).val());
	},
	setBtnValue:function(v){
		if(v>0&&v<=this.total){
			var pt = this.makeHref("?page={0}&"+this.param.reqParam,v);
			$("#customSureBtn").attr("href",pt);
		}else{
			$("#customSureBtn").attr("href","javascript:void(0)");
		}
	}
}