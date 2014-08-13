package iox.pureodb.internal;

import iox.pureodb.Delete;
import iox.pureodb.Get;
import iox.pureodb.Id;
import iox.pureodb.RefAdd;
import iox.pureodb.RefClear;
import iox.pureodb.RefGet;
import iox.pureodb.RefMultiGet;
import iox.pureodb.RefSet;
import iox.pureodb.Set;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Collection;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;

public class ObjectWrapper implements InvocationHandler,Comparable<Object> {
	public final ObjectManager manager;
	public final Vertex wrapped;
	
	public ObjectWrapper(ObjectManager manager, Vertex wrapped) {
		super();
		this.manager = manager;
		this.wrapped = wrapped;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		Object result=null;
		Set set = method.getAnnotation(Set.class);
		Get get = method.getAnnotation(Get.class);
		RefGet rget = method.getAnnotation(RefGet.class);
		RefSet rset = method.getAnnotation(RefSet.class);
		RefAdd radd = method.getAnnotation(RefAdd.class);
		RefMultiGet rmget = method.getAnnotation(RefMultiGet.class);
		RefClear rclear = method.getAnnotation(RefClear.class);
		
		boolean id = method.getAnnotation(Id.class)!=null;
		boolean delete = method.getAnnotation(Delete.class)!=null;
		boolean getThis = method.getAnnotation(GetVirtualEntity.class)!=null;
		if(set!=null){
			Object property = args[0];
			if(property==null)
				wrapped.removeProperty(set.value());
			else
				wrapped.setProperty(set.value(), property);
			result = Util.notNull(method.getReturnType());
		}else if(get!=null){
			result = wrapped.getProperty(get.value());
			if(result==null)
				result = Util.notNull(method.getReturnType());
		}else if(rget!=null){
			for(Vertex v:wrapped.getVertices(Direction.OUT, rget.value()))
				return manager.proxyFor(
						(String)v.getProperty("__class__"),
						new ObjectWrapper(manager,v));
			return null;
		}else if(rset!=null){
			Vertex other = ((VirtualEntityGetter)args[0]).getObjectWrapper().wrapped;
			for(Edge str:wrapped.getEdges(Direction.OUT, rset.value()))
				str.remove();
			wrapped.addEdge(rset.value(), other);
		}else if(radd!=null){
			Vertex other = ((VirtualEntityGetter)args[0]).getObjectWrapper().wrapped;
			wrapped.addEdge(radd.value(), other);
		}else if(rmget!=null){
			String rmgv = rmget.value();
			Collection<Object> objs = Util.makeCollection(method.getReturnType());
			result = objs;
			for(Vertex v:wrapped.getVertices(Direction.OUT, rmgv)){
				objs.add(manager.proxyFor(
						(String)v.getProperty("__class__"),
						new ObjectWrapper(manager,v)));
			}
		}else if(rclear!=null){
			for(Edge str:wrapped.getEdges(Direction.OUT, rclear.value()))
				str.remove();
		}else if(id){
			result = wrapped.getId();
		}else if(delete){
			wrapped.remove();
			result = Util.notNull(method.getReturnType());
		}else if(getThis){
			result = this;
		}else result = method.invoke(this, args);
		return result;
	}

	@Override
	public int compareTo(Object o) {
		int other = o==null?0:o.hashCode();
		return hashCode()-other;
	}

	@Override
	public String toString() {
		return ""+wrapped.getProperty("__class__")+"@"+Integer.toHexString(wrapped.hashCode());
	}
}
