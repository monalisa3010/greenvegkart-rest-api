package com.mycart.dao.impl;

import java.math.BigDecimal;
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
import org.springframework.transaction.annotation.Transactional;

import com.mycart.dao.CustomerDao;
import com.mycart.dao.mapper.UserAddressRowMapper;
import com.mycart.dao.mapper.UserDetailsRowMapper;
import com.mycart.exception.CartException;
import com.mycart.model.UserAddress;
import com.mycart.model.UserDetails;
import com.mycart.model.api.LoginRequest;
import com.mycart.model.api.UpdateAddressRequest;
import com.mycart.model.api.UserRegistrationRequest;
import com.zaxxer.hikari.pool.HikariProxyConnection;

import oracle.jdbc.OracleConnection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;

@Repository
public class CustomerDaoImpl implements CustomerDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerDaoImpl.class);

	@Autowired
	JdbcTemplate jdbcTemplate;

	SimpleJdbcCall simpleJdbcCallForRegisterUser;

	SimpleJdbcCall simpleJdbcCallForAddAddress;

	SimpleJdbcCall simpleJdbcCallForLogin;

	SimpleJdbcCall simpleJdbcCallForDeletingAddressById;
	SimpleJdbcCall simpleJdbcCallForUpdateAddress;

	@PostConstruct
	public void init() {
		simpleJdbcCallForRegisterUser = new SimpleJdbcCall(jdbcTemplate).withProcedureName("register_user")
				.declareParameters(new SqlParameter("i_user_details", OracleTypes.ARRAY),
						new SqlOutParameter("o_return_code", OracleTypes.NUMBER));

		simpleJdbcCallForAddAddress = new SimpleJdbcCall(jdbcTemplate).withProcedureName("add_address")
				.declareParameters(new SqlParameter("i_addresses", OracleTypes.ARRAY),
						new SqlOutParameter("o_return_code", OracleTypes.NUMBER),
						new SqlOutParameter("o_cur_addresses", OracleTypes.CURSOR, new UserAddressRowMapper()));

		simpleJdbcCallForLogin = new SimpleJdbcCall(jdbcTemplate).withProcedureName("validate_login").declareParameters(
				new SqlParameter("i_username", OracleTypes.VARCHAR),
				new SqlParameter("i_password", OracleTypes.VARCHAR),
				new SqlOutParameter("cur_user_details", OracleTypes.CURSOR, new UserDetailsRowMapper()),
				new SqlOutParameter("cur_address_list", OracleTypes.CURSOR, new UserAddressRowMapper()),
				new SqlOutParameter("o_return_code", OracleTypes.NUMBER),
				new SqlOutParameter("o_message", OracleTypes.VARCHAR));

		simpleJdbcCallForDeletingAddressById = new SimpleJdbcCall(jdbcTemplate)
				.withProcedureName("delete_address_by_id")
				.declareParameters(
						new SqlParameter("i_user_id", OracleTypes.NUMBER),
						new SqlParameter("i_address_id", OracleTypes.NUMBER),
						new SqlOutParameter("o_cur_addresses", OracleTypes.CURSOR,new UserAddressRowMapper() ));
	/*	update_address(
				i_user_id in number,
				i_address_id in number, 
				i_addr_line_1 in varchar2,
				i_addr_line_2 in varchar2,
				i_city in varchar2,
				i_state in varchar2,
				i_pin in number,
				o_cur_addresses out sys_refcursor*/
		
		simpleJdbcCallForUpdateAddress = new SimpleJdbcCall(jdbcTemplate)
				.withProcedureName("update_address")
				.declareParameters(
						new SqlParameter("i_user_id", OracleTypes.NUMBER),
						new SqlParameter("i_address_id", OracleTypes.NUMBER),
						new SqlParameter("i_addr_line_1", OracleTypes.VARCHAR),
						new SqlParameter("i_addr_line_2", OracleTypes.VARCHAR),
						new SqlParameter("i_city", OracleTypes.VARCHAR),
						new SqlParameter("i_state", OracleTypes.VARCHAR),
						new SqlParameter("i_pin", OracleTypes.NUMBER),
						new SqlOutParameter("o_cur_addresses", OracleTypes.CURSOR,new UserAddressRowMapper() ));
		LOGGER.info("SimpleJdbcCall template initialaized sucessfully");

	}

	@Override
	@Transactional
	public Map<String, Object> checkUserLogin(LoginRequest loginRequest) {
		LOGGER.info("Entering dao method: checkUserLogin()");
		Map<String, Object> map = simpleJdbcCallForLogin.execute(loginRequest.getUsername(),
				loginRequest.getPassword());
		LOGGER.info("----Leaving dao method :  checkUserLogin()");
		return map;
	}

	@Override
	@Transactional
	public int registerUser(UserRegistrationRequest userRegistrationRequest) throws CartException {
		LOGGER.info("Entering dao method: registerUser()");
		oracle.jdbc.OracleConnection connection = null;
		int statusCode = 0;
		try {
			connection = getOracleConnction();

			StructDescriptor structDescriptor_user = StructDescriptor.createDescriptor("O_USER_OBJ", connection);

			STRUCT[] user_struct_array = { createStructObjectForUserObject(connection, structDescriptor_user,
					userRegistrationRequest.getUserDetails(), userRegistrationRequest.getPassword()) };

			ArrayDescriptor arrayDescriptor = ArrayDescriptor.createDescriptor("T_USER_TAB", connection);
			ARRAY user_array_to_pass = new ARRAY(arrayDescriptor, connection, user_struct_array);

			Map<String, Object> map = simpleJdbcCallForRegisterUser.execute(user_array_to_pass);

			statusCode = ((BigDecimal) map.get("o_return_code")).intValue();

		} catch (SQLException sqlException) {
			LOGGER.error("Error while execcute stored procedure for register order in registerUser() ", "",
					sqlException.getCause());
			throw new CartException("Error occured...user not created..", "CART_0002", sqlException);
		} finally {
			try {
				jdbcTemplate.getDataSource().getConnection().close();
			} catch (SQLException sqlException) {
				LOGGER.error("Error while closing connection in registerUser()", "", sqlException.getCause());
			}
		}
		LOGGER.info("----Leaving dao method :  registerUser()");
		return statusCode;
	}

	@Override
	@Transactional
	public Map<String, Object> updateAddress(UpdateAddressRequest updateAddressRequest) throws CartException {
		LOGGER.info("Entering dao method: updateAddress()");
		oracle.jdbc.OracleConnection connection = null;
		Map<String, Object> addAddressResponseMap = null;
		try {
			connection = getOracleConnction();
			StructDescriptor structDescriptor_addr = StructDescriptor.createDescriptor("O_ADDRESS_OBJ", connection);
			// initialize array with size of address list inside userdetails object
			int count = updateAddressRequest.getUserAddressList().size();
			STRUCT[] address_struct_array = new STRUCT[count];
			for (UserAddress userAddress : updateAddressRequest.getUserAddressList()) {
				STRUCT userAddressSTRUCT = createStructObjectForAddressObject(connection, structDescriptor_addr,
						updateAddressRequest.getUserId(), userAddress);
				// now add this stuct object in address_struct_array
				int currentIndex = updateAddressRequest.getUserAddressList().indexOf(userAddress);
				address_struct_array[currentIndex] = userAddressSTRUCT;
			}
			ArrayDescriptor arrayDescriptor = ArrayDescriptor.createDescriptor("T_ADDRESS_TAB", connection);
			ARRAY address_array_to_pass = new ARRAY(arrayDescriptor, connection, address_struct_array);
			addAddressResponseMap = simpleJdbcCallForAddAddress.execute(address_array_to_pass);
			// statusCode = ((BigDecimal) map.get("o_return_code")).intValue();
			// List<UserAddress> userAddressses=
		} catch (SQLException sqlException) {
			LOGGER.error("Error while execcute stored procedure for register order in registerUser() ", "",
					sqlException.getCause());
			throw new CartException("Error ocurred...Address not updated..", "CART_0003", sqlException);
		} finally {
			try {
				jdbcTemplate.getDataSource().getConnection().close();
			} catch (SQLException sqlException) {
				LOGGER.error("Error while closing connection in registerUser()", "", sqlException.getCause());
			}
		}
		LOGGER.info("----Leaving dao method :  updateAddress()");
		return addAddressResponseMap;
	}

	private STRUCT createStructObjectForAddressObject(oracle.jdbc.OracleConnection connection,
			StructDescriptor structDescriptor_addr, int userid, UserAddress address) throws SQLException {
		STRUCT addr_struct = new STRUCT(structDescriptor_addr, connection,
				new Object[] { userid, address.getAddrLine1(), address.getAddrLine2(), address.getCity(),
						address.getState(), address.getPin() });
		return addr_struct;

	}

	private STRUCT createStructObjectForUserObject(oracle.jdbc.OracleConnection connection,
			StructDescriptor structDescriptor_user, UserDetails userdetails, String password) throws SQLException {
		STRUCT user_struct = new STRUCT(structDescriptor_user, connection,
				new Object[] { userdetails.getUsername(), userdetails.getFirstName(), userdetails.getLastName(),
						userdetails.getContact(), userdetails.getEmail(), password });
		return user_struct;

	}

	private OracleConnection getOracleConnction() throws SQLException {

		HikariProxyConnection hikariConnection = (HikariProxyConnection) jdbcTemplate.getDataSource().getConnection();
		OracleConnection connection = hikariConnection.unwrap(oracle.jdbc.OracleConnection.class);
		return connection;

	}

	@Override
	public Map<String, Object> deleteAddressById(int userid, int addressid) {
		LOGGER.info("---- dao method : entering deleteAddressById()");
		Map<String, Object> map =simpleJdbcCallForDeletingAddressById.execute(userid,addressid);
		LOGGER.info("---- dao method : leaving deleteAddressById()");
		return map;
	}
	
	@Override
	public Map<String, Object> updateAddress(int userid, UserAddress userAddress) {
		LOGGER.info("---- dao method : entering updateAddress()");
		Map<String, Object> map =simpleJdbcCallForUpdateAddress.execute(userid,
				userAddress.getAddressId(),userAddress.getAddrLine1(),
				userAddress.getAddrLine2(),userAddress.getCity(),userAddress.getState(),
				userAddress.getPin());
		LOGGER.info("---- dao method : leaving updateAddress()");
		return map;
	}
	
	
	public boolean isUserNameExists(String username) throws CartException{
	    String sql = "SELECT count(*) FROM USER_DETAILS WHERE USER_NAME = ?";
	    boolean result = false;
	    int count = jdbcTemplate.queryForObject(sql, new Object[] { username }, Integer.class);
	    if (count > 0) {
	      result = true;
	    }
	    return result;
	}

}
