<%@page contentType="text/html;charset=utf-8" %>
<%@page import = "java.util.List, bean.MemoRegister" %>
<%
bean.UserRegister user = (bean.UserRegister)session.getAttribute("user"); 
List<MemoRegister> memoList = (List<MemoRegister>) request.getAttribute("memoList");
String error = (String)request.getAttribute("error");
response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
response.setHeader("Pragma", "no-cache");
response.setDateHeader("Expires", 0);
if (user == null) {
    response.sendRedirect("LoginPage.html");
    return;
}
%>

<html>
  <head>
    <title>My_Notes</title>
    <link rel="stylesheet" href="/My_Notes/UserPage.css"> 
  </head>
<body>
<div class="all_content">
 <div class="title_content">
   My Notes
 </div>
  <div class="user_content">
   <%if (user != null && user.getName() != null ) {%>
     ユーザー名：<strong><%= user.getName() %></strong>
   <% } else { %>
     ユーザー名が登録されていません。<br/>
   <% } %>
   </div>
   <div class="list_content">
  <% if (user != null && memoList != null && !memoList.isEmpty()) { %>
    <ol class="memo_list">
      <% for (MemoRegister memo : memoList) { %>
        <li class="memo_item">
          <form action="<%= request.getContextPath() %>/MemoEdit?id=<%= memo.getId() %>" method="post" class="memo_form">
            <button type="submit" class="heading">
                <strong><%= memo.getTitle() %></strong>
                <%= memo.getCreateAt() %>
            </button>
          </form>
          <form action="<%= request.getContextPath() %>/MemoDelete" method="post" class="delete_form">
            <input type="hidden" name="id" value="<%= memo.getId() %>">
            <button type="submit"class="delete_button">削除</button>
          </form>
        </li>
      <% } %>
    </ol>
  <% } else { %>
    <p>メモを登録してあなただけのメモを作ろう！</p>
  <% } %>
</div>

      <div class="button_area">
        <a href="/My_Notes/MemoPage.jsp" class="new_register_button">
            新規作成
        </a>
        <form action="<%= request.getContextPath() %>/Logout" method="post" style="margin: 0;">
           <button type="submit" class="logout_button">ログアウト</button>
       </form>
    </div>
</div>
<script src="button.js"></script>
</body>
</html>