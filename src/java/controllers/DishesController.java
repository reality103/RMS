/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import dal.implement.CategoryDAOImpl;
import dal.implement.DishDAOImpl;
import dal.interfaces.IDishDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import models.Category;
import models.Dish;

public class DishesController extends HttpServlet {

    private final int record_per_page = 8;

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
            HttpSession session = request.getSession();
            IDishDAO pd = new DishDAOImpl();
            // get all category
            List<Category> listcp = new CategoryDAOImpl().getCategoryList();

            // get index of page
            String indexPage = request.getParameter("index");
            if (indexPage == null) {
                indexPage = "1";
            }
            int index = Integer.parseInt(indexPage);

            // Set category
            int cp_id = 0;
            String categoryId = "!= -1";
            String strCategoryId = request.getParameter("cid");
            if (strCategoryId != null) {
                cp_id = Integer.parseInt(strCategoryId);
                categoryId = "= " + strCategoryId;
            }

            // Set key for search 
            String searchKey = "";
            String strSearchKey = request.getParameter("key");
            if (strSearchKey != null) {
                searchKey = strSearchKey.trim();
            }

            // Set sort 
            String value = "price";
            String type = "";
            String strType = request.getParameter("type");
            String strValue = request.getParameter("value");
            if (strType != null) {
                type = strType;
            }
            if (strValue != null) {
                value = strValue;
            }

            // System.out.println(searchKey);
            // filter
            List<Dish> listOfPage = pd.filterPaging(index, record_per_page, searchKey, categoryId, type, value);
            int count = pd.countDishByCondition(searchKey, categoryId);

            //paging
            int endPage = (count / record_per_page);
            if (count % record_per_page != 0) {
                endPage++;
            }
            // Set param request to jsp page    
            session.setAttribute("list", listOfPage);
            session.setAttribute("listcp", listcp);
            session.setAttribute("historyUrl", "dishes");
            String history = "dishes?index=" + indexPage;
            if (strCategoryId != null) {
                //set cp_id
                history = history + "&cid=" + strCategoryId;
                request.setAttribute("historyCategoryId", "&cid=" + strCategoryId);
                request.setAttribute("cid", cp_id);
            }
            if (strSearchKey != null) {
                history = history + "&key=" + strSearchKey;
                request.setAttribute("historyKey", "&key=" + strSearchKey);
            }
            request.setAttribute("key", searchKey);
            //set value
            if (strValue != null) {
                history = history + "&value=" + strValue;
                request.setAttribute("historyValue", "&value=" + strValue);
            }
            request.setAttribute("value", value);

            if (strType != null) {
                history = history + "&type=" + strType;
                request.setAttribute("historyType", "&type=" + strType);
            }
            request.setAttribute("type", type);

            request.setAttribute("active1", 1);
            session.setAttribute("historyUrl", history);
            request.setAttribute("cid", cp_id);

            request.setAttribute("endPage", endPage);
            request.setAttribute("listOfPage", listOfPage);
            request.setAttribute("pageIndex", index);

            request.getRequestDispatcher("./view/dishes.jsp").forward(request, response);
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
        HttpSession session = request.getSession();
        IDishDAO pd = new DishDAOImpl();
        // get all category
        List<Category> listcp = new CategoryDAOImpl().getCategoryList();

        // get index of page
        String indexPage = request.getParameter("index");
        if (indexPage == null) {
            indexPage = "1";
        }
        int index = Integer.parseInt(indexPage);

        // Set category
        int cp_id = 0;
        String categoryId = "!= -1";
        String strCategoryId = request.getParameter("cid");
        if (strCategoryId != null && !"0".equals(strCategoryId)) {
            cp_id = Integer.parseInt(strCategoryId);
            categoryId = "= " + strCategoryId;
        }

        // Set key for search 
        String searchKey = "";
        String strSearchKey = request.getParameter("key");
        if (strSearchKey != null) {
            searchKey = strSearchKey.trim();
        }

        // Set sort 
        String value = "price";
        String type = "";
        String strType = request.getParameter("type");
        String strValue = request.getParameter("value");
        if (strType != null) {
            type = strType;
        }
        if (strValue != null) {
            value = strValue;
        }

        // filter
        List<Dish> listOfPage = pd.filterPaging(index, record_per_page, searchKey, categoryId, type, value);
        int count = pd.countDishByCondition(searchKey, categoryId);

        //paging
        int endPage = (count / record_per_page);
        if (count % record_per_page != 0) {
            endPage++;
        }
        // Set param request to jsp page    
        session.setAttribute("list", listOfPage);
        session.setAttribute("listcp", listcp);
        session.setAttribute("historyUrl", "dishes");
        String history = "dishes?index=" + indexPage;
        if (strCategoryId != null) {
            //set cp_id
            history = history + "&cid=" + strCategoryId;
            request.setAttribute("historyCategoryId", "&cid=" + strCategoryId);
            request.setAttribute("cid", cp_id);
        }
        if (strSearchKey != null) {
            history = history + "&key=" + strSearchKey;
            request.setAttribute("historyKey", "&key=" + strSearchKey);
        }
        request.setAttribute("key", searchKey);
        //set value
        if (strValue != null) {
            history = history + "&value=" + strValue;
            request.setAttribute("historyValue", "&value=" + strValue);
        }
        request.setAttribute("value", value);

        if (strType != null) {
            history = history + "&type=" + strType;
            request.setAttribute("historyType", "&type=" + strType);
        }
        request.setAttribute("type", type);
        session.setAttribute("historyUrl", history);
        request.setAttribute("cid", cp_id);

        request.setAttribute("endPage", endPage);
        request.setAttribute("pageIndex", index);

        session.setAttribute("historyUrl", "dishes");
        String noti = request.getParameter("notification");
        if (noti != null) {
            request.setAttribute("notification", noti);
        }
        request.setAttribute("active", 1);
        request.getRequestDispatcher("/views/dishes.jsp").forward(request, response);
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
