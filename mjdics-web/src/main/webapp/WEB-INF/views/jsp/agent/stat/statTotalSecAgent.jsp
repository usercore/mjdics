<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/views/jsp/taglibs.jsp"%>
<html>
<head>
<base href="${siteContext}/resources/" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>易拍百万</title>
<link rel="stylesheet" type="text/css" href="${siteContext}/resources/plugin/jquery-easyui-1.3.2/themes/gray/easyui.css" />
<link rel="stylesheet" type="text/css" href="${siteContext}/resources/plugin/jquery-easyui-1.3.2/themes/icon.css" />
 <link rel="stylesheet" type="text/css" href="${siteContext}/resources/plugin/jquery-easyui-1.3.2/demo.css" />
<link href="${siteContext}/resources/css/demo.css" type="text/css" rel="stylesheet" />
<link rel="stylesheet" href="${siteContext}/resources/css/validform.css" type="text/css" media="all" />
<link rel="stylesheet" href="${siteContext}/resources/css/magic_dlg.css" type="text/css" media="all" />
<style type="text/css">
<!--
.mydiv { background-color: #eff9ff; border: 1px solid #84B6E3; text-align: center; font-size: 13px; padding: 0 0 10px 0; z-index: 999; width: 400px; height: auto; left: 45%; top: 30%; margin-left: -150px!important;/*FF IE7 该值为本身宽的一半 */ margin-top: -60px!important;/*FF IE7 该值为本身高的一半*/ margin-top: 0px; position: fixed!important;/* FF IE7*/ position: absolute;/*IE6*/ _top:       expression(eval(document.compatMode &&  document.compatMode=='CSS1Compat') ?  documentElement.scrollTop + (document.documentElement.clientHeight-this.offsetHeight)/2 :/*IE6*/
 document.body.scrollTop + (document.body.clientHeight - this.clientHeight)/2);/*IE5 IE5.5*/
}
.bg, .popIframe { background-color: #666; display: none; width: 100%; height: 100%; left: 0; top: 0;/*FF IE7*/ filter: alpha(opacity=50);/*IE*/ opacity: 0.5;/*FF*/ z-index: 1; position: fixed!important;/*FF IE7*/ position: absolute;/*IE6*/ _top:       expression(eval(document.compatMode &&  document.compatMode=='CSS1Compat') ?  documentElement.scrollTop + (document.documentElement.clientHeight-this.offsetHeight)/2 :/*IE6*/
 document.body.scrollTop + (document.body.clientHeight - this.clientHeight)/2);
}
.popIframe { filter: alpha(opacity=0);/*IE*/ opacity: 0;/*FF*/ }
.mydivtable{width:100%; height:auto; overflow:hidden;}
.mydivtable dl{margin:0; padding:0; overflow:hidden; height:auto; clear:both;border-bottom:1px dashed #6CF}
.mydivtable dt{ margin:0 2%; padding:5px 0; width: 24%; float: left; text-align: right; border-top:1px dashed #6CF}
.mydivtable dd{margin:0; padding:5px 0; width:70%; float:left; text-align:left; border-top:1px dashed #6CF;color:#2B6584}
.mydivtitle{ width:100%; background:#6CF; color:#2B6584; line-height:30px; font-size:14px; font-weight:bold; text-align: center;}
.mydivclose{ padding: 2px 5px; background-color: #aaa; border: 1px solid #999; color: #FFF; text-decoration: none; margin-top: 10px;}
.mydivclose:hover{background-color: #eee; color: #000; }
-->
</style>
<script type="text/javascript" src="${siteContext}/resources/js/jquery-1.6.2.min.js"></script>
<script type="text/javascript" src="${siteContext}/resources/plugin/jquery-easyui-1.3.2/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${siteContext}/resources/plugin/jquery-easyui-1.3.2/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${siteContext}/resources/js/validatebox_extend.js"></script>


						          
<script type="text/javascript">

var type = -1;
var agentTyeps = new Array();
	var ajaxbg = $("#background,#progressBar");
	ajaxbg.hide();
	$(document).ajaxStart(function() {
		ajaxbg.show();
	}).ajaxStop(function() {
		ajaxbg.hide();
	});

	$(function() {
		$('#tt').datagrid({
			title : '业务汇总',
			singleSelect : true,
			pagination : true,
			rownumbers : true,
			fitColumns : true,
			idField : 'id',
			url : '',
			columns : [ [ {
				field : 'bussCount',
				title : '业务员数',
				width : 80,
			} , {
				field : 'userCount',
				title : '注册用户数',
				width : 80,
			},{
				field : 'userByCardMoney',
				title : '用户购卡金额',
				width : 80,
			},{
				field : 'promotionCount',
				title : '业务员推广次数统计',
				width : 80,
			},{
				field : 'commission',
				title : '累计佣金',
				width : 80,
			}] ],
			onLoadSuccess: function(data){
		    	if( "undefined" != typeof(data) && "undefined" != typeof(data.errorInfo) ){
		         	$.messager.alert('信息提示',data.errorInfo,'error');
		    	}
			},
			onBeforeLoad:function(param){
					$(this).datagrid('options').url = '${siteContext}/admin/agent/statTotalSecAgent';
			}
		});
	});
</script>
</head>

<body>
				<div id="toolbar" style="padding: 5px; height: auto">
</div>
				
	<table height=100% width="100%" cellSpacing=0 cellPadding=0 id=table0>
		<tr>
			<td>
				<table id="tt" title="渠道商管理"
					toolbar="#toolbar" fit="true">
				</table>
			</td>
		</tr>
	</table>
				<div id="background" class="background" style="display: none;"></div>
				<div id="progressBar" class="progressBar" style="display: none;">数据加载中，请稍等...</div>
				<div id="detail" class="easyui-dialog"
					style="width: 550px; height: 470px; padding: 10px 0px 0px 20px"
					closed="true">
					<form id="fmAdd" class="registerform"
						action="${siteContext}/admin/agent/insertAgent?type=${type}" method="post">
						<table width="100%" style="table-layout: fixed;">
							<tbody>
								<tr>
									<td class="need" width="30px">*</td>
									<td width="80px" align="right">代理商ID：</td>
									<td width="270px"><input id="agentId" type="text" name="agentId" value=""
										class="inputxt" datatype="s4-10" nullmsg="请输代理商Id"
										errormsg="格式为4-10位字符" /></td>
									<td><div class="Validform_checktip"></div></td>
								</tr>
								<tr>
									<td colspan="4" height="3"></td>
								</tr>
								<tr>
									<td class="need" width="30px">*</td>
									<td align="right">姓名：</td>
									<td><input id="name" name="name" type="text"
										class="inputxt" datatype="s2-12" nullmsg="请输入代理商姓名"
										errormsg="长度为2-12" /></td>
									<td align="left"><div class="Validform_checktip"></div></td>
								</tr>
								<tr>
									<td colspan="4" height="3"></td>
								</tr>
								<tr>
									<td class="need">*</td>
									<td align="right">电话：</td>
									<td><input id="tel" type="text" name="tel" value=""
										class="inputxt" datatype="s11-16" nullmsg="请输入联系电话"
										errormsg="长度为11-16" /></td>
									<td><div class="Validform_checktip"></div></td>
								</tr>
								<tr>
									<td class="need">*</td>
									<td align="right">地址：</td>
									<td><input id="address" type="text" name="address" value=""
										class="inputxt" datatype="*" nullmsg="请输入地址"
										errormsg="长度为1-64" /></td>
									<td><div class="Validform_checktip"></div></td>
								</tr>
								<tr>
									<td colspan="4" height="3"></td>
								</tr>
								
								<tr>
									<td colspan="4" height="3"></td>
								</tr>
								<tr>
									<td class="need">*</td>
									<td align="right">所属代销商：</td>
									<td><input id="paraAgent" type="text" name="paraAgent" value=""
										class="inputxt" /></td>
									<td><div class="Validform_checktip"></div></td>
								</tr>
								<tr>
									<td colspan="4" height="3"></td>
								</tr>
								<tr>
									<td class="need">*</td>
									<td align="right">金额：</td>
									<td><input id="money" type="text" name="money" value=""
										class="inputxt"  /></td>
									<td><div class="Validform_checktip"></div></td>
								</tr>
								<tr>
									<td colspan="4" height="3"></td>
								</tr>
								<tr>
									<td class="need">*</td>
									<td align="right">冻结金额：</td>
									<td><input id="freezeMoney" type="text" name="freezeMoney" value=""
										class="inputxt" /></td>
									<td><div class="Validform_checktip"></div></td>
								</tr>
								<tr>
									<td colspan="4" height="3"></td>
								</tr>
								<tr>
									<td class="need">*</td>
									<td align="right">开户银行：</td>
									<td><input id="bank" type="text" name="bank" value=""
										class="inputxt" /></td>
									<td><div class="Validform_checktip"></div></td>
								</tr>
								<tr>
									<td colspan="4" height="3"></td>
								</tr>
								<tr>
									<td class="need">*</td>
									<td align="right">银行卡号：</td>
									<td><input id="bankCard" type="text" name="bankCard" value=""
										class="inputxt" /></td>
									<td><div class="Validform_checktip"></div></td>
								</tr>
							</tbody>
						</table>
					</form>
				</div>
				<div id="dlg" class="easyui-dialog"
					style="width: 550px; height: 470px; padding: 10px 0px 0px 20px"
					closed="true">
					<form id="fmAdd" class="registerform"
						action="${siteContext}/admin/agent/insertAgent?type=${type}" method="post">
						<table width="100%" style="table-layout: fixed;">
							<tbody>
								<tr>
									<td class="need" width="30px">*</td>
									<td width="80px" align="right">代理商ID：</td>
									<td width="270px"><input id="agentId" type="text" name="agentId" value=""
										class="inputxt" datatype="s4-10" nullmsg="请输代理商Id"
										errormsg="格式为4-10位字符" /></td>
									<td><div class="Validform_checktip"></div></td>
								</tr>
								<tr>
									<td colspan="4" height="3"></td>
								</tr>
								<tr>
									<td class="need">*</td>
									<td align="right">密码：</td>
									<td><input id="pass" type="text" name="pass" value=""
										class="inputxt" datatype="s6-12" nullmsg="请输入密码"
										errormsg="长度为6-12" /></td>
									<td><div class="Validform_checktip"></div></td>
								</tr>
								<tr>
									<td colspan="4" height="3"></td>
								</tr>
								<tr>
									<td class="need" width="30px">*</td>
									<td align="right">姓名：</td>
									<td><input id="name" name="name" type="text"
										class="inputxt" datatype="s2-12" nullmsg="请输入代理商姓名"
										errormsg="长度为2-12" /></td>
									<td align="left"><div class="Validform_checktip"></div></td>
								</tr>
								<tr>
									<td colspan="4" height="3"></td>
								</tr>
								<tr>
									<td class="need">*</td>
									<td align="right">电话：</td>
									<td><input id="tel" type="text" name="tel" value=""
										class="inputxt" datatype="s11-16" nullmsg="请输入联系电话"
										errormsg="长度为11-16" /></td>
									<td><div class="Validform_checktip"></div></td>
								</tr>
								<tr>
									<td colspan="4" height="3"></td>
								</tr>
								<tr>
									<td class="need">*</td>
									<td>是否有效：</td>
									<td><input type="radio"  value="Y" name="enabled"
										id="isEnabled" class="pr1" datatype="*" errormsg="请选择是否生效！"
										 /><label for="isEnabled">是</label> <input
										type="radio" value="N" name="enabled" id="notEnabled"
										class="pr1" /><label for="notEnabled">否</label></td>
									<td><div class="Validform_checktip"></div></td>
								</tr>
								<tr>
									<td class="need">*</td>
									<td align="right">地址：</td>
									<td><input id="address" type="text" name="address" value=""
										class="inputxt" datatype="*" nullmsg="请输入地址"
										errormsg="长度为1-64" /></td>
									<td><div class="Validform_checktip"></div></td>
								</tr>
							
								
								<tr>
									<td colspan="4" height="3"></td>
								</tr>
								<tr>
									<td colspan="4" style="vertical-align:middle; text-align:center;"><input name="Submit"
										type="submit" value="添加" /></td>
								</tr>
							</tbody>
						</table>
					</form>
				</div>
				
				<div id="invite" class="easyui-dialog"
					style="width: 550px; height: 470px; padding: 10px 0px 0px 20px"
					closed="true">
					<form id="fmInvite" class="registerform"
						action="" method="post">
						<table width="100%" style="table-layout: fixed;">
							<tbody>
								<tr>
									<td class="need">*</td>
									<td align="right">电话：</td>
									<td><input id="phone" type="text" name="phone" value=""
										class="inputxt" datatype="s11-16" nullmsg="请输入联系电话"
										errormsg="长度为11-16" />
									<input id="type" type="hidden" name="type" value="${type}"/>	
								    </td>
									<td><div class="Validform_checktip"></div></td>
								</tr>
								<tr>
									<td colspan="4" height="3"></td>
								</tr>
								<tr>
									<td colspan="4" style="vertical-align:middle; text-align:center;"><input name="Submit"
										type="button"  onclick="invite();" value="邀请" /></td>
								</tr>
							</tbody>
						</table>
					</form>
				</div>
				
<script type="text/javascript" src="${siteContext}/resources/js/Validform5.js"></script>
	<script type="text/javascript">
		$(function() {
			$(".registerform").Validform({
				tiptype : 2,
				showAllError : true,
				ignoreHidden : true,
				beforeSubmit:function(curform){
					return true;
				}
			});
		})
	</script>
<!--弹成功窗口-->
<div id="popDivLoading" class="mydiv" style="display:none;">数据加载中...<br/>
  <img src="${siteContext}/resources/images/load.gif" width="155" height="11" /><br/>请稍候<br/>
</div>

<!--内容层-->
<div id="bg" class="bg" style="display:none;"></div>
<iframe id='popIframe' class='popIframe' frameborder='0' ></iframe>
<!--弹成功窗口 end-->
</body>

</html>