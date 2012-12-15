package org.sd.client.ui;

import java.awt.Toolkit;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

public class DayFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	public DayFrame(String text) {
		super(text);
	}

	public void shutdown() {
		WindowEvent wev = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
	    Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(wev);
	}
	
}
