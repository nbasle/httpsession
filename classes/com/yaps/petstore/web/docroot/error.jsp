<%@ page isErrorPage="true" %>
<html>
<head>
	<title>YAPS Error</title>
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



            <P><strong><font color="#FF0000">Error has occured !!!</font></strong></P>
            <P><strong>

            <jsp:scriptlet>
                if ("".equals(request.getParameter("exception")) || request.getParameter("exception")==null) {
                    out.println("Come back later, the administrator will solve the problem");
                } else {
                    out.println(request.getParameter("exception"));
                }
            </jsp:scriptlet>

            </strong></P>

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