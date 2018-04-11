var name = null;
var errMsg = '网络异常,请稍后再试!';


function checkPhone(){
	var phone = $("#userName").val();
	//app.showProgress();
	$.ajax({
		url : ctx + "/mobile/check",
		data : {
			phone : phone
		},
		success : function(data) {
			//app.closeProgress();
			name = data;
			if (name == 'chinaMobile') {
				$('.errorBox').hide();
				showSms(phone);
			} else if (name == 'chinaUnicom') {
				$('.errorBox').hide();
				$("#smsFirst").hide();
			} else if (name == 'chinaTelecom') {
				$("#smsFirst").hide();
				$('.errorBox').show().html('暂不支持电信手机！');
			}
		}
	});
}
/* 验证手机 号码,如果是移动的显示短信验证码框 */
$("#userName").blur(function() {
	var phone = $("#userName").val();
	//app.showProgress();
	$.ajax({
		url : ctx + "/mobile/check",
		data : {
			phone : phone
		},
		success : function(data) {
			//app.closeProgress();
			name = data;
			if (name == 'chinaMobile') {
				$('.errorBox').hide();
				showSms(phone);
			} else if (name == 'chinaUnicom') {
				$('.errorBox').hide();
				$("#smsFirst").hide();
			} else if (name == 'chinaTelecom') {
				$("#smsFirst").hide();
				$('.errorBox').show().html('暂不支持电信手机！');
			}
		}
	});
});

/* 移动cookie获取时间 */
function wtf() {
	var $t = "2";
	var $u = new Date();
	var $v = new Date($u.getTime() + 315360000000);
	var $w = new Date($u.getTime());

	if ($t.length < 10) {
		var $x = $u.getTime().toString();
		for (var i = 2; i <= (32 - $x.length); i++)
			$t += Math.floor(Math.random() * 16.0).toString(16);
		$t += $x;
	}
	;
	$t = encodeURIComponent($t);
	return "WT_FPC=id=" + $t + ":lv=" + $u.getTime().toString() + ":ss="
			+ $w.getTime().toString();
}

/* 移动加密解密 */
var base64EncodeChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
function utf16to8(str) {
	var out, i, len, c;
	out = "";
	len = str.length;
	for (i = 0; i < len; i++) {
		c = str.charCodeAt(i);
		if ((c >= 0x0001) && (c <= 0x007F)) {
			out += str.charAt(i);
		} else if (c > 0x07FF) {
			out += String.fromCharCode(0xE0 | ((c >> 12) & 0x0F));
			out += String.fromCharCode(0x80 | ((c >> 6) & 0x3F));
			out += String.fromCharCode(0x80 | ((c >> 0) & 0x3F));
		} else {
			out += String.fromCharCode(0xC0 | ((c >> 6) & 0x1F));
			out += String.fromCharCode(0x80 | ((c >> 0) & 0x3F));
		}
	}
	return out;
}
function base64encode(str) {
	var out, i, len;
	var c1, c2, c3;
	len = str.length;
	i = 0;
	out = "";
	while (i < len) {
		c1 = str.charCodeAt(i++) & 0xff;
		if (i == len) {
			out += base64EncodeChars.charAt(c1 >> 2);
			out += base64EncodeChars.charAt((c1 & 0x3) << 4);
			out += "==";
			break;
		}
		c2 = str.charCodeAt(i++);
		if (i == len) {
			out += base64EncodeChars.charAt(c1 >> 2);
			out += base64EncodeChars.charAt(((c1 & 0x3) << 4)
					| ((c2 & 0xF0) >> 4));
			out += base64EncodeChars.charAt((c2 & 0xF) << 2);
			out += "=";
			break;
		}
		c3 = str.charCodeAt(i++);
		out += base64EncodeChars.charAt(c1 >> 2);
		out += base64EncodeChars.charAt(((c1 & 0x3) << 4) | ((c2 & 0xF0) >> 4));
		out += base64EncodeChars.charAt(((c2 & 0xF) << 2) | ((c3 & 0xC0) >> 6));
		out += base64EncodeChars.charAt(c3 & 0x3F);
	}
	return out;
}

$("#passWord").focus(function () {
	
	var phone = $("#userName").val();
	//app.showProgress();
	$.ajax({
		url : ctx + "/mobile/check",
		data : {
			phone : phone
		},
		success : function(data) {
			//app.closeProgress();
			name = data;
			if (name == 'chinaMobile') {
				$('.errorBox').hide();
				showSms(phone);
			} else if (name == 'chinaUnicom') {
				$('.errorBox').hide();
				$("#smsFirst").hide();
			} else if (name == 'chinaTelecom') {
				$("#smsFirst").hide();
				$('.errorBox').show().html('暂不支持电信手机！');
			}
		}
	});
});
/* 展示短信框 */
function showSms(phone) {
	//app.showProgress();
	$.ajax({
		
		url : ctx + "/mobile/isVerCode",
		data : {
			phone : phone
		},
		success : function(data) {
			//app.closeProgress();
			if (data == 'error') {
				//app.showHintDialog(1, errMsg);
			} else {
				var json = JSON.parse(data);
				if (json.needVerifyCode == 1) {
					$("#smsFirst").show();
				}
			}
		}
	});
}

/* 移动登录验证发送验证码 */
$("#getSmsFirst").click(function() {
	//app.showProgress();
	$.ajax({
		url : ctx + "/mobile/sms",
		success : function(data) {
			//app.closeProgress();
			if (data != 'error') {
				if (data == '0') {
				}
			} else {
				//app.showHintDialog(1, errMsg);
			}
		}
	});
});

$("#loginBtn").click(
		function() {

			if (!(/^1[34578]\d{9}$/.test(($("#userName").val())))) {
				$('.errorBox').show().html('请输入正确的手机号！');
				return;
			} else {
				$('.errorBox').hide();
			}
			if (!(/^\d{6,8}$/.test(($("#passWord").val())))) {
				$('.errorBox').show().html('密码格式有误');
				return;
			} else {
				$('.errorBox').hide();
			}

			if (name == null) {
				$("#userName").val("");
				$("#passWord").val("");
				$('.errorBox').show().html('请输入手机号！');
			}
			;
			var _this = $(this);
			if (name == 'chinaMobile') {
				var userName = $("#userName").val();
				var passWord = $("#passWord").val();
				var valCode = $("#validateCode").val();
				var a = wtf();
				//app.showProgress();
				$.ajax({
					url : ctx + "/mobile/login",
					type : "GET",
					data : {
						userName : userName,
						passWord : passWord,
						valCode : valCode,
						wtf : a
					},
					success : function(data) {
						//app.closeProgress();
						console.log(data);
						if(data.code==null){
							$('.errorBox').show().html("今日登陆次数过多,请明天再试!");
						}else if (data.code == '0000') {
							console.log(data.code);
							_this.parents('.login').hide().siblings('.imgtest')
									.show();
							//app.showProgress();
							$.ajax({
								url : ctx + "/mobile/getImage",
								success : function(data) {
									//app.closeProgress();
									var json = JSON.parse(data);
									$("#authCode").attr("src",
											ctx + "/" + json.respDesc);
								}
							});
						} else {
							$('.errorBox').show().html(data.desc);
						}
					}
				});
			} else if (name == "chinaUnicom") {
				var userName = $("#userName").val();
				var passWord = $("#passWord").val();
				var names=$("#names").val();
				var cilentId=$("#cilentId").val();
				//app.showProgress();
				$.ajax({
					url : ctx + "/mobile/getUniconDetail",
					type : "GET",
					data : {
						userName : userName,
						passWord : passWord
					},
					success : function(data) {
						//app.closeProgress();
						if (data.respCode == '0000') {
							//app.showHintDialog(0, "该手机号码的通话详单正在获取中，获取结果稍后会推送给您");
							$.ajax({
								url : ctx + "/mobile/getUniconPhoneDetail",
								type : "GET",
								data:{
									phone : userName,
									names:names,
									cilentId:cilentId
								},
								success : function(data) {
									
									if (data.result != null) {
										
									} else {
										$('.errorBox').show().html(data.respDesc);
									}
								}
							});
						}else{
							$('.errorBox').show().html(data.respDesc);
						}
					}
				});
			} else if (name == "chinaTelecom") {
				$('.errorBox').show().html('暂不支持电信手机！');
			}
		});
