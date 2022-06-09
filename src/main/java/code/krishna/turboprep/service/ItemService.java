package code.krishna.turboprep.service;

import static java.util.Objects.isNull;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.utils.SerializationUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import code.krishna.turboprep.dao.ItemDao;
import code.krishna.turboprep.domain.Item;
import code.krishna.turboprep.exception.ItemNotFoundException;
import lombok.AllArgsConstructor;

@Service @AllArgsConstructor
public class ItemService {
	
	private ItemDao itemDao;
	private RedisTemplate<Object, Object> template;
	private RabbitTemplate publisherTemplate;
	
	public Item getItem(Integer itemId) {
		Object val = template.opsForValue().get(itemId);
		if(null != val) {
			System.out.println("Item returned from cache");
			return (Item)val;
		}
		Item item = itemDao.getItemById(itemId);
		if(isNull(item)) throw new ItemNotFoundException("Invalid item / code");
		template.opsForValue().set(item.getId(), item);
		return item;
	}
	
	
	public Item createItem(String itemName) {
		Item newItm = itemDao.createItem(itemName);
		template.opsForValue().set(newItm.getId(), newItm);
		Message msg = MessageBuilder.withBody(SerializationUtils.serialize(newItm))
		.setAppId("turbo-prep").build();
		publisherTemplate.send("create.event", msg);
		return newItm;
	}

}
