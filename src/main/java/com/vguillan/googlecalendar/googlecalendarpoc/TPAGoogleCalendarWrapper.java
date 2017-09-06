package com.vguillan.googlecalendar.googlecalendarpoc;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.api.client.googleapis.batch.BatchRequest;
import com.google.api.client.googleapis.batch.json.JsonBatchCallback;
import com.google.api.client.googleapis.json.GoogleJsonError;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.util.Lists;
import com.google.api.services.calendar.model.Calendar;
import com.google.api.services.calendar.model.CalendarList;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;

public class TPAGoogleCalendarWrapper {

final static Logger LOGGER = LogManager.getRootLogger();
	
	private com.google.api.services.calendar.Calendar client;

	public TPAGoogleCalendarWrapper(com.google.api.services.calendar.Calendar client) {
		this.client = client;
	}

	public void showCalendars() throws IOException {
		View.header("Show Calendars");
		CalendarList feed = client.calendarList().list().execute();
		View.display(feed);
	}

	public List<Calendar> addCalendarsUsingBatch(List<Calendar> calendars) throws IOException {
		View.header("Add Calendars using Batch");
		BatchRequest batch = client.batch();
		final List<Calendar> addedCalendarsUsingBatch = Lists.newArrayList();
		
		JsonBatchCallback<Calendar> callback = new JsonBatchCallback<Calendar>() {

			@Override
			public void onSuccess(Calendar calendar, HttpHeaders responseHeaders) {
				View.display(calendar);
				addedCalendarsUsingBatch.add(calendar);
			}

			@Override
			public void onFailure(GoogleJsonError e, HttpHeaders responseHeaders) {
				System.out.println("Error Message: " + e.getMessage());
			}
		};

		for (Calendar calendar : calendars) {
			client.calendars().insert(calendar).queue(batch, callback);
		}

		batch.execute();
		return addedCalendarsUsingBatch;
	}

	public Calendar addCalendar(Calendar calendar) throws IOException {
		View.header("Add Calendar");
		Calendar result = client.calendars().insert(calendar).execute();
		View.display(result);
		return result;
	}

	public Calendar updateCalendar(Calendar calendar) throws IOException {
		View.header("Update Calendar");
		Calendar result = client.calendars().patch(calendar.getId(), calendar).execute();
		View.display(result);
		return result;
	}

	public void addEvent(Calendar calendar, Event event) throws IOException {
		View.header("Add Event");
		Event result = client.events().insert(calendar.getId(), event).execute();
		View.display(result);
	}

	public void showEvents(Calendar calendar) throws IOException {
		View.header("Show Events");
		Events feed = client.events().list(calendar.getId()).execute();
		View.display(feed);
	}

	public void deleteCalendarsUsingBatch(List<Calendar> calendars) throws IOException {
		View.header("Delete Calendars Using Batch");
		BatchRequest batch = client.batch();
		
		for (Calendar calendar : calendars) {
			client.calendars().delete(calendar.getId()).queue(batch, new JsonBatchCallback<Void>() {

				@Override
				public void onSuccess(Void content, HttpHeaders responseHeaders) {
					LOGGER.info("Delete is successful!");
				}

				@Override
				public void onFailure(GoogleJsonError e, HttpHeaders responseHeaders) {
					LOGGER.info("Error Message: {}", e.getMessage());
				}
			});
		}

		batch.execute();
	}

	public void deleteCalendar(Calendar calendar) throws IOException {
		View.header("Delete Calendar");
		client.calendars().delete(calendar.getId()).execute();
	}

	
}
