package com.retroexchanges.rest.model;

import javax.persistence.*;
import java.io.Serializable;
import com.retroexchanges.rest.json.UserSecure;


/**
 * Created by fjmartincubino
 */
@Entity
@Table(name = "favorites")
public class Favorite implements Serializable {
	
	private static final long serialVersionUID = -6437397199015504697L;

	public Favorite() {
		
	}
	
	public Favorite(String email, Long productId) {
		super();
		this.userProduct = new FavoritePK(email,productId);
	}
	
	public FavoritePK getUserProduct() {
	    return userProduct;
    }
	
	@EmbeddedId
	private FavoritePK userProduct;
		
    @ManyToOne()
    @MapsId("user")
    @JoinColumn(name="email")
    private User user;
	
    @ManyToOne()
    @MapsId("product")
    @JoinColumn(name="productId")
    private Product product;


	public void setUser(User user) {
		this.user = user;
	}

	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
}
