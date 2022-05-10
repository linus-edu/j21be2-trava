package se.mbi.be2.trava.rabbitworker

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class RabbitworkerApplication

fun main(args: Array<String>) {
	runApplication<RabbitworkerApplication>(*args)
}
