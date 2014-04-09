<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/views/jsp/taglibs.jsp"%>
<html>
<head>
<base href="${siteContext}" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>易拍百万</title>
<link rel="stylesheet" type="text/css" href="${siteContext}/resources/plugin/jquery-easyui-1.3.2/themes/gray/easyui.css" />
<link rel="stylesheet" type="text/css" href="${siteContext}/resources/plugin/jquery-easyui-1.3.2/themes/icon.css" />
<link rel="stylesheet" type="text/css" href="${siteContext}/resources/plugin/jquery-easyui-1.3.2/demo.css" />
<link href="${siteContext}/resources/css/demo.css" type="text/css" rel="stylesheet" />
<link rel="stylesheet" href="${siteContext}/resources/css/validform.css"
	type="text/css" media="all" />

<link rel="stylesheet" href="${siteContext}/resources/css/magic_dlg.css" type="text/css" media="all" />
<script type="text/javascript" src="${siteContext}/resources/js/jquery-1.6.2.min.js"></script>
<script type="text/javascript" src="${siteContext}/resources/plugin/jquery-easyui-1.3.2/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${siteContext}/resources/plugin/jquery-easyui-1.3.2/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${siteContext}/resources/js/validatebox_extend.js"></script>
<script type="text/javascript">
	var levels = '${levels}';

	function editNode() {
		var node = $('#tt').datagrid('getSelected');
		if (node) {
			var rowIndex = $('#tt').datagrid('getRowIndex', node);
			$('#tt').datagrid('beginEdit', rowIndex);
		}else{
			$.messager.alert('提示信息', "请选择一条记录", 'info');
		}
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
				url : "${siteContext}/admin/userGroup/updateUserGroup",
				type : "POST",
				data : "groupId=" + node.groupId + "&name=" + node.groupName + "&memo="
						+ node.memo + "&level=" + node.level  + "&code=" + node.code,
				success : function(text) {
					$.messager.alert('提示信息', text, 'info');
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
		$('#dlg').dialog('open').dialog('setTitle', '新增用户组');
		$('#fm').form('clear');
	}
	function deleteNode() {
		var node = $('#tt').datagrid('getSelected');
		if(node){
		$.messager.defaults = {
			ok : "确定",
			cancel : "取消"
		};
		$.messager.confirm('操作提示', '您确定要删除吗？', function(r) {
			if (r) {
				if (node) {
					$.ajax({
						url : "lottery/manager?action=jsonDeleteUserGroup&rid"
								+ Math.random(),
						type : "POST",
						data : "id=" + node.id,
						dataType : "json",
						async : false,
						cache : false,
						success : function(text) {
							$('#tt').datagrid("reload");
							$.messager.alert('提示信息', text.msg, 'info');
						}
					});
				}
			}
		});
		}else{
			$.messager.alert('提示信息', "请选择一条记录", 'info');
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
			title : '用户组列表',
			singleSelect : true,
			pagination : true,
			rownumbers : true,
			fitColumns : true,
			idField : 'groupId',
			columns : [ [ {
				field : 'groupName',
				title : '名称',
				width : 100
			} , {
				field : 'code',
				title : '编码',
				width : 100,
				editor : {
					type:'validatebox',
					options:{
						validType:['positiveInt','mustLength[4]']
					}
				}
			},{
				field : 'level',
				title : '级别',
				width : 80,
				formatter : function(value) {
					for ( var i = 0; i < levels.length; i++) {
						if (levels[i].levelid == value)
							return levels[i].levelname;
					}
					return value;
				},
				editor : {
					type : 'combobox',
					options : {
						valueField : 'levelid',
						textField : 'levelname',
						data : levels,
						required : true,
						editable:false 
					}
				}
			}, {
				field : 'enabled',
				title : '有效',
				width : 60,
				align : 'center',
				editor : {
					type : 'checkbox',
					options : {
						on : 'Y',
						off : 'N'
					}
				}
			}, {
				field : 'memo',
				title : '备注',
				width : 300,
				editor : {
					type:'validatebox',
					options:{
						required:true,
						validType:'length[1,128]'
					}
				}
			} ,
			{field:'groupId',title:'授权',width:50,align:'center',
				formatter:function(value,row,index){
						var e = '<a href=${siteContext}/admin/sysPriviledges/gotoEditSysPriviledges?groupId='+value+'>授权</a> ';
						return e;
				}
			}  ] ],
			onLoadSuccess: function(data){
		    	if( "undefined" != typeof(data) && "undefined" != typeof(data.errorInfo) ){
		         	$.messager.alert('信息提示',data.errorInfo,'error');
		    	}
			},
			onBeforeLoad:function(param){

				$(this).datagrid('options').url = '${siteContext}/admin/userGroup/selectUserGroup';
			}
		});
	});

	function init(){
		$('#cbxLevel').combobox('loadData', levels);
	}
</script>
</head>

<body onload="init()">
				<div id="toolbar">
					<a href="javascript:newNode()" class="easyui-linkbutton">新增</a> <a
						href="javascript:editNode()" class="easyui-linkbutton">编辑</a> <a
						href="javascript:saveNode()" class="easyui-linkbutton">保存</a> <a
						href="javascript:cancelNode()" class="easyui-linkbutton">取消</a> <a
						href="javascript:deleteNode()" class="easyui-linkbutton">删除</a>
				</div>

				
	<table height=100% width="100%" cellSpacing=0 cellPadding=0 id=table0>
		<tr>
			<td>
				<table id="tt" title="用户组列表"
					toolbar="#toolbar" fit="true">
				</table>
			</td>
		</tr>
	</table>
				<div id="background" class="background" style="display: none;"></div>
				<div id="progressBar" class="progressBar" style="display: none;">数据加载中，请稍等...</div>

				<div id="dlg" class="easyui-dialog"
					style="width: 550px; height: 320px; padding: 10px 0px 0px 20px"
					closed="true">
					<form class="registerform"
						action="lottery/manager?action=addUserGroup" method="post">
						<table width="100%" style="table-layout: fixed;">
							<tbody>
								<tr>
									<td class="need" width="30px">*</td>
									<td width="80px" align="right">组名：</td>
									<td width="270px"><input id="name" name="name" type="text"
										class="inputxt" datatype="s2-12" nullmsg="请输入组名"
										errormsg="长度为2-12" /></td>
									<td align="left"><div class="Validform_checktip"></div></td>
								</tr>
								<tr>
									<td colspan="4" height="3"></td>
								</tr>
								<tr>
									<td class="need"></td>
									<td align="right">编码：</td>
									<td><input id="code" type="text" name="code" value=""
										class="inputxt" datatype="n4-4" ignore="ignore"
										errormsg="长度为4位数字" /></td>
									<td><div class="Validform_checktip"></div></td>
								</tr>
								<tr>
									<td colspan="4" height="3"></td>
								</tr>
								<tr>
									<td class="need">*</td>
									<td align="right">备注：</td>
									<td><input id="memo" type="text" name="memo" value=""
										class="inputxt" datatype="*1-128" nullmsg="请输入备注"
										errormsg="长度为1-128" /></td>
									<td><div class="Validform_checktip"></div></td>
								</tr>
								<tr>
									<td colspan="4" height="3"></td>
								</tr>
								<tr>
									<td class="need">*</td>
									<td>是否有效：</td>
									<td><input type="radio" value="Y" name="enabled"
										id="isEnabled" class="pr1" datatype="*" errormsg="请选择是否生效！"
										checked /><label for="isEnabled">是</label> <input
										type="radio" value="N" name="enabled" id="notEnabled"
										class="pr1" /><label for="notEnabled">否</label></td>
									<td><div class="Validform_checktip"></div></td>
								</tr>
								<tr>
									<td colspan="4" height="3"></td>
								</tr>
								<tr>
									<td class="need">*</td>
									<td align="right">级别：</td>
									<td><input id="cbxLevel" name="cbxLevel" class="easyui-combobox" editable="false" style="width: 200px; height:26px"
				valueField="levelid" textField="levelname"/>
				
                        <input type="hidden" name="level" id="level" value="" /></td>
									<td><div class="Validform_checktip"></div></td>
								</tr>
								<tr>
									<td colspan="4" style="vertical-align:middle; text-align:center;"><input name="Submit"
										type="submit" value="添加" /></td>
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
					var obj = $('#cbxLevel').combobox('getValue');
					if(obj == ""){
						$.messager.alert('提示信息', "请选择级别！", 'info');
						return false;
					}else{
						$("#level").val(obj);
						return true;
					}
				}
			});
		})
	</script>
</body>

</html>