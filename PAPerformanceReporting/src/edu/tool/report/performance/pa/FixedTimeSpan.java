package edu.tool.report.performance.pa;

import java.time.LocalDateTime;

public class FixedTimeSpan implements SplunkQueryTimeSpan {

	private LocalDateTime start, end;
	
	public FixedTimeSpan(LocalDateTime start, LocalDateTime end) {
		this.start = start;
		this.end = end;
	}

	@Override
	public LocalDateTime getStart() {
		return start;
	}

	@Override
	public LocalDateTime getEnd() {
		return end;
	}
}
