package edu.tool.report.performance.pa;

import java.time.LocalDateTime;

public interface SplunkQueryTimeSpan {
	LocalDateTime getStart();
	LocalDateTime getEnd();
}

