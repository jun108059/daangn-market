package me.youngjun.daangnmarket.common.repository

import me.youngjun.daangnmarket.common.domain.Message
import org.springframework.data.jpa.repository.JpaRepository

interface MessageRepository : JpaRepository<Message, Long>
