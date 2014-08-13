package iox.pureodb;

public interface DB {
	<T> T load(Object id);
	<T> T createObject(Class<T> cls,Object id);
}
