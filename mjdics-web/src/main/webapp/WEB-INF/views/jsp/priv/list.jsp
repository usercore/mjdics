<%@ page contentType="text/html; charset=UTF-8"%>
<html>
<head>
<%
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Pragma", "no-cache");
	response.setDateHeader("Expires", 0);
%>
<base href="${siteContext}" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>易拍百万</title>
<link rel="stylesheet" type="text/css" href="${siteContext}/resources/plugin/jquery-easyui-1.3.2/themes/gray/easyui.css" />
<link rel="stylesheet" type="text/css" href="${siteContext}/resources/plugin/jquery-easyui-1.3.2/themes/icon.css" />
<link rel="stylesheet" type="text/css" href="${siteContext}/resources/plugin/jquery-easyui-1.3.2/demo.css" />
<link href="${siteContext}/resources/css/demo.css" type="text/css" rel="stylesheet" />
<link rel="stylesheet" href="${siteContext}/resources/css/validform.css" type="text/css" media="all" />

<script type="text/javascript" src="${siteContext}/resources/js/jquery-1.6.2.min.js"></script>
<script type="text/javascript" src="${siteContext}/resources/plugin/jquery-easyui-1.3.2/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${siteContext}/resources/plugin/jquery-easyui-1.3.2/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${siteContext}/resources/js/validatebox_extend.js"></script>
<script type="text/javascript">
	function editNode() {
		var node = $('#tt').treegrid('getSelected');
		if (node) {
			$('#tt').treegrid('beginEdit', node.id);
		}else{
			$.messager.alert('提示信息', "请选择一条记录", 'info');
		}
	}
	function saveNode() {
		var node = $('#tt').treegrid('getSelected');
		if (node) {
			var flag =  $('#tt').treegrid('validateRow', node.id);
			if(!flag){
				$.messager.alert('提示信息', "数据非法，请按照页面要求修改数据！", 'info');
				return;
			}
			$('#tt').treegrid('endEdit', node.id);
			$.ajax({
				url : "${siteContext}/admin/sysPriviledges/updateSysPriviledges?",
				type : "POST",
				data : "id=" + node.id + "&name=" + node.name + "&url="
						+ node.url + "&order=" + node.order + "&menu="
						+ node.menu + "&anonymous=" + node.anonymous+"&folder="+node.folder,
				dataType : "json",
				async : false,
				cache : false,
				success : function(text) {
					$.messager.alert('提示信息', text.msg, 'info');
					$('#tt').treegrid("reload", node.id);
				}
			});
		}else{
			$.messager.alert('提示信息', "请选择一条记录", 'info');
		}
	}
	function cancelNode() {
		var node = $('#tt').treegrid('getSelected');
		if (node) {
			$('#tt').treegrid('cancelEdit', node.id);
		}else{
			$.messager.alert('提示信息', "请选择一条记录", 'info');
		}
	}
	function newNode() {
		var node = $('#tt').treegrid('getSelected');
		var parentID = 0;
		var parentName = "根目录";
		var _parent = null;
		
		if (node) {
			if (node.folder == "Y") {
				parentID = node.id;
				parentName = node.code;
			} else {
				_parent = $('#tt').treegrid('getParent', node.id);
				if (_parent != null) {
					parentID = _parent.id;
					parentName = _parent.code;
				}
			}
		}

		$('#dlg').dialog('open').dialog('setTitle', '新增权限');
		$('#fm').form('clear');
		$("#parentFolderName").text(parentName);
		$("#pid").val(parentID);
	}
	function deleteNode() {
		var node = $('#tt').treegrid('getSelected');
		if(node){
		$.messager.defaults = {
			ok : "确定",
			cancel : "取消"
		};
		$.messager.confirm('操作提示', '您确定要删除吗？', function(r) {
			if (r) {
				if (node) {
					$.ajax({
						url : "${siteContext}/admin/sysPriviledges/deleteSysPriviledges",
						type : "POST",
						data : "id=" + node.id,
						dataType : "json",
						async : false,
						cache : false,
						success : function(text) {
							$('#tt').treegrid('remove', node.id);
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
		$('#tt').treegrid({
			title : '权限管理',
			singleSelect : true,
			fitColumns : true,
			idField : 'id',
			treeField : 'text',
			url : '',
			columns : [ [ {
				field : 'text',
				title : '名称',
				width : 100,
				editor : 'text'
			}, {
				field : 'url',
				title : '权限地址',
				width : 200,
				formatter : function(value, rec) {
					return value;
				},
				editor : 'text'
			}, {
				field : 'order',
				title : '排序',
				width : 50,
				formatter : function(value, rec) {
					return value;
				},
				editor : 'text'
			}, {
				field : 'folder',
				title : '目录',
				width : 50,
				align : 'center',
				editor : {
					type : 'checkbox',
					options : {
						on : 'Y',
						off : 'N'
					}
				}
			}, {
				field : 'menu',
				title : '菜单项',
				width : 50,
				align : 'center',
				editor : {
					type : 'checkbox',
					options : {
						on : 'Y',
						off : 'N'
					}
				}
			}, {
				field : 'anonymous',
				title : '匿名访问',
				width : 50,
				align : 'center',
				editor : {
					type : 'checkbox',
					options : {
						on : 'Y',
						off : 'N'
					}
				}
			} ] ],
			onLoadSuccess: function(row, data){
		    	if( "undefined" != typeof(data.errorInfo) ){
		         	$.messager.alert('信息提示',data.errorInfo,'error');
		    	}
			},
			onBeforeLoad:function(row, param){
				
				$(this).treegrid('options').url = '${siteContext}/admin/sysPriviledges/selectSysPriviledges';
			}
		});
	});
</script>
</head>

<body>
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
				<table id="tt" title="广告列表"
					toolbar="#toolbar" fit="true">
				</table>
			</td>
		</tr>
	</table>
	<div id="background" class="background" style="display: none;"></div>
	<div id="progressBar" class="progressBar" style="display: none;">数据加载中，请稍等...</div>
	
	<div id="dlg" class="easyui-dialog" style="width:550px;height:420px;padding:10px 0px 0px 20px"  
            closed="true">  
        	<form class="registerform" action="lottery/priv?action=addPriv"
					method="post">
					<table width="100%" align="center" cellpadding="0" cellspacing="0">
						<tbody>
							<tr>
								<td class="need" width="30px">*</td>
								<td width="80px" align="right">权限名称：</td>
								<td width="220px"><input id="name" name="name" type="text"
									class="inputxt" datatype="s2-12" nullmsg="请输入权限名称"
									errormsg="长度为2-12" /></td>
								<td align="left"><div class="Validform_checktip"></div></td>
							</tr>
							<tr>
								<td colspan="4" height="3"></td>
							</tr>
							<tr>
								<td class="need">*</td>
								<td align="right">相对地址：</td>
								<td><input id="url" type="text" name="url" value="/"
									class="inputxt" datatype="*" nullmsg="请输入地址"
									errormsg="长度为1-128" /></td>
								<td><div class="Validform_checktip"></div></td>
							</tr>
							<tr>
								<td colspan="4" height="3"></td>
							</tr>
							<tr>
								<td class="need">*</td>
								<td>是否目录：</td>
								<td><input type="radio" value="Y" name="folder"
									id="isFolder" class="pr1" datatype="*" errormsg="请选择是否目录！" /><label
									for="isFolder">是</label> <input type="radio" value="N"
									name="folder" id="notFolder" class="pr1"  checked/><label
									for="notFolder">否</label></td>
								<td><div class="Validform_checktip"></div></td>
							</tr>
							<tr>
								<td colspan="4" height="3"></td>
							</tr>
							<tr>
								<td class="need">*</td>
								<td>是否菜单：</td>
								<td><input type="radio" value="Y" name="menu" id="isMenu"
									class="pr1" datatype="*" errormsg="请选择是否菜单项！" /><label
									for="isMenu">是</label> <input type="radio" value="N"
									name="menu" id="notMenu" class="pr1"  checked/><label for="notMenu">否</label></td>
								<td><div class="Validform_checktip"></div></td>
							</tr>
							<tr>
								<td colspan="4" height="3"></td>
							</tr>
							<tr>
								<td class="need">*</td>
								<td>匿名访问：</td>
								<td><input type="radio" value="Y" name="anonymous"
									id="isAnonymous" class="pr1" datatype="*" errormsg="请选择是否菜单项！" /><label
									for="isAnonymous">是</label> <input type="radio" value="N"
									name="anonymous" id="notAnonymous" class="pr1"  checked/><label
									for="notAnonymous">否</label></td>
								<td><div class="Validform_checktip"></div></td>
							</tr>
							<tr>
								<td colspan="4" height="3"></td>
							</tr>
							<tr>
								<td class="need">*</td>
								<td align="right">排列顺序：</td>
								<td><input id="url" type="text" name="order" value="0"
									class="inputxt" datatype="n1-2" nullmsg="请输入顺序号"
									errormsg="取值[0-99]" /></td>
								<td><div class="Validform_checktip"></div></td>
							</tr>
							<tr>
								<td colspan="4" height="3"></td>
							</tr>
							<tr>
								<td class="need"></td>
								<td>归属目录：</td>
								<td>
									<div id="parentFolderName">根目录</div>
									<input id="pid" type="hidden" name="pid" value="0"/>
								</td>
								<td></td>
							</tr>
							<tr>
								<td height="40" colspan="4" style="vertical-align:middle; text-align:center;"><input name="Submit"
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
				ignoreHidden : true
			});
		})
	</script>
</body>

</html>