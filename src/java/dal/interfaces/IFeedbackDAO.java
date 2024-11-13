/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dal.interfaces;

import java.util.List;
import models.Feedback;

public interface IFeedbackDAO {

    public List<Feedback> getAllFeedbackByProductId(int product_id);

    public List<Feedback> pagingFeedback(int product_id, int index, int record_per_page);

    public void deleteFeedbacktById(int id);

    public void addNewFeedback(String fullName, int rated_star, String feedback_content, String image, int product_id, int user_id, String avatar_user);
}
