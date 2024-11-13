/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import dal.implement.CategoryDAOImpl;
import dal.implement.DishDAOImpl;
import dal.implement.UserDAOImpl;
import dal.interfaces.ICategoryDAO;
import dal.interfaces.IDishDAO;
import dal.interfaces.IUserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import models.Category;
import models.Dish;
import models.User;
import jakarta.servlet.http.Part;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.Base64;

import org.apache.commons.io.IOUtils;

@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2 MB
        maxFileSize = 1024 * 1024 * 10, // 10 MB
        maxRequestSize = 1024 * 1024 * 50 // 50 MB
)

public class ManageDishController extends HttpServlet {

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
            out.println("<title>Servlet ManageDishController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ManageDishController at " + request.getContextPath() + "</h1>");
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
        IDishDAO ud = new DishDAOImpl();
        ICategoryDAO ic = new CategoryDAOImpl();
        List<Category> categories = ic.getCategoryList();
        List<Dish> list = ud.getAll();
        request.setAttribute("listOfPage", list);
        request.setAttribute("categories", categories);

        request.setAttribute("p", 1);
        request.getRequestDispatcher("./views/manage-dish.jsp").forward(request, response);
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
        IDishDAO dd = new DishDAOImpl();
        String action = request.getParameter("action");
        String name = request.getParameter("dishname");
        String price = request.getParameter("price");
        String brief = request.getParameter("brief_info");
        String description = request.getParameter("description");
        String category = request.getParameter("category_id");
        String status = request.getParameter("is_available");
        Dish d = new Dish();
        d.setName(name);
        d.setPrice(Integer.parseInt(price));
        d.setBrief_info(brief);
        d.setDescription(description);
        d.setCategory_id(Integer.parseInt(category));
        d.setIs_available(Boolean.valueOf(status));
        if (action.equals("edit")) {
            String id = request.getParameter("dishId");
            d.setId(Integer.parseInt(id));
            dd.updateDish(d);
        } else {
            String base64Image = "";

            Part filePart = request.getPart("dishImage");
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // Lấy tên tệp
            if (fileName != null && !fileName.isEmpty()) {
                InputStream imageStream = filePart.getInputStream();
                byte[] imageBytes = IOUtils.toByteArray(imageStream);
                base64Image = Base64.getEncoder().encodeToString(imageBytes);
                base64Image = "data:image/png;base64," + base64Image;
            }
            int newdishId = dd.createDish(d);
            int newimageId = dd.createImage(base64Image);
            dd.saveImageToDish(newdishId, newimageId);
        }
        response.sendRedirect("manage-dish");
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
