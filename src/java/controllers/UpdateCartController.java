/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import dal.implement.CartDAOImpl;
import dal.implement.DishDAOImpl;
import dal.interfaces.ICartDAO;
import dal.interfaces.IDishDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.Cart;
import models.Dish;
import models.User;

public class UpdateCartController extends HttpServlet {

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
        String productId_raw = request.getParameter("product_id");
        int product_id = Integer.parseInt(productId_raw);
        String quantity_raw = request.getParameter("quantity");
        System.out.println("quantity: " + quantity_raw);
        int quantity = Integer.parseInt(quantity_raw);
        HttpSession session = request.getSession();
        User u = (User) session.getAttribute("user");
        if (u == null) {
            response.sendRedirect(request.getContextPath() + "/login");
        } else {
            IDishDAO dd = new DishDAOImpl();
            Dish d = dd.getDishByID(product_id);
            int total = 0;
            String table_raw = request.getParameter("table_id");
            int table_id = Integer.parseInt(table_raw);

            ICartDAO cd = new CartDAOImpl();
            Cart c = cd.getCartByUserIdAndDishId(u.getUserId(), product_id);
            if (c != null) {
                int sum = quantity * d.getPrice();
                cd.updateQuantityProductInCart(product_id, table_id, quantity, sum, u.getUserId());
            }
            int totalItem = cd.getTotalItemInCart(u.getUserId());
            session.setAttribute("totalItem", totalItem);
            response.sendRedirect(request.getContextPath() + "/cartinfo?is_takeaway=" + (table_id == -1 ? "true" : "false"));
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
        processRequest(request, response);
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
