package com.eventviewer.eventviewerservice;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.elasticsearch.client.Client;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import com.eventviewer.entities.Event;

@RunWith(MockitoJUnitRunner.class)
public class EventViewerControllerTests {

	@InjectMocks
	private EventViewerController controller;
	
	@Mock
	Client client;
	
	@Mock
	private EventRepository eventRepository;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void test_getAllEvents() {
		List<Event> mockedListOfEvents = new ArrayList<Event>();
		when(eventRepository.findAll()).thenReturn(this.loadMockedEvents());
		mockedListOfEvents = controller.getAllEvents();
		assertEquals(2, mockedListOfEvents.size());
		assertEquals("3273bf58-bf03-5cc2-b13b-9101b0846766", mockedListOfEvents.get(0).getId());
		assertEquals("3273bf58-bf03-5cc2-b13b-9101b084ad45", mockedListOfEvents.get(1).getId());
	}
	
	@Test
	public void test_getByEventType() {
		List<Event> mockedListOfEvents = new ArrayList<Event>();
		when(eventRepository.findByType("ERROR")).thenReturn(this.loadMockedErrorEvents());
		mockedListOfEvents = controller.getByEventType("ERROR");
		assertEquals(2, mockedListOfEvents.size());
		assertEquals("3273bf58-bf03-5cc2-b13b-9101b0846766", mockedListOfEvents.get(0).getId());
		assertEquals("3273bf58-bf03-5cc2-b13b-9101b084ad45", mockedListOfEvents.get(1).getId());
	}
	
	@Test
	public void test_getByDays() {
		List<Event> response = new ArrayList<Event>();
		when(eventRepository.findByCreatedAt(Mockito.anyLong(), Mockito.anyLong())).thenReturn(this.loadMockedLast30DaysEvents());
		response = controller.getByDays(30);
		assertEquals(2, response.size());
		assertEquals("3273bf58-bf03-5cc2-b13b-9101b0846766", response.get(0).getId());
		assertEquals("3273bf58-bf03-5cc2-b13b-9101b084ad45", response.get(1).getId());
	}
	
	public Iterable<Event> loadMockedEvents(){
		
		List<Event> list = new ArrayList();
		Event event1 = new Event();
		event1.setId("3273bf58-bf03-5cc2-b13b-9101b0846766");
		event1.setCreatedAt("2019-03-16T11:17:10Z");
		event1.setDetails("User login success");
		event1.setSource("MOBILE");
		event1.setType("DEBUG");
		Event event2 = new Event();
		event2.setId("3273bf58-bf03-5cc2-b13b-9101b084ad45");
		event2.setCreatedAt("2019-03-16T11:17:10Z");
		event2.setDetails("File Processed successfully");
		event2.setSource("Data processor");
		event2.setType("INFO");
		list.add(event1);
		list.add(event2);
		
		Iterable<Event> it = new Iterable<Event>() {
			
			@Override
			public Iterator<Event> iterator() {
				// TODO Auto-generated method stub
				return list.iterator();
			}
		};
		return it;
	}
	
	public List<Event> loadMockedErrorEvents(){
		
		List<Event> list = new ArrayList();
		Event event1 = new Event();
		event1.setId("3273bf58-bf03-5cc2-b13b-9101b0846766");
		event1.setCreatedAt("2019-03-16T11:17:10Z");
		event1.setDetails("User login success");
		event1.setSource("MOBILE");
		event1.setType("ERROR");
		Event event2 = new Event();
		event2.setId("3273bf58-bf03-5cc2-b13b-9101b084ad45");
		event2.setCreatedAt("2019-03-16T11:17:10Z");
		event2.setDetails("File Processed successfully");
		event2.setSource("Data processor");
		event2.setType("ERROR");
		list.add(event1);
		list.add(event2);
		
		return list;
	}
	
	public List<Event> loadMockedLast30DaysEvents(){
		
		List<Event> list = new ArrayList();
		Event event1 = new Event();
		event1.setId("3273bf58-bf03-5cc2-b13b-9101b0846766");
		event1.setCreatedAt("2019-03-16T11:17:10Z");
		event1.setDetails("User login success");
		event1.setSource("MOBILE");
		event1.setType("ERROR");
		Event event2 = new Event();
		event2.setId("3273bf58-bf03-5cc2-b13b-9101b084ad45");
		event2.setCreatedAt("2019-03-16T11:17:10Z");
		event2.setDetails("File Processed successfully");
		event2.setSource("Data processor");
		event2.setType("ERROR");
		list.add(event1);
		list.add(event2);
		
		return list;
	}
	
}
