<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="../taglibs.jsp" %>
<c:if test="${msg!=null}">
	<script language="javascript">alert("<c:out value='${msg}'/>");</script>
</c:if>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh-CN"
	xmlns:my="http://wfsr.net">
<head>
<base href="${siteContext}/" />
<title>精彩人生</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="favicon.ico" rel="shortcut icon" />
<link rel="stylesheet" type="text/css" href="${siteContext}/resources/plugin/jquery-easyui-1.3.2/themes/gray/easyui.css" />
<link rel="stylesheet" type="text/css" href="${siteContext}/resources/plugin/jquery-easyui-1.3.2/themes/icon.css" />
<link rel="stylesheet" type="text/css" href="${siteContext}/resources/plugin/jquery-easyui-1.3.2/demo.css" />
<style type="text/css">
body {margin-left: 0px; margin-top: 0px; margin-right: 0px; margin-bottom: 0px; font-family: "微软雅黑", "宋体", Arial, sans-serif; font-size: 13px; }
.top{height:300px;background-image:url(${siteContext}/resources/images/admin_logo.jpg);background-repeat: no-repeat;background-position: center bottom;background-color: #57afdb;}

.foot{position:absolute;width:100%; bottom:5px;}
.foot table tr td {color: #999;font-size: 12px;}

<!--/* 按钮*/-->
.btn{width:128px;height:32px;background-color:#61c3f5;border: 1px solid #0D87AE;color: #fff;font-size: 12px;padding: 7px 10px;boirder-radius: 7px;-moz-border-radius: 7px;font-weight: 700;}
.btn:hover {border: 1px solid #005977; cursor: pointer; background-color:#666; border: 1px solid #333;}

.inputxt {border: 1px solid #999; padding: 6px; -moz-border-radius: 3px; border-radius: 3px; margin-bottom: 5px; color: #666; background:url(${siteContext}/resources/images/bg_fade_sml.png) repeat-x top;}
.inputxt:focus {
	border: 1px solid #57b0dc;
}

.verifycode{margin-left: 5px; vertical-align: baseline}
		
.login table tr td a {text-decoration: none;font-size: 12px; color:#000;}
.login table tr td a:hover {color:#16539e;}

.Validform_checktip{margin-left:0;}

.info{
	border:1px solid #ccc; 
	padding:2px 20px 2px 5px; 
	color:#666; 
	position:absolute;
	display:none;
	line-height:20px;
	background-color:#fff;
}

</style>
<script type="text/javascript" src="${siteContext}/resources/js/jquery-1.6.2.min.js"></script>
<script type="text/javascript"
	src="${siteContext}/resources/plugin/jquery-easyui-1.3.2/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${siteContext}/resources/plugin/jquery-easyui-1.3.2/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript"
	src="${siteContext}/resources/js/validatebox_extend.js"></script>
<script type="text/javascript">

function reloadF(){
	if(top.location.href!=location.href){
	top.location=location;
	}
}
	function changeValidateCode() {
		var code = document.getElementById('rImg');
		code.onclick = function() {
			this.src = '${siteContext}/verifyCodeServlet?id=' + Math.floor(Math.random() * 10000);
		};
	}
</script>
</head>
<body BGCOLOR="#FFFFFF" onload="reloadF()">
<!--logo图片-->
<div class="top"></div>
	<table width="100%" border="0" align="center" cellpadding="0"
		cellspacing="0">
		<tr>
			<td><form class="registerform"
					action="${siteContext}/admin/login" method="post">
					<div align="center" class="login">
					<table width="35%" border="0" align="center" cellpadding="0" cellspacing="0">
  						<tr>
    						<td colspan="4" bgcolor="#FFFFFF">&nbsp;</td>
    					</tr>
							<tr>
								<td height="55" align="right" bgcolor="#FFFFFF"><img src="${siteContext}/resources/images/admin_user.jpg" width="100" height="29" /></td>
								<td width="260" height="50" align="right" bgcolor="#FFFFFF">
									<input id="agentId" name="agentId" class="easyui-validatebox inputxt" style="width:255px" data-options="required:true,validType:['length[4,12]']" />
								</td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td colspan="4" height="3"></td>
							</tr>
							<tr>
								<td height="55" align="right" bgcolor="#FFFFFF"><img src="${siteContext}/resources/images/admin_password.jpg" width="100" height="29" /></td>
								<td height="50" align="right" bgcolor="#FFFFFF">
									<input id="pass" name="pass" type="password" class="easyui-validatebox inputxt" style="width:255px" data-options="required:true,validType:['length[6,12]']" />
								</td>
								<td><div class="info"><span class="Validform_checktip">密码至少6个字符,最多12个字符</span></div></td>
								<td></td>
							</tr>
							<tr>
								<td colspan="4" height="11"></td>
							</tr>
							<tr>
								<td height="55" align="right" bgcolor="#FFFFFF"><img src="${siteContext}/resources/images/admin_code.jpg" width="100" height="29" /></td>
								<td align="left"><input name="valCode" type="text" id="valCode"
									class="easyui-validatebox inputxt" style="width:80px" data-options="required:false"/>
									<img border=0 src="verifyCodeServlet" id="rImg"
									name="rImg"	alt="点击刷新" class="verifycode" /></td>
								<td><div class="info"><span class="Validform_checktip">验证码输入不正确</span></div></td>
								<td></td>

							</tr>
							<tr>
								<td height="40" colspan="3" align="right" bgcolor="#FFFFFF"><input name="Submit" id="Submit"
									type="submit" class="btn" value="登 录" />
									<a href="${siteContext}/admin/reg">注册</a></td>
							</tr>
					</table>
					</div>
				</form></td>
		</tr>
	</table>

	<script type="text/javascript">
		$(function() {
			$(".registerform").submit(function(e) {
				var flag = true;
        		$('.registerform input').each(function () {
            		if ($(this).hasClass("easyui-validatebox")){
                		if (!($(this).validatebox('isValid'))) {
                			flag = false;
						}
					}
        		});
        		if(!flag){
					e.preventDefault();
        		}
    		});
			$("#rImg").click(function() {
				changeValidateCode();
				//return false;
			});
		})
	</script>
	
</body>
</html>
