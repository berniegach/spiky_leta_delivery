package com.spikingacacia.spikyletadelivery.database;

public class Orders
{
    private int id;
    private String waiterEmail;
    private int itemId;
    private int orderNumber;
    private int orderStatus;
    private String deliveryMobile;
    private String deliveryInstructions;
    private String deliveryLocation;
    private String deliveryCharge;
    private String urlCodeStartDelivery;
    private String urlCodeEndDelivery;
    private String deliveryEmail;
    private String deliveryStartTime;
    private String deliveryEndTime;
    private String deliveryBooked;
    private String deliveryBookedTime;
    private String dateAdded;
    private String dateChanged;
    private String dateAddedLocal;
    private String item;
    private String size;
    private double price;
    private int sellerId;
    private String sellerEmail;
    private String sellerImageType;
    private String sellerLocation;
    private String sellerNames;
    private String waiterNames;
    private int orderFormat;
    public int tableNumber;
    private int preOrder;
    private String collectTime;
    private int orderType;
    private String mpesaMessage;
    private String restaurantDistance;
    private String restaurantToBuyerDistance;
    private String buyerId;
    private String buyerUsername;

    public Orders(int id, String waiterEmail, int itemId, int orderNumber, int orderStatus, String deliveryMobile, String deliveryInstructions, String deliveryLocation, String deliveryCharge,
                  String urlCodeStartDelivery, String urlCodeEndDelivery, String deliveryEmail, String deliveryStartTime, String deliveryEndTime, String deliveryBooked, String deliveryBookedTime,
                  String dateAdded, String dateChanged, String dateAddedLocal, String item, String size, double price, int sellerId, String sellerEmail,
                  String sellerImageType, String sellerLocation,
                  String sellerNames, String waiterNames, int orderFormat, int tableNumber, int preOrder, String collectTime, int order_type, String mpesaMessage,
                  String restaurantDistance, String restaurantToBuyerDistance, String buyerId, String buyerUsername)
    {
        this.id = id;
        this.waiterEmail = waiterEmail;
        this.itemId = itemId;
        this.orderNumber = orderNumber;
        this.orderStatus = orderStatus;
        this.deliveryMobile = deliveryMobile;
        this.deliveryInstructions = deliveryInstructions;
        this.deliveryLocation = deliveryLocation;
        this.deliveryCharge = deliveryCharge;
        this.urlCodeStartDelivery = urlCodeStartDelivery;
        this.urlCodeEndDelivery = urlCodeEndDelivery;
        this.deliveryEmail = deliveryEmail;
        this.deliveryStartTime = deliveryStartTime;
        this.deliveryEndTime = deliveryEndTime;
        this.deliveryBooked = deliveryBooked;
        this.deliveryBookedTime = deliveryBookedTime;
        this.dateAdded = dateAdded;
        this.dateChanged = dateChanged;
        this.dateAddedLocal = dateAddedLocal;
        this.item = item;
        this.size = size;
        this.price = price;
        this.sellerId = sellerId;
        this.sellerEmail = sellerEmail;
        this.sellerImageType = sellerImageType;
        this.sellerLocation = sellerLocation;
        this.sellerNames = sellerNames;
        this.waiterNames = waiterNames;
        this.orderFormat = orderFormat;
        this.tableNumber = tableNumber;
        this.preOrder = preOrder;
        this.collectTime = collectTime;
        this.orderType = order_type;
        this.mpesaMessage = mpesaMessage;
        this.restaurantDistance = restaurantDistance;
        this.restaurantToBuyerDistance = restaurantToBuyerDistance;
        this.buyerId = buyerId;
        this.buyerUsername = buyerUsername;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getWaiterEmail()
    {
        return waiterEmail;
    }

    public void setWaiterEmail(String waiterEmail)
    {
        this.waiterEmail = waiterEmail;
    }

    public int getItemId()
    {
        return itemId;
    }

    public void setItemId(int itemId)
    {
        this.itemId = itemId;
    }

    public int getOrderNumber()
    {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber)
    {
        this.orderNumber = orderNumber;
    }

    public int getOrderStatus()
    {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus)
    {
        this.orderStatus = orderStatus;
    }
    public String getDeliveryMobile()
    {
        return deliveryMobile;
    }

    public void setDeliveryMobile(String deliveryMobile)
    {
        this.deliveryMobile = deliveryMobile;
    }

    public String getDeliveryInstructions()
    {
        return deliveryInstructions;
    }

    public void setDeliveryInstructions(String deliveryInstructions)
    {
        this.deliveryInstructions = deliveryInstructions;
    }

    public String getDeliveryLocation()
    {
        return deliveryLocation;
    }

    public void setDeliveryLocation(String deliveryLocation)
    {
        this.deliveryLocation = deliveryLocation;
    }

    public String getDeliveryCharge()
    {
        return deliveryCharge;
    }

    public void setDeliveryCharge(String deliveryCharge)
    {
        this.deliveryCharge = deliveryCharge;
    }
    public String getUrlCodeStartDelivery()
    {
        return urlCodeStartDelivery;
    }

    public void setUrlCodeStartDelivery(String urlCodeStartDelivery)
    {
        this.urlCodeStartDelivery = urlCodeStartDelivery;
    }

    public String getUrlCodeEndDelivery()
    {
        return urlCodeEndDelivery;
    }

    public void setUrlCodeEndDelivery(String urlCodeEndDelivery)
    {
        this.urlCodeEndDelivery = urlCodeEndDelivery;
    }
    public String getDeliveryEmail()
    {
        return deliveryEmail;
    }

    public void setDeliveryEmail(String deliveryEmail)
    {
        this.deliveryEmail = deliveryEmail;
    }

    public String getDeliveryStartTime()
    {
        return deliveryStartTime;
    }

    public void setDeliveryStartTime(String deliveryStartTime)
    {
        this.deliveryStartTime = deliveryStartTime;
    }

    public String getDeliveryEndTime()
    {
        return deliveryEndTime;
    }

    public void setDeliveryEndTime(String deliveryEndTime)
    {
        this.deliveryEndTime = deliveryEndTime;
    }

    public String getDeliveryBooked()
    {
        return deliveryBooked;
    }

    public void setDeliveryBooked(String deliveryBooked)
    {
        this.deliveryBooked = deliveryBooked;
    }

    public String getDeliveryBookedTime()
    {
        return deliveryBookedTime;
    }

    public void setDeliveryBookedTime(String deliveryBookedTime)
    {
        this.deliveryBookedTime = deliveryBookedTime;
    }

    public String getDateAdded()
    {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded)
    {
        this.dateAdded = dateAdded;
    }

    public String getDateChanged()
    {
        return dateChanged;
    }

    public void setDateChanged(String dateChanged)
    {
        this.dateChanged = dateChanged;
    }
    public String getDateAddedLocal()
    {
        return dateAddedLocal;
    }

    public void setDateAddedLocal(String dateAddedLocal)
    {
        this.dateAddedLocal = dateAddedLocal;
    }

    public String getItem()
    {
        return item;
    }

    public void setItem(String item)
    {
        this.item = item;
    }

    public String getSize()
    {
        return size;
    }

    public void setSize(String size)
    {
        this.size = size;
    }

    public double getPrice()
    {
        return price;
    }

    public void setPrice(double price)
    {
        this.price = price;
    }

    public int getSellerId()
    {
        return sellerId;
    }

    public void setSellerId(int sellerId)
    {
        this.sellerId = sellerId;
    }
    public String getSellerEmail()
    {
        return sellerEmail;
    }

    public void setSellerEmail(String sellerEmail)
    {
        this.sellerEmail = sellerEmail;
    }
    public String getSellerImageType()
    {
        return sellerImageType;
    }

    public void setSellerImageType(String sellerImageType)
    {
        this.sellerImageType = sellerImageType;
    }
    public String getSellerLocation()
    {
        return sellerLocation;
    }

    public void setSellerLocation(String sellerLocation)
    {
        this.sellerLocation = sellerLocation;
    }
    public String getSellerNames()
    {
        return sellerNames;
    }

    public void setSellerNames(String sellerNames)
    {
        this.sellerNames = sellerNames;
    }

    public String getWaiterNames()
    {
        return waiterNames;
    }

    public void setWaiterNames(String waiterNames)
    {
        this.waiterNames = waiterNames;
    }

    public int getOrderFormat()
    {
        return orderFormat;
    }

    public void setOrderFormat(int orderFormat)
    {
        this.orderFormat = orderFormat;
    }

    public int getTableNumber()
    {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber)
    {
        this.tableNumber = tableNumber;
    }

    public int getPreOrder()
    {
        return preOrder;
    }

    public void setPreOrder(int preOrder)
    {
        this.preOrder = preOrder;
    }

    public String getCollectTime()
    {
        return collectTime;
    }

    public void setCollectTime(String collectTime)
    {
        this.collectTime = collectTime;
    }
    public int getOrderType()
    {
        return orderType;
    }

    public void setOrderType(int orderType)
    {
        this.orderType = orderType;
    }
    public String getMpesaMessage()
    {
        return mpesaMessage;
    }

    public void setMpesaMessage(String mpesaMessage)
    {
        this.mpesaMessage = mpesaMessage;
    }

    public String getRestaurantDistance()
    {
        return restaurantDistance;
    }

    public void setRestaurantDistance(String restaurantDistance)
    {
        this.restaurantDistance = restaurantDistance;
    }
    public String getRestaurantToBuyerDistance()
    {
        return restaurantToBuyerDistance;
    }

    public void setRestaurantToBuyerDistance(String restaurantToBuyerDistance)
    {
        this.restaurantToBuyerDistance = restaurantToBuyerDistance;
    }
    public String getBuyerId()
    {
        return buyerId;
    }

    public void setBuyerId(String buyerId)
    {
        this.buyerId = buyerId;
    }

    public String getBuyerUsername()
    {
        return buyerUsername;
    }

    public void setBuyerUsername(String buyerUsername)
    {
        this.buyerUsername = buyerUsername;
    }

}
