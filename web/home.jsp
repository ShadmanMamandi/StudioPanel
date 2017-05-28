<%@ page import="ir.valinejad.commons.Constants" %>
<%@ page import="ir.valinejad.commons.Util" %>
<%@ page import="ir.valinejad.entity.Studio" %>
<%--
  Created by IntelliJ IDEA.
  User: arash
  Date: 8/20/16
  Time: 4:01 PM
  To change this template use File | Settings | File Templates.
--%>
<%
  Studio sessionInfo = (Studio) session.getAttribute(Constants.SESSION_INFO);
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="article">
  <div class="h2Title">سامانه نگار - آتلیه
  <%=sessionInfo.getName()%>
  </div>
  <div class="clr"></div>
  <p>خالق عکس های زنده...</p>
  <img src="images/img1.jpg" width="100%" height="250" alt="" />
  <p>منوی سامانه</p>
  <%=Util.getAuthorizedLink("weddings", "", "تعریف مراسم", sessionInfo, true)%>
  <%=Util.getAuthorizedLink("logout", "", "خروج", sessionInfo, true)%>
</div>
