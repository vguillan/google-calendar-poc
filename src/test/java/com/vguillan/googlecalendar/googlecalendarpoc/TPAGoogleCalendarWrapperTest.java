package com.vguillan.googlecalendar.googlecalendarpoc;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Date;
import java.util.TimeZone;

import org.junit.Before;
import org.junit.Test;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar.CalendarList;
import com.google.api.services.calendar.Calendar.CalendarList.List;
import com.google.api.services.calendar.Calendar.Calendars;
import com.google.api.services.calendar.Calendar.Events;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;

public class TPAGoogleCalendarWrapperTest {

	private com.google.api.services.calendar.Calendar client;
	private TPAGoogleCalendarWrapper sut;
	private Event newEvent;
	
	@Before
	public void init(){
		this.client = mock(com.google.api.services.calendar.Calendar.class);
		this.sut = new TPAGoogleCalendarWrapper(client);
		
		newEvent = new Event();
		newEvent.setSummary("New Event");
		Date startDate = new Date();
		Date endDate = new Date(startDate.getTime() + 3600000);
		DateTime start = new DateTime(startDate, TimeZone.getTimeZone("UTC"));
		newEvent.setStart(new EventDateTime().setDateTime(start));
		DateTime end = new DateTime(endDate, TimeZone.getTimeZone("UTC"));
		newEvent.setEnd(new EventDateTime().setDateTime(end));
	}
	
	@Test
	public void should_call_calendar_list_list_execute_when_show_calendar() throws IOException{
		// ARRANGE
		CalendarList calendarList = mock(CalendarList.class);
		List list = mock(List.class);
		when(this.client.calendarList()).thenReturn(calendarList);
		when(this.client.calendarList().list()).thenReturn(list);
		com.google.api.services.calendar.model.CalendarList feed = new com.google.api.services.calendar.model.CalendarList();
		when(this.client.calendarList().list().execute()).thenReturn(feed);
		
		// ACT
		this.sut.showCalendars();
		
		// ASSERT
		verify(this.client.calendarList().list()).execute();
	}
	
	
	@Test
	public void should_call_calendars_insert_execute_when_add_calendar() throws IOException{
		// ARRANGE
		com.google.api.services.calendar.model.Calendar calendar = new com.google.api.services.calendar.model.Calendar();
		Calendars calendarList = mock(Calendars.class);
		Calendars.Insert insert = mock(Calendars.Insert.class);
		when(this.client.calendars()).thenReturn(calendarList);
		when(this.client.calendars().insert(calendar)).thenReturn(insert);
		com.google.api.services.calendar.model.Calendar feed = new com.google.api.services.calendar.model.Calendar();
		when(this.client.calendars().insert(calendar).execute()).thenReturn(feed);
		
		// ACT
		this.sut.addCalendar(calendar);
		
		// ASSERT
		verify(this.client.calendars().insert(calendar)).execute();
	}
	
	@Test
	public void should_call_calendars_patch_execute_when_update_calendar() throws IOException{
		// ARRANGE
		com.google.api.services.calendar.model.Calendar calendar = new com.google.api.services.calendar.model.Calendar();
		calendar.setId("1");
		Calendars calendarList = mock(Calendars.class);
		Calendars.Patch patch = mock(Calendars.Patch.class);
		when(this.client.calendars()).thenReturn(calendarList);
		when(this.client.calendars().patch(calendar.getId(), calendar)).thenReturn(patch);
		com.google.api.services.calendar.model.Calendar feed = new com.google.api.services.calendar.model.Calendar();
		when(this.client.calendars().patch(calendar.getId(), calendar).execute()).thenReturn(feed);
		
		// ACT
		this.sut.updateCalendar(calendar);
		
		// ASSERT
		verify(this.client.calendars().patch(calendar.getId(), calendar)).execute();
	}
	
	
	@Test
	public void should_call_events_insert_execute_when_add_event() throws IOException{
		// ARRANGE
		com.google.api.services.calendar.model.Calendar calendar = new com.google.api.services.calendar.model.Calendar();
		calendar.setId("1");
		Events events = mock(Events.class);
		Events.Insert insert = mock(Events.Insert.class);
		when(this.client.events()).thenReturn(events);
		when(this.client.events().insert(calendar.getId(), newEvent)).thenReturn(insert);
		when(this.client.events().insert(calendar.getId(), newEvent).execute()).thenReturn(newEvent);
		
		// ACT
		this.sut.addEvent(calendar, newEvent);
		
		// ASSERT
		verify(this.client.events().insert(calendar.getId(), newEvent)).execute();
	}
	
	@Test
	public void should_call_events_list_execute_when_show_events() throws IOException{
		// ARRANGE
		com.google.api.services.calendar.model.Calendar calendar = new com.google.api.services.calendar.model.Calendar();
		calendar.setId("1");
		Events events = mock(Events.class);
		Events.List list = mock(Events.List.class);
		com.google.api.services.calendar.model.Events eventsModel = new com.google.api.services.calendar.model.Events();
		when(this.client.events()).thenReturn(events);
		when(this.client.events().list(calendar.getId())).thenReturn(list);
		when(this.client.events().list(calendar.getId()).execute()).thenReturn(eventsModel);
		
		// ACT
		this.sut.showEvents(calendar);
		
		// ASSERT
		verify(this.client.events().list(calendar.getId())).execute();
	}
	
}
