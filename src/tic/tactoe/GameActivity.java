package tic.tactoe;

import android.support.v7.app.ActionBarActivity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class GameActivity extends ActionBarActivity {

	public static final String KEY_RESTORE = "key_restore";
	public static final String PREF_RESTORE = "pref_restore";
	private GameFragment mGameFragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		
		// Restore game here
		mGameFragment = (GameFragment)getFragmentManager().findFragmentById(R.id.fragment_game);
		boolean restore = getIntent().getBooleanExtra(KEY_RESTORE, false);
		if(restore){
			String gameData = getPreferences(MODE_PRIVATE).getString(PREF_RESTORE, null);
			if(gameData != null){
				mGameFragment.putState(gameData);
			}
		}
		Log.d("UT3", "restore = " + restore);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		String gameData = mGameFragment.getState();
		getPreferences(MODE_PRIVATE).edit().putString(PREF_RESTORE, gameData).commit();
		Log.d("UT3", "restore = " + gameData);
	}
	
	public void restartGame(){
		mGameFragment.restartGame();
	}
	
	public void reportWinner(final Tile.Owner winner){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(getString(R.string.declare_winner,winner));
		builder.setCancelable(false);
		builder.setPositiveButton(R.string.ok_label, new DialogInterface.OnClickListener() {		
			@Override
			public void onClick(DialogInterface dialog, int i) {
				finish();
			}
		});
		final Dialog dialog = builder.create();
		dialog.show();
		
		// Reset the board to the initial position
		mGameFragment.initGame();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
