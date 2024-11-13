/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import dal.implement.CategoryDAOImpl;
import dal.implement.DishDAOImpl;
import dal.implement.TableDAOImpl;
import dal.interfaces.ICategoryDAO;
import dal.interfaces.IDishDAO;
import dal.interfaces.ITableDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import models.Category;
import models.DiningTable;
import models.Dish;
import org.apache.commons.io.IOUtils;

public class ManageTableController extends HttpServlet {

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
            out.println("<title>Servlet ManageTableController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ManageTableController at " + request.getContextPath() + "</h1>");
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
        ITableDAO td = new TableDAOImpl();
        List<DiningTable> tables = td.getAllTable();

        request.setAttribute("listOfPage", tables);
        request.setAttribute("p", 2);
        request.getRequestDispatcher("./views/manage-table.jsp").forward(request, response);
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
        ITableDAO dd = new TableDAOImpl();
        String action = request.getParameter("action");
        String name = request.getParameter("tableName");
        String capacity = request.getParameter("capacity");

        DiningTable d = new DiningTable();
        d.setName(name);
        d.setCapacity(capacity);

        if (action.equals("edit")) {
            String id = request.getParameter("tableId");
            d.setId(Integer.parseInt(id));
            dd.updateTable(d);
        } else {
            int newdishId = dd.createTable(d);
        }
        response.sendRedirect("manage-table");
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
