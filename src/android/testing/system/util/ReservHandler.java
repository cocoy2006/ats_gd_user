package android.testing.system.util;

import java.util.List;

public class ReservHandler {

	private List<String> servers;
	
	public ReservHandler() {
		servers = Util.getInstance().getServers();
	}
	
	public String handle() {
		StringBuffer sb = new StringBuffer();
		for(String server : servers) {
			sb.append(server).append(",");
		}
		return sb.toString();
	}
}
