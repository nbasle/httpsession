<%@ page errorPage="error.jsp"%>

<html>
<head>
	<title>YAPS PetStore - Item</title>
</head>
<body>

<table cellspacing="0" cellpadding="5" width="100%">
    <%--HEADER--%>
	<tr>
		<td colspan="3">
			<jsp:include page="common/header.jsp"/>
		</td>
	</tr>

	<tr>
        <%--NAVIGATION--%>
        <td valign="top" width="20%">
    		<jsp:include page="common/navigation.jsp"/>
    	</td>

        <td align="left" valign="top" width="60%">
        <%--CENTRAL BODY--%>

<table border="0" cellspacing="4">

                <!-- You are signed out. -->
                <tr>
                    <td colspan="2"><strong>You are signed out.</strong></td>
	            </tr>
	            <center>Thank you for shopping</center>
	            </table>
            <p>You may <a href="signon.jsp">Sign on</a>again</p>
	            
    <%--FOOTER--%>
    	</td>
        <td></td>
    </tr>
    <tr>
    	<td colspan="3">
    		<jsp:include page="common/footer.jsp"/>
    	</td>
    </tr>
</table>
</body>
</html>