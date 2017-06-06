package com.citrus.suzaku;

import android.content.*;
import android.os.*;
import android.support.v4.app.*;
import android.view.*;
import java.util.*;

// Attached to MainActivity
public class GenreFragment extends Fragment
{
	private static final String FRAGMENT_TAG = "GenreAlbumListFragment";
	private static final int LOADER_ID = 5001;
	
	private Genre genreItem;
	
	
	public static GenreFragment newInstance(Genre genre)
	{
		GenreFragment fragment = new GenreFragment();
		Bundle bundle = new Bundle();

		bundle.putSerializable("GENRE", genre);
		fragment.setArguments(bundle);

		return fragment;
	}
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_base, container, false);

		((MainActivity)getActivity()).showDrawerIndicator(false);
		setHasOptionsMenu(true);

		// Data

		genreItem = (Genre)getArguments().getSerializable("GENRE");

		// UI
		
		((MainActivity)getActivity()).getSupportActionBar().setTitle(genreItem.getGenreString());

		// Fragment

		if(savedInstanceState == null){			// 非再生成時
			getChildFragmentManager()
			.beginTransaction()
			.replace(R.id.list, GenreAlbumListFragment.newInstance(LOADER_ID, genreItem), FRAGMENT_TAG)
			.commit();
		}
		
		return view;
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
	{
		super.onCreateOptionsMenu(menu,inflater);

		inflater.inflate(R.menu.menu_fragment, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch(item.getItemId()){
			case android.R.id.home:				// up button
			//	finish();
				return true;
			
			case R.id.menu_shuffle:

				Intent intent = PlayerService.newPlayIntent(PlaylistManager.PLAY_RANGE_TRACKS, genreItem, 0, true);
				getActivity().startService(intent);

				boolean ps = MyPreference.getBoolean(MyPreference.PLAYER_SCREEN);
				if(ps){
					startActivity(new Intent(getActivity(), TrackActivity.class));
				}

				return true;
			
			default:
				return super.onOptionsItemSelected(item);
		}
	}


	public static class GenreAlbumListFragment extends AlbumListFragment
	{
		private final static String GENRE = "GENRE";
		
		private Genre genre;
		
		public static GenreAlbumListFragment newInstance(int loaderId, Genre genre)
		{
			GenreAlbumListFragment fragment = new GenreAlbumListFragment();
			Bundle bundle = new Bundle();

			bundle.putInt(LOADER_ID, loaderId);
			bundle.putSerializable(GENRE, genre);
			fragment.setArguments(bundle);

			return fragment;
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
		{
			genre = (Genre)getArguments().getSerializable(GENRE);
			return super.onCreateView(inflater, container, savedInstanceState);
		}
		
		@Override
		protected List<Album> getDataList()
		{
			return genre.getAlbums();
		}
		
	}
	
}
