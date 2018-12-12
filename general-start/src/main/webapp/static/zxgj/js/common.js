$(document).ready(function(){
      $(".nagivationMenu .nagivationMenuContext").mouseenter(function(){
          $(".moveDiv").css({display:'block'});
          $(".nagivationBottomSelect")[0].style.display="none";
          $(this).find("a")[0].style.color="#fff";
          $.each($(".nagivationBottom"),function(a,b){})
          if($(".moveDiv").css("left")==(this.offsetLeft+'px')){
              return;
          }else{
              $(".moveDiv").stop().animate({left: this.offsetLeft+'px'},"fast")
          }
      });
      
     $(".nagivationMenu .nagivationMenuContext").mouseleave(function(){
          $(".moveDiv").css({display:'none'});
          $(".nagivationBottomSelect")[0].style.display="";
          $(this).find("a")[0].style.color="#444";
      });
      
      
      $(".toolsBtn4").mouseover(function(){
          $(this).stop().animate({width:'136px',height:'66px',margin:'0px',lineHeight:'66px',fontSize:'24px'});
      });
      $(".toolsBtn4").mouseout(function(){
          $(this).stop().animate({width:'126px',height:'56px',margin:'5px',lineHeight:'56px',fontSize:'16px'});
      });
      
      
      $(".toolsTitle").mouseover(function(){
          $(this).stop().animate({top: '-10px'},"fast");
          
      })
      $(".toolsTitle").mouseout(function(){
          $(this).stop().animate({top: '0px'},"fast").animate({top: '-5px'},"fast").animate({top: '0px'},"fast").animate({top: '-2px'},"fast").animate({top: '0px'},"fast");
          
      }) 
      $(window).scroll(function(){
  		var srollPosH = $(window).scrollTop(); 
  		if(srollPosH>400){
  			$(".commonUl").show();
  		}else{
  			$(".commonUl").hide();
  		}
  	 });
      
    setTimeout(function(){
    	footReset()},200);
	$(window).resize(function(){
		footReset();
	});
	function footReset(){
		if($(document).height()<=$(window).height()){
			if(!$(".bottomInfoDiv").hasClass("bottomInfoAuto")){
				$(".bottomInfoDiv").addClass("bottomInfoAuto");				
			}
		}else{
			if($(".bottomInfoDiv").hasClass("bottomInfoAuto")){
				$(".bottomInfoDiv").removeClass("bottomInfoAuto");				
			}
		}
		$(".bottomInfoDiv").show();
	}
});