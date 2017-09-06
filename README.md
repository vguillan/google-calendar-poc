# google-calendar-poc
Google Calendar API Proof Of Concept

# Steps to run the app
* Visit the [Google Cloud console](https://cloud.google.com/console/start/api?id=calendar).
* If necessary, sign in to your Google Account, select or create a project, and agree to the terms of service. Click Continue.
* Go to the [APIs console]https://console.developers.google.com/apis/dashboard, select on top of the view the project created before and select in the left bar menu the "Credentials" option. Here select "create credentials" within "OAuth 2.0 Client ID" and Application type "other" and give it a name.
* Once the credentials were created, you can click on "Download JSON". Later on, after you check out the poc project, you will copy this downloaded file to src/main/resources/client_secrets.json.

# Checkout Instructions
* cd [someDirectory]
* git clone url-repo-google-calendar-api-poc
* mvn clean install -U
* mvn -q exec:java
