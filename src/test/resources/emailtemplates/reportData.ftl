<#list report as reportData>
  	<tr>
	    <td>${reportData.scenario}</td>
	    <td>
	    	<#if reportData.allProductID?size !=0>
		    	<#list reportData.allProductID as productID>
		    		${productID}<#sep>,
		    	</#list>
		    <#else>
		    	NA
	    	</#if>
	    </td>
	    <td>${reportData.testResult}</td>
 	</tr>
</#list>