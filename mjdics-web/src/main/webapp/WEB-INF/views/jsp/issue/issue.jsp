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
<script type="text/javascript" src="${siteContext}/resources/js/dateFormat.js"></script>
<script type="text/javascript">

var gameObj={"201":"双色球"};

//var groups = ${jsonUserGroups};
var name = "";

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
				url : "${siteContext}/admin/issue/updateIssue?",
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
		$('#dlg').dialog('open').dialog('setTitle', '新增卡类型');
		$('#fmAdd').form('clear'); 
		$("#sub").show();
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
			title : '期号管理',
			singleSelect : true,
			pagination : true,
			rownumbers : true,
			fitColumns : true,
			idField : 'id',
			url : '',
			columns : [ [ {
				field : 'game',
				title : '游戏',
				width : 80,
				formatter : function(value) {
					return gameObj[value];
				} 
			},{
				field : 'issue',
				title : '期号',
				width : 80,
				editor : {
					type:'validatebox',
					options:{
						validType:'length[0,6]'
					}
				}
			},{
				field : 'addTime',
				title : '时间',
				width : 80,	
				formatter : function(value) {
					var now = new Date(value).Format("yyyy-MM-dd hh:mm:ss");
					return now;
				} 
			},{
				field : 'startTime',
				title : '开始时间',
				width : 80,	
				formatter : function(value) {
					var now = new Date(value).Format("yyyy-MM-dd hh:mm:ss");
					return now;
				} 
			},{
				field : 'endTime',
				title : '结束时间',
				width : 80,	
				formatter : function(value) {
					var now = new Date(value).Format("yyyy-MM-dd hh:mm:ss");
					return now;
				} 
			},{
				field : 'status',
				title : '状态',
				width : 80,
				formatter : function(value) {
					<c:forEach var="issue" items="${issueStatusEnum}" varStatus="i"> 
						if ('${issue}' == value)
							return '${issue.name}'; 
					</c:forEach>
				}
			
			}] ],
			onLoadSuccess: function(data){
		    	if( "undefined" != typeof(data) && "undefined" != typeof(data.errorInfo) ){
		         	$.messager.alert('信息提示',data.errorInfo,'error');
		    	}
			},
			onBeforeLoad:function(param){
				var issue = $('#issue').val();
				$(this).datagrid('options').url = '${siteContext}/admin/issue/selectIssue?issue='+issue;
			}
		});
	});
	
	
</script>
</head>

<body>
				<div id="toolbar" style="padding: 5px; height: auto">
		<div style="margin-bottom: 5px">
					<!--<a  href="javascript:newNode()" class="easyui-linkbutton">新增</a> 
					<a	href="javascript:editNode()" class="easyui-linkbutton">编辑</a> 
					<a	href="javascript:saveNode()" class="easyui-linkbutton">保存</a>  
					<a	href="javascript:cancelNode()" class="easyui-linkbutton">取消</a> -->
				期号: <input type="text" id="issue" />
			<a href="javascript:filterNode()" class="easyui-linkbutton" iconCls="icon-search" style="padding:2px, 10px">查询</a>
				</div>
</div>
				
	<table height=100% width="100%" cellSpacing=0 cellPadding=0 id=table0>
		<tr>
			<td>
				<table id="tt" title="卡管理"
					toolbar="#toolbar" fit="true">
				</table>
			</td>
		</tr>
	</table>
				<div id="background" class="background" style="display: none;"></div>
				<div id="progressBar" class="progressBar" style="display: none;">数据加载中，请稍等...</div>

				<div id="dlg" class="easyui-dialog"
					style="width: 550px; height: 470px; padding: 10px 0px 0px 20px"
					closed="true">
					<form id="fmAdd" class="registerform"
						action="${siteContext}/admin/issue/insertIssue" method="post">
						<input id="id" name="id" type="hidden" value="" />
						<table width="100%" style="table-layout: fixed;">
							<tbody>
								<tr>
									<td class="need" width="30px">*</td>
									<td width="80px" align="right">面值：</td>
									<td width="250px"><input id="money" type="text" name="money" value=""
										class="inputxt" datatype="n"
										 errormsg="格式为数字" /></td>
									<td><div class="Validform_checktip"></div></td>
								</tr>
								
								<tr>
									<td colspan="4" height="3"></td>
								</tr>
								<tr>
									<td class="need">*</td>
									<td align="right">名称：</td>
									<td><input id="name" type="text" name="name" value=""
										class="inputxt" datatype="*2-30" nullmsg="请输入名称"
										errormsg="长度为1-30" /></td>
									<td><div class="Validform_checktip"></div></td>
								</tr>				
								<tr>
									<td colspan="4" height="3"></td>
								</tr>
								<tr>
									<td class="need" width="30px">*</td>
									<td align="right">经销商价格：</td>
									<td><input id="price" name="price" type="text"
										class="inputxt" datatype="n"
										 errormsg="格式为数字"  /></td>
									<td align="left"><div class="Validform_checktip"></div></td>
								</tr>
				
								<tr>
									<td colspan="4" height="3"></td>
								</tr>
								<tr>
									<td class="need" width="30px">*</td>
									<td align="right">网络销售价格：</td>
									<td><input id="webPrice" name="webPrice" type="text"
										class="inputxt" datatype="n"
										 errormsg="格式为数字"  /></td>
									<td align="left"><div class="Validform_checktip"></div></td>
								</tr>
																				
								<tr>
									<td colspan="4" height="3"></td>
								</tr>
								<tr>
									<td class="need" width="30px">*</td>
									<td align="right">一级经销商提成：</td>
									<td><input id="firstCommision" name="firstCommision" type="text"
										class="inputxt" datatype="firstCommision"
										 errormsg="格式为数字"  /></td>
									<td align="left"><div class="Validform_checktip"></div></td>
								</tr>
				
								<tr>
									<td colspan="4" height="3"></td>
								</tr>
								<tr>
									<td class="need" width="30px">*</td>
									<td align="right">二级经销商提成：</td>
									<td><input id="secCommision" name="secCommision" type="text"
										class="inputxt" datatype="secCommision"
										 errormsg="格式为数字"  /></td>
									<td align="left"><div class="Validform_checktip"></div></td>
								</tr>		
																		
								<tr>
									<td colspan="4" height="3"></td>
								</tr>
								<tr>
									<td class="need" width="30px">*</td>
									<td align="right">业务员提成：</td>
									<td><input id="saleCommision" name="saleCommision" type="text"
										class="inputxt" datatype="saleCommision"
										 errormsg="格式为数字"  /></td>
									<td align="left"><div class="Validform_checktip"></div></td>
								</tr>	
								
								<tr>
									<td colspan="4" height="3"></td>
								</tr>
								<tr>
									<td class="need" width="30px">*</td>
									<td align="right">业务员售卡提成：</td>
									<td><input id="saleCardCommision" name="saleCardCommision" type="text"
										class="inputxt" datatype="saleCardCommision"
										 errormsg="格式为数字"  /></td>
									<td align="left"><div class="Validform_checktip"></div></td>
								</tr>				
								
								<tr>
									<td colspan="4" height="3"></td>
								</tr>
								<tr>
									<td class="need" width="30px">*</td>
									<td align="right">彩票单次金额：</td>
									<td><input id="lotteryMoney" name="lotteryMoney" type="text"
										class="inputxt" datatype="lotteryMoney"
										 errormsg="格式为数字"  /></td>
									<td align="left"><div class="Validform_checktip"></div></td>
								</tr>															
								
								<tr>
									<td colspan="4" height="3"></td>
								</tr>
								<tr>
									<td class="need" width="30px">*</td>
									<td align="right">投注次数：</td>
									<td><input id="betCount" name="betCount" type="text"
										class="inputxt" datatype="n1-6"
										 errormsg="格式为数字" readonly="readonly" /></td>
									<td align="left"><div class="Validform_checktip"></div></td>
								</tr>
																
								<tr>
									<td colspan="4" height="3"></td>
								</tr>
								<tr>
									<td class="need">*</td>
									<td align="right">投注地址：</td>
									<td><input id="betAddress" type="text" name="betAddress" value=""
										class="inputxt" datatype="*" nullmsg="请输入地址"
										errormsg="长度为1-64" /></td>
									<td><div class="Validform_checktip"></div></td>
								</tr>
																
								<tr>
									<td colspan="4" height="3"></td>
								</tr>
								<tr>
									<td class="need">*</td>
									<td align="right">资源包ID：</td>
									<td>
									<c:forEach items="${resourceList}" var="entry"> 
									${entry.remark}<input name="resourceListId" type="checkbox" datatype="*" value="${entry.id}"/>&nbsp;
									</c:forEach></td>
									<td><div class="Validform_checktip"></div></td>
								</tr>															
								
								<tr>
									<td colspan="4" style="vertical-align:middle; text-align:center;"><input id="sub" name="Submit"
										id="sub" type="submit" value="提交" /></td>
								</tr>
							</tbody>
						</table>
					</form>
				</div>
				
				
<script type="text/javascript" src="${siteContext}/resources/js/Validform5.js"></script>
	<script type="text/javascript">
		$("#fmAdd").Validform({
			ajaxPost:true,
			tiptype : 2,
			showAllError : true,
			ignoreHidden : true,
			postonce:true,
			callback:function(data){
				$('#dlg').dialog('close');
				$('#tt').datagrid("reload");
				$("#fmAdd").Validform({postonce:false});
			}
		});
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