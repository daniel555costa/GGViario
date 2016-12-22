package st.domain.ggviario.secret.support.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import st.domain.support.android.adapter.BaseRecyclerAdapter;
import st.domain.support.android.adapter.SupportRecyclerAdapter;
import st.domain.ggviario.secret.R;
import st.domain.ggviario.secret.util.BaseCharacter;

import java.util.List;

/**
 * Created by xdata on 8/11/16.
 */
public class SupportHome implements SupportRecyclerAdapter.OnBindViewHolder, SupportRecyclerAdapter.OnCreateViewHolder, SupportRecyclerAdapter.OnCreateView {
    private final SupportRecyclerAdapter support;
    private final List<DataOperation> list;

    public SupportHome(Context content)
    {
        this.support = new SupportRecyclerAdapter(content);
        this.list = support.getListDataSet();


        this.support.setOnCreateViewHolder(this);
        this.support.setOnBindViewHolder(this);
        this.support.setOnCreateView(this);
    }

    public SupportRecyclerAdapter getCreatedSupport()
    {
        return this.support;
    }

    @Override
    public void onBindViewHolder(BaseRecyclerAdapter.ItemViewHolder viewHolder, BaseRecyclerAdapter.ItemDataSet dataSet, int position, int onRecyclerViewId)
    {
        if(viewHolder instanceof ItemOperation)
        {
            ItemOperation item = (ItemOperation) viewHolder;
            DataOperation data = (DataOperation) dataSet;
            item.setValues(data);
//            if(!data.efeito) support.notifyItemInserted(position);
        }
        try {
            YoYo.with(Techniques.RotateInDownLeft)
                    .duration(700)
                    .playOn(viewHolder.itemView)
            ;
        }
        catch (Throwable throwable)
        {
            Toast.makeText(this.support.getContext(), "Nao Consegui Pegar a animacao", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public BaseRecyclerAdapter.ItemViewHolder onCreateViewHolder(View view, int viewType, int onRecyclerViewId) {
        return new ItemOperation(view);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup group, final int viewType, int onRecyclerViewId) {
        final int screenOrientation = this.support.getContext().getResources().getConfiguration().orientation;
        final int HORIZONTAL_SCREEN = Configuration.ORIENTATION_LANDSCAPE;

        final View itemView = inflater.inflate(R.layout.item_operation, group, false);

        itemView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                final int type = viewType;
                final ViewGroup.LayoutParams layoutParams = itemView.getLayoutParams();
                if (layoutParams instanceof StaggeredGridLayoutManager.LayoutParams) {
                    StaggeredGridLayoutManager.LayoutParams staggerLayoutParams = (StaggeredGridLayoutManager.LayoutParams) layoutParams;

                    if (screenOrientation == HORIZONTAL_SCREEN) {
//                        staggerLayoutParams.setFullSpan(false);
//                        staggerLayoutParams.width = itemView.getWidth() - (itemView.getWidth()/2);
                    }
                    switch (type) {

//                        case 1:
//                            staggerLayoutParams.setFullSpan(true);
//                            break;
//
//                        case 2:
//                            staggerLayoutParams.setFullSpan(false);
//                            staggerLayoutParams.width = itemView.getWidth() + ( itemView.getWidth()/2);
//                            break;
//
//                        case 3:
//                            staggerLayoutParams.setFullSpan(false);
//                            staggerLayoutParams.width = itemView.getWidth() / 2;
//                            staggerLayoutParams.height = itemView.getHeight() / 2;
//                            break;
                    }
//                    staggerLayoutParams.setFullSpan(false);
//                            staggerLayoutParams.width = itemView.getWidth() / 2;
//                            staggerLayoutParams.height = itemView.getHeight() / 2;

                    itemView.setLayoutParams(staggerLayoutParams);
                    final StaggeredGridLayoutManager lm = (StaggeredGridLayoutManager) ((RecyclerView) group).getLayoutManager();
                    lm.invalidateSpanAssignments();


                }
                itemView.getViewTreeObserver().removeOnPreDrawListener(this);
                return true;
            }
        });
        return itemView;
    }

    public class ItemOperation extends BaseRecyclerAdapter.ItemViewHolder
    {
        private final CardView cardView;
        private final TextView titleOperation;
        private final ImageView imageOperation;
        private DataOperation value;


        public ItemOperation(View itemView)
        {
            super(itemView);
            this.cardView = (CardView) itemView;
            this.imageOperation = (ImageView) this.cardView.findViewById(R.id.img_operation);
            this.titleOperation = (TextView) this.cardView.findViewById(R.id.tv_title_operation);

        }

        public void setValues(DataOperation values)
        {
            int cardColor =  support.getContext().getResources().getColor(values.color);
            this.imageOperation.setImageResource(values.image);
            this.titleOperation.setText(values);
            this.cardView.setCardBackgroundColor(cardColor);
            this.value = values;
        }

        @Override
        public boolean isClickable(int position) {
            return true;
        }

        @Override
        public void onClink(int position)
        {
            if(this.value.activity == null) return;
            Intent intent = new Intent(this.getContext(), this.value.activity);
            support.getContext().startActivity(intent);
        }
    }

    public static class DataOperation extends BaseCharacter implements BaseRecyclerAdapter.ItemDataSet
    {
        private int color;
        private String name;
        private  int image;
        private Class<? extends Activity> activity;
        public boolean efeito;

        public DataOperation color(int color) {
            this.color = color;
            return this;
        }

        public DataOperation name(String operation) {
            this.name = operation;
            return this;
        }

        public DataOperation image(int image) {
            this.image = image;
            return this;
        }

        public DataOperation activity(Class<? extends Activity> activity) {
            this.activity = activity;
            return this;
        }

        public DataOperation efeito(boolean efeito) {
            this.efeito = efeito;
            return this;
        }

        @Override
        public String toString()
        {
            return name.toString();
        }


        @Override
        public int getTypeView() {
            return 0;
        }
    }

    public static DataOperation newOperation()
    {
        return new DataOperation();
    }
}
