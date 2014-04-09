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
/* 	showLoading();
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
	}); */
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
	function bindBank(){
		var node = $('#tt').datagrid('getSelected');
		if (node) {
			$('#bank').dialog('open').dialog('setTitle', '绑定银行卡');
			$("#id").val(node.id);
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
			title : '资金账户管理',
			singleSelect : true,
			pagination : true,
			rownumbers : true,
			fitColumns : true,
			idField : 'id',
			url : '',
			columns : [ [ {
				field : 'name',
				title : '经销商姓名',
				width : 80,
				editor : {
					type:'validatebox',
					options:{
						validType:'length[0,20]'
					}
				}
			},{
				field : 'tel',
				title : '手机',
				width : 80,
				editor : {
					type:'validatebox',
					options:{
						validType:'length[0,20'
					}
				}
			},/* {
				field : 'email',
				title : '邮箱',
				width : 80,
				editor : {
					type:'validatebox',
					options:{
						validType:'length[0,20]'
					}
				}
			}, */{
				field : 'money',
				title : '余额',
				width : 80,
				align : 'right',
				editor : {
					type:'validatebox',
					options:{
						validType:'length[0,10]'
					}
				}
			},{
				field : 'commission',
				title : '提成金额',
				align : 'right',
				width : 80,
			},{
				field : 'salesCardCommission',
				title : '售卡提成金额',
				align : 'right',
				width : 80,
			},{
				field : 'freezeMoney',
				title : '冻结金额',
				align : 'right',
				width : 80
			}] ],
			onLoadSuccess: function(data){
		    	if( "undefined" != typeof(data) && "undefined" != typeof(data.errorInfo) ){
		         	$.messager.alert('信息提示',data.errorInfo,'error');
		    	}
			},
			onBeforeLoad:function(param){
				$(this).datagrid('options').url = '${siteContext}/admin/agent/selectAgentAccount';
			}
		});
	});
	function updateBank(){
		$.ajax({
			url : "${siteContext}/admin/agent/updateAgent?",
			type : "POST",
			data : $('#fmAdd').serialize() ,
			success : function(text) {
				$('#bank').dialog('close');
				$.messager.alert('提示信息', text, 'info');
				$('#tt').datagrid("reload");
			}
		});
	}
	function initApplyCahs(){
		$('#applyMoney').dialog('open').dialog('setTitle', '提现申请');
	}
	function applyCash(){
		$.ajax({
			url : "${siteContext}/admin/agent/applyCash?",
			type : "POST",
			data : $('#applyForm').serialize() ,
			success : function(text) {
				$('#applyMoney').dialog('close');
				$.messager.alert('提示信息', text, 'info');
				$('#tt').datagrid("reload");
			}
		});
	}
	function initRecharge(){
		$('#rechargeMoney').dialog('open').dialog('setTitle', '充值');
	}
	function recharge(){
		var rechargeForm = $("#rechargeForm");
		rechargeForm.submit();
		//window.location.href = "${pageContext.request.contextPath}/admin/agent/recharge?money="+money;
		/* $.ajax({
			type : "POST",
			data : $('#rechargeForm').serialize() ,
			success : function(text) {
				$('#rechargeMoney').dialog('close');
				$.messager.alert('提示信息', text, 'info');
				$('#tt').datagrid("reload");
			}
		});  */
	}
</script>
</head>

<body>
				<div id="toolbar" style="padding: 5px; height: auto">
		<div style="margin-bottom: 5px">
					<a  href="javascript:bindBank()" class="easyui-linkbutton">绑定银行卡</a> 
					<a	href="javascript:initApplyCahs()" class="easyui-linkbutton">提现</a> 
					<a	href="javascript:initRecharge()" class="easyui-linkbutton">充值</a> 
					<a	href="javascript:cancelNode()" class="easyui-linkbutton">账户历史查询</a> 
				代销商Id: <input type="text" id="type" />
			<a href="javascript:filterNode()" class="easyui-linkbutton" iconCls="icon-search" style="padding:2px, 10px">查询</a>
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

				<div id="bank" class="easyui-dialog"
					style="width: 550px; height: 470px; padding: 10px 0px 0px 20px"
					closed="true">
					<form id="fmAdd" class="registerform"
						 method="post">
						<table width="100%" style="table-layout: fixed;">
							<tbody>
								<tr>
									<td class="need">*</td>
									<td align="right">开户行：</td>
									<td><input id="bank" type="text" name="bank" value=""
										class="inputxt"  nullmsg="请输入开户行"  />
										<input id="id" type="hidden" name="id"></td>
									<td><div class="Validform_checktip"></div></td>
								</tr>
								<tr>
									<td colspan="4" height="3"></td>
								</tr>
								<tr>
									<td class="need" width="30px">*</td>
									<td align="right">银行卡号：</td>
									<td><input id="bankCard" name="bankCard" type="text"
										class="inputxt"  nullmsg="请输入银行卡号" /></td>
									<td align="left"><div class="Validform_checktip"></div></td>
								</tr>
								<tr>
									<td colspan="4" height="3"></td>
								</tr>
								<tr>
									<td class="need">*</td>
									<td align="right">真实姓名：</td>
									<td><input id="name" type="text" name="name" value=""
										class="inputxt" nullmsg="请输入真实姓名" /></td>
									<td><div class="Validform_checktip"></div></td>
								</tr>
								<tr>
									<td colspan="4" height="3"></td>
								</tr>
								<tr>
									<td colspan="4" style="vertical-align:middle; text-align:center;"><input name="Submit"
										type="button" onclick="updateBank();" value="绑定" /></td>
								</tr>
							</tbody>
						</table>
					</form>
				</div>
				
				<div id="applyMoney" class="easyui-dialog"
					style="width: 550px; height: 180px; padding: 10px 0px 0px 20px"
					closed="true">
					<form id="applyForm" class="registerform"
						 method="post">
						<table width="100%" style="table-layout: fixed;">
							<tbody>
								<tr>
									<td class="need">*</td>
									<td align="right">金额：</td>
									<td>
									  <input id="money" type="text" name="money" value="" class="inputxt" nullmsg="请输入金额" />
									</td>
									<td><div class="Validform_checktip"></div></td>
								</tr>
								<tr>
									<td colspan="4" height="3"></td>
								</tr>
								<tr>
									<td colspan="4" style="vertical-align:middle; text-align:center;"><input name="Submit"
										type="button" onclick="applyCash();" value="确定" /></td>
								</tr>
							</tbody>
						</table>
					</form>
				</div>
				<!--充值  -->
				<div id="rechargeMoney" class="easyui-dialog"
					style="width: 550px; height: 180px; padding: 10px 0px 0px 20px"
					closed="true">
					<form id="rechargeForm" action="${pageContext.request.contextPath}/admin/agent/recharge" target="_blank" class="registerform"
						 method="post">
						<table width="100%" style="table-layout: fixed;">
							<tbody>
								<tr>
									<td class="need">*</td>
									<td align="right">金额：</td>
									<td>
									  <input id="money" type="text" name="money" value="" class="inputxt" nullmsg="请输入金额" />
									</td>
									<td><div class="Validform_checktip"></div></td>
								</tr>
								<tr>
									<td colspan="4" height="3"></td>
								</tr>
								<tr>
									<td colspan="4" style="vertical-align:middle; text-align:center;"><input name="Submit"
										type="submit"  value="确定" /></td>
								</tr>
							</tbody>
						</table>
					</form>
				</div>
	<script type="text/javascript" src="${siteContext}/resources/js/Validform5.js"></script>
	<script type="text/javascript">
		$(function() {
			//$(".registerform").Validform();  //就这一行代码！;

			$(".registerform").Validform({
				tiptype : 2,
				showAllError : true,
				ignoreHidden : true,
				beforeSubmit:function(curform){
					
						var obj = $('#cbxCategory').combobox('getValue');
						if (obj == "") {
							$.messager.alert('提示信息', "请选择类别！", 'info');
							return false;
						} else {
							$("#category").val(obj);
						}
						obj = $('#cbxChannel').combobox('getValue');
						if (obj == "") {
							$.messager.alert('提示信息', "请选择渠道商！", 'info');
							return false;
						} else {
							$("#channel").val(obj);
						}
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