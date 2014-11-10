<!-- Tell the JSP Page that please do not ignore Expression Language -->
<%@ page isELIgnored="false"%>
<html>
<body>
<!-- CommandName 'user' will bind the form parameter to the 'User' bean -->
<form action="/mvc/" method="post">
    <label path="name">Name : </label> ${user.name} <br/>
    <input type="submit" title="Return To Input Form"  name="Return To Input Form">
</form>
</body>
</html>