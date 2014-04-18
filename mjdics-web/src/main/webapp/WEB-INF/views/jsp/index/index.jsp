<%@ page contentType="text/html; charset=UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh-CN"
	xmlns:my="http://wfsr.net">
<head>
<%
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Pragma", "no-cache");
	response.setDateHeader("Expires", 0);
%>
<base href="${siteContext}" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<link href="favicon.ico" rel="shortcut icon" />
<title>精彩人生后台系统</title>
<style>
body {height: 100%; font-family: "微软雅黑", "宋体", Arial, sans-serif;font-size: 13px; text-decoration: none; margin: 0; padding: 0; color: #666; background:#eaeaea;}

table {margin: 0; padding: 0; border: 0;}

</style>
</head>

<frameset rows="70,*" cols="*" frameborder="no" border="0" framespacing="0">
  <frame src="${siteContext}/admin/top" name="topFrame" scrolling="No" noresize="noresize" id="topFrame" title="topFrame" />
  <frameset rows="*" cols="225,*" frameborder="no">
    <frame src="${siteContext}/admin/menu" name="leftFrame" frameborder="no" scrolling="no" noresize="noresize" id="leftFrame" title="leftFrame" />
    <frame src="" id="mainFrame" name="mainFrame" title="mainFrame" />
  </frameset>
</frameset>
<noframes><body>您的浏览器无法处理框架！
</body></noframes>

</html>