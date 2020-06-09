package com.mycart.dao.impl;

import java.sql.SQLException;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.mycart.dao.OrderDao;
import com.mycart.dao.mapper.OrderItemRowMapper;
import com.mycart.dao.mapper.UserAddressRowMapper;
import com.mycart.exception.CartException;
import com.mycart.model.OrderItem;
import com.mycart.model.api.CreateOrderRequest;
import com.mycart.model.api.LoginRequest;
import com.zaxxer.hikari.pool.HikariProxyConnection;

import oracle.jdbc.OracleConnection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;

@Repository
public class OrderDaoImpl implements OrderDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderDaoImpl.class);

	@Autowired
	JdbcTemplate jdbcTemplate;

	SimpleJdbcCall simpleJdbcCall;

	SimpleJdbcCall simpleJdbcCallForFetchOrderDetails;

	/*
	 * create or replace PROCEDURE create_order ( i_cart_items IN T_CART_ITEM_TAB,
	 * i_user_id IN number, i_delivery_address_id in number, i_payment_type
	 * varchar2, i_total_amount IN NUMBER, o_order_id OUT VARCHAR2 )
	 * 
	 * create or replace type o_cart_item_obj as object ( product_id varchar2(20),
	 * quantity number )
	 * 
	 * create or replace type t_cart_item_tab as table of o_cart_item_obj
	 */
	@PostConstruct
	public void init() {
		simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate).withProcedureName("create_order").declareParameters(
				new SqlParameter("i_cart_items", OracleTypes.ARRAY), new SqlParameter("i_user_id", OracleTypes.NUMBER),
				new SqlParameter("i_delivery_address_id", OracleTypes.NUMBER),
				new SqlParameter("i_payment_type", OracleTypes.VARCHAR),
				new SqlParameter("i_total_amount", OracleTypes.NUMBER),
				new SqlOutParameter("o_order_id", OracleTypes.VARCHAR));

		simpleJdbcCallForFetchOrderDetails = new SimpleJdbcCall(jdbcTemplate).withProcedureName("GET_ORDER_DETAILS").declareParameters(
				new SqlParameter("i_order_id", OracleTypes.VARCHAR),
				new SqlOutParameter("o_addr_cur", OracleTypes.CURSOR, new UserAddressRowMapper()),
				new SqlOutParameter("o_items_cur", OracleTypes.CURSOR, new OrderItemRowMapper()),
				new SqlOutParameter("o_expected_delivery", OracleTypes.DATE),
				new SqlOutParameter("o_order_status", OracleTypes.VARCHAR));

		LOGGER.info("SimpleJdbcCall template initialaized sucessfully");
	}

	public String createOrder(CreateOrderRequest createOrderRequest) throws CartException {
		LOGGER.info("Entering dao method: createOrder()");
		oracle.jdbc.OracleConnection connection = null;
		Map<String, Object> map = null;
		try {
			connection = getOracleConnction();
			StructDescriptor structDescriptor_order_item = StructDescriptor.createDescriptor("O_CART_ITEM_OBJ",
					connection);
			int count = createOrderRequest.getOrderItems().size();
			STRUCT[] order_item_struct_array = new STRUCT[count];
			for (OrderItem orderItem : createOrderRequest.getOrderItems()) {
				STRUCT orderItemSTRUCT = createStructObjectForOrderItemObject(connection, structDescriptor_order_item,
						orderItem);
				int currentIndex = createOrderRequest.getOrderItems().indexOf(orderItem);
				order_item_struct_array[currentIndex] = orderItemSTRUCT;
			}
			ArrayDescriptor arrayDescriptor = ArrayDescriptor.createDescriptor("T_CART_ITEM_TAB", connection);
			ARRAY order_item_array_to_pass = new ARRAY(arrayDescriptor, connection, order_item_struct_array);
			map = simpleJdbcCall.execute(order_item_array_to_pass, createOrderRequest.getCustomerId(),
					createOrderRequest.getDeliveryAddressId(), createOrderRequest.getPaymentType(),
					createOrderRequest.getTotalAmount());
		} catch (SQLException sqlException) {
			LOGGER.error("Error while execcute stored procedure for create order OrderDaoImpl ", "",
					sqlException.getCause());
			throw new CartException("Order not created", "CART_0001", sqlException);
		} finally {
			try {
				connection.close();
			} catch (SQLException sqlException) {
				LOGGER.error("Error while closing connection", "", sqlException.getCause());
			}
		}
		LOGGER.info("Leaving dao method: createOrder()");
		LOGGER.info("---Created Order id is: -----" + (String) map.get("o_order_id"));
		return (String) map.get("o_order_id");
	}
	
	
	public Map<String, Object> fetchOrderDetails(String orderid) throws CartException{
		LOGGER.info("Entering dao method: checkUserLogin()");
		Map<String, Object> map = simpleJdbcCallForFetchOrderDetails.execute(orderid);
		LOGGER.info("----Leaving dao method :  checkUserLogin()");
		return map;
	}
	
	

	private STRUCT createStructObjectForOrderItemObject(oracle.jdbc.OracleConnection connection,
			StructDescriptor structDescriptor_order_item, OrderItem orderItem) throws SQLException {
		STRUCT order_item_struct = new STRUCT(structDescriptor_order_item, connection,
				new Object[] { orderItem.getProductId(), orderItem.getQuantity() });
		return order_item_struct;

	}

	private OracleConnection getOracleConnction() throws SQLException {
		HikariProxyConnection hikariConnection = (HikariProxyConnection) jdbcTemplate.getDataSource().getConnection();
		OracleConnection connection = hikariConnection.unwrap(oracle.jdbc.OracleConnection.class);
		return connection;

	}
}
