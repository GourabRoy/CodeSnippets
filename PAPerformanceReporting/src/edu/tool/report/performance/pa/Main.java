package edu.tool.report.performance.pa;

import java.time.LocalDate;

public class Main {

	public static void main(String[] args) {
		/**
		 * 1. Stage 1 Reporting 
		 * 1.1. Get skeleton splunk query to find job runs : (hard coded) -> (Splunk Query)
		 * 1.2. Execute/Run query on server and get result : (Splunk Query, Parameters, Server details) -> (Result)
		 * 1.2.1 Parameters : Analysis, Search Date range, Job Date Range, Environment 
		 * 1.2.2 Result : Instances of run
		 * 1.2.2.1 Instance of run : Input Parameters, JobId, RootJobId, LogTime
		 * 1.3. Check runtime for each instance : (Result) -> (Map<Result.Instance, RunTime>)
		 * 1.3.1. RunTime : Job level runtimes of PV, RC, PO, CR
		 * 1.4. Format the result into a report
		 * 1.5. Mail the report
		 * 
		 * 
		 * 2. Stage 2 Reporting
		 * 2.1 Stage 2 Formatting : (Result of Stage 1, Formatter) -> (Formatted report)
		 * 2.1.1 : Formatter : 
		 * 2.2 Detailed reporting to capture cause for differences : (Differences from Stage 1 results) -> ()
		 * 2.2.2    repeat of 1.1 to 1.4 followed by 2.1
		 * 	
		 */

	}
	
	public Object getStats(String jobName, LocalDate endDate) {
		// todo : implement
		
		/**
		 * Get Splunk queries
		 * 
		 */
		
		return null;
	}
	
	public Object generateReport(Object stats) {
		// todo : implement
		return null;
	}
	
	public void sendMail(Object report, Object sentList) {
		// todo : implement
	}

}
