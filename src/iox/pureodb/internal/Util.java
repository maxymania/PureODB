/*
   Copyright 2014 Simon Schmidt

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
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
