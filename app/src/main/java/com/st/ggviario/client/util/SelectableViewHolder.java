package com.st.ggviario.client.util;

import android.util.Log;
import android.view.View;

import com.daimajia.androidanimations.library.Techniques;
import com.st.dbutil.android.adapter.BaseRecyclerAdapter;
import com.st.ggviario.client.util.animator.SelectableAnimatorManager;
import com.st.ggviario.client.view.adapters.dataset.SelectableDataSet;

/**
 * Created by Daniel Costa at 9/4/16.
 * Using user computer xdata
 */
public class SelectableViewHolder extends BaseRecyclerAdapter.ItemViewHolder
{
    private View avatar;
    public  Techniques enterAnimation = Techniques.RotateInUpLeft;
    public  Techniques exitAnimation = Techniques.RotateOutDownLeft;

    private int avatarId;
    private SelectableDataSet selectableDataSet;
    private SelectableAnimatorManager selectableAnimator;


    public SelectableViewHolder(View itemView)
    {
        super(itemView);
    }

    public void setAvatarId(int avatarId)
    {
        this.avatarId = avatarId;
        this.avatar = this.findViewById(this.avatarId);
        Log.i("DBA:APP.TEST", "Searching for the selectableAnimator "+ avatar.getClass().getName());
//        if(animator.support(avatar.getClass())) {
//            this.selectableAnimator = animator;
//            Log.i("DBA:APP.TEST", "Animator encontred "+avatar.getClass());
//            break;
//        }
    }

    public void setSelectableAnimator(SelectableAnimatorManager selectableAnimator) {
        this.selectableAnimator = selectableAnimator;
        selectableAnimator.acceptEnterAnimation(this.enterAnimation);
        selectableAnimator.acceptExitAnimation(this.exitAnimation);
    }


    public void setEnterAnimation(Techniques enterAnimation) {
        this.enterAnimation = enterAnimation;
        if(this.selectableAnimator != null)
            this.selectableAnimator.acceptEnterAnimation(this.enterAnimation);
    }

    public void setExitAnimation(Techniques exitAnimation) {
        this.exitAnimation = exitAnimation;
        if(this.selectableAnimator != null)
            this.selectableAnimator.acceptExitAnimation(this.exitAnimation);
    }

    @Override
    public boolean bind(BaseRecyclerAdapter.ItemDataSet dataSet, int position)
    {
        if(dataSet instanceof SelectableDataSet)
        {
            this.selectableDataSet = (SelectableDataSet) dataSet;
            if(selectableDataSet.isRequireBuild())
            {
                this.selectableDataSet.context(this.getContext())
                        .build();
            }

            if(selectableAnimator != null)
            {
                this.selectableAnimator
                        .animateOn(this)
                        .viewManifest(avatar,
                                selectableDataSet,
                                animateOnBind(dataSet,
                                        position));
            }
        }
        posBindAnimate(dataSet, position);
        return true;
    }

    private boolean animateOnBind(BaseRecyclerAdapter.ItemDataSet dataSet, int position) {
        return true;
    }


    public void posBindAnimate(BaseRecyclerAdapter.ItemDataSet dataSet, int position) {
    }


    @Override
    public boolean isClickable(int position) {
        return true;
    }

    public void setSelected(boolean selected) {
        this.selectableDataSet.setSelected(selected);
    }

    public boolean isSeleted() {
        return this.selectableDataSet.isSelected();
    }

    @Override
    public void onClink(int position)
    {
        this.selectableDataSet.setSelected(!this.selectableDataSet.isSelected());
        this.setSelected(this.selectableDataSet.isSelected());
        this.selectableAnimator.animateOn(this);
        this.selectableAnimator.viewManifest(this.avatar, this.selectableDataSet, true);
        onSelection(this.selectableDataSet, position, !this.selectableDataSet.isSelected(), this.selectableDataSet.isSelected());
    }

    private void onSelection(SelectableDataSet selectableDataSet, int position, boolean oldSelection, boolean newSelection) {

    }

    public SelectableDataSet getSelectableDataSet() {
        return this.selectableDataSet;
    }

    public View getAvatarView() {
        return this.avatar;
    }
}
