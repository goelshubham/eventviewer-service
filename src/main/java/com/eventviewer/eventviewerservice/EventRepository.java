package com.eventviewer.eventviewerservice;

import java.util.List;

import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.eventviewer.entities.Event;

public interface EventRepository extends ElasticsearchRepository<Event, String>{
	
	List<Event> findByType(String type);
	
	@Query("{\"bool\" : {\"must\" : {\"range\" : {\"createdAt\" : {\"from\" : ?0,\"to\" : ?1,\"include_lower\" : true,\"include_upper\" : true}}}}}")
	List<Event> findByCreatedAt(long startDate, long endDate);
}
