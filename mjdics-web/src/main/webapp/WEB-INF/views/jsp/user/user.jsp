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
function showLoading(){
document.getElementById('popDivLoading').style.display='block';
document.getElementById('popIframe').style.display='block';
document.getElementById('bg').style.display='block';
}

function showContent(){
	document.getElementById('popDivLoading').style.display='none';
	document.getElementById('popDivContent').style.display='block';
}

function closeDiv(){
document.getElementById('popDivLoading').style.display='none';
document.getElementById('popDivContent').style.display='none';
document.getElementById('bg').style.display='none';
document.getElementById('popIframe').style.display='none';

}
function showDetail(id)
{	
	showLoading();
	$.ajax({
		url : "lottery/channel?action=jsonChannelOverview&rid="
				+ Math.random(),
		type : "POST",
		data : "id=" + id,
		dataType : "json",
		async : false,
		cache : false,
		success : function(text) {
			if(text.msg==''){
				$('#msgTitle').text("渠道商账户概览");
				$('#balance').text(text.balance);
				$('#overdraft').text(text.overdraft);
				$('#refill').text(text.refill);
				$('#adCount').text(text.adCount);
				$('#transCount').text(text.transCount);
				showContent();
			}else{
				closeDiv();
				$.messager.alert('信息提示',text.msg,'error');
			}
		}
	});
}
	function editNode() {
		var node = $('#tt').datagrid('getSelected');
		if (node) {
			var rowIndex = $('#tt').datagrid('getRowIndex', node);
			$('#tt').datagrid('beginEdit', rowIndex);
		}else{
			$.messager.alert('提示信息', "请选择一条记录", 'info');
		}
	}

	function filterNode(){
		$('#tt').datagrid("load");
	}
	
	function saveNode() {
		var node = $('#tt').datagrid('getSelected');
		if (node) {
			var rowIndex = $('#tt').datagrid('getRowIndex', node);
			var flag =  $('#tt').datagrid('validateRow', rowIndex);
			if(!flag){
				$.messager.alert('提示信息', "数据非法，请按照页面要求修改数据！", 'info');
				return;
			}
			$('#tt').datagrid('endEdit', rowIndex);
			$.ajax({
				url : "${siteContext}/admin/agent/updateAgent?",
				type : "POST",
				data : "money=" + node.money+"&id="+node.id ,
				dataType : "json",
				async : false,
				cache : false,
				success : function(text) {
					$.messager.alert('提示信息', text.msg, 'info');
					$('#tt').datagrid("reload");
				}
			});
		}else{
			$.messager.alert('提示信息', "请选择一条记录", 'info');
		}
	}
	function cancelNode() {
		var node = $('#tt').datagrid('getSelected');
		if (node) {
			var rowIndex = $('#tt').datagrid('getRowIndex', node);
			$('#tt').datagrid('cancelEdit', rowIndex);
		}else{
			$.messager.alert('提示信息', "请选择一条记录", 'info');
		}
	}
	function newNode() {
		$('#dlg').dialog('open').dialog('setTitle', '新增代理商');
		$('#fmAdd').form('clear'); 
	}
	function showDetail(){
		var node = $('#tt').datagrid('getSelected');
		if (node) {
			$('#dlg').dialog('open').dialog('setTitle', '修改二维码广告');
			$("#name").val(node.name);
		}
	}
	
	
	var ajaxbg = $("#background,#progressBar");
	ajaxbg.hide();
	$(document).ajaxStart(function() {
		ajaxbg.show();
	}).ajaxStop(function() {
		ajaxbg.hide();
	});

	$(function() {
		$('#tt').datagrid({
			title : '用户管理',
			singleSelect : true,
			pagination : true,
			rownumbers : true,
			fitColumns : true,
			idField : 'id',
			url : '',
			columns : [ [ {
				field : 'agentId',
				title : '经销商',
				width : 80,
				editor : {
					type:'validatebox',
					options:{
						validType:'length[0,20]'
					}
				}
			},{
				field : 'phone',
				title : '手机',
				width : 80,
				editor : {
					type:'validatebox',
					options:{
						validType:'length[0,20]'
					}
				}
			},{
				field : 'loginTime',
				title : '邮箱',
				width : 80,
				editor : {
					type:'validatebox',
					options:{
						validType:'length[0,20]'
					}
				}
			},{
				field : 'isAddCommision',
				title : '是否计算提成',
				width : 80,
				editor : {
					type:'validatebox',
					options:{
						validType:'length[0,10]'
					}
				}
			},
			{field:'id',title:'详细信息',width:50,align:'center',
				formatter:function(value,row,index){
					return '<a href="javascript:showDetail()">详细信息</a>';
				}
			}] ],
			onLoadSuccess: function(data){
		    	if( "undefined" != typeof(data) && "undefined" != typeof(data.errorInfo) ){
		         	$.messager.alert('信息提示',data.errorInfo,'error');
		    	}
			},
			onBeforeLoad:function(param){
				//type = '${type}';
				$(this).datagrid('options').url = '${siteContext}/admin/user/selectUser';
			}
		});
	});
	function addUser(){
		$.ajax({
			url : "${siteContext}/admin/user/insertUser?",
			type : "POST",
			data : $('#fmAdd').serialize() ,
			success : function(text) {
				$('#dlg').dialog('close');
				$.messager.alert('提示信息', text, 'info');
				$('#tt').datagrid("reload");
			}
		});
	}
	function initInvite(){
		$('#invite').dialog('open').dialog('setTitle', '邀请注册用户');
	}
	function invite(){
		alert("");
		$.ajax({
			url : "${siteContext}/admin/inviteCode/inviteUser?",
			type : "POST",
			data : $('#fmInvite').serialize() ,
			success : function(text) {
				$('#invite').dialog('close');
				$.messager.alert('提示信息', text, 'info');
			}
		});
	}
</script>
</head>

<body>
				<div id="toolbar" style="padding: 5px; height: auto">
		<div style="margin-bottom: 5px">
					<!-- <a  href="javascript:newNode()" class="easyui-linkbutton">新增</a> 
					<a	href="javascript:editNode()" class="easyui-linkbutton">编辑</a> 
					<a	href="javascript:saveNode()" class="easyui-linkbutton">保存</a> 
					<a	href="javascript:cancelNode()" class="easyui-linkbutton">取消</a>  -->
<%-- 					<c:if test="${sessionScope.agent.type=='SALER'}">
					<a  href="javascript:initInvite()" class="easyui-linkbutton">邀请</a>
					</c:if> --%>
			<!-- 	代销商Id: <input type="text" id="type" />
			<a href="javascript:filterNode()" class="easyui-linkbutton" iconCls="icon-search" style="padding:2px, 10px">查询</a> -->
				</div>
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

				<div id="dlg" class="easyui-dialog"
					style="width: 450px; height: 270px; padding: 10px 0px 0px 20px"
					closed="true">
					<form id="fmAdd" class="registerform"
						action="" method="post">
						<table width="100%" style="table-layout: fixed;">
							<tbody>
								<tr>
									<td class="need" width="30px">*</td>
									<td width="80px" align="right">手机号码：</td>
									<td width="270px"><input id="phone" type="text" name="phone" value=""
										class="inputxt"  nullmsg="请输代手机号码" /></td>
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
									<td colspan="4" style="vertical-align:middle; text-align:center;"><input name="Submit"
										type="button" onclick="addUser();" value="添加" /></td>
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
									<input id="type" type="hidden" name="type" value="${sessionScope.agent.type}"/>	
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
	<script type="text/javascript" src="js/Validform5.js"></script>
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