package com.webrest.hobbyte.core.utils;

import java.io.File;
import java.io.FileFilter;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.aspectj.util.FileUtil;
import org.reflections.Reflections;
import org.reflections.util.ConfigurationBuilder;

public class ClassUtils {

	public static final Class<?>[] EMPTY_ARRAY = new Class[0];

	/**
	 * Return collection of subtypes of passes class. </br>
	 * Scanner look at everything lower than "com.webrest.hobbyte" package. </br>
	 * 
	 * TODO: I'm not sure set filterInputsBy by file extension is good solution.
	 * 
	 * @param clazz
	 * @return
	 */
	public static <T> Set<Class<? extends T>> findSubTypes(Class<T> clazz) {
		ConfigurationBuilder reflectionBuilder = new ConfigurationBuilder();
		reflectionBuilder.addClassLoader(ClassUtils.class.getClassLoader()).forPackages("com.webrest.hobbyte")
				.filterInputsBy(x -> x.endsWith(".class") || x.endsWith(".java")).useParallelExecutor();

		Reflections reflections = new Reflections(reflectionBuilder);

		return reflections.getSubTypesOf(clazz);
	}

	/**
	 * Filter passed collection and return only non abstract classes.
	 * 
	 * @param collection
	 * @return
	 */
	public static <T> Set<Class<? extends T>> onlyNonAbstract(Collection<Class<? extends T>> collection) {
		return collection.parallelStream().filter(x -> !Modifier.isAbstract(x.getModifiers()))
				.collect(Collectors.toSet());
	}

	public static File[] findProjectFiles() {
		return FileUtil.listFiles(new File("."), new FileFilter() {

			@Override
			public boolean accept(File pathname) {
				String name = pathname.getName();
				return name.startsWith("script") && name.endsWith(".sql");
			}
		});
	}

	public static void addValueToField(Object obj, Object valueToAdd) {
		Field[] fields = obj.getClass().getFields();
		for (Field f : fields) {
			if (f.getType().equals(valueToAdd.getClass()))
				try {
					f.set(obj, valueToAdd);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
		}
	}

}
