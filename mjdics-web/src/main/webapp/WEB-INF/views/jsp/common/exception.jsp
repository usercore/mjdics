<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<c:choose>
	<c:when test="${is_json_request}">
		{"errorInfo":"${exception_message}","total":0,"rows":[]}
	</c:when>
	<c:otherwise>
		<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
		<html xmlns="http://www.w3.org/1999/xhtml">
			<head>
				<base href="${siteContext}" />
				<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="favicon.ico" rel="shortcut icon" />
				<title>易拍百万</title>
<style type="text/css">
.errorinfo{ width:540px; height:410px; background-image:url(images/errorBg.jpg); background-repeat:no-repeat;}
.errorinfo a{ color:#F00; font-size:14px;}
.errorbtn{ color:#F00; font-size:14px;}

</style>
        <script type="text/javascript">
            function reloadF(){
                if (location.href.indexOf("menu") >= 0 && top.location.href != location.href) {
                    top.location = location;
                }
            }
        </script>
			</head>
			<body  onload="reloadF()">
<div id="header"> 
<!--LOGO-->
<div class="logo"><!-- img src="images/cp_logo.jpg" width="300" height="70" /--></div>
<!--LOGO end-->
</div>
<div align="center" id="error" style="margin-top:10px">
  <div class="errorinfo" style=" margin:0px 60px;">
    <div align="center" class="errortitle" style="padding-top:120px">
      <h1>错误原因：</h1>

      <a>${exception_message}</a>
      <br />
      <a href="lottery/manager">重新登录</a>
    </div>
  </div>
</div>
			</body>
		</html>
	</c:otherwise>
</c:choose>

