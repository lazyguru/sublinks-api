package com.sublinks.sublinksapi.comment.repositories;

import static com.sublinks.sublinksapi.utils.PaginationUtils.applyPagination;

import com.sublinks.sublinksapi.comment.entities.CommentReply;
import com.sublinks.sublinksapi.comment.models.CommentReplySearchCriteria;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CommentReplyRepositoryImpl implements CommentReplyRepositorySearch {

  private final EntityManager em;

  public List<CommentReply> allCommentReplysBySearchCriteria(
      CommentReplySearchCriteria commentReplySearchCriteria) {

    final CriteriaBuilder cb = em.getCriteriaBuilder();
    final CriteriaQuery<CommentReply> cq = cb.createQuery(CommentReply.class);

    final Root<CommentReply> commentReplyTable = cq.from(CommentReply.class);

    final List<Predicate> predicates = new ArrayList<>();

    if (commentReplySearchCriteria.unreadOnly()) {
      predicates.add(cb.equal(commentReplyTable.get("isRead"), false));
    }

    cq.where(predicates.toArray(new Predicate[0]));

    switch (commentReplySearchCriteria.sortType()) {
      case New:
        cq.orderBy(cb.desc(commentReplyTable.get("createdAt")));
        break;
      case Old:
        cq.orderBy(cb.asc(commentReplyTable.get("createdAt")));
        break;
      default:
        cq.orderBy(cb.desc(commentReplyTable.get("createdAt")));
        break;
    }

    int perPage = Math.min(Math.abs(commentReplySearchCriteria.perPage()), 20);

    TypedQuery<CommentReply> query = em.createQuery(cq);

    applyPagination(query, commentReplySearchCriteria.page(), perPage);

    return query.getResultList();
  }
}
