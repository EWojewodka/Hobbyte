/**
 * 
 */
package com.webrest.hobbyte.core.download;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import org.springframework.stereotype.Service;

/**
 * @author Emil Wojewódka
 *
 * @since 12 cze 2018
 */
@Service
public class ImageDownloader implements IDownloader {

	// TODO:
	@Override
	public File download(IOMetaData ioMetaData) throws Exception {
		ReadableByteChannel rbc = Channels.newChannel(ioMetaData.getURL().openStream());
		try (FileOutputStream fos = new FileOutputStream(ioMetaData.getDestinationFile())) {
			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
		}
		return ioMetaData.getDestinationFile();
	}

}
