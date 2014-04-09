<%@ page contentType="text/html; charset=UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh-CN"
	xmlns:my="http://wfsr.net">
<head>
<%
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Pragma", "no-cache");
	response.setDateHeader("Expires", 0);
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<base href="${siteContext}" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>易拍百万</title>
<style>
/*--TOP--*/
#header {width: 100%;height: 70px;padding: 0; margin: 0;background-color: #57b0dc;background-image: url(${pageContext.request.contextPath}/resources/images/bg_header.jpg);background-repeat: repeat-x;background-position: bottom;}

/*--标志--*/
.logo {float: left; padding: 0px 0px;}

</style>

</head>

<body leftMargin=0 topMargin=0 scroll=no marginwidth="0"
	marginheight="0"">
<div id="header"> 
<!--LOGO-->
<div class="logo"><img src="${pageContext.request.contextPath}/resources/images/cp_logo.jpg" width="300" height="70" /></div>
</div>
</body>
</html>