package st.domain.quitanda.client.util.animator;

import android.util.Log;
import android.view.View;

import com.daimajia.androidanimations.library.Techniques;
import st.domain.support.android.adapter.BaseRecyclerAdapter;
import st.domain.quitanda.client.util.SelectableViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel Costa at 9/4/16.
 * Using user computer xdata
 */
public abstract class SelectableAnimatorManager<T extends View>
{
    Techniques exitAnimation;
    Techniques enterAnimation;
    private SelectionMode selectionMode;
    private BaseRecyclerAdapter.ItemViewHolder nextSelection;
    private BaseRecyclerAdapter.ItemViewHolder lastSelection;
    private List<OnAnimateSelection> onAnimateSelectionList;


    public SelectableAnimatorManager()
    {
        this.selectionMode = SelectionMode.MULT_SELECTION;
        this.onAnimateSelectionList = new ArrayList<>();
    }

    public void addOnAnimateSelection(OnAnimateSelection onAnimateSelection)
    {
        this.onAnimateSelectionList.add(onAnimateSelection);
    }

    public abstract boolean support(Class<? extends View> aClass);

    public abstract T asSupportedView(View view);

    public void acceptEnterAnimation(Techniques enterAnimation) {
        this.enterAnimation = enterAnimation;
    }

    public void selectionMode(SelectionMode selectionMode) {
        this.selectionMode = selectionMode;
    }


    public void acceptExitAnimation(Techniques exitAnimation) {
        this.exitAnimation = exitAnimation;
    }

    public SelectableAnimatorManager<T> animateOn(BaseRecyclerAdapter.ItemViewHolder itemViewHolder) {
        this.nextSelection = itemViewHolder;
        return this;
    }

    public void viewManifest(View view, Selectable selectable, boolean animate){
        for(OnAnimateSelection onAnimateSelection: this.onAnimateSelectionList)
            onAnimateSelection.onPreAnimate(this.nextSelection, selectable);

        if(selectable.isSelected())
            onSelect(asSupportedView(view), selectable, animate);
        else onUnSelect(asSupportedView(view), selectable, animate);

        if(this.nextSelection != null
                && this.nextSelection instanceof SelectableViewHolder)
        {
            if(nextSelection.equals(lastSelection))
            {
                Log.i("DBA:APP.TEST", "OLD SELECTION equals NEW SELECTION");
            }
            else Log.i("DBA:APP.TEST", "OLD SELECTION not equals NEW SELECTION");
            if(this.selectionMode == SelectionMode.ONE_SELECTION)
            {
                if(lastSelection != null
                        && lastSelection instanceof  SelectableViewHolder)
                {
                    ((SelectableViewHolder) lastSelection).setSelected(false);
//                    if(this.support(((SelectableViewHolder) lastSelection).getAvatarView().getClass())) {
                    this.onUnSelect((T) ((SelectableViewHolder) lastSelection).getAvatarView(),
                                ((SelectableViewHolder) lastSelection).getSelectableDataSet(),
                                animate);
//                    }
                }
            }
        }

        for(OnAnimateSelection onAnimateSelection: this.onAnimateSelectionList)
            onAnimateSelection.onPosAnimate(this.nextSelection, selectable);

        this.lastSelection = nextSelection;
        this.nextSelection = null;
    }

    public abstract void onSelect(T t, Selectable selectable, boolean animateView);

    public abstract void onUnSelect(T t, Selectable selectable, boolean animateView);

    public enum SelectionMode {
        ONE_SELECTION,
        MULT_SELECTION, selectionMode;
    }

    public BaseRecyclerAdapter.ItemViewHolder getLastSelection() {
        return lastSelection;
    }

    public boolean hasSelected() {
        return this.lastSelection != null;
    }

}

