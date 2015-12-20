package com.fynd.demo.modal;

public class ItemInfo
{
	public static final int ITEM_NAME_INDEX = 0;
	public static final int SHADE_NAME_INDEX = ITEM_NAME_INDEX + 1;
	public static final int SIZE_INDEX_START = SHADE_NAME_INDEX + 1;
	public static final int SIZE_INDEX_END = SIZE_INDEX_START + 9;
	public static final int TOTAL_INDEX = SIZE_INDEX_END + 1;
	public static final int MRP_INDEX = TOTAL_INDEX + 1;
	
    private SizeQuantity[] sizeQuantity;

    private String shadeName;

    private String itemName;

    private String mrp;

    public SizeQuantity[] getSizeQuantity ()
    {
        return sizeQuantity;
    }

    public void setSizeQuantity (SizeQuantity[] sizeQuantity)
    {
        this.sizeQuantity = sizeQuantity;
    }

    public String getShade_name ()
    {
        return shadeName;
    }

    public void setShadeName (String shadeName)
    {
        this.shadeName = shadeName;
    }

    public String getItemName ()
    {
        return itemName;
    }

    public void setItemName (String itemName)
    {
        this.itemName = itemName;
    }

    public String getMrp ()
    {
        return mrp;
    }

    public void setMrp (String mrp)
    {
        this.mrp = mrp;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [size_quantity = "+sizeQuantity+", shade_name = "+shadeName+", item_name = "+itemName+", mrp = "+mrp+"]";
    }
}



