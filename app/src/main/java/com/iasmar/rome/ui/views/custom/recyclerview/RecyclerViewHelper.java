package com.iasmar.rome.ui.views.custom.recyclerview;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.iasmar.rome.R;
import com.iasmar.rome.data.modules.ModulesHolder;

/**
 * Created by asmar on 02/7/2018.
 */
//TODO  add comments

public class RecyclerViewHelper<T extends RecyclerView.ViewHolder, S extends ModulesHolder> {
    private final RecyclerView recyclerView;
    private AnimationItem[] mAnimationItems;
    private AnimationItem mSelectedItem;
    private BaseAdapter<T, S> adapter;

    public RecyclerViewHelper(final RecyclerView recyclerView, BaseAdapter<T, S> adapter, int spanCount) {
        this.recyclerView = recyclerView;
        this.adapter = adapter;
        setupRecyclerView(spanCount);
        mAnimationItems = getAnimationItems();
        mSelectedItem = mAnimationItems[0];
        runLayoutAnimation();

    }

    private AnimationItem[] getAnimationItems() {
        return new AnimationItem[]{
                new AnimationItem("Fall down", R.anim.layout_animation_fall_down),
                new AnimationItem("Slide from right", R.anim.layout_animation_from_right),
                new AnimationItem("Slide from bottom", R.anim.layout_animation_from_bottom)
        };
    }

    private void runLayoutAnimation() {
        final Context context = recyclerView.getContext();

        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(context, mSelectedItem.getResourceId());


        recyclerView.setLayoutAnimation(controller);
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();
    }

    public void replaceData(S data, boolean animation) {
        adapter.replaceData(data);
        recyclerView.getAdapter().notifyDataSetChanged();
        if (animation) {
            runLayoutAnimation();
        }
    }

    private void setupRecyclerView(int spanCount) {
        final Context context = recyclerView.getContext();
        if (spanCount>1) {
            recyclerView.setLayoutManager(new GridLayoutManager(context, spanCount));
        } else {
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        }
        recyclerView.setAdapter(adapter);
    }
}
