<%@page import="bean.MemoRegister"%>
<%@page contentType="text/html;charset=utf-8" %>
<%
  bean.UserRegister user = (bean.UserRegister)session.getAttribute("user");
  bean.MemoRegister memo = (bean.MemoRegister)request.getAttribute("memo");
  boolean isEdit = (memo != null);
%>
<html>
  <head>
    <title>My_Notes</title>
    <link rel="stylesheet" href="/My_Notes/MemoPage.css">
  </head>
<body>
<form action="<%=request.getContextPath() %>/<%= isEdit ? "MemoUpdate" : "MemoRegister" %>" method="post">
<div class="all_content">
  <div class="title_content">
    My Notes
  </div>
  <div class="user_content">
    <% if (user != null && user.getName() != null) { %>
      ユーザー名：<strong><%= user.getName() %></strong><br/>
    <% } else { %>
      ユーザー名が登録されていません。<br/>
    <% } %>
  </div>

  <% if (isEdit) { %>
    <!-- 編集モードならメモIDをhiddenで渡す -->
    <input type="hidden" name="id" value="<%= memo.getId() %>">
  <% } %>

  <!-- 件名 -->
  <input class="text_content" type="text" name="title" placeholder="件名"
         value="<%= isEdit ? memo.getTitle() : "" %>" /><br/>

  <!-- 内容 -->
  <textarea class="message_content" name="message" placeholder="内容"><%= isEdit ? memo.getMessage() : "" %></textarea><br/>

  <!-- 登録 or 更新 -->
  <div class="register_button_container">
    <button type="submit" class="register_button"><%= isEdit ? "更新" : "登録" %></button>
  </div>
</div>
</form>
</body>
</html>