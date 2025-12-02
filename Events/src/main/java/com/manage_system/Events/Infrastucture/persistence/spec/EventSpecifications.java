package com.manage_system.Events.Infrastucture.persistence.spec;

import com.manage_system.Events.Infrastucture.persistence.entity.EventEntity;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class EventSpecifications {

    public static Specification<EventEntity> hasVenue(int venueId) {
        return (root, query, cb) -> cb.equal(root.get("venue").get("venueId"), venueId);
    }

    public static Specification<EventEntity> startDateAfter(LocalDateTime date) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("startAt"), date);
    }

    public static Specification<EventEntity> endDateBefore(LocalDateTime date) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("endAt"), date);
    }

    public static Specification<EventEntity> betweenDates(LocalDateTime start, LocalDateTime end) {
        return (root, query, cb) ->
                cb.between(root.get("startAt"), start, end);
    }

}
