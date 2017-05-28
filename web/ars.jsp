<%@ page import="ir.valinejad.commons.Constants" %>
<%@ page import="ir.valinejad.commons.FileUpload" %>
<%@ page import="ir.valinejad.commons.Util" %>
<%@ page import="ir.valinejad.entity.Studio" %>
<%@ page import="ir.valinejad.entity.Wedding" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="ir.valinejad.dao.*" %>
<%@ page import="ir.valinejad.entity.ArContent" %>
<%@ page import="java.awt.image.BufferedImage" %>
<%@ page import="java.io.File" %>
<%--
  Created by IntelliJ IDEA.
  User: arash
  Date: 8/20/16
  Time: 4:01 PM
  To change this template use File | Settings | File Templates.
--%>

<%
  Studio sessionInfo = (Studio) session.getAttribute(Constants.SESSION_INFO);
  Wedding wedding = null;
  List<ArContent> arContentList = null;
  String errorMessage = null;

  try {
    if (StudioDAO.checkPageAccess(sessionInfo, "ars")) {
      // Define contract
      wedding = WeddingDAO.findById(Long.parseLong(request.getParameter("wid")));

      if (StudioDAO.checkWeddingAccess(sessionInfo, wedding)) {
        // Delete file request
        if (request.getParameter("did") != null){
          long arIdToDelete = Long.parseLong(request.getParameter("did"));
          if (StudioDAO.checkArAccess(sessionInfo, ArContentDAO.findById(arIdToDelete))) {
            ArContentDAO.delete(arIdToDelete);
            WeddingDAO.increaseAssetVersion(wedding);
          }
        }

        // Upload file request
        String contentType = request.getContentType();
        if ((contentType != null) && (contentType.contains("multipart/form-data"))) {
          String fileName1 = String.valueOf(System.currentTimeMillis()) + "_1";
          String fileName2 = String.valueOf(System.currentTimeMillis()) + "_2";
          String subImageFolder = PhotoDAO.getSubImageFolder(new Date(), sessionInfo.getId(), wedding.getId());
          String realPath = PhotoDAO.getBaseImageFolder(application) + subImageFolder;
          Map<String, String> formValues = FileUpload.uploadFile2File(request, realPath, fileName1, fileName2);
          if (formValues.get(FileUpload.FILE_TYPE_1) != null && formValues.get(FileUpload.FILE_TYPE_2) != null) {
            fileName1 += "." + formValues.get(FileUpload.FILE_TYPE_1);
            fileName2 += "." + formValues.get(FileUpload.FILE_TYPE_2);

            // Save ArContent
            BufferedImage bimg = javax.imageio.ImageIO.read(new File(realPath+fileName1));
            Double width=bimg.getWidth()*FileUpload.PX_TO_MM;
            Double height=bimg.getHeight()*FileUpload.PX_TO_MM;
            ArContentDAO.save(wedding, subImageFolder + fileName1, subImageFolder + fileName2,width.toString(),height.toString());
            WeddingDAO.increaseAssetVersion(wedding);

          }
          else
            errorMessage = "هر دو فایل تصویر و ویدئو باید انتخاب گردد.";
        }

        // Prepare photo list
        arContentList = ArContentDAO.findByWedding(wedding.getId());
      }
      else // access denied
        wedding = null;
    }
  } catch (Exception e) {
    long id = LogDAO.logError(sessionInfo, "ars", null, e);
    errorMessage = "خطای نامشخص! کد خطا: "  + id;
  }

%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="article">
  <div class="h2Title">بارگذاری تصاویر زنده مراسم
    <%=wedding.getTitle()%>
  </div>
  <div class="clr"></div>
  <%
    if (errorMessage != null){
  %>
  <table class="gridError">
    <tr><th>خطا در بارگذاری</th></tr>
    <tr><td><%=errorMessage%></td></tr>
  </table>
  <%
    }
  %>
  <p>تصاویر بارگذاری شده</p>
  <table class="grid">
    <tr>
      <th>حذف</th>
      <th>نمایش</th>
    </tr>
    <%
      for (ArContent ar : arContentList){
    %>
    <tr>
      <td><a href="index.jsp?to=ars&did=<%=ar.getId()%>&wid=<%=wedding.getId()%>" onclick="return confirm('آیا از حذف این عکس مطمئن هستید؟')">حذف</a> </td>
      <td><img src="<%=PhotoDAO.getWebImageFolder()+ar.getPhoto_url()%>" width="150" height="150" /></td>
    </tr>
    <%
      }
    %>
  </table>
  <p>بارگذاری عکس جدید</p>
  <form enctype="multipart/form-data" method=post class="box">
    <input type="hidden" name="to" value="ars">
    <input type="hidden" id="actionId" name="action" value="submitted">
    <input type="hidden" name="wid" value="<%=wedding.getId()%>">
    <ol>
      <li>
        <label for="f1">فا یل تصویر (فقط فرمت jpg با کیفیت 72dpi)</label>
        <input type="file" name="f1" id="f1" value="f1" class="text" dir="ltr" accept="image/jpeg">
      </li>
      <li>
        <label for="f2">فایل ویدئو متناظر با این تصویر (فقط فرمت mp4)</label>
        <input type="file" name="f2" id="f2" value="f2" class="text" dir="ltr" accept="video/mp4">
      </li>
      <li>
        <input type="submit" name="imageField" id="imageField" value= " ذخیره " class="flatButton" />&nbsp;&nbsp;&nbsp;
        <div class="clr"></div>
      </li>
    </ol>
  </form>
  <p>&nbsp;</p>
  <%=Util.getAuthorizedLink("weddings", "", "فهرست مراسم های تعریف شده", sessionInfo, true)%>
  <%=Util.getAuthorizedLink("home", "",  "صفحه اصلی", sessionInfo, true)%>
</div>
