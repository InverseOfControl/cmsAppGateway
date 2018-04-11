<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
<jsp:include page="/views/include.jsp" />
</head>
<body>
	<h2 align="left">图片迁移</h2>
	
	工号：<input id="userId" name="userId" type="text" value=""  class="easyui-textbox"  /> 
	申请单号：<input id="appNo" name="appNo" type="text" value=""  class="easyui-textbox" /> 
	身份证号：<input id="idNo" name="idNo" type="text" value="" class="easyui-textbox" /> 
	申请人姓名：<input id="userName" name="userName" type="text" value="" class="easyui-textbox" /> 
	<a href="#" onclick="javascript:searchBt()"  class="easyui-linkbutton" iconCls="icon-search">查询</a>
	<br/>
	<br/>
	<div id="magazineGrid">
	</div>
	
<script type="text/javascript">

$('#magazineGrid').datagrid({
    height: 500,
    url: '${pageContext.request.contextPath}/photo/queryList',
    method: 'POST',
    striped: true,
    fitColumns: true,
    singleSelect: false,
    rownumbers: true,
    pagination: true,
    nowrap: false,
    pageSize: 10,
    pageList: [10, 20, 50, 100, 150, 200],
    showFooter: true,
    columns: [[
        { field: 'ck', checkbox: true },
        { field: 'userId', title: '工号', width: 150, align: 'left' },
        { field: 'oldAppNo', title: '旧申请单号', width: 150, align: 'left' },
        { field: 'newAppNo', title: '新申请单号', width: 150, align: 'left' },
        { field: 'idCardNoLastFourDigits', title: '身份证号', width: 100, align: 'left' },
        { field: 'applicantName', title: '申请人姓名', width: 100, align: 'left' },
        { field: 'applyAccount', title: '申请金额', width: 100, align: 'right' },
        { field: 'applyTerm', title: '申请期限', width: 100, align: 'right' },
        { field: 'applyTime', title: '申请时间', width: 100, align: 'right' },
        { field: 'productName', title: '产品名称', width: 100, align: 'right' },
        { field: 'state', title: '状态', width: 100, align: 'right' },
        { field: 'operate', title: '图片迁移', width: 100, align: 'right' ,formatter:formatOper}
    ]]
});

function formatOper(val,row,index){  
    return '<a href="#" onclick="operation('+index+')">图片迁移</a>';  
}  

function operation(index){  
    $('#magazineGrid').datagrid('selectRow',index);// 关键在这里  
    var row = $('#magazineGrid').datagrid('getSelected');  
    if (row){  
        $('#dlg').dialog('open').dialog('setTitle','修改学生信息');  
        $('#fm').form('load',row);  
        url = '${pageContext.request.contextPath}/photo/operation';  
        $.ajax({ 
            type: "post", 
            url: url, 
            data: {"oldAppNo":row.oldAppNo,"newAppNo":row.newAppNo},
            dataType: "json", 
            success: function (data) { 
                    alert(data.message) ;
            }, 
            error: function (data) { 
                    alert("图片迁移失败"); 
            } 
    	});
    }  
}  
		

function searchBt(){
	
	var userId = $('#userId').val(); 
	var appNo = $('#appNo').val(); 
	var idNo = $('#idNo').val(); 
	var userName = $('#userName').val(); 
	
	var queryParams = $('#magazineGrid').datagrid('options').queryParams;
		queryParams.userId = userId ;
		queryParams.appNo = appNo;
		queryParams.idNo = idNo;
		queryParams.userName = userName ;
		$('#magazineGrid').datagrid('options').queryParams = queryParams;
		$("#magazineGrid").datagrid('load');
}
		
	</script>
</body>
</html>
