<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>顶部菜单</title>
	<script type="text/javascript">
		function loginOut(){
			$.ajax({
				url:"${path}/loginOut",
				type:"get",
				success:function(data){
					if(data.state==1){
						window.location="${path}/login"
					}
				}
			})
		}
	</script>
</head>
<body>
	<div class="navbar">
		<div class="navbar-inner">
			<div class="container-fluid">
				<a id="main-menu-toggle" class="hidden-phone open"><i class="icon-reorder"></i></a>		
				<div class="row-fluid">
				<a class="brand span2" href="${path}/totalInfo/backIndex"><span>后台系统</span></a>
				</div>		
				<!-- start: Header Menu -->
				<div class="nav-no-collapse header-nav">
					<ul class="nav pull-right" style="margin-top: 5px" >
						<!-- <li class="dropdown hidden-phone">
							<a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
								<i class="icon-warning-sign"></i>
							</a>
							<ul class="dropdown-menu notifications">
								<li class="dropdown-menu-title">
 									<span>You have 11 notifications</span>
								</li>	
                            	<li>
                                    <a href="#">
										<span class="icon blue"><i class="icon-user"></i></span>
										<span class="message">New user registration</span>
										<span class="time">1 min</span> 
                                    </a>
                                </li>
								<li>
                                    <a href="#">
										<span class="icon green"><i class="icon-comment-alt"></i></span>
										<span class="message">New comment</span>
										<span class="time">7 min</span> 
                                    </a>
                                </li>
							</ul>
						</li> -->
						<!-- start: Notifications Dropdown -->
						<!-- <li class="dropdown hidden-phone">
							<a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
								<i class="icon-tasks"></i>
							</a>
							<ul class="dropdown-menu tasks">
								<li>
									<span class="dropdown-menu-title">You have 17 tasks in progress</span>
                            	</li>
								<li>
                                    <a href="#">
										<span class="header">
											<span class="title">iOS Development</span>
											<span class="percent"></span>
										</span>
                                        <div class="taskProgress progressSlim progressBlue">80</div> 
                                    </a>
                                </li>
							</ul>
						</li> -->
						<!-- end: Notifications Dropdown -->
						<!-- start: Message Dropdown -->
						<%-- <li class="dropdown hidden-phone">
							<a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
								<i class="icon-envelope"></i>
							</a>
							<ul class="dropdown-menu messages">
								<li>
									<span class="dropdown-menu-title">You have 9 messages</span>
								</li>	
                            	<li>
                                    <a href="#">
										<span class="avatar"><img src="${resPath }/back/img/avatar.jpg" alt="Avatar" /></span>
										<span class="header">
											<span class="from">
										    	Łukasz Holeczek
										     </span>
											<span class="time">
										    	6 min
										    </span>
										</span>
                                        <span class="message">
                                            Lorem ipsum dolor sit amet consectetur adipiscing elit, et al commore
                                        </span>  
                                    </a>
                                </li>
								<li>
                            		<a class="dropdown-menu-sub-footer">View all messages</a>
								</li>	
							</ul>
						</li> --%>
						<!-- end: Message Dropdown -->
						<!-- <li>
							<a class="btn" href="#">
								<i class="icon-wrench"></i>
							</a>
						</li> -->
						<!-- start: User Dropdown -->
						<li class="dropdown" style="margin-top: 5px">
							<a class="btn account dropdown-toggle" data-toggle="dropdown" href="#">
								<c:if test="${user.picPlace!=null }">
									<div class="avatar"><img id="topPic" src="${user.picPlace}" alt="头像" /></div>
								</c:if>
								<c:if test="${user.picPlace==null }">
									<div class="avatar"><img id="topPic" src="${resPath }/back/img/avatar.jpg" alt="头像" /></div>
								</c:if>
								<div class="user">
<!-- 									<span class="hello">Welcome!</span> -->
									<span class="name">${user.userName }</span>
								</div>
							</a>
							 <ul class="dropdown-menu" style="min-width: 132px;">
<!-- 								<li class="dropdown-menu-title"> -->
									
<!-- 								</li> -->
								<!-- <li><a href="#"><i class="icon-user"></i> Profile</a></li>
								<li><a href="#"><i class="icon-cog"></i> Settings</a></li>
								<li><a href="#"><i class="icon-envelope"></i> Messages</a></li> -->
								<li><a href="javascript:void(0)" style="font-size: 12px;" onclick="loginOut()"><i class="icon-off"></i> 退出</a></li>
							</ul> 
						</li>
						<!-- end: User Dropdown -->
					</ul>
				</div>
				<!-- end: Header Menu -->
			</div>
		</div>
	</div>
</body>
</html>