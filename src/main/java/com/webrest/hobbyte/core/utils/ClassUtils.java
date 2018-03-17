package com.webrest.hobbyte.core.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.reflections.Reflections;
import org.reflections.util.ConfigurationBuilder;
import org.slf4j.Logger;

import com.webrest.hobbyte.core.logger.LoggerFactory;

public class ClassUtils {

	public static final Class<?>[] EMPTY_ARRAY = new Class[0];

	private static final Logger LOGGER = LoggerFactory.getLogger();

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
	public static <T> Set<Class<? extends T>> filterNonAbstract(Collection<Class<? extends T>> collection) {
		return collection.parallelStream().filter(x -> !Modifier.isAbstract(x.getModifiers()))
				.collect(Collectors.toSet());
	}

	public static <T> Set<Class<? extends T>> findNonAbstract(Class<T> clazz) {
		return filterNonAbstract(findSubTypes(clazz));
	}

	/**
	 * Return {@link Class} instance if there is a {@link Constructor} with passed
	 * <code>constructorParameters</code> types. If so, create new instance, and
	 * pass to that constructor parameters.
	 * 
	 * @see #getConstructor(Class, Class...)
	 * @param constructorClass
	 * @param constructorParameters
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T createInstance(Class<? extends T> constructorClass, Object... constructorParameters) {

		int arrayLength = constructorParameters == null ? 0 : constructorParameters.length;
		Class<?>[] constructorParameterClasses = new Class<?>[arrayLength];

		for (int i = 0; i < arrayLength; i++)
			constructorParameterClasses[i] = constructorParameters[i].getClass();

		Constructor<?> constructor = getConstructor(constructorClass, constructorParameterClasses);

		try {
			return (T) constructor.newInstance(constructorParameters);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Return {@link Constructor} of {@link Class}. If Class<?> array parameter is
	 * null there will be replaced by {@link #EMPTY_ARRAY}, so returned
	 * {@link Constructor} will be without arguments. </br>
	 * Also method return <code>null</code> if there is no
	 * {@link Class#getConstructor(Class...)} with specified constructor parameter
	 * types.
	 * 
	 * @param constructorClass
	 * @param constructorParameterClasses
	 * @return
	 */
	public static <T> Constructor<? extends T> getConstructor(Class<? extends T> constructorClass,
			Class<?>... constructorParameterClasses) {
		if (constructorClass == null)
			return null;

		if (constructorParameterClasses == null)
			constructorParameterClasses = EMPTY_ARRAY;

		try {
			return constructorClass.getConstructor(constructorParameterClasses);
		} catch (NoSuchMethodException | SecurityException e) {
			LOGGER.info("Can't find constructor of (" + constructorClass.getName() + " ) class with parameters "
					+ StringUtils.toGenericString(constructorParameterClasses, ","));
			e.printStackTrace();
		}
		return null;
	}

}
