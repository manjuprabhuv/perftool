package com.hawkeye.agent.aggregator;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.Gson;
import com.hawkeye.beans.JSONAspectRequest;
import com.hawkeye.beans.JSONVitalStatsRequest;
import com.hawkeye.beans.WebserviceStatsBean;
import com.hawkeye.utils.ConfigReader;

public class StatisticsAggregatorAndForwarder {

	static boolean isServerUp = false;

	public static String flushStats(JSONVitalStatsRequest request) {
		Gson gson = new Gson();
		String json = gson.toJson(request);
		return sendJSONRequest(json);
	}

	public static String flushStats(JSONAspectRequest request) {
		Gson gson = new Gson();
		String json = gson.toJson(request);
		return sendJSONRequest(json);

	}
	public static String flushWSStats(WebserviceStatsBean request) {
		Gson gson = new Gson();
		String json = gson.toJson(request);
		return sendJSONRequest(json);

	}

	private static String sendJSONRequest(String json) {
		String serverName = ConfigReader.getProps("app.ui.server");
		String serverport = ConfigReader.getProps("app.ui.port");
		if (serverName == null || serverport == null
				|| serverName.length() == 0 || serverport.length() == 0) {
			// TODO
			// notification code here
			isServerUp = false;
		} else {
			String collectorURL = "http://" + serverName + ":" + serverport
					+ "/HawkeyeWeb/StatsCollectorServlet";
			try {

				// TODO : MAKE CALL ASYNC
				URL url = new URL(collectorURL);
				HttpURLConnection httpCon = (HttpURLConnection) url
						.openConnection();
				httpCon.setDoOutput(true);
				httpCon.setRequestMethod("POST");
				httpCon.setRequestProperty("Content-Type", "application/json");

				OutputStreamWriter out = new OutputStreamWriter(
						httpCon.getOutputStream());
				// String fileContent =
				// "<?xml version=\"1.0\"encoding=\"UTF-8\"?>\n<WAV>\n";
				// fileContent = fileContent + "this is the content</WAV>";
				// out.write(fileContent);
				out.write(json);

				out.flush();
				out.close();
				BufferedReader in = new BufferedReader(new InputStreamReader(
						httpCon.getInputStream()));

				isServerUp = true;

			} catch (Exception e) {
				isServerUp = false;

				new NotificationTimer(collectorURL);
				// TODO
				// notification code here . send when check count is 1, 100,
				// 200,300...
			}
		}

		return "";
	}
}
