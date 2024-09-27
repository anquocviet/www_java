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
    <style>
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
    <script>
        // Fetch product data from the server and populate the product table
        document.addEventListener("DOMContentLoaded", function () {
            fetch('api/products')
                .then(response => response.json())
                .then(data => {
                    const tableBody = document.getElementById('productTableBody');
                    data.forEach(product => {
                        const row = document.createElement('tr');
                        row.innerHTML = `
                            <td>\${product.name}</td>
                            <td>\${product.description}</td>
                            <td>\${product.unit}</td>
                            <td>\${product.manufacturer}</td>
                            <td>\${product.prices[0].price}</td>
                            <td><input type="checkbox" name="productId" value="\${product.id}" /></td>
                            `;
                        tableBody.appendChild(row);
                    });
                })
                .catch(error => console.error('Error fetching product data:', error));
        });
    </script>
</head>
<body>
<form id="paymentForm" action="controller">
    <input type="hidden" name="action" value="buy">
    <div style="display: flex; justify-content: space-between; align-items: center">
        <h1>Product</h1>
        <button style="height: 30px" type="submit">Buy</button>
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
</body>
</html>