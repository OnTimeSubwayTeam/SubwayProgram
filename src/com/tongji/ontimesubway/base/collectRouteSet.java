package com.tongji.ontimesubway.base;

import java.util.ArrayList;

public class collectRouteSet {
	private ArrayList<CollectRoute> collectrouteset;
	
	
	public CollectRoute getRoute(int index)
	{
		return collectrouteset.get(index);
	}
	public Boolean addRoute(CollectRoute addroute)
	{
		if(collectrouteset.add(addroute))
			return true;
		else 
			return false;
	}
	public boolean removeRoute(CollectRoute removeroute)
	{
		if(collectrouteset.remove(removeroute))
			return true;
		else 
			return false;
	}

}
