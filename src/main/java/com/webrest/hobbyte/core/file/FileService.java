/**
 * 
 */
package com.webrest.hobbyte.core.file;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.util.StringUtils;

import com.webrest.hobbyte.core.utils.Asserts;
import com.webrest.hobbyte.core.utils.FileUtils;

/**
 * {@link FileService} for parse text files. Can extract comments and content.
 * </br>
 * Services are cached - and remove from cache if file is modified. - for more
 * info look at {@link FileService#getFromCache()}}
 * 
 * @author Emil Wojewódka
 *
 * @since 27 lut 2018
 */
public class FileService implements IFileService {

	protected final File file;

	private InputStream is;

	// For null safty - empty string.
	protected String content = "";

	protected boolean isRead = false;

	protected String[] comments = new String[0];

	/**
	 * Create instance of {@link FileService}.
	 * 
	 * @param file
	 */
	public FileService(File file) {
		Asserts.exists(file,
				String.format("cannot create file service, cause file is null or doesnt exists. (%s)", file));
		this.file = file;
	}

	/**
	 * This implementation using {@link InputStreamReader}, {@link InputStream} and
	 * {@link BufferedReader} all of them are close by finally block. </br>
	 * TODO: Perhaps I should use {@link FileReader} bitwise an
	 * {@link InputStreamReader}
	 */
	@Override
	public String read() throws IOException {
		FileService fromCache = getFromCache();
		if (fromCache != null) {
			/**
			 * #read should return String from first instance of FileService for this file.
			 */
			comments = fromCache.comments;
			return fromCache.read();
		}

		if (isRead)
			return content;
		isRead = true;
		InputStreamReader in = null;
		BufferedReader br = null;

		// Read file to String. Every line end by \n - break line.
		try {
			in = new InputStreamReader(open());
			br = new BufferedReader(in);
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = br.readLine()) != null)
				sb.append(line + "\n"); // break line
			content = sb.toString();
			comments = findComments();
		} finally {
			close(is, in, br);
		}
		SERVICE_BUFFER.add(new FileServiceBuffer(this));
		return content;
	}

	/**
	 * Override this method for find file comments.
	 * 
	 * @return
	 */
	protected String getCommentRegex() {
		return "";
	}

	protected String[] findComments() {
		if (StringUtils.isEmpty(getCommentRegex()))
			return comments;
		Matcher m = Pattern.compile(getCommentRegex()).matcher(content);
		List<String> tmpList = new ArrayList<>();
		while (m.find())
			tmpList.add(m.group());
		return tmpList.toArray(new String[tmpList.size()]);
	}

	/**
	 * Open file by {@link FileInputStream} implementation of InputStream
	 */
	@Override
	public InputStream open() throws IOException {
		if (is == null)
			is = new FileInputStream(file);
		return is;
	}

	@Override
	public void close() throws IOException {
		close(is);
	}

	/**
	 * Use this method for closing streams.
	 * 
	 * @param closeable
	 */
	protected void close(Closeable... closeable) {
		for (Closeable c : closeable)
			FileUtils.close(c);
	}

	/**
	 * Close file on finalize
	 */
	@Override
	protected void finalize() throws Throwable {
		close();
		super.finalize();
	}

	/**
	 * Return file passed to constructor.
	 * 
	 * @return
	 */
	public File getFile() {
		return file;
	}

	/**
	 * Return <code>true</code> if {@link #read()} was invoked at least once.
	 * 
	 * @return
	 */
	public boolean isRead() {
		return isRead;
	}

	/**
	 * Return file comments.
	 * 
	 * @return
	 */
	public String[] getComments() {
		if (!isRead)
			_read();
		return comments;
	}

	/**
	 * Invoke {@link #read()} and handle exception by simple try&catch
	 */
	protected void _read() {
		try {
			read();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Return {@link FileService} from cache if file wasn't modified since
	 * {@link #_read()}, in otherwise return null.
	 * 
	 * @return
	 */
	private FileService getFromCache() {
		@SuppressWarnings("unlikely-arg-type")
		int cacheNumber = SERVICE_BUFFER.indexOf(this);
		if (cacheNumber == -1)
			return null;
		FileServiceBuffer cacheObject = SERVICE_BUFFER.get(cacheNumber);
		if (cacheObject.isExpired()) {
			SERVICE_BUFFER.remove(cacheObject);
			return null;
		}
		return cacheObject.service;
	}

	/**
	 * Class for store cache object.
	 * 
	 * @author Emil Wojewódka
	 *
	 * @since 24 mar 2018
	 */
	static class FileServiceBuffer {

		/**
		 * Object to store in cache.
		 */
		private final FileService service;

		/**
		 * Time of create cache object in unix time. It's neccessery for recognize is
		 * file modified since last {@link FileService#read()}
		 */
		private volatile long time;

		/**
		 * Create instance of cache object for {@link FileService}
		 * 
		 * @param service
		 */
		public FileServiceBuffer(FileService service) {
			this.service = service;
			this.time = System.currentTimeMillis();
		}

		/**
		 * Return true if file wansn't modified since last {@link FileService#read()}
		 * 
		 * @return
		 */
		private boolean isExpired() {
			return service.getFile().lastModified() > time;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((service == null) ? 0 : service.hashCode());
			result = prime * result + (int) (time ^ (time >>> 32));
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			FileServiceBuffer other = (FileServiceBuffer) obj;

			if (service == null) {
				if (other.service != null)
					return false;
				/** If File#equals(Object) are different - they're not equals. */
			} else if (!service.getFile().equals(other.service.getFile()))
				return false;
			return true;
		}

	}

}
