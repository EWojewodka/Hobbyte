/**
 * 
 */
package com.webrest.hobbyte.core.download;

import java.io.File;

/**
 * @author Emil Wojewódka
 *
 * @since 12 cze 2018
 */
public interface IDownloader {

	File download(IOMetaData ioMetaData) throws Exception;

}
