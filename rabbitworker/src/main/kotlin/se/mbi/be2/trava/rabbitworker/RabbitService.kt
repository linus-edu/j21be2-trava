package se.mbi.be2.trava.rabbitworker

import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.TopicExchange
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Service

@Service
class RabbitService {
    val exchangeName = "trava-exchange"
    val queueName = "mailservice-jobs"
    val topic = "mailservice.job"

    @Bean
    fun queue(): Queue = Queue(queueName, true)

    @Bean
    fun exchange() = TopicExchange(exchangeName)

    @Bean
    fun binding(queue: Queue, exchange: TopicExchange): Binding =
        BindingBuilder.bind(queue).to(exchange).with(topic)

    @Bean
    fun container(
        connectionFactory: ConnectionFactory
    ): SimpleMessageListenerContainer {
        val container = SimpleMessageListenerContainer()
        container.connectionFactory = connectionFactory
        container.setQueueNames(queueName)
        container.setMessageListener(JobMessageListener())
        container.setDefaultRequeueRejected(false)

//        container.configureAndBindQueue()

        return container
    }

}