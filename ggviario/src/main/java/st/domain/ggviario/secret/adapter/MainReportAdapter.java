package st.domain.ggviario.secret.adapter;

import android.content.Context;
import android.view.View;

import st.domain.ggviario.secret.items.CropChartLineViewHolder;
import st.domain.ggviario.secret.R;
import st.zudamue.support.android.adapter.ItemViewHolder;
import st.zudamue.support.android.adapter.RecyclerViewAdapter;

/**
 *
 * Created by dchost on 28/01/17.
 */

public class MainReportAdapter extends RecyclerViewAdapter {

    public MainReportAdapter(Context context) {
        super(context);
        super.registerFactory(R.layout._crop_report_chart, new ViewHolderFactory() {
            @Override
            public ItemViewHolder factory(View view) {
                return new CropChartLineViewHolder(view);
            }
        });
    }
}
