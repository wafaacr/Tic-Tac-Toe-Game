/**
 * 
 */
package tic.tactoe;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import tic.tactoe.R;

/**
 * @author owner
 *
 */
public class MainFragment extends Fragment {

	private AlertDialog mDialog;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							Bundle savedInstanceState){
		View rootView = inflater.inflate(R.layout.fragment_main, container, false);
		// Handle buttons here
		
		// new game button
		View newButton = rootView.findViewById(R.id.new_button);
		newButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),GameActivity.class);
				getActivity().startActivity(intent);
			}
		});
		
		View continueButton = rootView.findViewById(R.id.continue_button);
		continueButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), GameActivity.class);
				intent.putExtra(GameActivity.KEY_RESTORE,  true);
				getActivity().startActivity(intent);
			}
		});
		
		View aboutButton = rootView.findViewById(R.id.about_button);
		aboutButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
				builder.setTitle(R.string.about_title);
				builder.setMessage(R.id.title);
				builder.setCancelable(false);
				builder.setPositiveButton(R.string.ok_label, new DialogInterface.OnClickListener() {		
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						
					}
				});
				mDialog = builder.show();
			}
		});
		return rootView;
	}
	
	public void onPause(){
		super.onPause();
		
		// Get rid of the about dialog if it's still up
		if(mDialog != null)
			mDialog.dismiss();
	}
}
