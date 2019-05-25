package cn.ecit.store.domain;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


public class Cart {
	//代表购物车上的个数不确定的购物项，键：商品pid， 值：购物项
	private Map<String, CartItem> map = new HashMap<String, CartItem>();
	
	private double total = 0;
	
	//1,向购物车中添加购物项
	public void addCart(CartItem item) {
		//获得待添加的商品呢id
		String pid = item.getProduct().getPid();
		if(map.containsKey(pid)) {
			CartItem old = map.get(pid);
			old.setNum(old.getNum()+item.getNum());
			}else {
				map.put(pid, item);
			} 
	}
	
	//2,从购物车中移除单个购物项
	public void delCart(String pid) {
		map.remove(pid);
		
	}
	
	//3,清空购物车
	public void clearCart() {
		map.clear();
	}
	
	//计算总数
	public double getTotal() {
		total = 0;
		for(CartItem cartItem : map.values()) {
			total = total + cartItem.getSubTotal();
		}
		return total;
	}
	
	//为了方便遍历map中的所有的购物项，提供以下方法
	public Collection<CartItem> getCartItems(){
		return map.values();
	}
	
	public Map<String, CartItem> getMap(){
		return map;
	}
	
	public void setMap(Map<String, CartItem> map) {
		this.map = map;    
	}
	
	public void setTotal(double total) {
		this.total = total;
	}
	
}


