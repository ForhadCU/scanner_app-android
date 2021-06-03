package com.agamilabs.smartshop.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.agamilabs.smartshop.Interfaces.ICallbackClickHandler;
import com.agamilabs.smartshop.R;
import com.agamilabs.smartshop.model.AdminDashboardModel;
import com.agamilabs.smartshop.model.StockReportModel;

import java.text.DecimalFormat;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class StockReportAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mCtx;
    private List<StockReportModel> mItemList;
    private ICallbackClickHandler iCallbackClickHandler;

    public StockReportAdapter(Context mCtx, List<StockReportModel> mItemList, ICallbackClickHandler iCallbackClickHandler) {
        this.mCtx = mCtx;
        this.mItemList = mItemList;
        this.iCallbackClickHandler = iCallbackClickHandler;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.stock_report_list, null);
        return new StockReportAdapter.StockReportViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        StockReportModel current = mItemList.get(position);
        ((StockReportAdapter.StockReportViewHolder) holder).bind(current, position);
    }

    @Override
    public int getItemCount() {
        return mItemList == null ? 0 : mItemList.size();
    }

    class StockReportViewHolder extends RecyclerView.ViewHolder {
        TextView mItemName, mInitialQty, mRemainingQty, mSaleRate, mPurchaseRate, mStockAmount, mReorder ;
        CircleImageView mImageLogo;
        private ImageView imgV_stockLots;

        public StockReportViewHolder(View itemView) {
            super(itemView);
            mItemName = itemView.findViewById(R.id.text_item_name);
            mInitialQty = itemView.findViewById(R.id.text_initial_qty);
            mRemainingQty = itemView.findViewById(R.id.text_remaining_qty);
            mSaleRate = itemView.findViewById(R.id.text_sale_rate);
            mPurchaseRate = itemView.findViewById(R.id.text_purchase_rate);
            mStockAmount = itemView.findViewById(R.id.text_stock_amount);
            mReorder = itemView.findViewById(R.id.text_reorder_point);
            mImageLogo = itemView.findViewById(R.id.image_logo);
            imgV_stockLots = itemView.findViewById(R.id.imgV_stockLots);

        }

        public void bind(StockReportModel current, int position) {
            mItemName.setText(current.getItemname());
            mInitialQty.setText(String.valueOf(current.getInitialqty()));
            mRemainingQty.setText(String.valueOf(current.getRemainingqty()));
            mSaleRate.setText(String.format("\u09F3 " + "%.2f", current.getSalerate()));
            mPurchaseRate.setText(String.format("\u09F3 " + "%.2f", current.getPrate()));
            mStockAmount.setText(String.format("\u09F3 " + "%.2f", current.getStockamount()));
            String reorder;
            if (current.getReorderpoint().equalsIgnoreCase("null"))
                reorder = "0";
            else
                reorder = current.getReorderpoint();
            mReorder.setText(reorder);

            if(current.getRemainingqty() <= Integer.parseInt(reorder)){
                mImageLogo.setBorderColor(Color.RED);
            } else if(current.getRemainingqty() <= Integer.parseInt(reorder) * 2){
                mImageLogo.setBorderColor(Color.YELLOW);
            } else{
                mImageLogo.setBorderColor(Color.GREEN);
            }

            imgV_stockLots.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iCallbackClickHandler.dataParsedStockLots(current.getLotsModelArrayList());
                    iCallbackClickHandler.handleBottomSheet();
                }
            });
        }
    }
}