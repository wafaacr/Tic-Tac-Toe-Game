/**
 * 
 */
package tic.tactoe;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author owner
 *
 */
public class ControlFragment extends Fragment {

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							Bundle savedInstanceState){
		View rootView = inflater.inflate(R.layout.fragment_control, container, false);
		// Handle buttons here
		
		View main = rootView.findViewById(R.id.button_main);
		main.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				getActivity().finish();
			}
		});
		
		View restart = rootView.findViewById(R.id.button_restart);
		restart.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				((GameActivity)getActivity()).restartGame();
			}
		});
		return rootView;
	}
}
