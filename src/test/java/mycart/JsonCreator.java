package mycart;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycart.model.OrderItem;
import com.mycart.model.UserAddress;
import com.mycart.model.UserDetails;
import com.mycart.model.api.CreateOrderRequest;
import com.mycart.model.api.LoginRequest;
import com.mycart.model.api.UpdateAddressRequest;
import com.mycart.model.api.UserRegistrationRequest;

public class JsonCreator {

	public static void main(String[] args) throws JsonProcessingException {

		ObjectMapper mapper = new ObjectMapper();
		System.out.println("----------------------LOGIN REQUEST----------------------");
		LoginRequest loginRequest = new LoginRequest("danny", "danny");
		System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(loginRequest));

		System.out.println("----------------------UPDATE ADDRESS REQUEST----------------------");
		UpdateAddressRequest addressRequest = new UpdateAddressRequest();
		List<UserAddress> userAddressList = new ArrayList<UserAddress>();

		UserAddress address1 = new UserAddress("Meena wood", "Flat 3b, Block C", "Kolkata", "WB", 700136);
		UserAddress address2 = new UserAddress("Meena Glory", "Flat 1C, Block A", "Kolkata", "WB", 700136);

		userAddressList.add(address1);
		userAddressList.add(address2);
		addressRequest.setUserId(8);
		addressRequest.setUserAddressList(userAddressList);

		System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(addressRequest));

		System.out.println("----------------------UserRegistrationRequest REQUEST----------------------");

		UserDetails userDetail = new UserDetails("abhipicku", "Abhishek", "Dutta", Long.valueOf("9733892131"),
				"abhi@gmail.com");

		UserRegistrationRequest userRegistrationRequest = new UserRegistrationRequest(userDetail, "password");

		System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(userRegistrationRequest));

		System.out.println("----------------------CreateOrderRequest REQUEST----------------------");
		List<OrderItem> orderItems = new ArrayList<>();
		OrderItem orderItem1 = new OrderItem(2, 2);
		OrderItem orderItem2 = new OrderItem(1, 3);
		orderItems.add(orderItem1);
		orderItems.add(orderItem2);
		CreateOrderRequest createOrderRequest = new CreateOrderRequest(8, 3, "card", new BigDecimal(200), orderItems);
		System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(createOrderRequest));
	}

}
