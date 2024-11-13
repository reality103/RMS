/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import dal.implement.DishDAOImpl;
import dal.implement.FeedbackDAOImpl;
import dal.implement.OrderDAOImpl;
import dal.interfaces.IDishDAO;
import dal.interfaces.IFeedbackDAO;
import dal.interfaces.IOrderDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import models.Dish;
import models.Feedback;
import models.Order;
import models.User;

public class DishDetailsController extends HttpServlet {

    private final int record_per_page = 3;

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
        IFeedbackDAO fd = new FeedbackDAOImpl();
        HttpSession session = request.getSession();

        IDishDAO pd = new DishDAOImpl();
        IOrderDAO od = new OrderDAOImpl();
        String productidStr = request.getParameter("id");
        String categoryidStr = request.getParameter("cid");
        int product_id = Integer.parseInt(productidStr);
        int category_id = Integer.parseInt(categoryidStr);
        User user = (User) session.getAttribute("user");

        // get index of page
        String indexPage = request.getParameter("index");
        if (indexPage == null) {
            indexPage = "1";
        }
        int index = Integer.parseInt(indexPage);

        // get total number of feedback
        List<Feedback> feedbackList = fd.getAllFeedbackByProductId(product_id);
        int endPage = (feedbackList.size() / record_per_page);
        if (feedbackList.size() % record_per_page != 0) {
            endPage++;
        }
        List<Feedback> feedbacks = fd.pagingFeedback(product_id, index, record_per_page);
        Order accept = null;
        if (user != null) {
            accept = od.checkDishOrderByUser(user.getUserId(), product_id);
        }
        Dish p = pd.getDishByID(product_id);
        List<Dish> relatedProducts = pd.getRelatedDish(product_id, category_id);

        String history = "dish-details?id=" + productidStr + "&cid=" + categoryidStr + "&index=" + indexPage;
        session.setAttribute("historyUrl", history);
        request.setAttribute("productFeedbacks", feedbacks);
        request.setAttribute("relatedProducts", relatedProducts);
        request.setAttribute("product", p);

        request.setAttribute("accept", accept);
        request.setAttribute("endPage", endPage);
        request.setAttribute("pageIndex", index);
        request.setAttribute("totalFeedback", feedbackList.size());
        request.getRequestDispatcher("/views/dish-details.jsp").forward(request, response);
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
