<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Basket</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script type="application/javascript" src="/buildshop/js/basket.js"></script>

    <link href="/buildshop/styles/catalog-style.css" rel="stylesheet">
</head>
<body>

<c:set var="productsArray" value="${products}"/>

<div class="contentContainer">
    <h1 class="goods-header">Basket</h1>

    <table>
        <c:forEach items="${products}" var="productLine">
            <tr id="${productLine[1]}"
                    <c:if test="${productLine[0] != ''}">
                        class="valid-product"
                    </c:if>
                    <c:if test="${productLine[0] == ''}">
                        class="invalid-product"
                    </c:if>
            >
                <td><span>Product: </span><c:out value="${productLine[0]}"/></td>
                <td><span>Code: </span><c:out value="${productLine[1]}"/></td>
                <td><span>Price: </span><c:out value="${productLine[2]}"/></td>
            </tr>
        </c:forEach>
    </table>

    <div class="button-cont" align="center">
        <button id="button-buy" class="button-body" onclick="buyProducts()">Buy</button>
    </div>

</div>

</body>
</html>
