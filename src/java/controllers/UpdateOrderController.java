/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import dal.implement.OrderDAOImpl;
import dal.interfaces.IOrderDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.Order;

@WebServlet(name = "UpdateOrderController", urlPatterns = {"/update-order"})
public class UpdateOrderController extends HttpServlet {

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
            out.println("<title>Servlet UpdateOrder</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateOrder at " + request.getContextPath() + "</h1>");
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
        String status = request.getParameter("status");
        int order_id = Integer.parseInt(request.getParameter("order_id"));
        IOrderDAO od = new OrderDAOImpl();

        Boolean b = od.handleOrder(status, order_id);
        HttpSession ses = request.getSession();
        int take_away = (int) ses.getAttribute("take_away");
        if (b) {
            ses.setAttribute("noti", "Xử lý đơn hàng " + order_id + " thành công.");
        } else {
            ses.setAttribute("noti", "Xử lý đơn hàng " + order_id + " thất bại. hãy kiểm tra lại");
        }
        response.sendRedirect(request.getContextPath() + "/manageorder?take_away=" + take_away);
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
        String status = request.getParameter("status");
        int order_id = Integer.parseInt(request.getParameter("order_id"));
        IOrderDAO od = new OrderDAOImpl();
        HttpSession ses = request.getSession();
        String url = (String) ses.getAttribute("historyUrl");
        Boolean b = od.handleOrder(status, order_id);
        if (b) {
            ses.setAttribute("noti", "Xử lý đơn hàng " + order_id + " thành công.");
        } else {
            ses.setAttribute("noti", "Xử lý đơn hàng " + order_id + " thất bại. hãy kiểm tra lại");
        }
        response.sendRedirect(url);
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
