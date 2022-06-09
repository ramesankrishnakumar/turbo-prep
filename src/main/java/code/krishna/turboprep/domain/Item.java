package code.krishna.turboprep.domain;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class Item implements Serializable {
	
	private static final long serialVersionUID = -2190463611660817012L;
	private Integer id;
	private String name;

}
