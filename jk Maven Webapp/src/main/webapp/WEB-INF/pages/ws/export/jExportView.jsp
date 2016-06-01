<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../base.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title></title>
	<script type="text/javascript">
		var xmlHttpRequest = new ActiveXObject("Microsoft.XMLHTTP");
		function sendMsg(){
			/*
				1.创建ajax对象 XMLHTTP
				2.打开一个链接open("POST",url,true)
				3.soap xml request
				4.设置requestHeader请求头
				5.回调函数，处理返回的xml，将信息展现到页面
				6.send(xml)
			*/
			
			var findId = document.getElementById("findId").value;
			findId = "%"+findId+"%";
			if(findId==null){
				alert("请输入编号");
				return false;
			}
			
			var requestBody = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:q0=\"http://impl.service.jk.itcast.cn/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"
						    + "<soapenv:Body><q0:wsview><arg0>"+findId+"</arg0></q0:wsview></soapenv:Body></soapenv:Envelope>"
			var url = "${ctx}/cxf/ExportServiceImpl";
			xmlHttpRequest.open("POST", url , true);
			xmlHttpRequest.setRequestHeader("Content-Type","text/xml;charset=utf-8;");
			xmlHttpRequest.onreadystatechange = _back;
			
			xmlHttpRequest.send(requestBody);
		}
		
		function _back(){
			if(xmlHttpRequest.readyState==4){									//处理完成
				if(xmlHttpRequest.status==200){
					var retxml = xmlHttpRequest.responseXML;					//返回的XML内容
					var ret = retxml.getElementsByTagName("return")[0];
					
					if(ret==null){
						alert("查询不到记录!");
						return false;
					}
					
					//将数据插入到对应的td
					document.getElementById("customerContract").innerHTML = ret.getElementsByTagName("customerContract")[0].text;
					if(ret.getElementsByTagName("inputDate")[0]!=null){
						document.getElementById("inputDate").innerHTML = ret.getElementsByTagName("inputDate")[0].text.substring(0,10);
					}
					if(ret.getElementsByTagName("consignee")[0]!=null){
						document.getElementById("consignee").innerHTML = ret.getElementsByTagName("consignee")[0].text;
					}
					if(ret.getElementsByTagName("lcno")[0]!=null){
						document.getElementById("lcno").innerHTML = ret.getElementsByTagName("lcno")[0].text;
					}
					if(ret.getElementsByTagName("shipmentPort")[0]!=null){
						document.getElementById("shipmentPort").innerHTML = ret.getElementsByTagName("shipmentPort")[0].text;
					}
					if(ret.getElementsByTagName("transportMode")[0]!=null){
						document.getElementById("transportMode").innerHTML = ret.getElementsByTagName("transportMode")[0].text;
					}
					if(ret.getElementsByTagName("priceCondition")[0]!=null){
						document.getElementById("priceCondition").innerHTML = ret.getElementsByTagName("priceCondition")[0].text;
					}
					if(ret.getElementsByTagName("marks")[0]!=null){
						document.getElementById("marks").innerHTML = "<pre>"+ret.getElementsByTagName("marks")[0].text+"</pre>";
					}
					if(ret.getElementsByTagName("remark")[0]!=null){
						document.getElementById("remark").innerHTML = "<pre>"+ret.getElementsByTagName("remark")[0].text+"</pre>";
					}
		            
				}else{
					//出错信息
					alert("你出错了!")
				}
				
			}
		}
	</script>
</head>
<body>
<form method="post">

     
<div class="textbox" id="centerTextbox">
    
    <div class="textbox-header">
    <div class="textbox-inner-header">
    <div class="textbox-title">
		浏览出口报运(WebService)
    </div> 
    </div>
    </div>
<div>

    <div>
		<table class="commonTable" cellspacing="1">
	        <tr>
	            <td class="columnTitle" style="height:100px;">编号：</td>
	            <td class="tableContentAuto">
		            <input type="text" id="findId" name="findId" style="width:200px;"/>
		            <input type="button" name="ok" value="查询" onclick="sendMsg();"/>
	            </td>
	            <td class="columnTitle">&nbsp;</td>
	            <td class="tableContent">&nbsp;</td>
	        </tr>
	        <tr>
	            <td class="columnTitle_mustbe">报运号：</td>
	            <td class="tableContent" id="customerContract"></td>
	            <td class="columnTitle_mustbe">制单日期：</td>
	            <td class="tableContent" id="inputDate"></td>
	        </tr>
	        <tr>
	            <td class="columnTitle_mustbe">收货人及地址：</td>
	            <td class="tableContent" id="consignee"></td>
	            <td class="columnTitle_mustbe">L/C No.：</td>
	            <td class="tableContentAuto" id="lcno"></td>	       
	        </tr>
	        <tr>
	            <td class="columnTitle_mustbe">装运港：</td>
	            <td class="tableContent" id="shipmentPort"></td>
	            <td class="columnTitle_mustbe">运输方式：</td>
		        <td class="tableContent" id="transportMode"></tr>	
	        <tr>
	            <td class="columnTitle_mustbe">价格条件：</td>
	            <td class="tableContent" id="priceCondition"></td>
	        </tr>
	        <tr>
	            <td class="columnTitle_mustbe">唛头：</td>
	            <td class="tableContent" id="marks"></td>
	            <td class="columnTitle_mustbe">说明：</td>
	            <td class="tableContent" id="remark"></td>
	        </tr>
		</table>
	</div>
</div>
 
 

 	
</form>
</body>
</html>

