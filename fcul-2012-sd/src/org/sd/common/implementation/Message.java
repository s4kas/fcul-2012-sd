package org.sd.common.implementation;

public class Message extends AbstractMessage {

	private static final long serialVersionUID = 2146638501733900728L;
	private Activity activity;
	
	@SuppressWarnings({ "hiding", "unchecked" })
	public <Activity> Activity getContent() {
		return (Activity) this.activity;
	}

	@SuppressWarnings("hiding")
	public <Activity> void setContent(Activity content) {
		this.activity = (org.sd.common.implementation.Activity) content;
	}

}
