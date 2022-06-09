package code.krishna.turboprep.web;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import code.krishna.turboprep.domain.Item;
import code.krishna.turboprep.exception.Response;
import code.krishna.turboprep.service.ItemService;
import lombok.AllArgsConstructor;

@RestController @RequestMapping("/v1/item") @AllArgsConstructor
public class ItemController {
	
	private ItemService itemSvc;
	
	@GetMapping("/{id}")
	public Response<Item> getItemById(@PathVariable Integer id) {
		return generateResponse(itemSvc.getItem(id));
	}
	
	@PostMapping
	public Response<Item> createItem(@RequestParam String name) {
		return generateResponse(itemSvc.createItem(name));
	}
	
	public static <T> Response<T> generateResponse(T obj) {
		Response<T> rsp = new Response<>();
		rsp.setValue(obj);
		return rsp;
	}
	
}
