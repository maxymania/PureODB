package iox.pureodb.internal;

public interface VirtualEntityGetter {
	@GetVirtualEntity
	ObjectWrapper getObjectWrapper();
}
