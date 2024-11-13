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
import models.User;

public class OrdersController extends HttpServlet {

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
        HttpSession session = request.getSession();
        String fullname = request.getParameter("fullName");
        String phone = request.getParameter("phone").trim();
        String email = request.getParameter("email").trim();
        String address = request.getParameter("address").trim();
        String table_id_raw = request.getParameter("table_id").trim();
        String note = request.getParameter("note").trim();
        String paymt = request.getParameter("paym-method");
        ICartDAO cd = new CartDAOImpl();
        IOrderDAO od = new OrderDAOImpl();
        IOrderDetailDAO odd = new OrderDetailDAOImpl();
        int table = Integer.parseInt(table_id_raw);

        List<Cart> listCart = (List<Cart>) session.getAttribute("listCart");
        if (listCart == null || listCart.isEmpty()) { // trường hợp click vào history browser
            //session.setAttribute("notification", "Giỏ hàng rỗng");
            response.sendRedirect(request.getContextPath() + "/dishes");
        } else {
            int sum = (int) session.getAttribute("sum");
            User u = (User) session.getAttribute("user");
            if (u == null) {
                response.sendRedirect(request.getContextPath() + "/login");
            } else {
                int user_id = u.getUserId();
                if (paymt.equals("vnpay")) {
                    session.setAttribute("note", note);
                    response.sendRedirect(request.getContextPath() + "/checkout?flag=0");
                } else {
                    int order_id = od.createNewOrder(sum, fullname, email,
                            phone, address, user_id, note, "cod", table);
                    odd.addCartToOrder(listCart, order_id);
                    cd.deleteCartByUserId(user_id, table);
                    ITableDAO it = new TableDAOImpl();
                    Cart o = cd.checkCart(user_id);
                    if (o != null) {
                        DiningTable t = o.getTable();
                        session.setAttribute("tb", t);
                    } else {
                        List<DiningTable> tables = it.getAllTable();
                        session.setAttribute("tables", tables);
                    }
                    int totalItem = cd.getTotalItemInCart(user_id);
                    session.setAttribute("totalItem", totalItem);
                    session.setAttribute("noti", "Đặt hàng thành công, hãy chờ chúng tôi xác nhận");
                    response.sendRedirect(request.getContextPath() + "/dishes");
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
