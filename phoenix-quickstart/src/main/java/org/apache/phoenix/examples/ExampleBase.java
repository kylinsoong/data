package org.apache.phoenix.examples;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

public class ExampleBase {

	static {
		String pattern = "%d %-5p [%c] (%t) %m%n";
		PatternLayout layout = new PatternLayout(pattern);
		ConsoleAppender consoleAppender = new ConsoleAppender(layout);
		Logger.getRootLogger().setLevel(Level.INFO);
		Logger.getRootLogger().addAppender(consoleAppender);
	}
}
