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
