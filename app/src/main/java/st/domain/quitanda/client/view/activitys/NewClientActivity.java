package st.domain.quitanda.client.view.activitys;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import st.domain.quitanda.client.R;

/**
 * Created by Daniel Costa at 8/28/16.
 * Using user computer xdata
 */
public class NewClientActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_new_client);
        Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        Button save = (Button) this.findViewById(R.id.bt_client_save);
        super.setSupportActionBar(toolbar);

        TextView toolbarTitle = (TextView) this.findViewById(R.id.toolbar_title);
        toolbarTitle.setText(R.string.new_client);

        if(this.getSupportActionBar() != null)
        {
            this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setTitleTextColor(getResources().getColor(R.color.white));
            this.getSupportActionBar().setTitle("");
        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id  == android.R.id.home) this.finish();
        return true;
    }
}
