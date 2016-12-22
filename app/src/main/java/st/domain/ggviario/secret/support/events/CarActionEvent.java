package st.domain.ggviario.secret.support.events;

import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import st.domain.ggviario.secret.R;
import st.domain.ggviario.secret.model.action.CarActionAdd;
import st.domain.ggviario.secret.model.action.CarActionRemove;
import st.domain.ggviario.secret.model.action.CarActionReplace;
import st.domain.ggviario.secret.model.ItemSell;
import st.domain.ggviario.secret.model.Product;
import st.domain.ggviario.secret.model.contract.ObserverCalculated;
import st.domain.ggviario.secret.model.action.CarAction;

/**
 * Created by Daniel Costa at 8/30/16.
 * Using user computer xdata
 */
public class CarActionEvent implements MenuObserver, ObserverCalculated
{
    private final Product product;
    private final boolean hasIncar;
    private ItemSell itemSell;
    private boolean add;

    public CarActionEvent(Product product, boolean hasIncar)
    {
        this.product = product;
        this.hasIncar = hasIncar;
        this.add = hasIncar;

        Log.i("DBA:APP.TEST", "event in car addItem:"+ this.add+" | has_in_car: "+hasIncar);
    }

    public void onMenu(Menu menu) {
        if(this.hasIncar && add)
            menu.findItem(getKey()).setIcon(R.drawable.ic_vector_remove_shopping_cart_white_24dp);
    }

    @Override
    public boolean accept(MenuItem menuItem, Activity activity)
    {
        if(add)
        {
            this.add = false;
            menuItem.setIcon(R.drawable.ic_add_shopping_cart_white_24dp);
            menuItem.setTitle(R.string.add);
        }
        else if(this.itemSell != null)
        {
            this.add = true;
            menuItem.setIcon(R.drawable.ic_vector_remove_shopping_cart_white_24dp);
            menuItem.setTitle(R.string.remove);
        }
        return true;
    }

    public CarAction getCarAction() {
        if(add && hasIncar) return  new CarActionReplace();
        else if(add && !hasIncar) return new CarActionAdd();
        else return new CarActionRemove();
    }

    @Override
    public int getKey() {
        return  R.id.opt_car_action;
    }

    @Override
    public void accept(ItemSell itemSell)
    {
        Log.i("DBA:APP.TEST", getClass().getSimpleName()+"-> accept on car event");
        this.itemSell = itemSell;
    }
}
