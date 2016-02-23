package tic.tactoe;

import android.support.v7.app.ActionBarActivity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class GameActivity extends ActionBarActivity {

	public static final String KEY_RESTORE = "key_restore";
	public static final String PREF_RESTORE = "pref_restore";
	private GameFragment mGameFragment;
	
	private MediaPlayer mMediaPlayer;
	private Handler mHandler = new Handler();
	
	protected void onResume(){
		super.onResume();
		mMediaPlayer = MediaPlayer.create(this, R.raw.power_juice);
		mMediaPlayer.setLooping(true);
		mMediaPlayer.start();
	}
	
	
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
		mHandler.removeCallbacks(null);
		mMediaPlayer.stop();
		mMediaPlayer.reset();
		mMediaPlayer.release();
		String gameData = mGameFragment.getState();
		getPreferences(MODE_PRIVATE).edit().putString(PREF_RESTORE, gameData).commit();
		Log.d("UT3", "state = " + gameData);
	}
	
	public void restartGame(){
		mGameFragment.restartGame();
	}
	
	public void reportWinner(final Tile.Owner winner){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		if(mMediaPlayer != null && mMediaPlayer.isPlaying()){
			mMediaPlayer.stop();
			mMediaPlayer.reset();
			mMediaPlayer.release();
		}
		builder.setMessage(getString(R.string.declare_winner,winner));
		builder.setCancelable(false);
		builder.setPositiveButton(R.string.ok_label, new DialogInterface.OnClickListener() {		
			@Override
			public void onClick(DialogInterface dialog, int i) {
				finish();
			}
		});
		final Dialog dialog = builder.create();
		mHandler.postDelayed(new Runnable(){
			public void run(){
				mMediaPlayer = MediaPlayer.create(GameActivity.this,
						winner == Tile.Owner.X ? R.raw.magic_wand_1
								: winner == Tile.Owner.O ? R.raw.beep
								: R.raw.magic_wand_1
				);
				mMediaPlayer.start();
				dialog.show();
			}
		}, 500);
		
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

	public void startThinking() {
		View thinkView = findViewById(R.id.thinking);
		thinkView.setVisibility(View.VISIBLE);
	}

	public void stopThinking() {
		View thinkView = findViewById(R.id.thinking);
		thinkView.setVisibility(View.GONE);
	}
}
