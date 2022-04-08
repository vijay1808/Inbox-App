
package org.inbox;
import java.io.File;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix="datastax.astra")
public class DataStaxAstraProperties {


	private File SecureConnectBundle;

	public File getSecureConnectBundle() {
		return SecureConnectBundle;
	}

	public void setSecureConnectBundle(File secureConnectBundle) {
		SecureConnectBundle = secureConnectBundle;
	}


}
