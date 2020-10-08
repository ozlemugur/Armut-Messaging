package com.armut.exception;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorMessage {

	@JsonProperty
	private String message;

	@JsonProperty("timestamp")
	private Date timestamp;

	@JsonProperty("uri")
	private String uriRequested;

	public ErrorMessage(Exception exception, String uriRequested) {
			this.message = exception.toString();
			this.uriRequested = uriRequested;
			this.timestamp = new Date();
		}

	public String getMessage() {
		return message;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public String getUriRequested() {
		return uriRequested;
	}

}
