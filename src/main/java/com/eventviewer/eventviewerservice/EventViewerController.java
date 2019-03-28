package com.eventviewer.eventviewerservice;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.elasticsearch.client.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.eventviewer.entities.Event;

@RestController
@RequestMapping("/eventviewer")
public class EventViewerController {

	@Autowired
	Client client;
	
	@Autowired
	private EventRepository eventRepository;
	
	/*
	 * API to get all the events
	 */
	@RequestMapping(value="/events/", method=RequestMethod.GET)
	public List<Event> getAllEvents(){
		System.out.println("called");
		List<Event> listOfEvents = new ArrayList<Event>();
		eventRepository.findAll().forEach(listOfEvents::add);
		Iterable<Event> it = eventRepository.findAll();
		System.out.println(listOfEvents.toString());
		return listOfEvents;
	}
	
	/*
	 * API to retrieve all events by 'type' field
	 */
	@RequestMapping(value="/events/type/{type}", method=RequestMethod.GET)
	public List<Event> getByEventType(@PathVariable String type){
		System.out.println("by type called");
		List<Event> listOfEvents = new ArrayList<Event>();
		eventRepository.findByType(type).forEach(listOfEvents::add);
		return listOfEvents;
	}
	
	/*
	 * API to retrieve all events for last given number of days
	 */
	@RequestMapping(value="/events/days/{days}", method=RequestMethod.GET)
	public List<Event> getByDays(@PathVariable int days){
		List<Event> listOfEvents = new ArrayList<Event>();
		Date currentDate = new Date();
		LocalDate localDate = LocalDate.now().minusDays(days);
		Date start = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
		eventRepository.findByCreatedAt(start.getTime(), currentDate.getTime()).forEach(listOfEvents::add);
		return listOfEvents;
	}
	
}
