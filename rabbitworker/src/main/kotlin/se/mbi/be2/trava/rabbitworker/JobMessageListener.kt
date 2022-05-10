package se.mbi.be2.trava.rabbitworker

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.rabbitmq.client.Channel
import org.springframework.amqp.core.Message
import org.springframework.amqp.rabbit.listener.adapter.AbstractAdaptableMessageListener

class JobMessageListener: AbstractAdaptableMessageListener() {

    data class JobInfo(val name: String, val jobNbr: Int)

    private val mapper = jacksonObjectMapper()

    override fun onMessage(message: Message, channel: Channel?) {
        val jobInfo = mapper.readValue(message.body, JobInfo::class.java)
        handleJob(jobInfo)
    }

    private fun handleJob(jobInfo: JobInfo) {
        println("Received message, pretending to work for a while: $jobInfo")
        Thread.sleep(2000)
        println("Done")
    }

}