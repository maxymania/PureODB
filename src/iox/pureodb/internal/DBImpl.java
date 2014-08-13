package iox.pureodb.internal;

import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;

import iox.pureodb.DB;

public class DBImpl implements DB {
	private ObjectManager manager;
	private Graph grph;
	public DBImpl(ClassLoader loader,Graph gph){
		manager = new ObjectManager(loader);
		grph = gph;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T  load(Object id) {
		Vertex vtx = grph.getVertex(id);
		if(vtx==null)return null;
		String name = vtx.getProperty("__class__");
		try {
			return (T)manager.proxyFor(name, new ObjectWrapper(manager,vtx));
		} catch (ReflectiveOperationException e) {
			throw new RuntimeException(e);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T createObject(Class<T> cls, Object id) {
		Vertex vtx = grph.addVertex(id);
		String name = cls.getName();
		vtx.setProperty("__class__", name);
		try {
			return (T)manager.proxyFor(name, new ObjectWrapper(manager,vtx));
		} catch (ReflectiveOperationException e) {
			throw new RuntimeException(e);
		}
	}
}
