package kz.kartel.dutyscheduler.components.duty.service;

import kz.kartel.dutyscheduler.components.duty.forms.CreateCommentForm;
import kz.kartel.dutyscheduler.components.duty.model.Comment;
import kz.kartel.dutyscheduler.components.duty.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    public Comment getCommentById(Long id){
        return commentRepository.getComment(id);
    }

    public void save(CreateCommentForm createCommentForm){
        commentRepository.saveComment(createCommentForm.getDutyId(), createCommentForm.getText(), createCommentForm.getDate());
    }

    public void delete(Long id){
        commentRepository.deleteComment(id);
    }
}