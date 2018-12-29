package kz.kartel.dutyscheduler.components.duty.repository;

import kz.kartel.dutyscheduler.components.duty.model.Comment;
import kz.kartel.dutyscheduler.components.duty.model.Duty;
import kz.kartel.dutyscheduler.components.vacation.model.UserDuty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;


@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>{

    @Query("select c from Comment c where c.id = :id")
    public Comment getComment(@Param("id") Long id);

    @Query("select c from Comment c where c.duty.id = :dutyId")
    public List<Comment> getComments(@Param("dutyId") Long dutyId);

    @Modifying
    @Transactional
    @Query(value="insert into comments (duty_id, text, insert_date, user_id) values (:duty_id, :text, :insert_date, :user_id)", nativeQuery = true)
    public void saveComment(@Param("duty_id") Long dutyId, @Param("text") String text, @Param("insert_date") Date insertDate, @Param("user_id") Long userId);

    @Modifying
    @Transactional
    @Query("delete from Comment where id = :id")
    public void deleteComment(@Param("id") Long commentId);
}
