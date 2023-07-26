package com.jwt.Exception;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ErrorDetails {

	private String timestamp;
	private String message;
	private String path;
	private String errorCode;

	public ErrorDetails(LocalDateTime timestamp, String message, String path, String errorCode) {
		super();
		this.timestamp = formatTimestamp(timestamp);
		this.message = message;
		this.path = path;
		this.errorCode = errorCode;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = formatTimestamp(timestamp);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	private String formatTimestamp(LocalDateTime timestamp) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		return timestamp.format(formatter);
	}
}
