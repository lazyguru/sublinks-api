package com.sublinks.sublinksapi.instance.event;

import com.sublinks.sublinksapi.comment.event.CommentCreatedEvent;
import com.sublinks.sublinksapi.instance.InstanceAggregate;
import com.sublinks.sublinksapi.instance.InstanceAggregateRepository;
import com.sublinks.sublinksapi.instance.LocalInstanceContext;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class InstanceCommentCreatedListener implements ApplicationListener<CommentCreatedEvent> {
    private final InstanceAggregateRepository instanceAggregateRepository;
    private final LocalInstanceContext localInstanceContext;

    public InstanceCommentCreatedListener(
            final InstanceAggregateRepository instanceAggregateRepository,
            final LocalInstanceContext localInstanceContext
    ) {
        this.instanceAggregateRepository = instanceAggregateRepository;
        this.localInstanceContext = localInstanceContext;
    }

    @Override
    @Transactional
    public void onApplicationEvent(final CommentCreatedEvent event) {

        if (!event.getComment().isLocal()) {
            return;
        }

        final InstanceAggregate instanceAggregate = localInstanceContext.instanceAggregate();
        instanceAggregate.setCommentCount(instanceAggregate.getCommentCount() + 1);
        instanceAggregateRepository.save(instanceAggregate);
    }
}