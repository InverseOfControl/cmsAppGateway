<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<title>App接口测试页面</title>
<jsp:include page="/views/common/headIncludeFile.jsp" />
<script type="text/javascript">
	function generateKey(){
		$.ajaxPackage({
			url: global.contextPath + '/pre/generateKey',
			type:"post",
            dataType:"json",
            success:function (response, textStatus, jqXHR){
            	var code = response.code;
            	if(code == "0000"){
            		$("#key").val(response.msgEx.infos);
            	}else{
            		alert('异常 Code：' + code);
            	}
            },
            error:function(response, textStatus, jqXHR){
                $.messager.alert("异常", "操作失败", "error");
            },
            complete:function(jqXHR,textStatus){
            }
		});
		
	}
	function generateSign(){
		var func_no = $("#func_no").val();
		var param_in = $("#param_in").val();
		var key = $("#key").val();
		if(func_no == ""){
			alert("功能号不能为空");
			return;
		}
		if(param_in == ""){
			alert("业务参数不能为空");
			return;
		}
		if(key == ""){
			alert("key不能为空");
			return;
		}
		var json = {"func_no":func_no,"param_in":param_in,"key":key};
		$.ajaxPackage({
			url: global.contextPath + '/pre/generateSign',
			type:"post",
			data:json,
            dataType:"json",
            success:function (response, textStatus, jqXHR){
            	var code = response.code;
            	if(code == "0000"){
            		$("#sign").val(response.msgEx.infos);
            	}else{
            		alert('异常 Code：' + code);
            	}
            },
            error:function(response, textStatus, jqXHR){
                $.messager.alert("异常", "操作失败", "error");
            },
            complete:function(jqXHR,textStatus){
            }
		});
		
	}
	function paramOut(){
		var func_no = $("#func_no").val();
		var param_in = $("#param_in").val();
		var key = $("#key").val();
		var sign = $("#sign").val();
		var json = {"func_no":func_no,"param_in":param_in,"key":key,"sign":sign};
		$.ajaxPackage({
			url: global.contextPath + '/pre/preSubmitTest',
			type:"post",
			data:json,
            dataType:"json",
            success:function (response, textStatus, jqXHR){
            	$("#param_out").val(response.result);
            },
            error:function(response, textStatus, jqXHR){
                $.messager.alert("异常", "操作失败", "error");
            },
            complete:function(jqXHR,textStatus){
            }
		});
			
	}
</script>
</head>

<body>
	<div>
		<form action="<%=request.getContextPath() %>/pre/preSubmitTest" id="testform" target="_blank" method="post">
			功能号：&nbsp;<input type="text" style="width: 200px" name="func_no" id ="func_no"/></p>
			业务参数：<textarea type="text" style="width: 200px;height:100px" name="param_in" id ="param_in"/></textarea></p>
			key：&nbsp;&nbsp; <textarea  name="key" style="width: 200px;height:100px " id ="key"/></textarea> <button type="button" name="keybtn" onclick="generateKey();" id="keybtn"  >生成key</button></p>
			签名：&nbsp;&nbsp;<textarea  name="sign" style="width: 200px;height:100px " id ="sign"/></textarea> <button type="button" name="signbtn" id="signbtn" onclick="generateSign();" >生成签名</button></p>
			<button type="button"  name="subbtn" id ="subbtn" onclick="paramOut();" >提交 </button></p>
			返回结果：<textarea id="param_out" name="param_out" style="width: 500px;height:100px " ></textarea>
		</form>
	</div>
</body>
<script type="text/javascript">
	
</script>
</html>