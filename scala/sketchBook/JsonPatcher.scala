//> using scala 3
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

  /** Entry point for the JSON patching CLI tool.
    *
    * This function parses a JSON string, updates the value of the specified
    * field in each JSON object, and prints the resulting JSON.
    *
    * Example {{{ $ scala-cli JsonPatcher.scala -- '[{"hello": "world"},
    * {"hello": "universe", "other": "value"}]' "hello" "modified" }
    *
    * [{ "hello":"modified" },{ "hello":"modified", "other":"value" }]
    *
    * }}}
    *
    * @param input
    *   A JSON array string (each element should be a JSON object).
    * @param key
    *   The field name to patch in each object.
    * @param patchedValue
    *   The new value to assign to the specified field.
    */
  @main def runJsonPatcher(
      input: String,
      key: String,
      patchedValue: String
  ): Unit = {
    parseJson(input) match {
      case Success(jsonObject) =>
        val result = patchField(jsonObject, key, patchedValue)
        println(JsonMethods.pretty(JsonMethods.render(result)))
      case Failure(exception) =>
        println(s"Failed to parse JSON: ${exception.getMessage}")
    }
  }
}
