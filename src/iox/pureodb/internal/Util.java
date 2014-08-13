package iox.pureodb.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class Util {
	static final Number NUL = 0;
	static final char CNUL = '\0';
	public static Object notNull(Class<?> cls){
		if(boolean.class.equals(cls))
			return false;
		if(byte.class.equals(cls))
			return NUL.byteValue();
		if(short.class.equals(cls))
			return NUL.shortValue();
		if(int.class.equals(cls))
			return NUL.intValue();
		if(long.class.equals(cls))
			return NUL.longValue();
		if(float.class.equals(cls))
			return NUL.floatValue();
		if(double.class.equals(cls))
			return NUL.doubleValue();
		if(char.class.equals(cls))
			return CNUL;
		return null;
	}
	public static Collection<Object> makeCollection(Class<?> cls) {
		if(List.class.equals(cls)||
				ArrayList.class.equals(cls))
			return new ArrayList<Object>();
		if(Queue.class.equals(cls)||
				Deque.class.equals(cls)||
				LinkedList.class.equals(cls))
			return new LinkedList<Object>();
		if(Set.class.equals(cls)||
				HashSet.class.equals(cls))
			return new HashSet<Object>();
		if(SortedSet.class.equals(cls)||
				TreeSet.class.equals(cls))
			return new TreeSet<Object>();
		return new ArrayList<Object>();
	}
}
