package com.example.orderfood;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.orderfood.model.Popular;

import java.util.ArrayList;

public class ManagementCart {
    private Context context;
    private DBFood dbFood;
    private static final ArrayList<Popular> listFood = new ArrayList<>();

    public ManagementCart(Context context) {
        this.context = context;
        this.dbFood = new DBFood(context);
    }

    public void insertFood(Popular item) {
        boolean existAlready = false;
        int n = 0;
        for (int i = 0; i < listFood.size(); i++) {
            if (listFood.get(i).getTitle().equals(item.getTitle())) {
                existAlready = true;
                n = i;
                break;
            }
        }

        if (existAlready) {
            listFood.get(n).setCount(item.getCount());
        } else {
            listFood.add(item);
        }

        dbFood.putListObject("CartList", listFood);
        Toast.makeText(context, "Added To Your Cart", Toast.LENGTH_LONG).show();
    }

    public ArrayList<Popular> getListCart() {
        if(listFood.isEmpty()) return null;
        return dbFood.getListObject("CartList");
    }

    public void plusCountFood(ArrayList<Popular> listFood, int position, ChangeNumberItem changeNumberItem){
        listFood.get(position).setCount(listFood.get(position).getCount() + 1);
        dbFood.putListObject("CartList", listFood);
        changeNumberItem.changed();
    }

    public void minusCountFood(ArrayList<Popular> listFood, int position, ChangeNumberItem changeNumberItem){
        if(listFood.get(position).getCount() == 1){
            listFood.remove(position);
        }else {
            listFood.get(position).setCount(listFood.get(position).getCount() - 1);
        }
        dbFood.putListObject("CartList", listFood);
        changeNumberItem.changed();
    }

    public Double getTotalFood(ArrayList<Popular> listFood, int position){
        ArrayList<Popular> list = listFood;
        double fee = 0;
        fee = fee + (Double.parseDouble(list.get(position).getPrice())* list.get(position).getCount());
        return fee;
    }
}
