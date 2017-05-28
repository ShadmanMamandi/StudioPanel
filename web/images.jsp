<%@ page import="ir.valinejad.commons.Constants" %>
<%@ page import="ir.valinejad.commons.FileUpload" %>
<%@ page import="ir.valinejad.commons.Util" %>
<%@ page import="ir.valinejad.dao.LogDAO" %>
<%@ page import="ir.valinejad.dao.PhotoDAO" %>
<%@ page import="ir.valinejad.dao.StudioDAO" %>
<%@ page import="ir.valinejad.dao.WeddingDAO" %>
<%@ page import="ir.valinejad.entity.Photo" %>
<%@ page import="ir.valinejad.entity.Studio" %>
<%@ page import="ir.valinejad.entity.Wedding" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.*" %>
<%@ page import="org.apache.commons.fileupload.FileItem" %>
<%@ page import="org.apache.commons.fileupload.servlet.ServletFileUpload" %>
<%@ page import="org.apache.commons.fileupload.disk.DiskFileItemFactory" %>
<%@ page import="org.apache.commons.fileupload.FileItemFactory" %>
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
    List<Photo> photoList = null;
    String errorMessage = null;
    String photoContent = "";
    try {
        if (StudioDAO.checkPageAccess(sessionInfo, "images")) {
            // Define contract
            wedding = WeddingDAO.findById(Long.parseLong(request.getParameter("wid")));

            if (StudioDAO.checkWeddingAccess(sessionInfo, wedding)) {
                // Delete file request
                if (request.getParameter("did") != null) {
                    long photoIdToDelete = Long.parseLong(request.getParameter("did"));
                    if (StudioDAO.checkPhotoAccess(sessionInfo, PhotoDAO.findById(photoIdToDelete))) {
                        PhotoDAO.delete(photoIdToDelete);
                        WeddingDAO.increaseAssetVersion(wedding);
                    }
                }

                // Upload file request
                String contentType = request.getContentType();

                if ((contentType != null) && (contentType.contains("multipart/form-data"))) {
                    String fileName = String.valueOf(System.currentTimeMillis());
                    String subImageFolder = PhotoDAO.getSubImageFolder(new Date(), sessionInfo.getId(), wedding.getId());
                    String realPath = PhotoDAO.getBaseImageFolder(application) + subImageFolder;
                    Map<String, String> formValues = FileUpload.uploadFile1File(request, realPath, fileName);


                    if (formValues.get(FileUpload.FILE_TYPE_1) != null) {
                        fileName += "." + formValues.get(FileUpload.FILE_TYPE_1);


                        // Save Photo

                        photoContent = request.getParameter("xxx");
                        PhotoDAO.save(subImageFolder + fileName, photoContent, wedding.getId());
                        WeddingDAO.increaseAssetVersion(wedding);

                    } else
                        errorMessage = "هیج فایلی انتخاب نشده است.";
                }

                // Prepare photo list
                photoList = PhotoDAO.findByWedding(wedding.getId());
      /*  if (contentType != null){
          wedding = WeddingDAO.findById(Long.parseLong(request.getParameter("hhh")));

        }*/


            } else // access denied
                wedding = null;
        }
    } catch (Exception e) {
        long id = LogDAO.logError(sessionInfo, "images", null, e);
        errorMessage = "خطای نامشخص! کد خطا: " + id;
    }

%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="article">
    <div class="h2Title">بارگذاری گالری تصاویر مراسم
        <%=wedding.getTitle()%>
    </div>
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
    <p>تصاویر بارگذاری شده</p>
    <table class="grid">
        <tr>
            <th>حذف</th>
            <th>نمایش</th>
            <th>توضیحات</th>
        </tr>
        <%
            for (Photo photo : photoList) {
        %>
        <tr>
            <td><a href="index.jsp?to=images&did=<%=photo.getPhoto_id()%>&wid=<%=wedding.getId()%>"
                   onclick="return confirm('آیا از حذف این عکس مطمئن هستید؟')">حذف</a></td>
            <td><img src="<%=PhotoDAO.getWebImageFolder()+photo.getPhoto_url()%>" width="150" height="150"/></td>

        </tr>
        <%
            }
        %>
    </table>
    <p>بارگذاری عکس جدید</p>

    <script>
        submitForm = function () {
            document.getElementById("form1").submit();
            document.getElementById("form2").submit();
        }
    </script>
    <form id="form1" enctype="multipart/form-data" method=post class="box">
        <input type="hidden" name="to" value="images">
        <input type="hidden" id="actionId" name="action" value="submitted">
        <input type="hidden" name="wid" value="<%=wedding.getId()%>">
        <ol>
            <li>
                <label for="f1">فایل تصویر (فقط فرمت jpg)</label>
                <input type="file" name="f1" id="f1" value="f1" class="text" dir="ltr"
                       accept="image/jpeg,image/png,image/gif"/>
            </li>
            <li>
            <label for="xxx">توضیحات</label>
            <input type="text" id="xxx" name="xxx" value=" <%= photoContent %>" />
            </li>
            <li>
            <input type="submit" onclick="submitForm()" name="imageField" id="imageField" value=" ذخیره " class="flatButton"/>&nbsp;&nbsp;&nbsp;
            <div class="clr"></div>
        </li>


        </ol>
    <

    <p>&nbsp;</p>
    <%=Util.getAuthorizedLink("weddings", "", "فهرست مراسم های تعریف شده", sessionInfo, true)%>
    <%=Util.getAuthorizedLink("home", "", "صفحه اصلی", sessionInfo, true)%>
</div>
