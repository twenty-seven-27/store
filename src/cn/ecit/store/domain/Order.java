package cn.ecit.store.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order {
	private String oid;
	private Date ordertime;	//下单时间
	private int state;	//状态：1，下单为付款，2，付款未发货，3，发货未收货，4，收货结束
	
	private double total;
	
	private String address;
	private String name;
	private String telephone;
	
	//user可以携带更多的数据
	//面向对象角度：对象关联对象
	private User user;
	
	//当前订单项有多少订单项
	private List<OrderItem> list = new ArrayList<OrderItem>();

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public Date getOrderTime() {
		return ordertime;
	}

	public void setOrderTime(Date orderTime) {
		this.ordertime = orderTime;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<OrderItem> getList() {
		return list;
	}

	public void setList(List<OrderItem> list) {
		this.list = list;
	}
	
	
}
