package com.example.akshay_agrawal.btp_2;

/**
 * Created by Akshay_agrawal on 28-02-2017.
 */

import java.io.Serializable;

/**
 * Created by Aditya Agrawal on 2/19/2017.
 */
public class Expense implements Serializable
{
    private String category;
    private int expense;
    private String date;
    private String note;
    private String merchant;


    public Expense(String category, int expense, String date, String note, String merchant)
    {
        this.category = category;
        this.expense = expense;
        this.date = date;
        this.note= note;
        this.merchant= merchant;
    }
    public Expense() {
        // TODO Auto-generated constructor stub
    }

    public String getCategory()
    {
        return category;
    }

    public int getExpense()
    {
        return expense;
    }

    public String getDate()
    {
        return date;
    }

    public String getNote()
    {
        return note;
    }

    public String getMerchant()
    {
        return merchant;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public void setNote(String note)
    {
        this.note = note;
    }

    public void setExpense(int expense)
    {
        this.expense = expense;
    }

    public void setCategory(String category)
    {
        this.category =category;
    }

    public void setMerchant(String merchant)
    {
        this.merchant =merchant;
    }

}