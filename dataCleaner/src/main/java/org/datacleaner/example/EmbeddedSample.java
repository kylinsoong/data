package org.datacleaner.example;

import org.datacleaner.bootstrap.Bootstrap;
import org.datacleaner.bootstrap.BootstrapOptions;
import org.datacleaner.bootstrap.DefaultBootstrapOptions;

public class EmbeddedSample {

	public static void main(String[] args) {

		BootstrapOptions bootstrapOptions = new DefaultBootstrapOptions(args);
		Bootstrap bootstrap = new Bootstrap(bootstrapOptions);
		bootstrap.run();
	}

}
