<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="inputGroup">
    <label class="subtitle" for="tag">태그</label>
    <input class="textContent input" type="text" name="tag" id="tag" 
	value="<%= request.getParameter("tag") %>" disabled>
</div>