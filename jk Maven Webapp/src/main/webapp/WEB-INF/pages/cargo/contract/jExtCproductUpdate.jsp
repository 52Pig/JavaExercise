<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../baselist.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title></title>
    <script type="text/javascript" src="../../js/datepicker/WdatePicker.js"></script>
    <script language="javascript">
    	//设置选择的厂家名称
    	function setFactoryName(val){
    		document.getElementById("factoryName").value = val;
    	}
    </script>
</head>
<body>
<form method="post">
	<input type="text" name="id" value="${obj.id}">
	<input type="text" name="contractProductId" value="${obj.contractProductId}">

<div id="menubar">
<div id="middleMenubar">
<div id="innerMenubar">
    <div id="navMenubar">
<ul>
<li id="save"><a href="#" onclick="formSubmit('update.action','_self');this.blur();">确定</a></li>
<li id="back"><a href="${ctx}/cargo/contract/list.action">返回</a></li>
</ul>
    </div>
</div>
</div>
</div>
     
<div class="textbox" id="centerTextbox">
    <div class="textbox-header">
    <div class="textbox-inner-header">
    <div class="textbox-title">
		修改附件信息
    </div> 
    </div>
    </div>
<div>
 
    <div>
		<table class="commonTable" cellspacing="1">
		    <tr>
	            <td class="columnTitle_mustbe">货号：</td>
	            <td class="tableContent"><input type="text" name="productNo" value="${obj.productNo}"/></td>
	            <td class="columnTitle_mustbe">排序号：</td>
	            <td class="tableContent"><input type="text" name="orderNo" value="${obj.orderNo}"/></td>
	        </tr>		
		    <tr>
	            <td class="columnTitle_mustbe">生产厂家：</td>
	            <td class="tableContent">
	            	<select name="factoryId" onchange="setFactoryName(this.options[this.selectedIndex].text);">
	            		<option value="">--请选择--</option>
	            		<c:forEach items="${factoryList}" var="f">
	            			<option value="${f.id}" <c:if test="${f.id==obj.factoryId}">selected</c:if>>${f.factoryName}</option>
	            		</c:forEach>
	            	</select>
	            	<input type="hidden" id="factoryName" name="factoryName" value="${obj.factoryName}"/>
	            </td>
	            <td class="columnTitle_mustbe">分类：</td>
	            <td class="tableContent">
	            	<select name="ctype">
	            		<option value="">--请选择--</option>
	            		<c:forEach items="${ctypeList}" var="t">
	            			<option value="${t.orderNo}" <c:if test="${t.orderNo==obj.ctype}">selected</c:if>>${t.name}</option>
	            		</c:forEach>
	            	</select>
	            </td>
	        </tr>		
	        <tr>
	            <td class="columnTitle_mustbe">图片：</td>
	            <td class="tableContent"><input type="text" name="productImage" value="${obj.productImage}"/></td>
	            <td class="columnTitle_mustbe">数量：</td>
	            <td class="tableContent"><input type="text" name="cnumber" value="${obj.cnumber}"/></td>
	        </tr>
	        <tr>
	            <td class="columnTitle_mustbe">单价：</td>
	            <td class="tableContent"><input type="text" name="price" value="${obj.price}"/></td>
	        </tr>
	        <tr>
	            <td class="columnTitle">货物描述：</td>
	            <td class="tableContent"><textarea name="productRequest" style="width:100%;height:120px;">${obj.productRequest}</textarea></td>
	        </tr>

		</table>
	</div>
</div>


</form>
</body>
</html>

