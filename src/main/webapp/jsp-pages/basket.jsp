<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Basket</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script type="application/javascript" src="../js/basket.js"></script>

    <link href="../styles/catalog-style.css" rel="stylesheet">
</head>
<body>

<div class="content-container">
    <h1 class="goods-header">Basket</h1>

    <table>
        <c:forEach items="${productsSelected}" var="product">
            <tr id="${product.vendorCode}"
                    <c:if test="${product.name != ''}">
                        class="valid-product"
                    </c:if>
                    <c:if test="${product.name == ''}">
                        class="invalid-product"
                    </c:if>
            >
                <td><span>Product: </span><c:out value="${product.name}"/></td>
                <td><span>Code: </span><c:out value="${product.vendorCode}"/></td>
                <td><span>Price: </span>
                    <c:if test="${product.price > 0}">
                        <c:out value="${product.price}"/>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
    </table>

    <div class="button-cont" align="center">
        <button id="button-buy" class="button-body" onclick="buyProducts()">Buy</button>
    </div>

</div>

</body>
</html>
