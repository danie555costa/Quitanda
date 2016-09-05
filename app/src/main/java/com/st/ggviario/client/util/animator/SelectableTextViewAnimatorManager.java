package com.st.ggviario.client.util.animator;

import android.view.View;
import android.widget.TextView;

import com.daimajia.androidanimations.library.YoYo;
import com.nineoldandroids.animation.Animator;
import com.st.dbutil.android.adapter.SupportRecyclerAdapter;

/**
 * Created by Daniel Costa
 */
public class SelectableTextViewAnimatorManager extends SelectableAnimatorManager<TextView>
{
    private int firstAnimationDuration;
    private int secondAnimationDuration;

    public SelectableTextViewAnimatorManager()
    {
        this.firstAnimationDuration = 450;
        this.secondAnimationDuration = 450;
    }

    public void setFirstAnimationDuration(int firstAnimationDuration) {
        this.firstAnimationDuration = firstAnimationDuration;
    }

    public void setSecondAnimationDuration(int secondAnimationDuration) {
        this.secondAnimationDuration = secondAnimationDuration;
    }

    @Override
    public boolean support(Class<? extends View> aClass) {
        return TextView.class.isAssignableFrom(aClass);
    }

    @Override
    public TextView asSupportedView(View view) {
        return (TextView) view;
    }

    @Override
    public void onSelect(TextView textView, Selectable selectable, boolean animateView)
    {
        if(animateView)
            switchTextWhiteAnimation(textView,
                    selectable.getSelectedBackground(),
                    selectable.getSelectedCod());
        else {
            textView.setText(selectable.getSelectedCod());
            textView.setBackgroundResource(selectable.getSelectedBackground());
        }
    }

    @Override
    public void onUnSelect(TextView textView, Selectable selectable, boolean animate) {
        if(animate)
            switchTextWhiteAnimation(textView,
                    selectable.getUnSelectedBackground(),
                    selectable.getUnSelectedCod());
        else
        {
            textView.setText(selectable.getUnSelectedCod());
            textView.setBackgroundResource(selectable.getUnSelectedBackground());
        }
    }

    public void switchTextWhiteAnimation(final TextView textView,
                                         final int background,
                                         final CharSequence codSelection) {
        try
        {
            //Animação de saida
            YoYo.YoYoString yoYoString = YoYo.with(this.exitAnimation)
                    .duration(this.firstAnimationDuration)
                    .withListener(new SupportRecyclerAdapter.SupportAnimatorListener() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            //Animação de entrada
                            textView.setText(codSelection);
                            textView.setBackgroundResource(background);
                            YoYo.with(enterAnimation)
                                    .duration(secondAnimationDuration)
                                    .playOn(textView);
                        }
                    })
                    .playOn(textView);
        }catch (Exception ex) {
        }
    }
}
