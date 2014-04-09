<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="../taglibs.jsp"%>
<html>
<head>
<base href="${siteContext}" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>易拍百万</title>
<link rel="stylesheet" type="text/css" href="${siteContext}/resources/plugin/jquery-easyui-1.3.2/themes/gray/easyui.css" />
<link rel="stylesheet" type="text/css" href="${siteContext}/resources/plugin/jquery-easyui-1.3.2/themes/icon.css" />
<link rel="stylesheet" type="text/css" href="${siteContext}/resources/plugin/jquery-easyui-1.3.2/demo.css" />
<link href="${siteContext}/resources/css/demo.css" type="text/css" rel="stylesheet" />


<script type="text/javascript" src="${siteContext}/resources/js/jquery-1.6.2.min.js"></script>
<script type="text/javascript" src="${siteContext}/resources/plugin/jquery-easyui-1.3.2/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${siteContext}/resources/plugin/jquery-easyui-1.3.2/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${siteContext}/resources/js/validatebox_extend.js"></script>
<script type="text/javascript">

function doAccredit() {
	$.messager.confirm('操作提示', '您确定进行修改吗？', function(r) {
		if (r) {
			var privs="";
			var nodes = $('#tt').treegrid('getSelections');
			for(var i = 0; i < nodes.length; i ++){
				if(i > 0){
					privs += ",";
				}
				privs += nodes[i].id;
			}
			
			$.ajax({
				url : "${siteContext}/admin/sysPriviledges/updateSysPriviledgesByGroupId?",
				type : "POST",
				data : "groupId=${groupId}&privs="+privs,
				async : false,
				cache : false,
				success : function(text) {
					$.messager.alert('提示信息', text, 'info');
					$('#tt').treegrid("reload");
				}
			});
		}
	});
}
	var ajaxbg = $("#background,#progressBar");
	ajaxbg.hide();
	$(document).ajaxStart(function() {
		ajaxbg.show();
	}).ajaxStop(function() {
		ajaxbg.hide();
	});

	/**
	 * 扩展树表格级联勾选方法：
	 * @param {Object} container
	 * @param {Object} options
	 * @return {TypeName} 
	 */
	$.extend($.fn.treegrid.methods,{
		/**
		 * 级联选择
	     * @param {Object} target
	     * @param {Object} param 
		 *		param包括两个参数:
	     *			id:勾选的节点ID
	     *			deepCascade:是否深度级联
	     * @return {TypeName} 
		 */
		cascadeCheck : function(target,param){
			var opts = $.data(target[0], "treegrid").options;
			if(opts.singleSelect)
				return;
			var idField = opts.idField;//这里的idField其实就是API里方法的id参数
			var status = false;//用来标记当前节点的状态，true:勾选，false:未勾选
			var selectNodes = $(target).treegrid('getSelections');//获取当前选中项
			for(var i=0;i<selectNodes.length;i++){
				if(selectNodes[i][idField]==param.id)
					status = true;
			}
			//级联选择父节点
			selectParent(target[0],param.id,idField,status);
			selectChildren(target[0],param.id,idField,param.deepCascade,status);
			/**
			 * 级联选择父节点
			 * @param {Object} target
			 * @param {Object} id 节点ID
			 * @param {Object} status 节点状态，true:勾选，false:未勾选
			 * @return {TypeName} 
			 */
			function selectParent(target,id,idField,status){
				var parent = $(target).treegrid('getParent',id);
				if(parent){
					var parentId = parent[idField];
					if(status)
						$(target).treegrid('select',parentId);
					else{
						var tmp = haveBrother(target, parentId, idField);
						if(!tmp){
							$(target).treegrid('unselect',parentId);
						}
					}
					selectParent(target,parentId,idField,status);
				}
			}
			/**
			 * 级联选择子节点
			 * @param {Object} target
			 * @param {Object} id 节点ID
			 * @param {Object} deepCascade 是否深度级联
			 * @param {Object} status 节点状态，true:勾选，false:未勾选
			 * @return {TypeName} 
			 */
			function selectChildren(target,id,idField,deepCascade,status){
				//深度级联时先展开节点
				if(deepCascade)
					$(target).treegrid('expand',id);
				//根据ID获取下层孩子节点
				var children = $(target).treegrid('getChildren',id);
				for(var i=0;i<children.length;i++){
					var childId = children[i][idField];
					if(status)
						$(target).treegrid('select',childId);
					else
						$(target).treegrid('unselect',childId);
					selectChildren(target,childId,idField,deepCascade,status);//递归选择子节点
				}
			}

			function haveBrother(target, parentId, idField){
				var children = $(target).treegrid('getChildren',parentId);

				var selectNodes = $(target).treegrid('getSelections');//获取当前选中项
				for(var i=0;i<selectNodes.length;i++){
					for(var j=0;j<children.length;j++){
						if(selectNodes[i][idField]==children[j][idField])
							return true;
					}
				}
				return false;
			}
		}
	});

	$(function() {
		$('#tt').treegrid(
				{
					singleSelect : false,
					fitColumns : true,
					idField : 'id',
					treeField : 'text',  
					url : '',
					columns : [[ {
						field : 'text',
						title : '权限名称',
						width : 80
					}, {
						field : 'url',
						title : '权限地址',
						width : 80
					}, {
						field : 'folder',
						title : '是否目录',
						width : 80
					}, {
						field : 'menu',
						title : '是否菜单项',
						width : 60,
						align : 'center'
					}, {
						field : 'anonymous',
						title : '匿名访问',
						width : 60,
						align : 'center'
					} ] ],
					onBeforeLoad:function(row, param){
						
						$(this).treegrid('options').url = '${siteContext}/admin/sysPriviledges/editSysPriviledgesByGroupId?groupId=${groupId}';
					},
					onClickRow:function(row){
						//级联选择
						$(this).treegrid('cascadeCheck',{
							id:row.id, //节点ID
							deepCascade:true //深度级联
						});
					},
		            onLoadSuccess : function(node, data) {
				    	
							if ("undefined" != typeof (data.errorInfo)) {
									$.messager.alert('信息提示', data.errorInfo,
											'error');
								} else {
									var rows = data.rows;
									for ( var i = 0; i < rows.length; i++) {
										var row = rows[i];
										if (row && row.checked
												&& row.checked == true) {
											$('#tt').treegrid('select', row.id);
										}
									}
								}
							}
						});
	});
</script>
</head>

<body>

	<div id="toolbar">
		<a href="javascript:doAccredit()" class="easyui-linkbutton">提交授权</a>
	</div>
	<table height=100% width="100%" cellSpacing=0 cellPadding=0 id=table0>
		<tr>
			<td>
				<table id="tt" title="授权-${name}"
					toolbar="#toolbar" fit="true" style="width: 100%; height: 100%;">
				</table>
			</td>
		</tr>
	</table>
	<div id="background" class="background" style="display: none;"></div>
	<div id="progressBar" class="progressBar" style="display: none;">数据加载中，请稍等...</div>
</body>

</html>