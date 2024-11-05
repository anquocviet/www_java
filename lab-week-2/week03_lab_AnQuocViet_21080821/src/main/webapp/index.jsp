<%--
  Created by IntelliJ IDEA.
  User: vie
  Date: 20/9/24
  Time: 21:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Product Details</title>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
</head>

<style>
    body {
        font-family: Arial, sans-serif;
        margin: 0;
        padding: 0;
    }


    table {
        width: 100%;
        border-collapse: collapse;
    }

    th, td {
        border: 1px solid #ddd;
        padding: 8px;
    }

    th {
        background-color: #f2f2f2;
        text-align: left;
    }

    select {
        width: 100%;
        padding: 12px 20px;
        margin: 8px 0;
        display: inline-block;
        border: 1px solid #ccc;
        border-radius: 4px;
        box-sizing: border-box;
    }
</style>
<body>
<% String message = (String) session.getAttribute("message"); %>
<% if (message != null) { %>
<script>
    alert("<%= message %>");
</script>
<% session.removeAttribute("message"); %>
<% } %>
<form action="controller" method="get">
    <input type="hidden" name="action" value="buy">
    <div style="display: flex; justify-content: space-between; align-items: center">
        <h1>Product</h1>
        <button class="submit" style="height: 30px" type="submit">Buy</button>
    </div>
    <table>
        <thead>
        <tr>
            <th>Name</th>
            <th>Description</th>
            <th>Unit</th>
            <th>Manufacturer</th>
            <th>Price</th>
            <th>Buy</th>
        </tr>
        </thead>
        <tbody id="productTableBody">
        <!-- Product rows will be inserted here by JavaScript -->
        </tbody>
    </table>
</form>
<script>
    $(document).ready(function () {
        $.getJSON('api/products', function (data) {
            const tableBody = $('#productTableBody');
            data.forEach(product => {
                const row = `
                    <tr>
                        <td>\${product.name}</td>
                        <td>\${product.description}</td>
                        <td>\${product.unit}</td>
                        <td>\${product.manufacturer}</td>
                        <td>\${product.price.price}</td>
                        <td><input type="checkbox" name="productId" value="\${product.id}" /></td>
                    </tr>
                `;
                tableBody.append(row);
            });
        }).fail(function (jqxhr, textStatus, error) {
            console.error('Error fetching product data:', textStatus, error);
        });
    });
    $('button[type="submit"]').click(function () {
        const selectedProductIds = $('input[name="productId"]:checked').map(function () {
            return $(this).val();
        }).get();
        if (selectedProductIds.length === 0) {
            alert('Please select at least one product to buy.');
            return false;
        }
    });
</script>
</body>
</html>