package com.daikeshi

import org.json4s.{Formats, DefaultFormats}
import spray.httpx.Json4sSupport

object Json4sProtocol extends Json4sSupport {
  implicit def json4sFormats: Formats = DefaultFormats
}
