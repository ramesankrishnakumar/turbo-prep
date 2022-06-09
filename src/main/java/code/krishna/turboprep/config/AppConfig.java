package code.krishna.turboprep.config;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

import code.krishna.turboprep.messaging.CreateMessageListener;

@Configuration
public class AppConfig {
	
	@Bean
	public JedisConnectionFactory factory() {
		RedisStandaloneConfiguration config = new RedisStandaloneConfiguration("localhost", 6379);
		config.setPassword(RedisPassword.of("guest"));
		return new JedisConnectionFactory(config);
	}
	
	@Bean
	public CachingConnectionFactory publisherConnectionFactory() {
		CachingConnectionFactory factory = new CachingConnectionFactory("localhost");
		factory.setPassword("guest");
		factory.setPort(5672);
		factory.setConnectionNameStrategy((cf) -> "turbo-prep-publisher");
		factory.setCloseTimeout(10000);
		return factory;
	}
	
	@Bean
	public CachingConnectionFactory listenerConnectionFactory() {
		CachingConnectionFactory factory = new CachingConnectionFactory("localhost");
		factory.setPassword("guest");
		factory.setPort(5672);
		factory.setConnectionNameStrategy((cf) -> "turbo-prep-listener");
		factory.setCloseTimeout(10000);
		return factory;
	}
	
	
	@Bean
	public RabbitAdmin rabbitAdmin(ConnectionFactory publisherConnectionFactory) {
		RabbitAdmin admin = new RabbitAdmin(publisherConnectionFactory);
		admin.setAutoStartup(true);
		return admin;
	}
	
	@Bean
	public Exchange itemExchange() {
		return ExchangeBuilder.directExchange("product.exchange")
				.durable(true)
				.build();
	}
	
	@Bean
	public Queue createServiceQueue() {
		return QueueBuilder.durable("create.service.queue")
				.build();
	}
	
	@Bean
	public Binding createServiceBinding() {
		return BindingBuilder.bind(createServiceQueue())
				.to(itemExchange())
				.with("create.event")
				.noargs();
	}
	
	@Bean
	public SimpleMessageListenerContainer createListener(CreateMessageListener listener) {
		SimpleMessageListenerContainer ctnr = new SimpleMessageListenerContainer(listenerConnectionFactory());
		ctnr.addQueues(createServiceQueue());
		ctnr.setAcknowledgeMode(AcknowledgeMode.AUTO);
		ctnr.setExclusive(true);
		ctnr.setMessageListener(listener);
		return ctnr;
	}
	
	@Bean
	public RabbitTemplate publisherTemplate(ConnectionFactory publisherConnectionFactory) {
		RabbitTemplate publisherTemplate = new RabbitTemplate(publisherConnectionFactory);
		publisherTemplate.setExchange(itemExchange().getName());
		return publisherTemplate;
	}

}
