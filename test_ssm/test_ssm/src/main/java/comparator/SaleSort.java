package comparator;

import java.util.Comparator;

import com.hjn.tmall.pojo.Product;

public class SaleSort implements Comparator<Product> {

	@Override
	public int compare(Product p1, Product p2) {
		// TODO Auto-generated method stub
		return p1.getSaleCount()-p2.getSaleCount();
	}

}
