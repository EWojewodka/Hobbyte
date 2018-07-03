package com.webrest.hobbyte.core.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.reflections.Reflections;
import org.reflections.util.ConfigurationBuilder;
import org.slf4j.Logger;

import com.webrest.hobbyte.core.logger.LoggerFactory;
import com.webrest.hobbyte.core.utils.functions.ExceptionStream;

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
		return new Reflections(reflectionBuilder).getSubTypesOf(clazz);
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

	/**
	 * Return only non abstract subclasses of passed clazz.
	 * 
	 * @see #findSubTypes(Class)
	 * @see #filterNonAbstract(Collection)
	 * @param clazz
	 * @return
	 */
	public static <T> Set<Class<? extends T>> findNonAbstract(Class<T> clazz) {
		return filterNonAbstract(findSubTypes(clazz));
	}

	/**
	 * Return array of {@link Field} which are annotated by passed to parameter
	 * {@link Annotation} in specified {@link Class}
	 * 
	 * @param clazz
	 * @param annotation
	 * @return
	 */
	public static Field[] getAnnotatedFields(Class<?> clazz, Class<? extends Annotation> annotation) {
		Asserts.notNull(clazz, "Cannot get any fields from null class :<");
		Asserts.notNull(annotation,
				"Cannot get any fields from " + clazz.getName() + " because passed annotation is null.");

		List<Field> fields = new ArrayList<>();
		Field[] _fields = clazz.getDeclaredFields();
		for (Field field : _fields) {
			if (field.isAnnotationPresent(annotation)) {
				field.setAccessible(true);
				fields.add(field);
			}
		}
		return fields.toArray(new Field[fields.size()]);
	}

	public static Method[] getAnnotatedMethods(Class<?> clazz, Class<? extends Annotation> annotation) {
		Asserts.notNull(clazz, "Cannot get any methods from null class :<");
		Asserts.notNull(annotation,
				"Cannot get any methods from " + clazz.getName() + " because passed annotation is null.");

		List<Method> methods = new ArrayList<>();
		Method[] _methods = clazz.getDeclaredMethods();
		for (Method method : _methods) {
			if (method.isAnnotationPresent(annotation)) {
				method.setAccessible(true);
				methods.add(method);
			}
		}
		return methods.toArray(new Method[methods.size()]);
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

		return (T) ExceptionStream.printOnFailure().call(() -> {
			return (T) constructor.newInstance(constructorParameters);
		}).get();
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
	@SuppressWarnings("unchecked")
	public static <T> Constructor<? extends T> getConstructor(Class<? extends T> constructorClass,
			Class<?>... constructorParameterClasses) {
		if (constructorClass == null)
			return null;

		if (constructorParameterClasses == null)
			constructorParameterClasses = EMPTY_ARRAY;
		Class<?>[] conClazz = constructorParameterClasses;
		return (Constructor<? extends T>) ExceptionStream.printOnFailure().call(() -> {
			return constructorClass.getConstructor(conClazz);
		}).get();
	}

	@SuppressWarnings("rawtypes")
	public static Class<?> getGenericType(Class<?> clazz) {
		return (Class) ((ParameterizedType) clazz.getGenericSuperclass()).getActualTypeArguments()[0];
	}

	public static void setProperty(Field field, Object target, Object newValue) throws Exception {
		Asserts.notNull(field, "Field cannot be null");
		Asserts.notNull(target, "Target cannot be null");
		if (!field.isAccessible())
			field.setAccessible(true);

		Class<?> type = field.getType();
		Class<? extends Object> newValueClazz = newValue.getClass();
		if (type.isAssignableFrom(newValueClazz)) {
			setField(field, target, newValue);
			return;
		}

		if (type == Integer.class || (type.isPrimitive() && type.getName().equals("int"))) {
			int asInt = StringUtils.getAsInt(newValue, Integer.MIN_VALUE);
			if (asInt != Integer.MIN_VALUE)
				setField(field, target, asInt);
		} else if (type == Date.class) {
			setField(field, target, DateUtils.parseDate(String.valueOf(newValue)));
		} else if (type == String.class) {
			setField(field, target, String.valueOf(newValue));
		} else if (type == Class.class) {
			setField(field, target, Class.forName(String.valueOf(newValue)));
		}

	}

	public static void setField(Field field, Object target, Object newValue) {
		try {
			field.set(target, newValue);
		} catch (Exception e) {
			LOGGER.info("Field name: {}, Field type: {}, Value type: {}, Value: {}",
					target.getClass() + "." + field.getName(), field.getType(),
					newValue == null ? "null" : newValue.getClass(), newValue);
			e.printStackTrace();
		}
	}

}
