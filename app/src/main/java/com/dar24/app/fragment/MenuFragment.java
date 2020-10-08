package com.dar24.app.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dar24.app.R;
import com.dar24.app.adapter.MenuAdapter;
import com.dar24.app.data.MenuItem;
import com.dar24.app.model.Menu;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Simon Mawole
 * @version 0.1.0
 * @email simonmawole2011@gmail.com
 * @since 0.1.0
 */
public class MenuFragment extends Fragment {

    @BindView(R.id.recyclerViewMenuTop)
    RecyclerView recyclerViewMenuTop;
    @BindView(R.id.recyclerViewMenuBottom)
    RecyclerView recyclerViewMenuBottom;

    private RecyclerView.LayoutManager layoutManagerTop, layoutManagerBottom;
    private MenuAdapter topAdapter;
    private MenuAdapter bottomAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_menu, container, false);
        ButterKnife.bind(this, rootView);

        topAdapter = new MenuAdapter(getActivity(), true);
        bottomAdapter = new MenuAdapter(getActivity(), false);

        layoutManagerTop = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,
                false);
        layoutManagerBottom = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,
                false);
        recyclerViewMenuTop.setLayoutManager(layoutManagerTop);
        recyclerViewMenuBottom.setLayoutManager(layoutManagerBottom);

        recyclerViewMenuTop.swapAdapter(topAdapter, true);
        recyclerViewMenuBottom.swapAdapter(bottomAdapter, true);

        topAdapter.addItems(MenuItem.getTopMenu(getActivity()));
        bottomAdapter.addItems(MenuItem.getBottomMenu(getActivity()));

        return rootView;
    }

    public void updateTopMenuItem(int position){
        Menu item = MenuItem.getTopMenu(getActivity()).get(position);
        item.setIconResource(R.drawable.ic_star_red_24dp);
        topAdapter.updateItem(position, item);
    }

}
