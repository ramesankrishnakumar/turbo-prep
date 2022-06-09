package code.krishna.turboprep.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.EmptySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import code.krishna.turboprep.domain.Item;

@Repository
public class ItemDao {
	
	@Autowired private NamedParameterJdbcTemplate template;
	
	
	public Item getItemById(Integer id) {
		Map<String, Object> params = new HashMap<>();
		params.put("id", id);
		List<Item> results =  template.query("select id, name from product where id = :id", params, getItemRowMapper());
		return CollectionUtils.isEmpty(results) ? null : results.get(0);
	}
	
	public Item createItem(String itemName) {
		//Integer newItemId = template.queryForObject("select product_id_sequence.nextval from dual ", new EmptySqlParameterSource(), Integer.class);
		Item newItem = new Item();
		newItem.setName(itemName);
		KeyHolder key =  new GeneratedKeyHolder();
		template.update("insert into product (name) values (:name )", new BeanPropertySqlParameterSource(newItem) , key);
		newItem.setId(key.getKey().intValue());
		return newItem;
	}
	
	private RowMapper<Item> getItemRowMapper() {
		return (rs, num) -> {
			Item item = new Item();
			item.setId(rs.getInt("id"));
			item.setName(rs.getString("name"));
			return item;
		};
	}

}
