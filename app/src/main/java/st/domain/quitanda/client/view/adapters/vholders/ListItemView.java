package st.domain.quitanda.client.view.adapters.vholders;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import st.domain.support.android.adapter.BaseRecyclerAdapter;

/**
 * Created by Daniel Costa on 8/24/16.
 * User computer: xdata
 */
public class ListItemView
{
    public static class TextOneLineDataSet implements BaseRecyclerAdapter.ItemDataSet
    {
        private int typeView;
        private CharSequence textPrimary;

        public TextOneLineDataSet(int typeView)
        {
            this.typeView = typeView;
        }

        public void setTypeView(int typeView) {
            this.typeView = typeView;
        }

        public void setTextPrimary(CharSequence textPrimary) {
            this.textPrimary = textPrimary;
        }

        public CharSequence getTextPrimary() {
            return textPrimary;
        }
        @Override
        public int getTypeView() {
            return this.typeView;        }
    }

    public static class TextTowLineDataSet extends TextOneLineDataSet
    {
        private CharSequence textSecond1;

        public TextTowLineDataSet(int typeId)
        {
            super(typeId);
        }


        public void setTextSecond1(CharSequence textSecond1)
        {
            this.textSecond1 = textSecond1;
        }

        public CharSequence getTextSecond1() {
            return textSecond1;
        }
    }

    public static class TextTreeLineDataSet extends TextTowLineDataSet
    {
        private CharSequence textSecond2;

        public TextTreeLineDataSet(int typeId) {
            super(typeId);
        }

        public CharSequence getTextSecond2() {
            return textSecond2;
        }

        public void setTextSecond2(CharSequence textSecond2) {
            this.textSecond2 = textSecond2;
        }
    }


    public static class ItemViewHolder extends BaseRecyclerAdapter.ItemViewHolder
    {
        private final View view;
        private TextView tvPrimaryText;
        private TextView tvTextSecond1;
        private TextView tvTextSecond2;
        private TextView avatarText;
        private ImageView avatarImage;
        private ImageButton avatarImageButton;
        private Button avatarButton;

        public ItemViewHolder(View view, int idTextPrimary)
        {
            super(view);
            Log.i("DBA:APP.TEST", getClass().getSimpleName()+"-> CREATE ITEM TEXT VIEW HOLDER");
            this.view = view;
            this.tvPrimaryText = (TextView) this.itemView.findViewById(idTextPrimary);
        }


        public View getView() {
            return view;
        }

        public void setTextSecond1dId(int idTextSecond1) {
            this.tvTextSecond1 = (TextView) this.itemView.findViewById(idTextSecond1);
        }

        public void setTextSecond2Id(int idTextSecond2) {
            this.tvTextSecond2 = (TextView) this.itemView.findViewById(idTextSecond2);
        }

        public void setAvatarId(int avatarId) {
            View avatar = this.itemView.findViewById(avatarId);
            if(avatar instanceof TextView)
                this.avatarText = (TextView) avatar;
            else if(avatar instanceof ImageView)
                this.avatarImage = (ImageView) avatar;
            else if(avatar instanceof ImageButton)
                this.avatarImageButton = (ImageButton) avatar;
            else if(avatar instanceof Button)
                this.avatarButton = (Button) avatar;
        }

        public  boolean bind(BaseRecyclerAdapter.ItemDataSet dataSet, int position)
        {
            super.bind(dataSet, position);
            if(dataSet instanceof TextOneLineDataSet)
            {
                this.tvPrimaryText.setText(((TextOneLineDataSet) dataSet).getTextPrimary());
                if (dataSet instanceof TextTowLineDataSet && this.tvTextSecond1 != null)
                    this.tvTextSecond1.setText(((TextTowLineDataSet) dataSet).getTextSecond1());
                if(dataSet instanceof TextTreeLineDataSet && this.tvTextSecond2 != null)
                    this.tvTextSecond2.setText(((TextTreeLineDataSet) dataSet).getTextSecond2());
            }
            return true;
        }

        public TextView getTvPrimaryText() {
            return tvPrimaryText;
        }

        public TextView getTvTextSecond1() {
            return tvTextSecond1;
        }

        public TextView getTvTextSecond2() {
            return tvTextSecond2;
        }

        public TextView getAvatarText() {
            return avatarText;
        }

        public ImageView getAvatarImage() {
            return avatarImage;
        }

        public ImageButton getAvatarImageButton() {
            return avatarImageButton;
        }

        public Button getAvatarButton() {
            return avatarButton;
        }
    }
}
