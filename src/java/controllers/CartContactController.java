/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import dal.implement.CartDAOImpl;
import dal.implement.OrderDAOImpl;
import dal.implement.OrderDetailDAOImpl;
import dal.implement.TableDAOImpl;
import dal.interfaces.ICartDAO;
import dal.interfaces.IOrderDAO;
import dal.interfaces.IOrderDetailDAO;
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
import models.Order;
import models.User;

public class CartContactController extends HttpServlet {

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
        ICartDAO cd = new CartDAOImpl();
        IOrderDAO od = new OrderDAOImpl();
        IOrderDetailDAO odd = new OrderDetailDAOImpl();
        HttpSession session = request.getSession();
        User u = (User) session.getAttribute("user");
        if (u == null) {
            response.sendRedirect(request.getContextPath() + "/login");
        } else {
            int user_id = u.getUserId();
            String is_takeaway = request.getParameter("is_takeaway");

            // ăn tại quán
            if (is_takeaway != null && is_takeaway.equals("false")) {
                String table_str = request.getParameter("table_id");
                int table_id = Integer.parseInt(table_str);
                List<Cart> listCart = cd.getCartInfoByUserId(user_id, is_takeaway);

                int sum = 0;
                for (Cart c : listCart) {
                    sum += c.getTotal_cost();
                }

                Order existedOrder = od.getOrderByTalbeAndUser(user_id, table_id);
                if (existedOrder != null) {
                    odd.addCartToOrder(listCart, existedOrder.getId());
                } else {
                    int order_id = od.createNewOrder(sum, listCart.get(0).getCustomer().getFullName(), listCart.get(0).getCustomer().getEmail(),
                            listCart.get(0).getCustomer().getPhone(), listCart.get(0).getCustomer().getAddress(), user_id, "", "cod", listCart.get(0).getTable_id());
                    odd.addCartToOrder(listCart, order_id);
                }
                cd.deleteCartByUserId(user_id, listCart.get(0).getTable_id());
                int totalItem = cd.getTotalItemInCart(user_id);
                session.setAttribute("totalItem", totalItem);
                ITableDAO it = new TableDAOImpl();
                Cart o = cd.checkCart(user_id);
                if (o != null) {
                    DiningTable table = o.getTable();
                    session.setAttribute("tb", table);
                } else {
                    List<DiningTable> tables = it.getAllTable();
                    session.setAttribute("tables", tables);
                }
                session.setAttribute("noti", "Gọi món thành công, hãy chờ chúng tôi xác nhận");
                response.sendRedirect(request.getContextPath() + "/dishes");
            } else {
                List<Cart> listCart = cd.getCartInfoByUserId(user_id, is_takeaway);
                if (!listCart.isEmpty()) {
                    int sum = 0;
                    for (Cart o : listCart) {
                        sum += o.getTotal_cost();
                    }
                    session.setAttribute("take_away", 1);
                    session.setAttribute("sum", sum);
                    session.setAttribute("historyUrl", "cartinfo");
                    session.setAttribute("listCart", listCart);
                    request.getRequestDispatcher("./views/cartcontact.jsp").forward(request, response);
                } else {
                    response.sendRedirect(request.getContextPath() + "/cartinfo");
                }
            }

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
