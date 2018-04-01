package comparator;

import java.util.Comparator;

import com.hjn.tmall.pojo.Product;

public class GeneralSort implements Comparator<Product>{

	@Override
	public int compare(Product p1, Product p2) {
		// TODO Auto-generated method stub
		return p1.getReviewCount()*p1.getSaleCount()-p2.getReviewCount()*p2.getSaleCount();
	}

}
