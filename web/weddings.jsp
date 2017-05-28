<%@ page import="ir.valinejad.commons.Constants" %>
<%@ page import="ir.valinejad.commons.Util" %>
<%@ page import="ir.valinejad.dao.LogDAO" %>
<%@ page import="ir.valinejad.dao.StudioDAO" %>
<%@ page import="ir.valinejad.dao.WeddingDAO" %>
<%@ page import="ir.valinejad.entity.Studio" %>
<%@ page import="ir.valinejad.entity.Wedding" %>
<%@ page import="javax.xml.crypto.Data" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="sun.util.calendar.BaseCalendar" %>
<%@ page import="sun.util.calendar.LocalGregorianCalendar" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.DateFormat" %>
<%--
  Created by IntelliJ IDEA.
  User: arash
  Date: 8/20/16
  Time: 4:01 PM
  To change this template use File | Settings | File Templates.
--%>

<%
    Studio sessionInfo = (Studio) session.getAttribute(Constants.SESSION_INFO);
    String errorMessage = null;
    String formErrorMessage = null;
    List<Wedding> weddings = new LinkedList<Wedding>();
    Wedding wedding = new Wedding();
    int counter = 1;


    Date currentDate = Calendar.getInstance().getTime();
    DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    String today = formatter.format(currentDate);


    try {
        if (StudioDAO.checkPageAccess(sessionInfo, "weddings")) {

            // check for save action
            if (request.getParameter("action") != null) {
                // Check wedding access for studio
                boolean weddingAccess = true;
                if (!Util.isEmpty(request.getParameter("id"))) {
                    Wedding theWedding = WeddingDAO.findById(Long.parseLong(request.getParameter("id")));
                    if (!StudioDAO.checkWeddingAccess(sessionInfo, theWedding))
                        weddingAccess = false;
                }

                // Save wedding
                if (weddingAccess) {
                    try {
                        Wedding myWedding = new Wedding();
                        String title = request.getParameter("title");
                        if (title != null && !title.isEmpty()) {
                            myWedding.setTitle(Util.solveUtfProblem(title));
                            myWedding.setPassword(request.getParameter("password"));
                        }
                        myWedding.setId(Util.isEmpty(request.getParameter("id")) ? null : Long.parseLong(request.getParameter("id")));
                        myWedding.setAppVersion(myWedding.getId() == null ? 1 : Long.parseLong(request.getParameter("appVersion")));
                        myWedding.setAroos_name(Util.solveUtfProblem(request.getParameter("aroosName")));
                        myWedding.setAssetVersion(myWedding.getId() == null ? 1 : Long.parseLong(request.getParameter("assetVersion")));
                        myWedding.setDamadName(Util.solveUtfProblem(request.getParameter("damadName")));
                        myWedding.setLoginCount(myWedding.getId() == null ? 0 : Long.parseLong(request.getParameter("loginCount")));
                        myWedding.setStudio(sessionInfo);
                        myWedding.setDate(WeddingDAO.getDate());
                        myWedding.setToken("TOKEN");
                        WeddingDAO.save(myWedding);
                    } catch (Exception e) {
                        long id = LogDAO.logError(sessionInfo, "weddings", null, e);
                        formErrorMessage = "خطای نامشخص! کد خطا: " + id;
                    }
                }


            }

            //delete wedding
            if (!Util.isEmpty(request.getParameter("did"))) {
                WeddingDAO.delete(Long.parseLong(request.getParameter("did")));
            }


            // load center data
            if (!Util.isEmpty(request.getParameter("id"))) {
                wedding = WeddingDAO.findById(Long.parseLong(request.getParameter("id")));
                if (!StudioDAO.checkWeddingAccess(sessionInfo, wedding))
                    wedding = new Wedding();
            }


            // prepare list
            weddings = WeddingDAO.findAll(sessionInfo.getId());
            //Date

        /*    if (!Util.isEmpty(request.getParameter("sdf"))) {
                Date date = Calendar.getInstance().getTime();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
                wedding = WeddingDAO.findById(Long.parseLong(request.getParameter("sdf")));
                sdf.format(new Date());
                wedding = new Wedding();
            }*/

            if (!Util.isEmpty(request.getParameter("id"))) {
                wedding = WeddingDAO.findById(Long.parseLong(request.getParameter("id")));
                wedding.getDate();

            }


        }
    } catch (Exception e) {
        long id = LogDAO.logError(sessionInfo, "weddings", null, e);
        errorMessage = "خطای نامشخص! کد خطا: " + id;
    }
%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="article">
    <div class="h2Title">مراسم <%=wedding.getId() == null ? "جدید" : wedding.getTitle()%>
    </div>
    <%if (wedding.getId() != null) {%>
    <p><a href="index.jsp?to=weddings">نمایش فرم مراسم جدید</a></p>
    <%}%>
    <div class="clr"></div>
    <%
        if (formErrorMessage != null) {
    %>
    <table class="gridError">
        <tr>
            <th>خطا در ذخیره سازی</th>
        </tr>
        <tr>
            <td><%=formErrorMessage%>
            </td>
        </tr>
    </table>
    <%
        }
    %>
    <form method="post" class="box">
        <input type="hidden" name="action" value="save">
        <input type="hidden" name="id" value="<%=wedding.getId()==null ? "" : wedding.getId()%>">
        <input type="hidden" name="appVersion" value="<%=wedding.getId()==null ? "" : wedding.getAppVersion()%>">
        <input type="hidden" name="assetVersion" value="<%=wedding.getId()==null ? "" : wedding.getAssetVersion()%>">
        <input type="hidden" name="loginCount" value="<%=wedding.getId()==null ? "" : wedding.getLoginCount()%>">
        <ol>
            <li>
                <label for="title">عنوان مراسم</label>
                <input id="title" name="title" type="text" class="text" value="<%=Util.getText(wedding.getTitle())%>">
            </li>
            <li>
                <label for="password">رمز ورود به اپلیکیشن</label>
                <input id="password" name="password" type="text" class="text"
                       value="<%=Util.getText(wedding.getPassword())%>">
            </li>
            <li>
                <input type="submit" name="imageField" id="imageField" value=" ذخیره " class="flatButton"/>
                <div class="clr"></div>
            </li>
        </ol>
    </form>
</div>
<div class="article">
    <div class="h2Title">فهرست مراسم های تعریف شده</div>
    <div class="clr"></div>
    <%
        if (errorMessage != null) {
    %>
    <table class="gridError">
        <tr>
            <th>خطا در بارگذاری</th>
        </tr>
        <tr>
            <td><%=errorMessage%>
            </td>
        </tr>
    </table>
    <%
        }
    %>
    <p>
    <table class="grid">
        <tr>
            <th>ردیف</th>
            <th>حذف</th>
            <th>ویرایش</th>
            <th>عنوان</th>
            <th>تاریخ</th>
            <th>تعریف تصاویر نگارگری</th>
            <th>گالری تصاویر</th>
        </tr>

        <%
            for (Wedding myWedding : weddings) {
        %>
        <tr>

            <td><%%><%=counter++%>
            </td>
            <td><a href="index.jsp?to=weddings&did=<%=myWedding.getId()%>"
                   onclick="return confirm('آیا از حذف این مراسم اطمینان دارید؟')">حذف</a></td>
            <td><a href="index.jsp?to=weddings&id=<%=myWedding.getId()%>">ویرایش</a></td>
            <td><%=myWedding.getTitle()%>
            </td>
            <td><%=myWedding.getDate()%>
            </td>
            <td><a href="index.jsp?to=ars&wid=<%=myWedding.getId()%>">تعریف تصاویر زنده</a></td>
            <td><a href="index.jsp?to=images&wid=<%=myWedding.getId()%>">گالری تصاویر</a></td>
        </tr>
        <%
            }
        %>
    </table>
    </p>
    <%=Util.getAuthorizedLink("home", "", "منوی اصلی", sessionInfo, true)%>

</div>