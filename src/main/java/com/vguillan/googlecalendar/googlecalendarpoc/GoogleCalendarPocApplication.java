package com.vguillan.googlecalendar.googlecalendarpoc;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.Lists;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.model.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;

public class GoogleCalendarPocApplication {

	final static Logger LOGGER = LogManager.getRootLogger();
	
	private static final String APPLICATION_NAME = "TAP-CC-GC-POC/1.0";

	private static final java.io.File DATA_STORE_DIR = new java.io.File(System.getProperty("user.home"),
			".store/tpa_cc_gc_poc");

	private static FileDataStoreFactory dataStoreFactory;

	private static HttpTransport httpTransport;

	private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

	static final java.util.List<Calendar> addedCalendarsUsingBatch = Lists.newArrayList();

	Calendar entry1 = new Calendar().setSummary("Calendar for Testing 1");
	Calendar entry2 = new Calendar().setSummary("Calendar for Testing 2");
	Calendar entry3 = new Calendar().setSummary("Calendar for Testing 3");
	
	public static void main(String[] args) {
		final GoogleCalendarPocApplication app = new GoogleCalendarPocApplication();
		app.execute();
	}

	public void execute() {
		try {
			httpTransport = GoogleNetHttpTransport.newTrustedTransport();

			dataStoreFactory = new FileDataStoreFactory(DATA_STORE_DIR);

			TPAGoogleCalendarSecurityWrapper tpaGoogleCalendarSecurityWrapper = new TPAGoogleCalendarSecurityWrapper(JSON_FACTORY, httpTransport, dataStoreFactory);
			Credential credential = tpaGoogleCalendarSecurityWrapper.authorize();
			
			com.google.api.services.calendar.Calendar client = new com.google.api.services.calendar.Calendar.Builder(httpTransport, JSON_FACTORY, credential)
					.setApplicationName(APPLICATION_NAME).build();

			TPAGoogleCalendarWrapper tpaGoogleCalendarWrapper = new TPAGoogleCalendarWrapper(client);
			
			tpaGoogleCalendarWrapper.showCalendars();
			List<Calendar> addedCalendars = tpaGoogleCalendarWrapper.addCalendarsUsingBatch(Arrays.asList(entry1, entry2));
			Calendar calendar = tpaGoogleCalendarWrapper.addCalendar(entry3);
			calendar.setSummary("Updated Calendar for Testing");
			tpaGoogleCalendarWrapper.updateCalendar(calendar);
			tpaGoogleCalendarWrapper.addEvent(calendar, newEvent());
			tpaGoogleCalendarWrapper.showEvents(calendar);
			tpaGoogleCalendarWrapper.deleteCalendarsUsingBatch(addedCalendars);
			tpaGoogleCalendarWrapper.deleteCalendar(calendar);

		} catch (IOException e) {
			System.err.println(e.getMessage());
		} catch (Throwable t) {
			t.printStackTrace();
		}
		System.exit(1);
	}
	
	public Event newEvent() {
		Event event = new Event();
		event.setSummary("New Event");
		Date startDate = new Date();
		Date endDate = new Date(startDate.getTime() + 3600000);
		DateTime start = new DateTime(startDate, TimeZone.getTimeZone("UTC"));
		event.setStart(new EventDateTime().setDateTime(start));
		DateTime end = new DateTime(endDate, TimeZone.getTimeZone("UTC"));
		event.setEnd(new EventDateTime().setDateTime(end));
		return event;
	}

}
