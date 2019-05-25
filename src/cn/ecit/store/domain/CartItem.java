package cn.ecit.store.domain;

public class CartItem {
	
	private Product product;
	private int num;
	
	private double subTotal;	//小计
	
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	
	public double getSubTotal() {
		return product.getShop_price()*num;
	}
	
}
