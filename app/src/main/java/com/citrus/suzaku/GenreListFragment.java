package com.citrus.suzaku;

import android.content.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import java.util.*;


public class GenreListFragment extends TrackGroupListFragment<Genre>
{
	
	public static GenreListFragment newInstance(int loaderId)
	{
		GenreListFragment fragment = new GenreListFragment();
		Bundle bundle = new Bundle();

		bundle.putInt(LOADER_ID, loaderId);
		fragment.setArguments(bundle);

		return fragment;
	}
	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);

		setListAdapter(new GenreListAdapter());
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id)
	{
		GenreFragment fragment = GenreFragment.newInstance((Genre)l.getItemAtPosition(position));
		((MainActivity)getActivity()).replaceFragment(fragment);
	}
	
	@Override
	protected List<Genre> getDataList()
	{
		return (new MusicDB()).getAllGenres();
	}
	
	
	// Adapter
	private class GenreListAdapter extends BaseListAdapter<Genre>
	{
		private LayoutInflater inflater;


		public GenreListAdapter()
		{
			Context context = App.getContext();
			inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		@Override
		public void bindView(int position, View view)
		{
			ViewHolder holder = (ViewHolder)view.getTag();

			Genre item = getItem(position);
			holder.genreTextView.setText(item.getGenreString());
			
			view.setActivated(getListView().isItemChecked(position));
		}

		@Override
		public View newView(ViewGroup parent)
		{
			View view = inflater.inflate(R.layout.listitem_genre, parent, false);

			ViewHolder holder = new ViewHolder();
			holder.genreTextView = (TextView)view.findViewById(R.id.genre);

			view.setTag(holder);

			return view;
		}
	}
	
	private static class ViewHolder
	{
		TextView genreTextView;
//		TextView numArtistTextView;
//		TextView numSongsTextView;
	}
	
}
