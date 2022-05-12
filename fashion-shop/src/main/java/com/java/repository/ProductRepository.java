package com.java.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.java.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>{

	// Hiển thị danh sách product mới nhất ở trang chủ LIMIT = 8
	@Query(value = "SELECT * FROM products ORDER BY enteredDate DESC limit 8", nativeQuery = true)
	public List<Product> listProduct8();
	
	// Hiển thị mỗi thể loại có bao nhiêu sản phẩm
	@Query(value = "SELECT c.categoryId,c.categoryName,\r\n"
			+ "COUNT(*) AS SoLuong\r\n"
			+ "FROM products b\r\n"
			+ "JOIN categories c ON b.categoryId = c.categoryId\r\n"
			+ "GROUP BY c.categoryName;" , nativeQuery = true)	
	public List<Object[]> listCategoryByProductName();

	// sản phẩm theo danh mục
	@Query(value = "SELECT * FROM products where categoryId = ?", nativeQuery = true)
	public List<Product> listProductByCategory(Integer id);

	// Sản phẩm theo danh nhãn hiệu
	@Query(value = "SELECT * FROM products where brandId = ?", nativeQuery = true)
	public List<Product> listProductByBrand(Integer id);
	
	// Gợi ý sản phẩm cùng thể loại
	@Query(value = "SELECT \r\n"
			+ "*FROM products AS p\r\n"
			+ "WHERE p.categoryId = ?" , nativeQuery = true)
	public List<Product> productsByCategory(Integer categoryId);
	
	// Search Product
	@Query(value = "SELECT * FROM products WHERE name LIKE %?1%", nativeQuery = true)
	public List<Product> searchProduct(String name);
	
	// sort sản phẩm theo giá tăng dần của từng danh mục (sort túi, kính, giày, balo theo giá thông qua categoryID (giá bằng nhau thì id tăng dần)
	@Query(value = "SELECT * FROM products WHERE products.categoryId = ? ORDER BY (products.price * (1 - products.discount / 100))", nativeQuery = true)
	public List<Product> listProductByPriceInCategoryASC(Integer categoryId);
	
	// sort sản phẩm theo giá giảm dần của từng danh mục (sort túi, kính, giày, balo theo giá thông qua categoryID (giá bằng nhau thì id tăng dần)
	@Query(value = "SELECT * FROM products WHERE categoryId = ? ORDER BY (products.price * (1 - products.discount / 100)) DESC", nativeQuery = true)
	public List<Product> listProductByPriceInCategoryDESC(Integer categoryId);
		
	// sort sản phẩm theo giá tăng dần
	@Query(value = "SELECT * FROM products ORDER BY (products.price * (1 - products.discount / 100))", nativeQuery = true)
	public List<Product> listProductByPriceASC();
	
	// sort sản phẩm theo giá giảm dần
	@Query(value = "SELECT * FROM products ORDER BY (products.price * (1 - products.discount / 100)) DESC", nativeQuery = true)
	public List<Product> listProductByPriceDESC();
	
	// sort sản phẩm theo giá tăng dần nằm trong khoảng [a, b]
	@Query(value = "SELECT * FROM products WHERE (products.price * (1 - products.discount / 100)) >= ? and (products.price * (1 - products.discount / 100)) <= ? ORDER BY (products.price * (1 - products.discount / 100))", nativeQuery = true)
	public List<Product> listProductByPriceBetweenta_b_ASC(Integer minPrice, Integer maxPrice);
	
	// sort sản phẩm theo giá giảm dần nằm trong khoảng [a, b]
	@Query(value = "SELECT * FROM products WHERE (products.price * (1 - products.discount / 100)) >= ? and (products.price * (1 - products.discount / 100)) <= ? ORDER BY (products.price * (1 - products.discount / 100)) DESC", nativeQuery = true)
	public List<Product> listProductByPriceBetweenta_b_DESC(Integer minPrice, Integer maxPrice);
	
	// chỉ lấy sản phẩm nằm có giá nằm trong [a, b]
	@Query(value = "SELECT * FROM products WHERE (products.price * (1 - products.discount / 100)) >= ? and (products.price * (1 - products.discount / 100)) <= ?", nativeQuery = true)
	public List<Product> listProductByPriceBetweenta_b(Integer minPrice, Integer maxPrice);
	
	// chỉ lấy sản phẩm nằm có giá nằm trong [a, b] theo categoryId
	@Query(value = "SELECT * FROM products WHERE products.categoryId = ? and (products.price * (1 - products.discount / 100)) >= ? and (products.price * (1 - products.discount / 100)) <= ?", nativeQuery = true)
	public List<Product> listProductByPriceInCategoryBetweenta_b(Integer categoryId, Integer minPrice, Integer maxPrice);
	
	// chỉ lấy sản phẩm nằm có giá nằm trong [a, b] theo categoryId giá tăng dần
	@Query(value = "SELECT * FROM products WHERE products.categoryId = ? and (products.price * (1 - products.discount / 100)) >= ? and (products.price * (1 - products.discount / 100)) <= ? ORDER BY (products.price * (1 - products.discount / 100))", nativeQuery = true)
	public List<Product> listProductByPriceInCategoryBetweenta_b_ASC(Integer categoryId, Integer minPrice, Integer maxPrice);
	// chỉ lấy sản phẩm nằm có giá nằm trong [a, b] theo categoryId giá giảm dần
	@Query(value = "SELECT * FROM products WHERE products.categoryId = ? and (products.price * (1 - products.discount / 100)) >= ? and (products.price * (1 - products.discount / 100)) <= ? ORDER BY (products.price * (1 - products.discount / 100)) DESC", nativeQuery = true)
	public List<Product> listProductByPriceInCategoryBetweenta_b_DESC(Integer categoryId, Integer minPrice, Integer maxPrice);
}
