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

import org.junit.Assert;
import org.springframework.util.StringUtils;

import com.webrest.hobbyte.core.utils.FileUtils;

/**
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

	public FileService(File file) {
		Assert.assertNotNull("Cannot init file service for null file", file);
		Assert.assertTrue(String.format("File (%s) doesn't exists.", file.getPath()), file.exists());
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
		if (isRead)
			return content;
		isRead = true;
		InputStreamReader in = null;
		BufferedReader br = null;

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
		return content;
	}

	/**
	 * Override this method for find file comments.
	 * @return
	 */
	protected String getCommentRegex() {
		return "";
	}
	
	protected String[] findComments() {
		if(StringUtils.isEmpty(getCommentRegex()))
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
	 * @return
	 */
	public boolean isRead() {
		return isRead;
	}

	/**
	 * Return file comments.
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
}
