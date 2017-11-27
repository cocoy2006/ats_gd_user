package android.testing.system.util.servers;

import java.util.ArrayList;
import java.util.List;

/**
 * 	<servers>
 * 		<server>
 * 			<url>...</url>
 * 		</server>
 * 		...
 * 	</servers>
 * */
public class Servers {

	private List<Server> servers;
	
	public Servers() {
		servers = new ArrayList<Server>();
	}
	
	public void addServer(Server e) {
		servers.add(e);
	}
	
	public List<Server> getServers() {
		return servers;
	}
}
