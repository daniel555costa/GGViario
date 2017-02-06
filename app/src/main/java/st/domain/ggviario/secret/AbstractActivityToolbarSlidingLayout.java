package st.domain.ggviario.secret;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;

import st.domain.support.android.view.SlidingTabLayout;

public abstract class AbstractActivityToolbarSlidingLayout extends AbstractActivityToolbar {

    private ViewPager pager;
    private SlidingTabLayout slidingTabLayout;

    protected abstract @LayoutRes int getContentView();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // INIT
        super.onCreate(savedInstanceState);


        //View Pager
        this.pager = getViewPager();
        this.slidingTabLayout = getSlidingLayout();
        this.slidingTabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        this.slidingTabLayout.setSelectedIndicatorColors(getResources().getColor(R.color.white));

        this.pager.setAdapter(getAdapter());
        this.slidingTabLayout.setCustomTabView(R.layout.tab, R.id.tab_title);
        this.slidingTabLayout.setViewPager(this.pager);
    }

    protected abstract @NonNull ViewPager getViewPager();

    protected abstract @NonNull SlidingTabLayout getSlidingLayout();

    protected abstract @NonNull android.support.v4.view.PagerAdapter getAdapter();
}
