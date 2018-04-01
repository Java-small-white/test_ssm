package comparator;

import java.util.Comparator;

import com.hjn.tmall.pojo.Product;

public class PriceSort implements Comparator<Product>{

	@Override
	public int compare(Product p1, Product p2) {
		// TODO Auto-generated method stub
		return (int) (p1.getPromotePrice()-p2.getPromotePrice());
	}

}
