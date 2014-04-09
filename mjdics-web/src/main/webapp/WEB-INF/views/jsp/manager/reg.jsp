<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/views/jsp/taglibs.jsp"%>
<html lang="en">
<head>
<meta charset="utf-8" />
<title>精彩人生-商户注册</title>
<link rel="stylesheet" href="${siteContext}/resources/demo/css/style.css" type="text/css" media="all" />
<link href="${siteContext}/resources/demo/css/demo.css" type="text/css" rel="stylesheet" />
<style>
.Validform_checktip{margin:4px 0 12px 92px;}
.formsub label{display:inline-block; width:70px;}
.action{padding-left:92px;}
</style>
</head>
<script type="text/javascript">

</script>
<body>  
<div class="main">
    <div class="wraper">
        <p class="tr"><a href="${siteContext}/admin/index" class="blue ml10 fz12">返回首页&raquo;</a></p>
        
    	<h2 class="green">商户注册</h2>
        
        <form class="registerform" action="${siteContext}/admin/agent/regAgent" method="post">
            <div class="formsub">
            	<ul>
            		<li>
                    	<label><span class="need">*</span> 用户名：</label><!-- ajaxurl="demo/valid.php" -->
                    	<input type="text" value="" name="agentId" class="inputxt" datatype="s5-18"  errormsg="用户名至少6个字符,最多18个字符！" onchange="isExistAgent(this);"/>
                        <div class="Validform_checktip">用户名为5~18个字符</div>
                    </li>
                    <li>
                    	<label><span class="need">*</span> 密码：</label>
                    	<input type="password" value="" name="pass" class="inputxt" datatype="*6-16" nullmsg="请设置密码！" errormsg="密码范围在6~16位之间！" />
                        <div class="Validform_checktip">密码范围在6~16位之间</div>
                    </li>
                    <li>
                    	<label><span class="need">*</span> 确认密码：</label>
                    	<input type="password" value="" name="pass2" class="inputxt" datatype="*" recheck="pass" nullmsg="请再输入一次密码！" errormsg="您两次输入的账号密码不一致！" />
                        <div class="Validform_checktip">两次输入密码需一致</div>
                    </li>
                    <li>
                    	<label><span class="need">*</span> 手机号码：</label>
                    	<input type="text" value="" name="tel" class="inputxt" datatype="n11-11"  errormsg="手机至少11位！" />
                        <div class="Validform_checktip">手机号码为11位</div>
                    </li>
                    <li>
                    	<label><span class="need">*</span> 邀请码：</label>
                    	<input type="text" value="" name="inviteCode" class="inputxt" datatype="n6-6"  errormsg="请输入6位邀请码！" />
                        <div class="Validform_checktip">邀请码为6位数字</div>
                    </li>
                </ul>
                <div class="action">
                	<input type="button" class="ajaxpost" value="提 交" /> <input type="reset" value="重 置" />
                </div>
            </div>
        </form>
         <br><br><br><br><br><br><br><br><br><br>
        
         
    </div>
</div>

<script type="text/javascript" src="${siteContext}/resources/demo/js/jquery-1.6.2.min.js"></script>
<script type="text/javascript" src="${siteContext}/resources/demo/js/Validform_v5.0_min.js"></script>

<script type="text/javascript">
$(function(){
	//$(".registerform").Validform();  //就这一行代码！;
		
	$(".registerform").Validform({
		tiptype:function(msg,o,cssctl){
			//msg：提示信息;
			//o:{obj:*,type:*,curform:*}, obj指向的是当前验证的表单元素（或表单对象），type指示提示的状态，值为1、2、3、4， 1：正在检测/提交数据，2：通过验证，3：验证失败，4：提示ignore状态, curform为当前form对象;
			//cssctl:内置的提示信息样式控制函数，该函数需传入两个参数：显示提示信息的对象 和 当前提示的状态（既形参o中的type）;
			if(!o.obj.is("form")){//验证表单元素时o.obj为该表单元素，全部验证通过提交表单时o.obj为该表单对象;
				var objtip=o.obj.siblings(".Validform_checktip");
				cssctl(objtip,o.type);
				objtip.text(msg);
			}
		}
	});
	var demo=$(".registerform:eq(0)").Validform();
	$(".ajaxpost").click(function(){
		demo.ajaxPost({
			callback:function(data){
				alert(data);
			}
		});
		return false;
		
	});
	
})
$(function(){
	var msg = '${requestScope.msg}';
	if(msg){
		alert(msg);
	}
})
function  isExistAgent(dom){
		$.ajax({
			url : "${siteContext}/admin/agent/isExistAgent?agentId="+dom.value,
			type : "POST",
			success : function(text) {
				if(text){
					dom.value="";
					alert(text);
				}
				
			}
		});
	}
</script>
</body>
</html>