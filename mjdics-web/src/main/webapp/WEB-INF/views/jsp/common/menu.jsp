<%@ page contentType="text/html; charset=UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh-CN" xmlns:my="http://wfsr.net">
<head>
<%
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Pragma", "no-cache");
	response.setDateHeader("Expires", 0);
%>
<base href="${siteContext}" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>易拍百万</title>
<link rel="stylesheet" href="${siteContext}/resources/plugin/jquery-easyui-1.2.4/themes/menu/easyui.css" type="text/css" />
<link rel="stylesheet" href="${siteContext}/resources/plugin/jquery-easyui-1.2.4/themes/menu/icon.css" type="text/css" />
<link rel="stylesheet" href="${siteContext}/resources/css/navi.css" type="text/css" />
<style>
body {
	height: 100%;
	font-family: "微软雅黑", "宋体", Arial, sans-serif;
	font-size: 13px;
	text-decoration: none;
	margin: 0;
	padding: 0;
	color: #666;
	background: #eaeaea;
}

table {
	margin: 0;
	padding: 0;
	border: 0;
}

#testIframe {
	margin-left: 10px;
}
</style>
<script type="text/javascript"
	src="${siteContext}/resources/js/jquery-1.6.2.min.js"></script>
<script type="text/javascript"
	src="${siteContext}/resources/plugin/jquery-easyui-1.2.4/jquery.easyui.min.md.js"></script>
<script type="text/javascript">
	function show(url,dom) {
			$(dom).parent().parent().siblings().children().children().css("color","white");
			$(dom).parent().parent().parent().parent().siblings().children().children().children().children().css("color","white");
			$(dom).css("color","990000");
			
		top.mainFrame.location.href="${siteContext}"+url;
		
	}
	$(function() {
		$("#menu").tree({
			url : "${siteContext}/admin/sysPriviledges/selectSysPriviledgesByGroupId",
			onClick : function(node) {
				var url = null;
				if (node.attributes != null) {
					url = node.attributes.url;
				}
				if (url != null && url != "") {
					top.mainFrame.location.href="${siteContext}/"+url;
					//window.parent.frames["mainFrame"].src = url;
				} else {
					if (node.state == "closed") {
						$('#menu').tree('expand', node.target);
					} else {
						$('#menu').tree('collapse', node.target);
					}
				}
			}
		});
	});
</script>

</head>

<body>
	<div id="leftside">
		<div class="user">
			<div style="padding-left: 20px">
				<img src="${siteContext}/resources/images/avatar.png" width="44" height="44" class="hoverimg"
					alt="Avatar" />
				<p>
					<span>欢迎回来:</span>
				</p>
				<p>
					<span class="username">${ sessionScope.agent.name }</span> 
				</p>
				<p class="userbtn">
					<a href="${siteContext}/admin/logout" title="">安全退出</a>
				</p>
			</div>
		</div>

		<ul id="menu" class="tree">
	</div>
	
</body>

</html>