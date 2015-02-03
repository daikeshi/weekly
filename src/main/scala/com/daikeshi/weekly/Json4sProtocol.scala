package com.daikeshi.weekly

import org.json4s.{DefaultFormats, Formats}
import spray.httpx.Json4sJacksonSupport

object Json4sProtocol extends Json4sJacksonSupport {
  implicit def json4sJacksonFormats: Formats = DefaultFormats
}
