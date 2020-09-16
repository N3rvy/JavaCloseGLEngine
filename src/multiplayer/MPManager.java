package multiplayer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import org.lwjgl.util.vector.Vector3f;

import components.Transform;
import tools.Loader;

public class MPManager extends GameManager {
	
	private Socket socket = null;
	private PrintWriter printer = null;
	private BufferedReader reader = null;
	
	private Vector3f playerPosition;
	
	public MPManager(Loader loader, Transform transform) {
		super(loader);
		this.playerPosition = transform.getPosition();
	}

	public void Connect(String ip) {
		try {
			socket = new Socket("127.0.0.1", 4562);
			printer = new PrintWriter(socket.getOutputStream());
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		SendMessage(String.join(" ",
				"pos",
				String.valueOf(playerPosition.x),
				String.valueOf(playerPosition.y),
				String.valueOf(playerPosition.z),
				String.valueOf(playerPosition.y)));
	}
	
	public void Loop() {
		try {
			if (socket.isConnected() && reader.ready()) {
				String[] commands = reader.readLine().split("\n");
				
				for (String cmd : commands)
					CheckMessage(cmd.split(" "));
				
				SendMessage(String.join(" ",
						"pos",
						String.valueOf(playerPosition.x),
						String.valueOf(playerPosition.y),
						String.valueOf(playerPosition.z),
						String.valueOf(playerPosition.y)));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void Disconnect() {
		SendMessage("disconnect");
	}
	
	private void SendMessage(String message) {
		printer.println(message);
		printer.flush();
	}
	
	private void CheckMessage(String[] args) {
		System.out.println(String.join(" ", args));
		
		int playerID = Integer.parseInt(args[1]);
		switch(args[0]) {
		case "pos":
			if (players.containsKey(playerID))
				updatePlayer(
						playerID,
						new Vector3f(
							Float.parseFloat(args[2]),
							Float.parseFloat(args[3]),
							Float.parseFloat(args[4])),
						Float.parseFloat(args[5]));
			else
				spawnPlayer(playerID, 
						new Vector3f(
								Float.parseFloat(args[2]),
								Float.parseFloat(args[3]),
								Float.parseFloat(args[4])),
						Float.parseFloat(args[5]));
			break;
		case "remove":
			removePlayer(Integer.parseInt(args[1]));
			break;
		}
	}
}