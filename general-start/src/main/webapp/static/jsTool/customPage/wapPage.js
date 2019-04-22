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
			//第一页
			first:"",
			//最后一页
			last:"",
			//标签前半部分
			before:""
		},
		configParam:function(){
			if(this.index <= 1){
				this.joinParam.pre= (this.param.f+" id='customPrevious' class='unablest' "+this.param.d+"&lt;"+this.param.e).replace(/href='.*?'/g,"href='javascript:void(0)'");
			}else{
				this.joinParam.pre= this.makeHref((this.param.f+" id='customPrevious' "+this.param.d+"&lt;"+this.param.e),(this.index-1));
			}
			if(this.index  >= this.total){
				this.joinParam.next=(this.param.f+" id='customNext' class='unablest' "+this.param.d+"&gt;"+this.param.e).replace(/href='.*?'/g,"href='javascript:void(0)'");
			}else{
				this.joinParam.next= this.makeHref((this.param.f+" id='customNext' "+this.param.d+"&gt;"+this.param.e),(this.index+1));
			}
			if(this.index <= 1){
				this.joinParam.first=this.makeHref((this.param.f+ " class='unablest' " +this.param.d+"|&lt;"+this.param.e),1).replace(/href='.*?'/g,"href='javascript:void(0)'");
			}else{
				this.joinParam.first=this.makeHref((this.param.f+this.param.d+"|&lt;"+this.param.e),1);
			}
			if(this.index >= this.total){
				this.joinParam.last =this.makeHref((this.param.f+ " class='unablest' " +this.param.d+"&gt;|"+this.param.e),this.total).replace(/href='.*?'/g,"href='javascript:void(0)'");
			}else{
				this.joinParam.last =this.makeHref((this.param.f+this.param.d+"&gt;|"+this.param.e),this.total);
			}
			this.joinParam.select=this.makeHref((this.param.f+" class='selectIndex' "+this.param.d+this.index+this.param.e),this.index);
		},
		init:function(config){
			this.excId = config.excId;
			if(config.index){
				this.index = config.index;
			}
			if(config.size){
				this.size=config.size;			
			}
			if(config.req){
				this.param.reqParam=config.req;				
			}
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
			this.render();
		},
		render:function(){
			this.param.str += this.joinParam.first;
			this.param.str += this.joinParam.pre;
			this.param.str += this.joinParam.select;
			this.param.str += this.joinParam.next;
			this.param.str += this.joinParam.last;
			$(this.excId).html("<div class='customPage'>"+this.param.str+"</div>");
		},
		makeHref:function(href,value){
			return href.replace("{0}",value);
		}
}