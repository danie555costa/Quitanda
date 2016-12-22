package st.domain.quitanda.client.view.adapters.vholders;

import android.view.View;
import android.widget.TextView;

import st.domain.support.android.adapter.BaseRecyclerAdapter;
import st.domain.quitanda.client.R;
import st.domain.quitanda.client.util.SelectableViewHolder;
import st.domain.quitanda.client.view.adapters.dataset.ClientDataSet;

/**
 * Created by Daniel Costa at 8/28/16.
 * Using user computer xdata
 */
public class ClientViewHolder extends SelectableViewHolder
{

    private final TextView tvClientName;
    private final TextView tvClientResidence;
    private final TextView tvClientContact;
    private ClientDataSet clientDataSet;

    public ClientViewHolder(View itemView)
    {
        super(itemView);
        super.setAvatarId(R.id.tv_client_avatar);

        this.tvClientName = (TextView) itemView.findViewById(R.id.tv_client_name);
        this.tvClientResidence = (TextView) itemView.findViewById(R.id.tv_client_residence);
        this.tvClientContact = (TextView) itemView.findViewById(R.id.tv_client_contact);
    }

    @Override
    public void posBindAnimate(BaseRecyclerAdapter.ItemDataSet dataSet, int position) {
        if(dataSet instanceof ClientDataSet)
        {
            this.clientDataSet = (ClientDataSet) dataSet;
            this.tvClientName.setText(clientDataSet.getClient());
            this.tvClientContact.setText(clientDataSet.getClient().getContact());
            this.tvClientResidence.setText(clientDataSet.getClient().getResidence());
        }
    }
}
