package com.webrest.hobbyte.core.utils.files;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.util.StringUtils;

import com.webrest.hobbyte.core.utils.FileUtils;

/**
 * Common file finder from root path. </br>
 * By default it will search files in deep mode.
 * 
 * @author wojew
 *
 */
public class DeepFileFinder {

	private File root;

	private boolean deep = true;

	private String suffix = "";

	private String preffix = "";

	private String extension;

	public DeepFileFinder(String rootPath) {
		this(FileUtils.getFile(rootPath));
	}

	public DeepFileFinder(File rootDir) {
		rootDir = rootDir.getAbsoluteFile();
		if (rootDir == null || !rootDir.exists() || !rootDir.isDirectory()) {
			throw new IllegalArgumentException("Can't create DeepFileFinder for (" + rootDir.getAbsolutePath() + ")");
		}

		this.root = rootDir;
	}

	/**
	 * Find all files which match with specified rules.
	 * 
	 * @return
	 */
	public File[] find() {
		List<File> tmpList = new ArrayList<>();
		// invoke recursive method
		return find(root, tmpList, deep);
	}

	// recursive invoked method.
	private File[] find(File root, Collection<File> tmpCollection, boolean deep) {
		File[] children = root.listFiles();
		for (File f : children) {
			if (isCorrectFile(f))
				tmpCollection.add(f);
			else if (f.isDirectory())
				if(deep)
				find(f, tmpCollection, deep);
		}
		return tmpCollection.toArray(new File[tmpCollection.size()]);
	}

	// Checking that file is not directory, and if so, check specified prefix,
	// suffix and extension for passed rules.
	private boolean isCorrectFile(File f) {
		if (f.isDirectory())
			return false;
		String fName = f.getName();
		// get extension and remove them from file name.
		String ext = fName.substring(fName.lastIndexOf("."));
		fName = fName.replace(ext, "");
		return fName.startsWith(preffix) && fName.endsWith(suffix) && StringUtils.isEmpty(extension) ? true
				: ext.equals(extension);
	}

	public DeepFileFinder setDeep(boolean deep) {
		this.deep = deep;
		return this;
	}

	public DeepFileFinder setSuffix(String suffix) {
		this.suffix = suffix;
		return this;
	}

	public DeepFileFinder setPreffix(String preffix) {
		this.preffix = preffix;
		return this;
	}

	public DeepFileFinder setExtension(String extension) {
		this.extension = extension.startsWith(".") ? extension : "." + extension;
		return this;
	}

}
