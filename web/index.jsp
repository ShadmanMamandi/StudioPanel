<%@ page import="ir.valinejad.commons.Constants" %>
<%@ page import="ir.valinejad.dao.LogDAO" %>
<%@ page import="ir.valinejad.entity.Studio" %>
<%@ page import="ir.valinejad.dao.StudioDAO" %>
<%
  String message = "";
  String targetPage = request.getParameter("to");
  String logout = "logout";

  // Logout Request
  if (targetPage!=null && targetPage.equals(logout)){
    session.removeAttribute(Constants.SESSION_INFO);
  }

  // Login Request
  if (request.getParameter("login_action") != null){
    String username = request.getParameter("username");
    String password = request.getParameter("password");
    Studio user = null;
    boolean hasException = false;
    try {
      user = StudioDAO.findByUsernamePassword(username, password);
    } catch (Exception e) {
      long id = LogDAO.logError(null, "Login", null, e);
      message = "خطای نامشخص! کد خطا: "  + id;
      hasException = true;
    }
    if (user != null) {
      session.setAttribute(Constants.SESSION_INFO, user);
      targetPage = "home";
    }
    else {
      if (!hasException)
        message = "نام کاربری و یا کلمه عبور نامعتبر!";
    }
  }

  // Validation
  boolean isValid = false;
  Studio sessionInfo = (Studio)session.getAttribute(Constants.SESSION_INFO);
  if (sessionInfo != null)
      isValid = true;

  // Default Page
  if (targetPage==null && isValid)
    targetPage = "home";
%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <title>سامانه نگار</title>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <link href="style.css" rel="stylesheet" type="text/css" />
  <script type="text/javascript" src="js/cufon-yui.js"></script>
  <script type="text/javascript" src="js/arial.js"></script>
  <script type="text/javascript" src="js/cuf_run.js"></script>
  <script type="text/javascript" src="js/jquery-1.3.2.min.js"></script>
  <script type="text/javascript" src="js/radius.js"></script>
</head>
<body>
<div class="main">
  <div class="header">
    <div class="header_resize">
      <div class="menu_nav">
        <ul>
          <%
            if (isValid){
              %>
              <li><a href="index.jsp?to=logout">خروج</a></li>
              <li><a href="index.jsp">منوی اصلی</a></li>
              <%
            }
            else {
              %>
              <li><a href="index.jsp">ورود به سامانه</a></li>
              <%
            }
          %>
        </ul>
      </div>
      <div class="logo">
        <div id="logoTitle">نگار<span>&nbsp;</span>
          <small dir="rtl">
آتلیه
            <%=sessionInfo!=null ? sessionInfo.getName() : ""%>
          </small></div>
      </div>
      <div class="clr"></div>
    </div>
  </div>
  <div class="content" dir=rtl>
    <div class="content_resize">
      <div class="mainbar">
        <%
          if (isValid){
            session.removeAttribute(Constants.REDIRECT_TO);
            targetPage += ".jsp";
            %><jsp:include page="<%=targetPage%>" /><%
            String redirectTo = (String) session.getAttribute(Constants.REDIRECT_TO);
            if (redirectTo != null)
              response.sendRedirect(redirectTo);
      }
          else {
            %>
            <div class="article">
              <div class="h2Title">ورود به سامانه</div>
              <div class="clr"></div>
              <div class="error"><%=message%></div>
              <div class="clr"></div>
              <p>جهت ورود به سامانه نام کاربری و کلمه عبور خود را وارد نمایید.</p>
              <form method="post">
                <input name="login_action" value="login" type="hidden">
                <ol>
                  <li>
                    <label for="username">نام کاربری</label>
                    <input id="username" name="username" class="text" value="" />
                  </li>
                  <li>
                    <label for="password">کلمه عبور</label>
                    <input id="password" name="password" class="text" type="password" value="" />
                  </li>
                  <li>
                    <input type="submit" name="imageField" id="imageField" value=" ورود " class="flatButton" />
                    <div class="clr"></div>
                  </li>
                </ol>
              </form>
              <p>&nbsp;</p>
              <p class="spec"></p>
            </div>
            <%
          }
        %>

      </div>
      <div class="clr"></div>
    </div>
  </div>
  <div class="footer">
    <div class="footer_resize">
      <table border="0" width="100%">
        <tr>
          <td align="left" valign="bottom" style="font:normal 12px/1.8em yekan, Arial, Helvetica, sans-serif;">Copyright &copy; 2016 <a href="http://www.betavas.com" target="_blank">BetaVAS</a> - All Rights Reserved</td>
          <td align="right" valign="bottom"><img src="images/logo.png" border="0" width="150" height="60" style="padding: 0px"></td>
        </tr>
      </table>
      <div class="clr"></div>
    </div>
  </div>
</div>
<div align=center><a href='http://www.betavas.com'>گروه نرم افزاری بتاوس</a></div></body>
</html>
