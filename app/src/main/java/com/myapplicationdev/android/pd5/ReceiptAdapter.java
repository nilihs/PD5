package com.myapplicationdev.android.pd5;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ReceiptAdapter extends ArrayAdapter<Receipt> {

    public static final String LOG_TAG = ReceiptAdapter.class.getName();

    private ArrayList<Receipt> alReceipt;
    private Context context;

    public ReceiptAdapter(Context context, int resource, ArrayList<Receipt> objects){
        super(context, resource, objects);
        alReceipt = objects;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.receipt_row, parent, false);

        TextView tvTitle = (TextView) rowView.findViewById(R.id.tvTitleList);
        TextView tvDescription = (TextView) rowView.findViewById(R.id.tvDescriptionList);
        TextView tvCost = (TextView) rowView.findViewById(R.id.tvCostList);

        Receipt receipt = alReceipt.get(position);

        tvTitle.setText(receipt.getTitle());
        tvDescription.setText(receipt.getDescription() + "\n");
        tvCost.setText("$" + receipt.getCost());

        return rowView;
    }

}