package sample.example.niochannels;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Result implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map<String, Double> results = new HashMap<>();
	
	public Result(int size) {
		for(int i=0; i<size; i++) {
			results.put("key:"+ i, Math.random());
		}
	}
	
	public String toString() {
		int size = results.size();
		return "Result size=" + size 
				+ " [first Key=" + results.get("key:0") + "] [last key=" + results.get("key:" + (size-1)) + "]";
	}
	
	public String getMap() {
		return results.toString();
	}
}
