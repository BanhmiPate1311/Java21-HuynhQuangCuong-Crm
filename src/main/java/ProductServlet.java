import model.Products;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "ProductServlet", urlPatterns = { "/product" })
public class ProductServlet extends HttpServlet {
    ArrayList<Products> productList= new ArrayList<>();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("product.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String product_name = req.getParameter("product_name");
        String strQuantity = req.getParameter("quantity");
        System.out.println("Kiểm tra " + strQuantity);
        String strPrice = req.getParameter("price");
        int quantity = 0;
        double price = 0;
        // trim() : loại bỏ khoảng trắng
        if(!strQuantity.isEmpty()){
            quantity = Integer.parseInt(strQuantity);
        }
        if(!strPrice.isEmpty()){
            price = Integer.parseInt(strPrice);
        }
        Products products = new Products();
        products.setProduct_name(product_name);
        products.setQuantity(quantity);
        products.setPrice(price);

        productList.add(products);
        req.setAttribute("productList",productList);
        req.getRequestDispatcher("product.jsp").forward(req,resp);
    }
}
