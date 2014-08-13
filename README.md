PureODB
=======

Object Database Based inspired by ZODB using [tinkerpops blueprint API](https://github.com/tinkerpop/blueprints/wiki).

There is also another project called [TinkerPop Frames](https://github.com/tinkerpop/frames/wiki) you could be interested in.
Basically PureODB does the same what Frames does: using InvocationHandler, Proxy classes, and Annotations to provide a Graph-Element to Object mapping.

Though there are some differences: Frames tries to "frame" all graph elements, Vertices and Edges, into Java Interfaces while PureODB is meant as a Persistence framework using a Graph Database as Backend.

Example
-------

In this example, we have 2 Model classes:


Building.java
```java
package datamodel;

import java.util.Collection;

import iox.pureodb.*;

public interface Building {
	@Id
	Object id();
	
	@Get("address")
	String getAddress();
	
	@Set("address")
	void setAddress(String a);
	
	@RefMultiGet("rooms")
	Collection<Room> getRooms();
	
	@RefAdd("rooms")
	void addRoom(Room r);
	
	@RefClear("rooms")
	void clearRooms();

	@Delete
	void delete();
}


```

Room.java
```java
package datamodel;

import iox.pureodb.*;

public interface Room {
	@Id
	Object id();
	
	@Get("name")
	String getName();
	
	@Set("name")
	void setName(String n);

	@Delete
	void delete();
}
```

main.java
```java
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.impls.tg.TinkerGraph;

import iox.pureodb.DB;
import iox.pureodb.internal.DBImpl;

import datamodel.*

// and in the body...

ClassLoader loader = ...

Graph gph = new TinkerGraph();
DB db = new DBImpl(loader,gph);


Building building = db.createObject(Building.class, null);
Room room = db.createObject(Room.class, null);
building.addRoom(room);

// later we can load the objects using their ids.

Object id = ...

Building building = db.load(id);

```
