package com.citrus.suzaku;

import android.app.*;
import android.content.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import java.util.*;

import android.support.v4.app.DialogFragment;


// Get Title and Create New Playlist
public class PlaylistCreateDialog extends DialogFragment implements View.OnClickListener
{
/*	public static interface Callback
	{
		public void onPlaylistCreateDialogResult();
	}

	private Callback mCallback;
*/
	private EditText editText;

/*	@Override
	public void onAttach(Activity activity)
	{
		super.onAttach(activity);

		Fragment fragment = getTargetFragment();
		try{
			mCallback = (Callback)((fragment != null)? fragment : activity);
		}catch(ClassCastException e){
			throw(e);
		}
	}
*/
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View content = inflater.inflate(R.layout.dialog_create_playlist, null, false);

		editText = (EditText)content.findViewById(R.id.edit);
		Button okButton = (Button)content.findViewById(R.id.okButton);
		Button cancelButton = (Button)content.findViewById(R.id.cancelButton);
		
		okButton.setOnClickListener(this);
		cancelButton.setOnClickListener(this);
		
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(R.string.create_new_playlist);
		builder.setView(content);
	//	builder.setPositiveButton(android.R.string.ok, this);
	//	builder.setNegativeButton(android.R.string.cancel, this);
		
		return builder.create();
	}
/*
	@Override
	public void onDetach()
	{
		super.onDetach();
		mCallback = null;
	}
*/
	@Override
	public void onClick(View v)
	{
		switch(v.getId()){
			case R.id.okButton:
				
				Playlist playlist = new Playlist(null);
				playlist.title = editText.getText().toString();

				if(playlist.title == null || playlist.title.isEmpty()){
					Toast.makeText(App.getContext(), R.string.warn_input_playlist_title, Toast.LENGTH_SHORT).show();
					return;
				}

				String[] selectionArgs = { playlist.title };
				List<Playlist> playlists = (new MusicDB()).getPlaylists(MusicDB.Playlists.TITLE + " = ?", selectionArgs, null);
				if(playlists.size() != 0){
					Toast.makeText(App.getContext(), R.string.warn_title_already_exists, Toast.LENGTH_SHORT).show();
					return;
				}

				Intent intent = new Intent(MusicDBService.ACTION_CREATE_PLAYLIST);
				intent.putExtra(MusicDBService.INTENT_KEY_PLAYLIST, playlist);
				intent.setPackage(App.PACKAGE);
				getActivity().startService(intent);
			
				dismiss();

				break;
				
			case R.id.cancelButton:
				dismiss();
				break;
		}
	}

}
