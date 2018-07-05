package com.iasmar.rome.ui.views.base;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.iasmar.rome.data.modules.ModulesHolder;

import static com.iasmar.rome.util.ObjectHelper.requireNonNull;

/**
 * Created by Asmar on 01/7/2018.
 * <p>
 *
 * @author Asmar
 * @since 1.0
 */

public class BasePagerAdapter<S extends ModulesHolder> extends PagerAdapter {


    private S data;


    protected void replaceData(S data) {
        this.data = requireNonNull(data, "context cannot be null");

    }

    protected S getData() {
        return data;
    }

    @Override
    public int getCount() {
        return data.getSize();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // Remove viewpager_item.xml from ViewPager
        (container).removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    // Use this to change the page width inorder to show more than one item at a time.
//    @Override
//    public float getPageWidth(int position) {
//        return 0.9f;
//    }
}
