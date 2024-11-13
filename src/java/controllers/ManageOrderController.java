/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import dal.implement.OrderDAOImpl;
import dal.implement.TableDAOImpl;
import dal.interfaces.IOrderDAO;
import dal.interfaces.ITableDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import models.DiningTable;
import models.Order;

public class ManageOrderController extends HttpServlet {

    private final int RECORD_PER_PAGE = 12;

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
            out.println("<title>Servlet ManageOrderController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ManageOrderController at " + request.getContextPath() + "</h1>");
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
        String p = request.getParameter("p");
        request.setAttribute("p", p);
        IOrderDAO od = new OrderDAOImpl();
        int take_away = 1;
        String table_str = request.getParameter("take_away");
        if (table_str != null) {
            take_away = Integer.parseInt(table_str);
        }
        HttpSession session = request.getSession();

        if (take_away == 1) {
            List<Order> orders = od.getAllOrder();
            String indexPageStr = request.getParameter("index");
            if (indexPageStr == null) {
                indexPageStr = "1";
            }
            int index = Integer.parseInt(indexPageStr);

            String statusStr = " != 'completed'";
            String status = "%%";
            String statusOrderStr = request.getParameter("status");
            if (statusOrderStr != null && !statusOrderStr.equals("null")) {
                statusStr = "like " + "'%" + statusOrderStr + "%'";
                status = statusOrderStr;
            }

            String key = "";
            String keyStr = request.getParameter("key");
            if (keyStr != null) {
                key = keyStr.trim();
            }

            // using banana approach
            List<Order> listOfPage = od.pagingOrder(index, RECORD_PER_PAGE, key, statusStr, take_away);
            int count = od.countOrderByStatus(key, statusStr, take_away);
            int endPage = count / RECORD_PER_PAGE;
            if ((count % RECORD_PER_PAGE) != 0) {
                endPage++;
            }
            session.setAttribute("orderList", orders);
            String history = "manageorder?index=" + index + "&take_away=" + take_away;
            if (statusOrderStr != null) {
                history = history + "&status=" + statusOrderStr;
            }
            request.setAttribute("p", 3);

            session.setAttribute("historyUrl", history);
            request.setAttribute("status", status);
            session.setAttribute("take_away", take_away);
            request.setAttribute("key", key);
            String historyKey = "key=" + key;
            request.setAttribute("historyKey", historyKey);
            request.setAttribute("endPage", endPage);
            request.setAttribute("listOfPage", listOfPage);
            request.setAttribute("pageIndex", index);
            //request.setAttribute("listByCond", listByCondition);
            request.getRequestDispatcher("./views/manage-order-list.jsp").forward(request, response);
        } else {
            session.setAttribute("historyUrl", "manageorder?take_away=0");
            session.setAttribute("take_away", take_away);
            ITableDAO td = new TableDAOImpl();
            List<DiningTable> availables = td.getAvalableTables();
            List<DiningTable> list = td.getAllTable();
            List<Order> table_orders = od.getTableOrder();
            List<Integer> bookedTableIds = new ArrayList<>();
            for (Order order : table_orders) {
                bookedTableIds.add(order.getTable_id());
            }
            request.setAttribute("p", 4);
            request.setAttribute("bookedTableIds", bookedTableIds);
            request.setAttribute("table_orders", table_orders);
            request.setAttribute("tables", list);
            request.setAttribute("availables", availables);
            request.getRequestDispatcher("./views/manage-order-table.jsp").forward(request, response);
        }
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
