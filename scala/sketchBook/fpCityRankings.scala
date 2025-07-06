//> using scala 3
//> using dep "org.typelevel::cats-effect::3.6.1"
//> using dep "co.fs2::fs2-core::3.12.0"

import cats.effect.IO
import fs2.Stream

import cats.effect.unsafe.implicits.global

@main def runFpCityRankings(): Unit = {
  fpCityRankings.run().unsafeRunSync()
}

object fpCityRankings {
  private object model {
    opaque type City = String
    object City {
      def apply(name: String): City = name
      extension (city: City) def name: String = city
    }
    case class CityStats(city: City, checkIns: Int)
  }

  import model._

  //  private val checkIns: Stream[IO, City] =
  //    Stream(
  //      City("Sydney"),
  //      City("Sydney"),
  //      City("Cape Town"),
  //      City("Singapore"),
  //      City("Cape Town"),
  //      City("Sydney")
  //    ).covary[IO]

  private val checkIns: Stream[IO, City] =
    Stream(
      City("Sydney"),
      City("Dublin"),
      City("Cape Town"),
      City("Lima"),
      City("Singapore")
    )
      .repeatN(100_000)
      .append(Stream.range(0, 100_000).map(i => City(s"City $i")))
      .append(Stream(City("Sydney"), City("Sydney"), City("Lima")))
      .covary[IO]

  private def topCities(cityCheckIns: Map[City, Int]): List[CityStats] = {
    cityCheckIns.toList
      .map { case (city, checkIns) =>
        CityStats(city, checkIns)
      }
      .sortBy(_.checkIns)
      .reverse
      .take(3)
  }

  private def processCheckIns(checkIns: Stream[IO, City]): IO[Unit] = {
    checkIns
      .scan(Map.empty[City, Int])((cityCheckIns, city) =>
        cityCheckIns.updatedWith(city)(_.map(_ + 1).orElse(Some(1)))
      )
      .chunkN(100_000)
      .map(_.last)
      .unNone
      .map(topCities)
      .foreach(IO.println)
      .compile
      .drain
  }

  def run(): IO[Unit] = {
    processCheckIns(checkIns)
  }
}
