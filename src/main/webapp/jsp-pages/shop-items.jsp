<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:forEach items="${products}" var="productObject">
    <tr id="${productObject.vendorCode}" >
        <td><input type='checkbox'><span>Product: </span><c:out value="${productObject.name}"/></td>
        <td><span>Code: </span><c:out value="${productObject.vendorCode}"/></td>
        <td><span>Price: </span><c:out value="${productObject.price}"/></td>
    </tr>
</c:forEach>