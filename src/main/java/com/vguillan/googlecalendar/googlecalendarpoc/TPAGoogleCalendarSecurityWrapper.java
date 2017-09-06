package com.vguillan.googlecalendar.googlecalendarpoc;

import java.io.InputStreamReader;
import java.util.Collections;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.store.DataStoreFactory;
import com.google.api.services.calendar.CalendarScopes;

public class TPAGoogleCalendarSecurityWrapper {

	private JsonFactory JSON_FACTORY;
	
	private HttpTransport httpTransport;
	
	private DataStoreFactory dataStoreFactory;
	
	public TPAGoogleCalendarSecurityWrapper(JsonFactory jSON_FACTORY, HttpTransport httpTransport,
			DataStoreFactory dataStoreFactory) {
		JSON_FACTORY = jSON_FACTORY;
		this.httpTransport = httpTransport;
		this.dataStoreFactory = dataStoreFactory;
	}

	/** Authorizes the installed application to access user's protected data. */
	public Credential authorize() throws Exception {
		
		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY,
				new InputStreamReader(GoogleCalendarPocApplication.class.getResourceAsStream("/client_secrets.json")));
		
		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(httpTransport, JSON_FACTORY,
				clientSecrets, Collections.singleton(CalendarScopes.CALENDAR)).setDataStoreFactory(dataStoreFactory)
						.build();

		return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
	}
}
