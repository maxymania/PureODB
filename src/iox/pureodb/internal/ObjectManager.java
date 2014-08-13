package iox.pureodb.internal;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class ObjectManager {
	public final ClassLoader loader;
	private Map<String,Class<?>> proxies = new HashMap<String,Class<?>>();
	
	public ObjectManager(ClassLoader loader) {
		super();
		this.loader = loader;
	}
	
	public Object proxyFor(String cls, InvocationHandler ih) throws ReflectiveOperationException {
		Class<?> clz = getProxyClass(cls);
		return clz.getConstructor(InvocationHandler.class).newInstance(ih);
	}
	
	private synchronized Class<?> getProxyClass(String cls) throws ReflectiveOperationException {
		Class<?> clz = proxies.get(cls);
		if(clz!=null)
			return clz;
		Class<?> iclz = loader.loadClass(cls);
		clz = Proxy.getProxyClass(loader, iclz, Comparable.class, VirtualEntityGetter.class);
		proxies.put(cls, clz);
		return clz;
	}
}
