package uk.co.commandandinfluence;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.pusher.client.Pusher;
import com.pusher.client.channel.Channel;
import com.pusher.client.channel.SubscriptionEventListener;
import com.pusher.client.connection.ConnectionEventListener;
import com.pusher.client.connection.ConnectionState;
import com.pusher.client.connection.ConnectionStateChange;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Pusher pusher = new Pusher("7d3ebc72c0912d712cd6");
		
		final ListView list = (ListView) findViewById(R.id.main_list);
		
		final List<String> commands = new ArrayList<String>();
		
		/**
		 * Configure the list view to an array backup data
		 */
		final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, commands);
		list.setAdapter(adapter);
		
		// Connect
		pusher.connect(new ConnectionEventListener() {

			@Override
			public void onConnectionStateChange(ConnectionStateChange change) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onError(String message, String code, Exception e) {
				// TODO Auto-generated method stub
				
			}
			
		}, ConnectionState.ALL);
		
		// Subscribe to channel
		Channel channel = pusher.subscribe("game-1");
		
		// Listen to an event
		channel.bind("command", new SubscriptionEventListener() {

			@Override
			public void onEvent(String channel, String event, String data) {
				// Do something!
				Gson gson = new Gson();
				final Command command = gson.fromJson(data, Command.class);
				
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						commands.add(0, command.text);
						adapter.notifyDataSetChanged();
					}
					
				});
				
			}
			
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	class Command {
		private String text = "";
		
		Command() {
			
		}
	}
}
