/**
 * 
 */
package com.webrest.hobbyte.core.file.properties;

import com.webrest.hobbyte.core.platform.AvailablePlatformProfiles;

/**
 * @author Emil Wojewódka
 *
 * @since 30 mar 2018
 */
public interface IEnvironmentProperties {

	/**
	 * Return current server host.
	 * 
	 * @return
	 */
	String getServerHost();

	/**
	 * Return current server port.
	 * 
	 * @return
	 */
	String getServerPort();

	/**
	 * return mail host, e.g. smtp.gmail.com
	 * 
	 * @return
	 */
	String getMailHost();

	/**
	 * Return mail port, eg. 543
	 * 
	 * @return
	 */
	String getMailPort();

	/**
	 * Return username neccessery for logon to mail hosting.
	 * 
	 * @return
	 */
	String getMailUsername();

	/**
	 * return user password for logon to mail hosting
	 * 
	 * @return
	 */
	String getMailPassword();

	/**
	 * Return {@code true} if active profile is
	 * {@link AvailablePlatformProfiles#TEST}
	 * 
	 * @return
	 */
	boolean isTest();

	/**
	 * Return {@code true} if active profile is
	 * {@link AvailablePlatformProfiles#DEVELOPMENT}
	 * 
	 * @return
	 */
	boolean isDevelopment();

	/**
	 * Return {@code true} if active profile is
	 * {@link AvailablePlatformProfiles#PRODUCTION}
	 * 
	 * @return
	 */
	boolean isProduction();

}
