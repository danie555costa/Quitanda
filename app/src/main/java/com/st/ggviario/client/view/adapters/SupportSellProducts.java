package com.st.ggviario.client.view.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.st.dbutil.android.adapter.BaseReclyclerAdapter;
import com.st.dbutil.android.adapter.SupportRecyclerAdapter;
import com.st.ggviario.client.R;
import com.st.ggviario.client.model.Product;
import com.st.ggviario.client.model.template.BaseCharacter;
import com.st.ggviario.client.view.activitys.Calculator;
import com.st.ggviario.client.view.fragments.SellCarStep;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xdata on 8/11/16.
 */
public class SupportSellProducts implements SupportRecyclerAdapter.OnBindViewHolder, SupportRecyclerAdapter.OnCreateViewHolder, SupportRecyclerAdapter.OnItemClickListener, SupportRecyclerAdapter.OnPosViewCreated {
    private static final int TYPE_ITEM_CAR = R.layout.item_product_selected;
    private final SupportRecyclerAdapter support;
    private final List<BaseReclyclerAdapter.ItemDataSet> list;
    public static final int TYPE_CAR = R.layout.item_group_car;
    public static final int TYPE_PRODUCT = R.layout.item_cart_operation;

    public SupportSellProducts(Context content)
    {
//        this.support = new SupportRecyclerAdapter(content, R.layout.item_cart_operation);
        this.support = new SupportRecyclerAdapter(content);
        this.list = support.getListDataSet();
        this.list.add(new CarSummary());

        this.support.setOnCreateViewHolder(this);
        this.support.setOnBindViewHolder(this);
        this.support.setOnItemClickListener(this);
        this.support.useTypeViewAsReferenceLayout(true);
        this.support.setOnPosViewCreated(this);
    }

    public SupportRecyclerAdapter getCreatedSupport()
    {
        return this.support;
    }

    @Override
    public void onBindViewHolder(BaseReclyclerAdapter.ItemViewHolder viewHolder, BaseReclyclerAdapter.ItemDataSet dataSet, int position, int onRecyclerViewId)
    {
        if(viewHolder instanceof ViewHolderProduct)
        {
            ViewHolderProduct item = (ViewHolderProduct) viewHolder;
            DataProduct data = (DataProduct) dataSet;
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
    public BaseReclyclerAdapter.ItemViewHolder onCreateViewHolder(View view, int viewType, int onRecyclerViewId) {
        BaseReclyclerAdapter.ItemViewHolder viewHolder = null;
        switch (viewType)
        {
            case TYPE_PRODUCT:
                viewHolder = new ViewHolderProduct(view);
                break;
            case TYPE_CAR:
                viewHolder = new ViewHoderCar(view);
                break;
            case TYPE_ITEM_CAR:
                viewHolder = new ViewHolderItemCar(view);
        }
        return  viewHolder;
    }

    @Override
    public void onItemClick(View view, BaseReclyclerAdapter.ItemDataSet dataSet, int adapterPosition, int viewPosition)
    {
        if(dataSet instanceof DataProduct)
        {
            DataProduct dataProduct = (DataProduct) dataSet;
            Intent intent = new Intent(this.support.getContext(), Calculator.class);
            Bundle bunble = new Bundle();
            bunble.putCharSequence(SellCarStep.PRODUCT, dataProduct.product);
            intent.putExtras(bunble);
            support.getContext().startActivity(intent);
        }

    }

    @Override
    public void onViewCreated(final View itemView, final ViewGroup group, final int viewType)
    {
        Configuration configuration = this.support.getContext().getResources().getConfiguration();
        int screenOrientation = configuration.orientation;
        final int VERTICAL_SCREEN_ORIENTATION = Configuration.ORIENTATION_PORTRAIT;
        itemView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener()
        {
            @Override
            public boolean onPreDraw()
            {
                final int type = viewType;
                final ViewGroup.LayoutParams layoutParams = itemView.getLayoutParams();
                if (layoutParams instanceof StaggeredGridLayoutManager.LayoutParams)
                {

                    StaggeredGridLayoutManager.LayoutParams staggerLayoutParams = (StaggeredGridLayoutManager.LayoutParams) layoutParams;
                    if(viewType == TYPE_CAR)
                    {
                        staggerLayoutParams.setFullSpan(true);
                    }
                    else if(viewType == TYPE_PRODUCT)
                    {

                    }
                    itemView.setLayoutParams(staggerLayoutParams);
                    final StaggeredGridLayoutManager lm = (StaggeredGridLayoutManager) ((RecyclerView) group).getLayoutManager();
                    lm.invalidateSpanAssignments();
                }
                itemView.getViewTreeObserver().removeOnPreDrawListener(this);
                return true;
            }
        });
    }


    public class ViewHolderProduct extends BaseReclyclerAdapter.ItemViewHolder
    {
        private final CardView cardView;
        private final TextView titleOperation;
        private final ImageView imageOperation;


        public ViewHolderProduct(View itemView)
        {
            super(itemView);
            this.cardView = (CardView) itemView;
            this.imageOperation = (ImageView) this.cardView.findViewById(R.id.img_operation);
            this.titleOperation = (TextView) this.cardView.findViewById(R.id.tv_title_operation);

        }

        public void setValues(DataProduct values)
        {
            int cardColor =  support.getContext().getResources().getColor(values.idColor);
            this.imageOperation.setImageResource(R.drawable.ic_shopping_cart_white_48dp);
            this.titleOperation.setText(values);
            this.cardView.setCardBackgroundColor(cardColor);
        }
    }

    public static class DataProduct extends BaseCharacter implements BaseReclyclerAdapter.ItemDataSet
    {
        private int idColor;
        private Product product;
        public boolean efeito;

        public DataProduct(int idColor, Product product)
        {
            this.idColor = idColor;
            this.product = product;
            this.efeito = false;
        }

        @Override
        public String toString()
        {
            return product.toString();
        }

        @Override
        public int getTypeView() {
            return TYPE_PRODUCT;
        }
    }

    private class CarSummary implements BaseReclyclerAdapter.ItemDataSet
    {
        @Override
        public int getTypeView() {
            return TYPE_CAR;
        }
    }

    private class ViewHoderCar extends BaseReclyclerAdapter.ItemViewHolder
    {
        private final SupportRecyclerAdapter supportProductCar;
        RecyclerView recyclerView;
        public ViewHoderCar(View view)
        {
            super(view);
            ArrayList<BaseReclyclerAdapter.ItemDataSet> lista = new ArrayList<>();
            lista.add(new DataProduct(0, new Product("23", "Ovos")));
            lista.add(new DataProduct(0, new Product("23", "Ovos")));
            lista.add(new DataProduct(0, new Product("23", "Ovos")));
            lista.add(new DataProduct(0, new Product("23", "Ovos")));
            recyclerView = (RecyclerView) view.findViewById(R.id.rv_car_products);

            LinearLayoutManager llm = new LinearLayoutManager(support.getContext(), LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(llm);

            this.supportProductCar = new SupportRecyclerAdapter(support.getContext(), lista, R.layout.item_product_selected);
            this.supportProductCar.setOnCreateViewHolder(SupportSellProducts.this);
            this.recyclerView.setAdapter(this.supportProductCar);
        }
    }

    class ViewHolderItemCar extends BaseReclyclerAdapter.ItemViewHolder
    {

        public ViewHolderItemCar(View itemView)
        {
            super(itemView);
        }
    }

    class DataProductCar implements BaseReclyclerAdapter.ItemDataSet
    {

        @Override
        public int getTypeView() {
            return TYPE_ITEM_CAR;
        }
    }
}
