/*
 * Copyright (c) 2010 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.vguillan.googlecalendar.googlecalendarpoc;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.api.services.calendar.model.Calendar;
import com.google.api.services.calendar.model.CalendarList;
import com.google.api.services.calendar.model.CalendarListEntry;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;


public class View {
	
	final static Logger LOGGER = LogManager.getRootLogger();

	static void header(String name) {
		LOGGER.info("\n==============   {}   ==============", name);
	}

	static void display(CalendarList feed) {
		if (feed.getItems() != null) {
			for (CalendarListEntry entry : feed.getItems()) {
				LOGGER.info("\n-----------------------------------------------");
				display(entry);
			}
		}
	}

	static void display(Events feed) {
		if (feed.getItems() != null) {
			for (Event entry : feed.getItems()) {
				LOGGER.info("\n-----------------------------------------------");
				display(entry);
			}
		}
	}

	static void display(CalendarListEntry entry) {
		LOGGER.info("ID: {}", entry.getId());
		LOGGER.info("Summary: {}", entry.getSummary());
		if (entry.getDescription() != null) {
			LOGGER.info("Description: {}", entry.getDescription());
		}
	}

	static void display(Calendar entry) {
		LOGGER.info("ID: {}", entry.getId());
		LOGGER.info("Summary: {}", entry.getSummary());
		if (entry.getDescription() != null) {
			LOGGER.info("Description: {}", entry.getDescription());
		}
	}

	static void display(Event event) {
		if (event.getStart() != null) {
			LOGGER.info("Start Time: {}", event.getStart());
		}
		if (event.getEnd() != null) {
			LOGGER.info("End Time: {}", event.getEnd());
		}
	}
	
}
