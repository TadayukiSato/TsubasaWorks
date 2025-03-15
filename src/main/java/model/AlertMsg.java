package model;

import java.io.Serializable;

public class AlertMsg implements Serializable {

	private String alertMsg;

	public AlertMsg() {}
	public AlertMsg(String alertMsg) {
		this.alertMsg = alertMsg;
	}
	public String getAlertMsg() { return alertMsg; }
	public void setAlertMsg(String alertMsg) { this.alertMsg = alertMsg; }
}