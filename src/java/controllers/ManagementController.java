/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import dal.implement.CategoryDAOImpl;
import dal.implement.DateDAOImpl;
import dal.implement.DishDAOImpl;
import dal.implement.OrderDAOImpl;
import dal.implement.UserDAOImpl;
import dal.interfaces.ICategoryDAO;
import dal.interfaces.IDateDAO;
import dal.interfaces.IDishDAO;
import dal.interfaces.IOrderDAO;
import dal.interfaces.IUserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import models.Chart;
import models.DateObject;

public class ManagementController extends HttpServlet {

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
            out.println("<title>Servlet ManagementController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ManagementController at " + request.getContextPath() + "</h1>");
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
        IDishDAO pd = new DishDAOImpl();
        ICategoryDAO cp = new CategoryDAOImpl();
        IUserDAO ud = new UserDAOImpl();
        IOrderDAO od = new OrderDAOImpl();
        IDateDAO dd = new DateDAOImpl();

        int countCustomer = ud.CountCustomer();
        int countProduct = pd.getTotalProduct();

        DateObject date = dd.get7day();
        String start = date.getStart().toString();
        String end = date.getEnd().toString();
        String start_raw = request.getParameter("start");
        String end_raw = request.getParameter("end");
        if (start_raw != null) {
            start = start_raw;
            end = end_raw;
        }

        int numberOfDay = dd.countDayByStartEnd(start, end);

        // set chart revenue
        List<Chart> listChartRevenueArea = od.getChartRevenueArea(start, numberOfDay);
        int maxListChartRevenueArea = -1;
        for (Chart o : listChartRevenueArea) {
            if (o.getValue() > maxListChartRevenueArea) {
                maxListChartRevenueArea = o.getValue();
            }
        }
        maxListChartRevenueArea = (maxListChartRevenueArea / 1000000 + 2) * 1000000;

        // set chart customer
//        List<Chart> listChartCustomer = ud.getChartCustomerArea(start, numberOfDay);
//        int maxListChartCustomerArea = -1;
//        for (Chart o : listChartCustomer) {
//            if (o.getValue() > maxListChartCustomerArea) {
//                maxListChartCustomerArea = o.getValue();
//            }
//        }
//        maxListChartCustomerArea = (maxListChartCustomerArea / 10 + 1) * 10;
        // set char Order 
        List<String> listStatusOrder = new ArrayList();
        listStatusOrder.add("pending");
        listStatusOrder.add("processing");
        listStatusOrder.add("completed");

        int totalOrderByStatus1 = od.gettotalOrderByStatus("pending", start, numberOfDay);
        int totalOrderByStatus2 = od.gettotalOrderByStatus("processing", start, numberOfDay);
        int totalOrderByStatus3 = od.gettotalOrderByStatus("completed", start, numberOfDay);
        request.setAttribute("listStatusOrder", listStatusOrder);
        request.setAttribute("totalOrder1", totalOrderByStatus1);
        request.setAttribute("totalOrder2", totalOrderByStatus2);
        request.setAttribute("totalOrder3", totalOrderByStatus3);
        request.setAttribute("noCustomer", countCustomer);
        request.setAttribute("noProduct", countProduct);

        request.setAttribute("listChartRevenueArea", listChartRevenueArea);
        request.setAttribute("maxListChartRevenueArea", maxListChartRevenueArea);

        //request.setAttribute("listChartCustomer", listChartCustomer);
        //request.setAttribute("maxListChartCustomerArea", maxListChartCustomerArea);
        request.setAttribute("start", start);
        request.setAttribute("end", end);
        request.setAttribute("p", 0);
        request.getRequestDispatcher("./views/dashboard.jsp").forward(request, response);
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
