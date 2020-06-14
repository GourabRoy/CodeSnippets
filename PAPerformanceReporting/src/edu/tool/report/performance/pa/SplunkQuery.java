package edu.tool.report.performance.pa;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class SplunkQuery {
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd:HH:mm:ss");
	
	private String skeletonQuery;
	private Map<String, String> parameters;
	private SplunkQueryTimeSpan querySpan;
	
	public SplunkQuery(String skeletonQuery, Map<String, String> parameters, SplunkQueryTimeSpan querySpan) {
		this.skeletonQuery = skeletonQuery;
		this.querySpan = querySpan;
		this.parameters = new HashMap<>();
		this.parameters.putAll(parameters);
	}
	
	public String getExecutableQuery() {
		String result = skeletonQuery;
		for(Map.Entry<String, String> entry : parameters.entrySet()) {
			result = result.replace(entry.getKey(), entry.getValue());
		}
		result += "earliest=" + querySpan.getStart().format(FORMATTER) + " latest=" + querySpan.getStart().format(FORMATTER); 
		return result;
	}
}
