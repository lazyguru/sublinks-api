package com.sublinks.sublinksapi.post.events;

import com.sublinks.sublinksapi.post.dto.PostReport;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostReportCreatedPublisher {

  private final ApplicationEventPublisher applicationEventPublisher;

  public void publish(PostReport postReport) {

    PostReportCreatedEvent postReportCreatedEvent = new PostReportCreatedEvent(this, postReport);
    applicationEventPublisher.publishEvent(postReportCreatedEvent);
  }
}