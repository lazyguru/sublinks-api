package com.sublinks.sublinksapi.instance.events;

import com.sublinks.sublinksapi.community.events.CommunityCreatedEvent;
import com.sublinks.sublinksapi.instance.InstanceAggregate;
import com.sublinks.sublinksapi.instance.InstanceAggregateRepository;
import com.sublinks.sublinksapi.instance.LocalInstanceContext;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class InstanceCommunityCreatedListener implements ApplicationListener<CommunityCreatedEvent> {
    private final InstanceAggregateRepository instanceAggregateRepository;
    private final LocalInstanceContext localInstanceContext;

    public InstanceCommunityCreatedListener(
            final InstanceAggregateRepository instanceAggregateRepository,
            LocalInstanceContext localInstanceContext
    ) {
        this.instanceAggregateRepository = instanceAggregateRepository;
        this.localInstanceContext = localInstanceContext;
    }

    @Override
    @Transactional
    public void onApplicationEvent(CommunityCreatedEvent event) {

        if (!event.getCommunity().isLocal()) {
            return;
        }

        final InstanceAggregate instanceAggregate = localInstanceContext.instanceAggregate();
        instanceAggregate.setCommunityCount(instanceAggregate.getCommunityCount() + 1);
        instanceAggregateRepository.save(instanceAggregate);
    }
}