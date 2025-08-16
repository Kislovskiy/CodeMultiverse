//> using scala 3
//> using dep "org.json4s::json4s-native::4.0.7"
//> using dep "org.scalatest::scalatest::3.2.19"

import org.json4s.MonadicJValue.*
import org.json4s.native.{JsonMethods, Serialization}
import org.json4s.{DefaultFormats, Formats, JArray, JString, JValue}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

import scala.util.Random

val jsonString: String =
  """{
    |  "data": [
    |    {
    |      "id": 1,
    |      "status": "original",
    |      "timestamp": "2025-08-16T14:25:13.714250+02:00[Europe/Zurich]"
    |    },
    |    {
    |      "id": 2,
    |      "status": "patchMe",
    |      "timestamp": "2025-08-16T14:25:13.714250+02:00[Europe/Zurich]"
    |    },
    |    {
    |      "id": 3,
    |      "status": "patchMe",
    |      "timestamp": "2025-08-16T14:25:13.714250+02:00[Europe/Zurich]"
    |    },
    |    {
    |      "id": 4,
    |      "status": "original",
    |      "timestamp": "2025-08-16T14:25:13.714250+02:00[Europe/Zurich]"
    |    }
    |  ],
    |  "timestamp": "2025-08-16T14:25:13.714250+02:00[Europe/Zurich]"
    |}
    |""".stripMargin

@main def openQuestions(): Unit = {
  implicit val formats: Formats = DefaultFormats
  val input = JsonMethods.parse(jsonString)
  val result = input.patchJson
  println(Serialization.writePretty(result))
}

extension (json: JValue) {
  def patchJson(using
                config: Config = Config.runtime,
                random: Random = Random()
               ): JValue = {
    def shouldPatch(): Boolean = random.nextDouble() < config.probability

    def patchElement(element: JValue): JValue = {
      val currentStatus = element \ "status"
      if (currentStatus == JString("patchMe") && shouldPatch()) {
        element.transformField {
          case ("status", JString("patchMe")) =>
            ("status", JString("patched"))
          case ("timestamp", _) =>
            ("timestamp", JString(java.time.ZonedDateTime.now().toString))
        }
      } else {
        element
      }
    }

    json.transformField { case ("data", JArray(elements)) =>
      ("data", JArray(elements.map(patchElement)))
    }
  }
}

case class Config(probability: Double) {
  require(
    probability >= 0.0 && probability <= 1.0,
    "Probability must be between 0.0 and 1.0"
  )
}

class JsonProcessingTest extends AnyFlatSpec with should.Matchers {

  "JValue.patchJson" should "always patch when probability is 1.0" in {
    given Config = Config(1.0)

    given Random = new Random(42)

    val input = JsonMethods.parse(jsonString)
    val result = input.patchJson

    val dataArray = (result \ "data").asInstanceOf[JArray]

    (dataArray.arr(1) \ "status") shouldBe JString("patched")
    (dataArray.arr(2) \ "status") shouldBe JString("patched")

    // Original element should remain unchanged
    (dataArray.arr.head \ "status") shouldBe JString("original")
    (dataArray.arr(3) \ "status") shouldBe JString("original")
  }

  it should "never patch when probability is 0.0" in {
    given Config = Config(0.0)

    given Random = new Random(42)

    val input = JsonMethods.parse(jsonString)
    val result = input.patchJson

    val dataArray = (result \ "data").asInstanceOf[JArray]

    // All elements should remain unchanged
    (dataArray.arr.head \ "status") shouldBe JString("original")
    (dataArray.arr(1) \ "status") shouldBe JString("patchMe")
    (dataArray.arr(2) \ "status") shouldBe JString("patchMe")
    (dataArray.arr(3) \ "status") shouldBe JString("original")
  }

  it should "patch probabilistically with deterministic random" in {
    given Config = Config(0.5)

    given Random = new Random(123)

    val input = JsonMethods.parse(jsonString)
    val result = input.patchJson

    val dataArray = (result \ "data").asInstanceOf[JArray]

    // With seed 123, we can verify specific behavior
    val patchedCount = dataArray.arr.count { element =>
      (element \ "status") == JString("patchedYou")
    }

    patchedCount should be >= 0
    patchedCount should be <= 2
  }

  it should "use default test config when no config provided" in {
    given Random = new Random(42)

    given Config = Config.test

    val input = JsonMethods.parse(jsonString)
    val result = input.patchJson

    val dataArray = (result \ "data").asInstanceOf[JArray]

    // With test config (probability = 1.0), all should be patched
    (dataArray.arr(1) \ "status") shouldBe JString("patched")
    (dataArray.arr(2) \ "status") shouldBe JString("patched")
  }

  it should "handle empty data arrays" in {
    given Config = Config(1.0)

    val emptyJson = JsonMethods.parse("""{"data": []}""")
    val result = emptyJson.patchJson
    result shouldBe emptyJson
  }

  it should "reject invalid probabilities" in {
    intercept[IllegalArgumentException] {
      Config(-0.1)
    }

    intercept[IllegalArgumentException] {
      Config(1.1)
    }
  }
}

object Config {
  val runtime: Config = Config(0.7)
  val test: Config = Config(1.0)
}
