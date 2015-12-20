package com.fynd.demo.modal;

public class SizeQuantity
{
    private long quantity;

    private long size;

    public long getQuantity ()
    {
        return quantity;
    }

    public void setQuantity (long quantity)
    {
        this.quantity = quantity;
    }

    public long getSize ()
    {
        return size;
    }

    public void setSize (long size)
    {
        this.size = size;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [quantity = "+quantity+", size = "+size+"]";
    }
}