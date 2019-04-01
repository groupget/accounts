package com.agh.groupget.accounts.infrastructure;

import com.agh.groupget.accounts.domain.exception.RabbitException;
import com.agh.groupget.accounts.context.UserPublisher;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

//@Configuration
class PublisherConfiguration {

    private static final String EXCHANGE = "users.delete";

    private final Channel channel;

    PublisherConfiguration(ConnectionFactory factory) throws Exception {
        Connection connection = factory.newConnection();
        channel = connection.createChannel();
    }

    @Bean
    UserPublisher userPublisher() {
        return new RabbitPublisher(channel);
    }

    private static final class RabbitPublisher implements UserPublisher {

        private static final Logger LOGGER = LoggerFactory.getLogger(RabbitPublisher.class);

        private final Channel channel;

        private RabbitPublisher(Channel channel) {
            this.channel = channel;
        }

        @Override
        public void publishDeletion(String username) {
            try {
                channel.basicPublish(EXCHANGE, "", null, username.getBytes());
            } catch (IOException e) {
                LOGGER.error(e.getMessage());
                throw new RabbitException(e.getMessage());
            }
        }
    }
}
