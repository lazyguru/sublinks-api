package com.sublinks.sublinksapi.api.lemmy.v3.comment.models;

import java.util.List;
import lombok.Builder;

@Builder
public record ListCommentLikesResponse(
    List<VoteView> comment_likes
) {

}