package org.rexpd.core.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract class EnumUtils {

	public static <T extends Enum<T>> T valueOf(Class<T> enumType, Comparable<T> comparable) {
		for (T v : values(enumType)) {
			if (comparable.compareTo(v) == 0) {
				return v;
			}
		}
		throw new IllegalArgumentException(
				"No enum const " + enumType + "." + comparable);
	}

	public static <T extends Enum<T>> T fromString(Class<T> enumType, String name) {
		for (T v : values(enumType)) {
			if (name.equals(v.toString())) {
				return v;
			}
		}
		throw new IllegalArgumentException("No enum const " + enumType + "." + name);
	}

	@SuppressWarnings("unchecked")
	private static <T extends Enum<T>> T[] values(Class<T> enumType) {
		Throwable t = null;
		try {
			Method values = enumType.getMethod("values");
			return (T[])values.invoke(null);
		} catch (SecurityException e) {
			t = e;
		} catch (NoSuchMethodException e) {
			t = e;
		} catch (IllegalArgumentException e) {
			t = e;
		} catch (IllegalAccessException e) {
			t = e;
		} catch (InvocationTargetException e) {
			t = e;
		}

		throw new IllegalArgumentException("No enum const " + enumType, t);
	}

}
