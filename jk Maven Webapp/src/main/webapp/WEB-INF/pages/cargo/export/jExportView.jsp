<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../base.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title></title>
	<script type="text/javascript" src="${ctx}/js/datepicker/WdatePicker.js"></script>
		
</head>
<body>
<form method="post">

<div id="menubar">
<div id="middleMenubar">
<div id="innerMenubar">
    <div id="navMenubar">
<ul>
<li id="back"><a href="#" onclick="history.go(-1);">返回</a></li>
</ul>
    </div>
</div>
</div>
</div>
     
<div class="textbox" id="centerTextbox">
    
    <div class="textbox-header">
    <div class="textbox-inner-header">
    <div class="textbox-title">
		浏览出口报运
    </div> 
    </div>
    </div>
<div>

    <div>
		<table class="commonTable" cellspacing="1">
	        <tr>
	            <td class="columnTitle_mustbe">客户号：</td>
	            <td class="tableContent"><font size="4">${contractUrls}</font></td>
	            <td class="columnTitle_mustbe">制单日期：</td>
	            <td class="tableContent"><fmt:formatDate value="${obj.inputDate}" pattern="yyyy-MM-dd"/></td>
	        </tr>
	        <tr>
	            <td class="columnTitle_mustbe">收货人及地址：</td>
	            <td class="tableContent">${obj.consignee}</td>
	            <td class="columnTitle_mustbe">L/C No.：</td>
	            <td class="tableContentAuto">
	            	<c:if test="${obj.lcno=='T/T'}">T/T</c:if>
	            	<c:if test="${obj.lcno=='L/C'}">L/C</c:if>
	            </td>	       
	        </tr>
	        <tr>
	            <td class="columnTitle_mustbe">装运港：</td>
	            <td class="tableContent">${obj.shipmentPort}</td>
	            <td class="columnTitle_mustbe">运输方式：</td>
		            <td class="tableContentAuto">
	            	<c:if test="${obj.transportMode=='SEA'}">海运(SEA)</c:if>
	            	<c:if test="${obj.transportMode=='AIR'}">空运(AIR)</c:if>
	            </td>	
	        <tr>
	            <td class="columnTitle_mustbe">价格条件：</td>
	            <td class="tableContent">${obj.priceCondition}</td>
	        </tr>
	        <tr>
	            <td class="columnTitle_mustbe">唛头：</td>
	            <td class="tableContent"><pre>${obj.marks}</pre></td>
	            <td class="columnTitle_mustbe">说明：</td>
	            <td class="tableContent"><pre>${obj.remark}</pre></td>
	        </tr>
		</table>
	</div>
</div>
 
 

 	
</form>
</body>
</html>

