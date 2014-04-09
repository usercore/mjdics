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

	function filterNode(){
		$('#tt').datagrid("load");
	}
	
	
	function newNode() {
		$('#dlg').dialog('open').dialog('setTitle', '新增');
		$('#fmAdd').form('clear'); 
	}

	function filterNode() {
		$('#tt').datagrid("load");
	}

	$(function() {
		$('#tt')
				.datagrid(
						{
							title : '专家荐号',
							singleSelect : true,
							fitColumns : true,
							idField : 'id',
							url : '',
							columns : [ [ {
								field : 'addPerson',
								title : '推荐人',
								width : 80
							}, {
								field : 'game',
								title : '游戏',
								width : 80,
								formatter : function(value) {
									<c:forEach items="${game}" var="game">
									if('${game.key}'==value){
										return '${game.value}';
									}
									</c:forEach>
								}
								
							}, {
								field : 'issue',
								title : '期号',
								width : 80,
								
							} , {
								field : 'betNum',
								title : '投注号码',
								width : 80,
							} , {
								field : 'status',
								title : '状态',
								width : 80,
								formatter : function(value) {
									<c:forEach var="activeAndInvalid" items="${activeAndInvalidEnum}" varStatus="i"> 
										if ('${activeAndInvalid}' == value)
											return '${activeAndInvalid.name}'; 
									</c:forEach>
								}
								
							} , {
								field : 'id',
								title : '操作',
								width : 80,
								align : 'center',
								formatter : function(value,row,index) {
										/* if ('Active'== row.status){
											return '<a href="${siteContext}/admin/recommendNum/updateRecommendNum?status=Invalid&id='+value+'">失效</a>';
										} */
										if ('Active'== row.status){
											return '<a href=javascript:updateRecommendNum('+value+',"Invalid")>失效</a>'; 
										}
										if ('Invalid' == row.status)
											return '<a href=javascript:updateRecommendNum('+value+',"Active")>激活</a>'; 
								}
							} ] ],
							onLoadSuccess : function(data) {
								if ("undefined" != typeof (data)
										&& "undefined" != typeof (data.errorInfo)) {
									$.messager.alert('信息提示', data.errorInfo,
											'error');
								}
							},
							onBeforeLoad : function(param) {
								$(this).datagrid('options').url = '${siteContext}/admin/recommendNum/selectRecommendNum?rid'
										+ Math.random();
							}
						});
	});
	function updateRecommendNum(id,status){
		$.ajax({
			url : "${siteContext}/admin/recommendNum/updateRecommendNum?",
			type : "POST",
			data : "id=" + id+"&status="+status ,
			success : function(text) {
				$('#dlg').dialog('close');
				$.messager.alert('提示信息', text, 'info');
				$('#tt').datagrid("load");
			}
		});
	}
</script>
</head>

<body>
				<div id="toolbar" style="padding: 5px; height: auto">
		<div style="margin-bottom: 5px">
					<a  href="javascript:newNode()" class="easyui-linkbutton">新增</a> 
		</div>
</div>
				
	<table height=100% width="100%" cellSpacing=0 cellPadding=0 id=table0>
		<tr>
			<td>
				<table id="tt" title="专家荐号"
					toolbar="#toolbar" fit="true">
				</table>
			</td>
		</tr>
	</table>
				<div id="background" class="background" style="display: none;"></div>
				<div id="progressBar" class="progressBar" style="display: none;">数据加载中，请稍等...</div>
				<div id="dlg" class="easyui-dialog"
					style="width: 550px; height: 260px; padding: 10px 0px 0px 20px"
					closed="true">
					<form id="fmAdd" class="registerform"
						action="${siteContext}/admin/recommendNum/insertRecommendNum" method="post">
						<table width="100%" style="table-layout: fixed;">
							<tbody>
								<tr>
									<td class="need" width="30px">*</td>
									<td width="80px" align="right">标题：</td>
									<td width="270px"><input id="title" name="title" type="text"
										class="inputxt" datatype="s2-50"  nullmsg="请选择游戏"
										errormsg="长度为2-50" /></td>
									<td align="left"><div class="Validform_checktip"></div></td>
								</tr>
								<tr>
									<td colspan="4" height="3"></td>
								</tr>
								<tr>
									<td class="need" width="30px">*</td>
									<td width="80px" align="right">游戏：</td>
									<td>
										<select name="game" id="game" datatype="*" nullmsg="请选择游戏!" style="height: 26px;">
											<c:forEach items="${game}" var="gameList">
													<option value="${gameList.key}">${gameList.value}</option>
											</c:forEach>
										</select>	
									</td>
									
									<td align="left">
									<div class="Validform_checktip"></div></td>
								</tr>
								<tr>
									<td colspan="4" height="3"></td>
								</tr>
								<tr>
									<td class="need">*</td>
									<td align="right">投注号码：</td>
									<td><input id="betNum" type="text" name="betNum" value=""
										class="inputxt" datatype="*1-64" nullmsg="请输入投注号码"
										errormsg="长度为1-64" /></td>
									<td><div class="Validform_checktip"></div></td>
								</tr>
								<tr>
									<td colspan="4" height="3"></td>
								</tr>
								<tr>
									<td colspan="4"
										style="vertical-align: middle; text-align: center;"><input
										name="Submit" type="submit" value="添加" /></td>
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
			$('#fmAdd').form('clear'); 
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