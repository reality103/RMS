/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import dal.implement.CartDAOImpl;
import dal.implement.DishDAOImpl;
import dal.implement.TableDAOImpl;
import dal.interfaces.ICartDAO;
import dal.interfaces.IDishDAO;
import dal.interfaces.ITableDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import models.Cart;
import models.DiningTable;
import models.Dish;
import models.User;

public class CartsController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet CartsController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CartsController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ICartDAO cd = new CartDAOImpl();

        String productId_raw = request.getParameter("product_id");
        int product_id = Integer.parseInt(productId_raw);
        HttpSession session = request.getSession();
        User u = (User) session.getAttribute("user");

        if (u == null) {
            response.sendRedirect(request.getContextPath() + "/login");
        } else {
            int user_id = u.getUserId();
            Cart existCart = cd.checkCart(user_id);
            int table = -1;
            if (existCart != null) {
                table = existCart.getTable().getId();
                session.setAttribute("tb", existCart.getTable());
            } else {
                String table_raw = request.getParameter("table");
                if (table_raw != null) {
                    table = Integer.parseInt(table_raw);
                }
                ITableDAO it = new TableDAOImpl();
                if (table != -1) {
                    DiningTable t = it.getTableById(table);
                    session.setAttribute("tb", t);
                } else {
                    List<DiningTable> tables = it.getAllTable();
                    session.setAttribute("tables", tables);
                }
            }

            Cart c = cd.checkCart(user_id, product_id, table);

            int quantity = 1;
            String quantity_raw = request.getParameter("quantity");

            if (quantity_raw != null) {
                quantity = Integer.parseInt(quantity_raw);
            }

            int total_cost;
            IDishDAO pd = new DishDAOImpl();
            Dish p = pd.getDishByID(product_id);
            if (c == null) {
                int price = p.getPrice();
                if (p.getPrice() != 0) {
                    price = p.getPrice();
                }
                total_cost = quantity * price;
                cd.addToCart(product_id, table, quantity, total_cost, user_id);
            } else {
                quantity += c.getQuantity();
                total_cost = c.getTotal_cost() + quantity * p.getPrice();
                cd.updateQuantityProductInCart(product_id, table, quantity, total_cost, user_id);
            }
            int totalItem = cd.getTotalItemInCart(u.getUserId());

            session.setAttribute("totalItem", totalItem);
            String historyUrl = (String) session.getAttribute("historyUrl");
            session.setAttribute("noti", "Thêm thành công vào giỏ hàng.");
            response.sendRedirect(historyUrl);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
