//> using dep "org.json4s::json4s-native::4.0.7"

import org.json4s.{JArray, JObject, JString, JValue}
import org.json4s.native.{JsonMethods, JsonParser}

import scala.util.{Failure, Success, Try}

object JsonPatcher {

  private def patchField(
      inputJson: JObject,
      fieldName: String,
      newValue: String
  ): JObject = {
    inputJson merge JObject(fieldName -> JString(newValue))
  }

  private def patchField(
      inputJson: JValue,
      fieldName: String,
      newValue: String
  ): JValue = {
    inputJson match {
      case obj: JObject => obj merge JObject(fieldName -> JString(newValue))
      case arr: JArray =>
        JArray(arr.arr.map {
          case obj: JObject => patchField(obj, fieldName, newValue)
          case other        => other
        })
      case other => other
    }
  }

  private def parseJson(jsonString: String): Try[JValue] = {
    Try(JsonParser.parse(jsonString))
  }

  @main def runJsonParser(): Unit = {
    val jsonString =
      """[{"hello": "world"}, {"hello": "universe", "other": "value"}]"""

    parseJson(jsonString) match {
      case Success(jsonObject) =>
        val result = patchField(jsonObject, "hello", "patched")
        println(JsonMethods.pretty(JsonMethods.render(result)))
      case Failure(exception) =>
        println(s"Failed to parse JSON: ${exception.getMessage}")
    }
  }
}
